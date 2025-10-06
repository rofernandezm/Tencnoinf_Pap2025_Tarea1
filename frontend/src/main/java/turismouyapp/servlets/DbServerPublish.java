package turismouyapp.servlets;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hsqldb.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Listener del contexto de la aplicación web que gestiona el ciclo de vida
 * del servidor de base de datos HSQLDB embebido.
 * <p>
 * Este componente implementa {@link ServletContextListener} y se encarga de:
 * </p>
 * <ul>
 *   <li>Iniciar automáticamente el servidor HSQLDB cuando el WAR se despliega</li>
 *   <li>Verificar si el puerto ya está en uso antes de iniciar (evita duplicados)</li>
 *   <li>Detener el servidor HSQLDB cuando el contexto web se destruye</li>
 * </ul>
 * 
 * <p><strong>Configuración mediante propiedades del sistema:</strong></p>
 * <table>
 *   <tr>
 *     <th>Propiedad</th>
 *     <th>Valor por Defecto</th>
 *     <th>Descripción</th>
 *   </tr>
 *   <tr>
 *     <td>{@code db.port}</td>
 *     <td>9001</td>
 *     <td>Puerto TCP donde el servidor HSQLDB escuchará conexiones</td>
 *   </tr>
 *   <tr>
 *     <td>{@code db.name}</td>
 *     <td>turismoUyDB</td>
 *     <td>Nombre lógico de la base de datos</td>
 *   </tr>
 *   <tr>
 *     <td>{@code db.path}</td>
 *     <td>./data/turismoUyDB</td>
 *     <td>Ruta del sistema de archivos donde se almacenan los datos</td>
 *   </tr>
 * </table>
 * 
 * <p><strong>Ejemplo de configuración en setenv.sh:</strong></p>
 * <pre>
 * export CATALINA_OPTS="-Ddb.port=9001 -Ddb.name=turismoUyDB -Ddb.path=/var/data/turismoUyDB"
 * </pre>
 * 
 * <p><strong>Modo de operación:</strong></p>
 * <ol>
 *   <li>Al inicializar el contexto, verifica si el puerto está disponible</li>
 *   <li>Si el puerto está libre, inicia un nuevo servidor HSQLDB</li>
 *   <li>Si el puerto está ocupado, asume que ya hay un servidor corriendo</li>
 *   <li>Al destruir el contexto, detiene el servidor si fue iniciado por este listener</li>
 * </ol>
 * 
 * <p><strong>Nota importante:</strong> Esta clase es una alternativa al 
 * {@code HsqldbServerListener} que se configura en Tomcat. Solo uno de los
 * dos mecanismos debería estar activo simultáneamente.</p>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see ServletContextListener
 * @see org.hsqldb.server.Server
 * @see turismouyapp.core.db.HsqldbServerListener
 */
@WebListener
public class DbServerPublish implements ServletContextListener {
	
  /**
   * Instancia del servidor HSQLDB gestionado por este listener.
   * <p>
   * Es {@code null} si el servidor no fue iniciado por este listener
   * (por ejemplo, si el puerto ya estaba en uso).
   * </p>
   */
  private Server server;

