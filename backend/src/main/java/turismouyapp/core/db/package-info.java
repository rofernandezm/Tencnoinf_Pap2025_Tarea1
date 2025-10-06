/**
 * Gestión de base de datos del sistema TurismoUY.
 * <p>
 * Este paquete contiene componentes para la gestión y configuración de la base de
 * datos HSQLDB. Incluye listeners para el ciclo de vida del servidor de base de datos
 * y utilidades de conexión.
 * </p>
 * 
 * <h2>Componentes Principales:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.db.HsqldbServerListener} - Listener de Tomcat para iniciar/detener HSQLDB</li>
 * </ul>
 * 
 * <h2>Configuración de Base de Datos:</h2>
 * <p>
 * El sistema utiliza HSQLDB en modo servidor, permitiendo múltiples conexiones
 * concurrentes tanto desde la aplicación web como desde la aplicación de escritorio.
 * </p>
 * 
 * <h3>Parámetros de Conexión:</h3>
 * <ul>
 *   <li><strong>Puerto:</strong> 9001 (configurable vía System Property: db.port)</li>
 *   <li><strong>Nombre BD:</strong> turismoUyDB (configurable vía System Property: db.name)</li>
 *   <li><strong>Ruta BD:</strong> ./data/turismoUyDB (configurable vía System Property: db.path)</li>
 *   <li><strong>URL JDBC:</strong> jdbc:hsqldb:hsql://localhost:9001/turismoUyDB</li>
 * </ul>
 * 
 * <h2>Ciclo de Vida:</h2>
 * <ol>
 *   <li>Tomcat inicia → HsqldbServerListener.lifecycleEvent(BEFORE_START)</li>
 *   <li>Verifica si puerto 9001 está disponible</li>
 *   <li>Si disponible, inicia servidor HSQLDB</li>
 *   <li>Aplicaciones se conectan vía JDBC</li>
 *   <li>Tomcat se detiene → HsqldbServerListener.lifecycleEvent(AFTER_STOP)</li>
 *   <li>Detiene servidor HSQLDB limpiamente</li>
 * </ol>
 * 
 * @see org.hsqldb.server.Server
 * @see org.apache.catalina.LifecycleListener
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.db;
