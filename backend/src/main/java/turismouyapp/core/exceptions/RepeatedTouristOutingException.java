package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando se intenta crear una salida turística con un nombre
 * que ya existe para una actividad determinada.
 * <p>
 * Cada salida turística debe tener un nombre único dentro del contexto de su
 * actividad asociada. Esta restricción es necesaria para:
 * </p>
 * <ul>
 *   <li>Identificar inequívocamente cada salida en el sistema</li>
 *   <li>Evitar confusión entre turistas al momento de inscribirse</li>
 *   <li>Facilitar la gestión administrativa de salidas</li>
 *   <li>Mantener la integridad referencial con inscripciones</li>
 * </ul>
 * 
 * <p><strong>Contexto de dominio:</strong></p>
 * <p>
 * Una actividad turística (ej: "City Tour Montevideo") puede tener múltiples
 * salidas programadas (ej: "City Tour 15/03", "City Tour 22/03"). Cada salida
 * representa una instancia específica con fecha, hora y cupo definidos.
 * </p>
 * 
 * <p><strong>Escenarios que disparan la excepción:</strong></p>
 * <ul>
 *   <li>Crear una nueva salida con nombre ya existente en la actividad</li>
 *   <li>Renombrar una salida a un nombre ya usado en la misma actividad</li>
 *   <li>Importar datos con nombres de salidas duplicadas</li>
 * </ul>
 * 
 * <p><strong>Nota importante:</strong></p>
 * <p>
 * La validación es a nivel de actividad. Dos actividades diferentes PUEDEN
 * tener salidas con el mismo nombre. Por ejemplo:
 * </p>
 * <pre>
 * Actividad: "City Tour Montevideo" → Salida: "Grupo Mañana"  ✓
 * Actividad: "Wine Tour Canelones"  → Salida: "Grupo Mañana"  ✓
 * Actividad: "City Tour Montevideo" → Salida: "Grupo Mañana"  ✗ (duplicado)
 * </pre>
 * 
 * <p><strong>Ejemplo de uso:</strong></p>
 * <pre>
 * try {
 *     outingController.registerTouristOuting(
 *         activityName,
 *         outingName,
 *         startDate,
 *         endDate,
 *         meetingPlace,
 *         maxTourists,
 *         registrationDeadline
 *     );
 * } catch (RepeatedTouristOutingException e) {
 *     showWarning("Ya existe una salida con ese nombre para esta actividad. " +
 *                 "Por favor elige un nombre diferente, como agregar la fecha.");
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.TouristOuting
 * @see turismouyapp.core.entity.TouristActivity
 * @see turismouyapp.core.handler.TouristOutingAndInscrptionHandler
 */
import jakarta.xml.ws.WebFault;
import turismouyapp.core.exceptions.fault.RepeatedTouristOutingFault;

@SuppressWarnings("serial")
@WebFault(name = "RepeatedTouristOutingFault", targetNamespace = "http://ws.turismouyapp/schema", faultBean = "turismouyapp.core.exceptions.fault.RepeatedTouristOutingFault")
public class RepeatedTouristOutingException extends Exception {

	private RepeatedTouristOutingFault faultInfo;

	/**
	 * Construye una nueva excepción de salida turística duplicada con el mensaje especificado.
	 * <p>
	 * El mensaje típicamente incluye tanto el nombre de la actividad como el nombre
	 * de la salida que causó el conflicto para mayor claridad.
	 * </p>
	 * 
	 * @param string Mensaje de detalle describiendo la salida duplicada,
	 *               incluyendo nombre de actividad y nombre de salida
	 */
	public RepeatedTouristOutingException (String string) {
		super(string);
		this.faultInfo = new RepeatedTouristOutingFault(string);
	}

	public RepeatedTouristOutingException(String message, RepeatedTouristOutingFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public RepeatedTouristOutingFault getFaultInfo() {
		return faultInfo;
	}
}
