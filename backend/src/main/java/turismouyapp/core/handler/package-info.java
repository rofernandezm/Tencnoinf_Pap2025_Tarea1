/**
 * Manejadores de persistencia del sistema TurismoUY.
 * <p>
 * Este paquete contiene los handlers que gestionan el acceso a datos y la persistencia
 * mediante JPA/EclipseLink. Los handlers implementan el patrón DAO (Data Access Object)
 * y proporcionan métodos de bajo nivel para operaciones CRUD y consultas especializadas.
 * </p>
 * 
 * <h2>Handlers Disponibles:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.handler.UserHandler} - Persistencia de usuarios</li>
 *   <li>{@link turismouyapp.core.handler.TouristActivityHandler} - Persistencia de actividades</li>
 *   <li>{@link turismouyapp.core.handler.TouristOutingAndInscrptionHandler} - Persistencia de salidas e inscripciones</li>
 *   <li>{@link turismouyapp.core.handler.PersistenceHandler} - Gestor de EntityManager</li>
 * </ul>
 * 
 * <h2>Responsabilidades:</h2>
 * <ul>
 *   <li>Gestión de EntityManager y transacciones JPA</li>
 *   <li>Ejecución de consultas JPQL</li>
 *   <li>Operaciones CRUD sobre entidades</li>
 *   <li>Conversión entre entidades y DTOs</li>
 * </ul>
 * 
 * <h2>Patrón Singleton:</h2>
 * <p>
 * Todos los handlers implementan el patrón Singleton para garantizar una única
 * instancia por aplicación y facilitar el acceso global.
 * </p>
 * 
 * @see jakarta.persistence.EntityManager
 * @see turismouyapp.core.entity
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.handler;
