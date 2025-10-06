package turismouyapp.servlets;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hsqldb.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@WebListener
public class DbServerPublish implements ServletContextListener {
  private Server server;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    int port = Integer.parseInt(System.getProperty("db.port", "9001"));
    String name = System.getProperty("db.name", "turismoUyDB");
    String path = System.getProperty("db.path", "./data/turismoUyDB");

    if (!isPortOpen("127.0.0.1", port, 350)) {
      server = new Server();
      server.setDatabaseName(0, name);
      server.setDatabasePath(0, "file:" + path);
      server.setPort(port);
      server.setSilent(false);
      server.setTrace(false);
      server.setTls(false);
      server.start();
      sce.getServletContext().log("[DB] HSQLDB iniciado por WAR en puerto " + port);
    } else {
      sce.getServletContext().log("[DB] HSQLDB ya estaba arriba; no se inicia otro.");
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (server != null) {
      server.stop();
      sce.getServletContext().log("[DB] HSQLDB detenido por WAR.");
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
