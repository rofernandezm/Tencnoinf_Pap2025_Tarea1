/**
 * Data Transfer Objects (DTOs) del sistema TurismoUY.
 * <p>
 * Este paquete contiene las clases DTO utilizadas para transferir datos entre las
 * diferentes capas de la aplicación. Los DTOs son objetos serializables que no
 * contienen lógica de negocio y están diseñados para encapsular datos de manera
 * segura y eficiente.
 * </p>
 * 
 * <h2>Categorías de DTOs:</h2>
 * 
 * <h3>DTOs de Usuario:</h3>
 * <ul>
 *   <li>{@link turismouyapp.core.dto.DtUser} - Datos básicos de usuario</li>
 *   <li>{@link turismouyapp.core.dto.DtTourist} - Datos específicos de turista</li>
 *   <li>{@link turismouyapp.core.dto.DtSupplier} - Datos específicos de proveedor</li>
 *   <li>{@link turismouyapp.core.dto.DtUserProfile} - Perfil completo de usuario</li>
 *   <li>{@link turismouyapp.core.dto.DtTouristProfile} - Perfil completo de turista</li>
 *   <li>{@link turismouyapp.core.dto.DtSupplierProfile} - Perfil completo de proveedor</li>
 * </ul>
 * 
 * <h3>DTOs de Actividad:</h3>
 * <ul>
 *   <li>{@link turismouyapp.core.dto.DtTouristActivity} - Datos de actividad turística</li>
 *   <li>{@link turismouyapp.core.dto.DtActivityWithOutings} - Actividad con sus salidas</li>
 *   <li>{@link turismouyapp.core.dto.DtRanking} - Ranking de actividades</li>
 * </ul>
 * 
 * <h3>DTOs de Salida e Inscripción:</h3>
 * <ul>
 *   <li>{@link turismouyapp.core.dto.DtTouristOuting} - Datos de salida turística</li>
 *   <li>{@link turismouyapp.core.dto.DtInscriptionTouristOuting} - Datos de inscripción</li>
 * </ul>
 * 
 * <h3>Enumerados:</h3>
 * <ul>
 *   <li>{@link turismouyapp.core.dto.UserType} - Tipos de usuario (TOURIST, SUPPLIER)</li>
 *   <li>{@link turismouyapp.core.dto.TouristActivityStatus} - Estados de actividad</li>
 * </ul>
 * 
 * @see turismouyapp.core.entity
 * @see turismouyapp.core.controller
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.dto;
