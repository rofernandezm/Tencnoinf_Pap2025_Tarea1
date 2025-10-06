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
    String path = System.getProperty("db.path", "./data/turismoUyDB");

    if (isPortOpen("127.0.0.1", port, 300)) return;

    server = new Server();
    server.setDatabaseName(0, name);
    server.setDatabasePath(0, "file:" + path);
    server.setPort(port);
    server.setSilent(false);
    server.setTrace(false);
    server.start();
    Logger.getLogger(HsqldbServerListener.class.getName()).info("[DB] HSQLDB iniciado por Tomcat en puerto " + port);
  }

  private void stopIfStarted() {
    if (server != null) {
      server.stop();
      Logger.getLogger(HsqldbServerListener.class.getName()).info("[DB] HSQLDB detenido por Tomcat.");
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