/**
 * Entidades JPA del modelo de dominio de TurismoUY.
 * <p>
 * Este paquete contiene las clases de entidad mapeadas a la base de datos mediante
 * Jakarta Persistence API (JPA). Todas las entidades utilizan EclipseLink como
 * proveedor JPA y se mapean a tablas en HSQLDB.
 * </p>
 * 
 * <h2>Entidades Principales:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.entity.User} - Clase abstracta base para usuarios</li>
 *   <li>{@link turismouyapp.core.entity.Tourist} - Usuario turista (hereda de User)</li>
 *   <li>{@link turismouyapp.core.entity.Supplier} - Usuario proveedor (hereda de User)</li>
 *   <li>{@link turismouyapp.core.entity.TouristActivity} - Actividad turística</li>
 *   <li>{@link turismouyapp.core.entity.TouristOuting} - Salida turística</li>
 *   <li>{@link turismouyapp.core.entity.Inscription} - Inscripción de turista a salida</li>
 * </ul>
 * 
 * <h2>Estrategias de Herencia:</h2>
 * <p>
 * La jerarquía de Usuario utiliza {@code @Inheritance(strategy = InheritanceType.JOINED)}
 * para mapear Tourist y Supplier en tablas separadas con una relación JOIN.
 * </p>
 * 
 * <h2>Relaciones:</h2>
 * <ul>
 *   <li>Supplier 1:N TouristActivity</li>
 *   <li>TouristActivity 1:N TouristOuting</li>
 *   <li>Tourist N:M TouristOuting (a través de Inscription)</li>
 * </ul>
 * 
 * @see jakarta.persistence
 * @see turismouyapp.core.dto
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.entity;
