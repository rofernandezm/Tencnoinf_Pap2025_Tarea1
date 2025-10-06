package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un nickname (apodo)
 * que ya está siendo utilizado por otro usuario en el sistema.
 * <p>
 * El nickname es un identificador único y amigable que los usuarios utilizan para
 * iniciar sesión y ser reconocidos en el sistema. La unicidad del nickname es esencial
 * para:
 * </p>
 * <ul>
 *   <li>Autenticación sin ambigüedades</li>
 *   <li>Identificación visual en interfaces de usuario</li>
 *   <li>Menciones y referencias entre usuarios</li>
 *   <li>Búsqueda y filtrado de usuarios</li>
 * </ul>
 * 
 * <p><strong>Contextos de uso:</strong></p>
 * <ul>
 *   <li>Registro de nuevos usuarios (turistas o proveedores)</li>
 *   <li>Cambio de nickname en perfil de usuario</li>
 *   <li>Migración de datos desde sistemas legados</li>
 * </ul>
 * 
 * <p><strong>Diferencia con {@link RepeatedUserEmailException}:</strong></p>
 * <ul>
 *   <li>Email: identificador técnico, privado, usado para recuperación de cuenta</li>
 *   <li>Nickname: identificador público, visible, usado para interacción social</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de uso:</strong></p>
 * <pre>
 * try {
 *     userController.createSupplier(nickname, name, description, link);
 * } catch (RepeatedUserNicknameException e) {
 *     lblError.setText("El nickname '" + nickname + "' no está disponible. " +
 *                      "Por favor intenta con otro.");
 * } catch (RepeatedUserEmailException e) {
 *     lblError.setText("El email ya está registrado.");
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.User
 * @see turismouyapp.core.entity.Tourist
 * @see turismouyapp.core.entity.Supplier
 * @see RepeatedUserEmailException
 */
@SuppressWarnings("serial")
public class RepeatedUserNicknameException extends Exception {
	
	/**
	 * Construye una nueva excepción de nickname duplicado con el mensaje especificado.
	 * <p>
	 * El mensaje debería ser suficientemente descriptivo para que el usuario
	 * entienda el problema y pueda elegir un nickname alternativo.
	 * </p>
	 * 
	 * @param string Mensaje de detalle que indica el nickname en conflicto
	 *               y sugiere alternativas al usuario
	 */
	public RepeatedUserNicknameException(String string) {
		super(string);
	}
}