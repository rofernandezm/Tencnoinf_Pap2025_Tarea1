package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando se intenta acceder a una actividad turística que no existe en el sistema.
 * <p>
 * Esta excepción se utiliza típicamente en operaciones de consulta, modificación o eliminación
 * de actividades turísticas cuando el identificador o nombre proporcionado no corresponde
 * a ninguna actividad registrada en la base de datos.
 * </p>
 * 
 * <p><strong>Escenarios comunes de uso:</strong></p>
 * <ul>
 *   <li>Consultar detalles de una actividad con un nombre inexistente</li>
 *   <li>Intentar modificar una actividad que fue eliminada</li>
 *   <li>Registrar una salida turística asociada a una actividad no válida</li>
 *   <li>Buscar actividades con criterios que no coinciden con ningún registro</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de uso:</strong></p>
 * <pre>
 * try {
 *     TouristActivity activity = activityHandler.findByName("ActividadInexistente");
 * } catch (ActivityDoesNotExistException e) {
 *     System.err.println("La actividad no existe: " + e.getMessage());
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.TouristActivity
 * @see turismouyapp.core.handler.TouristActivityHandler
 */
import jakarta.xml.ws.WebFault;
import turismouyapp.core.exceptions.fault.ActivityDoesNotExistFault;

@SuppressWarnings("serial")
@WebFault(name = "ActivityDoesNotExistFault", targetNamespace = "http://ws.turismouyapp/schema", faultBean = "turismouyapp.core.exceptions.fault.ActivityDoesNotExistFault")
public class ActivityDoesNotExistException extends Exception {

	private ActivityDoesNotExistFault faultInfo;

	/**
	 * Construye una nueva excepción de actividad no existente con el mensaje de detalle especificado.
	 * <p>
	 * El mensaje típicamente incluye el nombre o identificador de la actividad
	 * que se intentó buscar sin éxito.
	 * </p>
	 * 
	 * @param string Mensaje de detalle que describe qué actividad no fue encontrada
	 *               y en qué contexto ocurrió el error
	 */
	public ActivityDoesNotExistException(String string) {
        super(string);
        this.faultInfo = new ActivityDoesNotExistFault(string);
    }

	public ActivityDoesNotExistException(String message, ActivityDoesNotExistFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public ActivityDoesNotExistFault getFaultInfo() {
		return faultInfo;
	}
}