  /**
   * Método invocado cuando el contexto del servlet se inicializa.
   * <p>
   * Este método se ejecuta automáticamente cuando Tomcat despliega el WAR.
   * Realiza las siguientes operaciones:
   * </p>
   * <ol>
   *   <li>Lee las propiedades del sistema para configuración de BD</li>
   *   <li>Verifica si el puerto de BD está disponible</li>
   *   <li>Si está disponible, configura e inicia el servidor HSQLDB</li>
   *   <li>Si está ocupado, registra un mensaje informativo en los logs</li>
   * </ol>
   * 
   * <p><strong>Configuración del servidor HSQLDB:</strong></p>
   * <ul>
   *   <li><strong>Modo:</strong> Servidor (permite múltiples conexiones)</li>
   *   <li><strong>Protocolo:</strong> HSQL nativo (no TLS)</li>
   *   <li><strong>Silent:</strong> false (logs habilitados)</li>
   *   <li><strong>Trace:</strong> false (debug deshabilitado)</li>
   * </ul>
   * 
   * <p><strong>Ejemplo de URL de conexión resultante:</strong></p>
   * <pre>
   * jdbc:hsqldb:hsql://localhost:9001/turismoUyDB
   * </pre>
   * 
   * @param sce El evento del contexto del servlet que contiene información
   *            sobre el contexto que se está inicializando
   * 
   * @see #isPortOpen(String, int, int)
   * @see org.hsqldb.server.Server#start()
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // Leer configuración desde propiedades del sistema
    int port = Integer.parseInt(System.getProperty("db.port", "9001"));
    String name = System.getProperty("db.name", "turismoUyDB");
    String path = System.getProperty("db.path", "./data/turismoUyDB");

    // Verificar si el puerto ya está en uso
    if (!isPortOpen("127.0.0.1", port, 350)) {
      // Puerto disponible - iniciar nuevo servidor HSQLDB
      server = new Server();
      server.setDatabaseName(0, name);
      server.setDatabasePath(0, "file:" + path);
      server.setPort(port);
      server.setSilent(false);  // Habilitar logs del servidor
      server.setTrace(false);   // Deshabilitar trazas de debug
      server.setTls(false);     // Sin encriptación TLS
      server.start();
      sce.getServletContext().log("[DB] HSQLDB iniciado por WAR en puerto " + port);
    } else {
      // Puerto ocupado - asumir que hay un servidor existente
      sce.getServletContext().log("[DB] HSQLDB ya estaba arriba; no se inicia otro.");
    }
  }

  /**
   * Método invocado cuando el contexto del servlet se destruye.
   * <p>
   * Este método se ejecuta automáticamente cuando:
   * </p>
   * <ul>
   *   <li>Tomcat se detiene</li>
   *   <li>El WAR se desinstala (undeploy)</li>
   *   <li>La aplicación se reinicia (redeploy)</li>
   * </ul>
   * 
   * <p>Si este listener inició un servidor HSQLDB (es decir, si
   * {@link #server} no es {@code null}), procede a detenerlo de forma
   * ordenada. Esto asegura que:</p>
   * <ul>
   *   <li>Todas las transacciones pendientes se finalizan</li>
   *   <li>Los archivos de la base de datos se cierran correctamente</li>
   *   <li>No quedan procesos huérfanos</li>
   * </ul>
   * 
   * <p><strong>Nota:</strong> Si el servidor fue iniciado externamente
   * (por ejemplo, por {@code HsqldbServerListener} en Tomcat), este método
   * no realiza ninguna acción.</p>
   * 
   * @param sce El evento del contexto del servlet que contiene información
   *            sobre el contexto que se está destruyendo
   * 
   * @see org.hsqldb.server.Server#stop()
   */
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (server != null) {
      server.stop();
      sce.getServletContext().log("[DB] HSQLDB detenido por WAR.");
    }
  }

  /**
   * Verifica si un puerto TCP está abierto y aceptando conexiones.
   * <p>
   * Este método intenta establecer una conexión socket al host y puerto
   * especificados. Si la conexión se establece exitosamente dentro del
   * tiempo límite, significa que el puerto está ocupado (posiblemente
   * por un servidor HSQLDB existente).
   * </p>
   * 
   * <p><strong>Casos de uso:</strong></p>
   * <ul>
   *   <li>Evitar iniciar múltiples instancias del servidor en el mismo puerto</li>
   *   <li>Detectar si hay un servidor HSQLDB ya corriendo</li>
   *   <li>Validación de configuración antes de despliegue</li>
   * </ul>
   * 
   * <p><strong>Comportamiento de timeout:</strong></p>
   * <ul>
   *   <li>Si la conexión tarda más de {@code timeoutMs}, se considera cerrado</li>
   *   <li>Un timeout corto (350ms) es suficiente para conexiones localhost</li>
   * </ul>
   * 
   * @param host La dirección del host a verificar (típicamente "127.0.0.1" o "localhost")
   * @param port El número de puerto TCP a verificar (1-65535)
   * @param timeoutMs El tiempo máximo en milisegundos para intentar la conexión
   * 
   * @return {@code true} si el puerto está abierto y acepta conexiones,
   *         {@code false} si el puerto está cerrado o no responde en el tiempo límite
   * 
   * @throws IllegalArgumentException Si el puerto está fuera del rango válido (implícito)
   * 
   * @see Socket#connect(java.net.SocketAddress, int)
   */
  private static boolean isPortOpen(String host, int port, int timeoutMs) {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress(host, port), timeoutMs);
      return true;  // Conexión exitosa - puerto ocupado
    } catch (IOException e) {
      return false; // Conexión fallida - puerto disponible
    }
  }
}
