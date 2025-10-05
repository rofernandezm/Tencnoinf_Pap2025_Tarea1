package db;

import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionIdListener;
import org.hsqldb.Server;

/**
 * Application Lifecycle Listener implementation class HsqldbServerListener
 *
 */
@WebListener
public class HsqldbServerListener implements ServletContextListener {
    private Server server;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Ruta de los archivos .script/.properties de la DB (fuera del WAR)
        // Podés pasarla por system prop o por web.xml (context-param)
        String dbDir = System.getProperty("hsqldb.dir", "/var/lib/miapp/mibase"); 
        int port = Integer.getInteger("hsqldb.port", 9001);

        server = new Server();
        server.setDatabaseName(0, "mibase");
        server.setDatabasePath(0, "file:" + dbDir); // <-- importante: "file:"
        server.setPort(port);                       // 9001 por defecto
        server.setSilent(true);                     // menos logs en consola
        server.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (server != null) {
            server.shutdown(); // cierra y guarda .script
        }
    }
}