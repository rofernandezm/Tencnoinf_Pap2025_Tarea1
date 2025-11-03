package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando un turista intenta inscribirse a una salida turística
 * en la que ya está registrado previamente.
 * <p>
 * Esta excepción previene inscripciones duplicadas del mismo turista a la misma salida,
 * lo cual es importante para:
 * </p>
 * <ul>
 *   <li>Mantener la integridad de los datos de inscripciones</li>
 *   <li>Evitar cargos duplicados al turista</li>
 *   <li>Prevenir conteo incorrecto de cupos disponibles</li>
 *   <li>Asegurar reportes y estadísticas precisas</li>
 * </ul>
 * 
 * <p><strong>Casos de uso:</strong></p>
 * <ul>
 *   <li>Turista intenta inscribirse dos veces a la misma salida</li>
 *   <li>Error en interfaz que permite envíos duplicados</li>
 *   <li>Recarga de página durante proceso de inscripción</li>
 *   <li>Procesos de sincronización con sistemas externos</li>
 * </ul>
 * 
 * <p><strong>Validación realizada:</strong></p>
 * <p>
 * La verificación típicamente comprueba la combinación única de:
 * </p>
 * <ul>
 *   <li>ID o nickname del turista</li>
 *   <li>Nombre de la salida turística</li>
 *   <li>Fecha de la salida (opcional, si la salida se repite)</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de manejo:</strong></p>
 * <pre>
 * try {
 *     inscriptionController.registerInscription(
 *         touristNickname, 
 *         activityName, 
 *         outingName, 
 *         numberOfTourists, 
 *         registrationDate
 *     );
 *     JOptionPane.showMessageDialog(null, "Inscripción realizada con éxito");
 * } catch (RepeatedInscriptionToTouristOutingException e) {
 *     JOptionPane.showMessageDialog(null, 
 *         "Ya estás inscrito en esta salida. Revisa 'Mis Inscripciones' para ver tus reservas.",
 *         "Inscripción duplicada", JOptionPane.WARNING_MESSAGE);
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.Inscription
 * @see turismouyapp.core.entity.TouristOuting
 * @see turismouyapp.core.entity.Tourist
 * @see turismouyapp.core.handler.TouristOutingAndInscrptionHandler
 */
import jakarta.xml.ws.WebFault;
import turismouyapp.core.exceptions.fault.RepeatedInscriptionToTouristOutingFault;

@SuppressWarnings("serial")
@WebFault(name = "RepeatedInscriptionToTouristOutingFault", targetNamespace = "http://ws.turismouyapp/schema", faultBean = "turismouyapp.core.exceptions.fault.RepeatedInscriptionToTouristOutingFault")
public class RepeatedInscriptionToTouristOutingException extends Exception {

	private RepeatedInscriptionToTouristOutingFault faultInfo;

	/**
	 * Construye una nueva excepción de inscripción duplicada con el mensaje especificado.
	 * <p>
	 * El mensaje debería incluir información sobre la salida y el turista
	 * para facilitar la identificación del problema, por ejemplo:
	 * "El turista 'juan123' ya está inscrito en la salida 'City Tour 15/03/2025'"
	 * </p>
	 * 
	 * @param string Mensaje de detalle describiendo la inscripción duplicada,
	 *               incluyendo identificadores del turista y la salida
	 */
	public RepeatedInscriptionToTouristOutingException (String string) {
		super(string);
		this.faultInfo = new RepeatedInscriptionToTouristOutingFault(string);
	}

	public RepeatedInscriptionToTouristOutingException(String message, RepeatedInscriptionToTouristOutingFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public RepeatedInscriptionToTouristOutingFault getFaultInfo() {
		return faultInfo;
	}
}
