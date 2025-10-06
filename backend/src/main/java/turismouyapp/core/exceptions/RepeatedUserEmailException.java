package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un correo electrónico
 * que ya está en uso por otro usuario en el sistema.
 * <p>
 * El correo electrónico es un identificador único de usuario en TurismoUY. Esta excepción
 * garantiza la integridad de datos evitando que múltiples usuarios compartan la misma
 * dirección de correo electrónico, lo cual es crítico para:
 * </p>
 * <ul>
 *   <li>Procesos de autenticación y recuperación de contraseña</li>
 *   <li>Comunicaciones personalizadas con usuarios</li>
 *   <li>Auditoría y trazabilidad de acciones en el sistema</li>
 *   <li>Cumplimiento de regulaciones de protección de datos</li>
 * </ul>
 * 
 * <p><strong>Escenarios que disparan esta excepción:</strong></p>
 * <ul>
 *   <li>Alta de nuevo usuario turista o proveedor con email existente</li>
 *   <li>Modificación del email de un usuario a uno ya registrado</li>
 *   <li>Importación masiva de usuarios con emails duplicados</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de manejo:</strong></p>
 * <pre>
 * try {
 *     userController.createTourist(nickname, name, lastName, email, birthDate, nationality);
 * } catch (RepeatedUserEmailException e) {
 *     showError("El correo " + email + " ya está registrado. " +
 *               "Si olvidaste tu contraseña, usa la opción de recuperación.");
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.User
 * @see turismouyapp.core.handler.UserHandler
 * @see RepeatedUserNicknameException
 */
@SuppressWarnings("serial")
public class RepeatedUserEmailException extends Exception {
	
	/**
	 * Construye una nueva excepción de email de usuario duplicado con el mensaje especificado.
	 * <p>
	 * El mensaje típicamente incluye el correo electrónico que causó el conflicto,
	 * aunque por razones de seguridad y privacidad, se debe tener cuidado de no
	 * exponer información sensible en mensajes de error visibles al usuario.
	 * </p>
	 * 
	 * @param string Mensaje de detalle describiendo el conflicto de email duplicado
	 */
	public RepeatedUserEmailException(String string) {
		super(string);
	}
}
