package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando se intenta crear o renombrar una actividad turística
 * con un nombre que ya existe en el sistema.
 * <p>
 * El nombre de las actividades turísticas debe ser único en el sistema para evitar
 * ambigüedades. Esta excepción se lanza cuando se viola esta restricción de unicidad,
 * ya sea durante la creación de una nueva actividad o al modificar el nombre de una existente.
 * </p>
 * 
 * <p><strong>Casos de uso comunes:</strong></p>
 * <ul>
 *   <li>Registrar una nueva actividad con un nombre ya registrado</li>
 *   <li>Modificar el nombre de una actividad existente a un nombre en uso</li>
 *   <li>Importar actividades desde un archivo con nombres duplicados</li>
 * </ul>
 * 
 * <p><strong>Manejo recomendado:</strong></p>
 * <pre>
 * try {
 *     activityController.createActivity(name, description, duration, cost, city, supplier);
 * } catch (RepeatedActivityNameException e) {
 *     JOptionPane.showMessageDialog(null, 
 *         "Ya existe una actividad con ese nombre. Por favor elija otro nombre.",
 *         "Nombre duplicado", JOptionPane.ERROR_MESSAGE);
 * }
 * </pre>
 * 
 * <p><strong>Nota:</strong> La validación de nombres únicos se realiza típicamente
 * a nivel de handler antes de persistir en la base de datos, evitando violaciones
 * de restricciones de integridad a nivel de BD.</p>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.TouristActivity
 * @see turismouyapp.core.handler.TouristActivityHandler
 * @see turismouyapp.core.controller.TouristActivityController
 */
@SuppressWarnings("serial")
public class RepeatedActivityNameException extends Exception {
	
	/**
	 * Construye una nueva excepción de nombre de actividad duplicado con el mensaje especificado.
	 * <p>
	 * El mensaje típicamente incluye el nombre de la actividad que causó el conflicto
	 * para ayudar al usuario a identificar el problema.
	 * </p>
	 * 
	 * @param string Mensaje de detalle indicando el nombre de actividad duplicado
	 *               y sugerencias para resolverlo
	 */
	public RepeatedActivityNameException(String string) {
		super(string);
	}
}