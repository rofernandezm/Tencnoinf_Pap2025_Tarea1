/**
 * Paquete que contiene los servlets y listeners de la capa de presentación web.
 * <p>
 * Este paquete implementa la interfaz web de la aplicación TurismoUY utilizando
 * tecnología Jakarta Servlet 6.0. Actúa como capa de presentación en la
 * arquitectura de tres capas del sistema.
 * </p>
 *
 * <h2>Componentes Principales</h2>
 *
 * <h3>Servlets</h3>
 * <ul>
 *   <li>{@link turismouyapp.servlets.Login} - Servlet principal mapeado a la raíz ("/")
 *       que proporciona la página de inicio y listado de usuarios</li>
 * </ul>
 *
 * <h3>Listeners</h3>
 * <ul>
 *   <li>{@link turismouyapp.servlets.DbServerPublish} - Listener del contexto que
 *       gestiona el ciclo de vida del servidor HSQLDB embebido</li>
 * </ul>
 *
 * <h2>Arquitectura de Capas</h2>
 * <pre>
 * ┌─────────────────────────────────────┐
 * │   Capa de Presentación (Frontend)   │  ← Este paquete
 * │   - Servlets Jakarta                │
 * │   - Listeners de contexto           │
 * └──────────────┬──────────────────────┘
 *                │
 *                ▼
 * ┌─────────────────────────────────────┐
 * │   Capa de Lógica (Backend)          │
 * │   - Controladores                   │
 * │   - Factory Pattern                 │
 * │   - DTOs                            │
 * └──────────────┬──────────────────────┘
 *                │
 *                ▼
 * ┌─────────────────────────────────────┐
 * │   Capa de Persistencia              │
 * │   - Entidades JPA                   │
 * │   - HSQLDB Server                   │
 * └─────────────────────────────────────┘
 * </pre>
 *
 * <h2>Integración con Backend</h2>
 * <p>
 * Los servlets de este paquete acceden a la lógica de negocio a través del
 * patrón Factory implementado en {@link turismouyapp.core.factory.FactoryUyTourism}.
 * Esta aproximación proporciona:
 * </p>
 * <ul>
 *   <li><strong>Separación de capas:</strong> La lógica de presentación no conoce
 *       detalles de implementación del backend</li>
 *   <li><strong>Testabilidad:</strong> Los controladores pueden ser mockeados fácilmente</li>
 *   <li><strong>Mantenibilidad:</strong> Cambios en el backend no afectan a los servlets</li>
 *   <li><strong>Reutilización:</strong> La misma lógica sirve para web y desktop</li>
 * </ul>
 *
 * <h2>Configuración y Despliegue</h2>
 *
 * <h3>Descriptor de Despliegue</h3>
 * <p>
 * La configuración se define en {@code WEB-INF/web.xml} (Jakarta EE 5.0).
 * Los servlets usan anotaciones {@code @WebServlet} y {@code @WebListener}
 * para configuración basada en código.
 * </p>
 *
 * <h3>Empaquetado</h3>
 * <p>
 * Este paquete se compila dentro del archivo WAR {@code turismouy.UI.war} que
 * incluye todas las dependencias necesarias del backend.
 * </p>
 *
 * <h3>Servidor de Aplicaciones</h3>
 * <p>
 * Requiere Apache Tomcat 11.0.11 o superior con soporte para:
 * </p>
 * <ul>
 *   <li>Jakarta Servlet API 6.0</li>
 *   <li>Jakarta Persistence API 3.1 (transitiva desde backend)</li>
 *   <li>HSQLDB Server 2.7.2 (embebido)</li>
 * </ul>
 *
 * <h2>Gestión de Base de Datos</h2>
 * <p>
 * El listener {@link turismouyapp.servlets.DbServerPublish} proporciona
 * funcionalidad de "auto-inicio" de HSQLDB cuando el WAR se despliega.
 * Esta característica es útil en entornos donde:
 * </p>
 * <ul>
 *   <li>No hay un servidor HSQLDB externo disponible</li>
 *   <li>Se requiere despliegue standalone (self-contained)</li>
 *   <li>Desarrollo y testing en entornos aislados</li>
 * </ul>
 *
 * <p><strong>Nota:</strong> En producción, se recomienda usar un servidor de
 * BD externo (PostgreSQL, MySQL) en lugar de HSQLDB embebido.</p>
 *
 * <h2>Seguridad</h2>
 * <p>
 * <strong>Estado actual:</strong> Este paquete no implementa autenticación
 * ni autorización. Es una versión de desarrollo/demostración.
 * </p>
 *
 * <p><strong>Recomendaciones para producción:</strong></p>
 * <ul>
 *   <li>Implementar Jakarta Security para autenticación</li>
 *   <li>Usar HTTPS (TLS) para todas las comunicaciones</li>
 *   <li>Validar y sanitizar todas las entradas del usuario</li>
 *   <li>Implementar CSRF protection</li>
 *   <li>Configurar session timeout apropiado</li>
 * </ul>
 *
 * <h2>Extensibilidad</h2>
 * <p>
 * Para agregar nuevos endpoints o funcionalidad web:
 * </p>
 * <ol>
 *   <li>Crear un nuevo servlet extendiendo {@link jakarta.servlet.http.HttpServlet}</li>
 *   <li>Anotar con {@code @WebServlet} especificando el URL pattern</li>
 *   <li>Implementar {@code doGet} y/o {@code doPost} según necesidad</li>
 *   <li>Acceder al backend vía {@code FactoryUyTourism.getInstance()}</li>
 *   <li>Recompilar y redesplegar el WAR</li>
 * </ol>
 *
 * <h2>Ejemplos de Uso</h2>
 *
 * <h3>Acceder a la página principal</h3>
 * <pre>
 * GET http://localhost:8080/turismouy.UI/
 * </pre>
 *
 * <h3>Estructura de URLs esperada</h3>
 * <pre>
 * http://localhost:8080/turismouy.UI/           → Login servlet (listado)
 * http://localhost:8080/turismouy.UI/users      → (futuro) Gestión de usuarios
 * http://localhost:8080/turismouy.UI/activities → (futuro) Gestión de actividades
 * http://localhost:8080/turismouy.UI/outings    → (futuro) Gestión de salidas
 * </pre>
 *
 * <h2>Dependencias</h2>
 * <table>
 *   <tr>
 *     <th>Dependencia</th>
 *     <th>Versión</th>
 *     <th>Scope</th>
 *     <th>Uso</th>
 *   </tr>
 *   <tr>
 *     <td>jakarta.servlet-api</td>
 *     <td>6.0.0</td>
 *     <td>provided</td>
 *     <td>API de servlets (provisto por Tomcat)</td>
 *   </tr>
 *   <tr>
 *     <td>turismouy.Backend</td>
 *     <td>1.0.0</td>
 *     <td>compile</td>
 *     <td>Lógica de negocio y acceso a datos</td>
 *   </tr>
 *   <tr>
 *     <td>hsqldb</td>
 *     <td>2.7.2</td>
 *     <td>provided</td>
 *     <td>Servidor de base de datos embebido</td>
 *   </tr>
 * </table>
 *
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 *
 * @see turismouyapp.core.factory.FactoryUyTourism
 * @see jakarta.servlet.http.HttpServlet
 * @see jakarta.servlet.ServletContextListener
 */
package turismouyapp.servlets;
