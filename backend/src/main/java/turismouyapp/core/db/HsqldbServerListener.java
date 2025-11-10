package turismouyapp.core.db;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.hsqldb.server.Server;

import java.util.logging.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HsqldbServerListener implements LifecycleListener {
  private Server server;

  @Override
  public void lifecycleEvent(LifecycleEvent event) {
    if (Lifecycle.BEFORE_START_EVENT.equals(event.getType())) {
      startIfNeeded();
    } else if (Lifecycle.AFTER_STOP_EVENT.equals(event.getType())) {
      stopIfStarted();
    }
  }

  private void startIfNeeded() {
    int port = Integer.parseInt(System.getProperty("db.port", "9001"));
    String name = System.getProperty("db.name", "turismoUyDB");
    String path = System.getProperty("db.path", "./data/db/turismoUyDB");

    if (isPortOpen("127.0.0.1", port, 300)) return;

    server = new Server();
    server.setDatabaseName(0, name);
    server.setDatabasePath(0, "file:" + path);
    server.setPort(port);
    server.setSilent(false);
    server.setTrace(false);
    server.start();
    Logger.getLogger(HsqldbServerListener.class.getName()).info("[DB] HSQLDB iniciado por Tomcat en puerto " + port);
    
    // Agregar shutdown hook como respaldo en caso de que Tomcat no se detenga correctamente
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      stopIfStarted();
    }));
    
    // Configurar checkpoint automático para mejor persistencia
    try {
      java.sql.Connection conn = java.sql.DriverManager.getConnection(
        "jdbc:hsqldb:hsql://localhost:" + port + "/" + name, 
        "SA", 
        ""
      );
      java.sql.Statement stmt = conn.createStatement();
      // Reducir el delay de escritura a disco (por defecto es 500ms)
      stmt.execute("SET FILES WRITE DELAY 100 MILLIS");
      // Habilitar log de transacciones
      stmt.execute("SET FILES LOG TRUE");
      // Configurar nivel de aislamiento por defecto
      stmt.execute("SET DATABASE DEFAULT ISOLATION LEVEL READ COMMITTED");
      // Habilitar NIO para mejor performance de I/O
      stmt.execute("SET FILES NIO TRUE");
      stmt.close();
      conn.close();
      Logger.getLogger(HsqldbServerListener.class.getName()).info("[DB] Configuración de persistencia aplicada.");
    } catch (Exception e) {
      Logger.getLogger(HsqldbServerListener.class.getName()).warning("[DB] Error al configurar persistencia: " + e.getMessage());
    }
  }

  private void stopIfStarted() {
    if (server != null) {
      // Ejecutar SHUTDOWN para garantizar que los datos se escriban al disco
      try {
        java.sql.Connection conn = java.sql.DriverManager.getConnection(
          "jdbc:hsqldb:hsql://localhost:" + server.getPort() + "/" + server.getDatabaseName(0, true), 
          "SA", 
          ""
        );
        java.sql.Statement stmt = conn.createStatement();
        stmt.execute("SHUTDOWN");
        stmt.close();
        conn.close();
        Logger.getLogger(HsqldbServerListener.class.getName()).info("[DB] SHUTDOWN ejecutado correctamente.");
      } catch (Exception e) {
        Logger.getLogger(HsqldbServerListener.class.getName()).warning("[DB] Error al ejecutar SHUTDOWN: " + e.getMessage());
      }
      
      server.stop();
      Logger.getLogger(HsqldbServerListener.class.getName()).info("[DB] HSQLDB detenido por Tomcat.");
      server = null; // Evitar múltiples llamadas
    }
  }

  private static boolean isPortOpen(String host, int port, int timeoutMs) {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress(host, port), timeoutMs);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}