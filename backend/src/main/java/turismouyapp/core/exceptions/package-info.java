/**
 * Excepciones personalizadas del sistema TurismoUY.
 * <p>
 * Este paquete contiene las excepciones de negocio específicas del dominio de
 * turismo. Todas las excepciones heredan de {@link java.lang.Exception} y son
 * checked exceptions que deben ser manejadas explícitamente.
 * </p>
 * 
 * <h2>Excepciones de Usuario:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.exceptions.RepeatedUserNicknameException} - Nickname duplicado</li>
 *   <li>{@link turismouyapp.core.exceptions.RepeatedUserEmailException} - Email duplicado</li>
 * </ul>
 * 
 * <h2>Excepciones de Actividad:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.exceptions.ActivityDoesNotExistException} - Actividad no encontrada</li>
 *   <li>{@link turismouyapp.core.exceptions.RepeatedActivityNameException} - Nombre de actividad duplicado</li>
 * </ul>
 * 
 * <h2>Excepciones de Salida e Inscripción:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.exceptions.TouristOutingDoesNotExistException} - Salida no encontrada</li>
 *   <li>{@link turismouyapp.core.exceptions.RepeatedTouristOutingException} - Salida duplicada</li>
 *   <li>{@link turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException} - Inscripción duplicada</li>
 * </ul>
 * 
 * <h2>Manejo de Excepciones:</h2>
 * <p>
 * Las excepciones son lanzadas por los controladores cuando se violan reglas de negocio
 * y deben ser capturadas en la capa de presentación para mostrar mensajes apropiados
 * al usuario.
 * </p>
 * 
 * @see turismouyapp.core.controller
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.exceptions;
