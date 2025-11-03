package turismouyapp.core.exceptions;

/**
 * Excepción lanzada cuando se intenta acceder a una salida turística que no existe en el sistema.
 * <p>
 * Esta excepción se utiliza en operaciones que requieren localizar una salida específica
 * por su nombre o identificador, pero la búsqueda no arroja resultados. Es fundamental
 * para garantizar que las operaciones se realicen sobre datos válidos y existentes.
 * </p>
 * 
 * <p><strong>Operaciones afectadas:</strong></p>
 * <ul>
 *   <li>Consulta de detalles de una salida turística</li>
 *   <li>Inscripción de turistas a una salida</li>
 *   <li>Modificación de atributos de una salida (fecha, cupo, lugar)</li>
 *   <li>Cancelación de una salida</li>
 *   <li>Generación de reportes de inscripciones por salida</li>
 * </ul>
 * 
 * <p><strong>Causas comunes:</strong></p>
 * <ul>
 *   <li>Error tipográfico en el nombre de la salida</li>
 *   <li>La salida fue eliminada previamente del sistema</li>
 *   <li>Datos obsoletos en caché de la aplicación cliente</li>
 *   <li>Referencias rotas desde otras entidades</li>
 *   <li>Problemas de sincronización en ambientes distribuidos</li>
 * </ul>
 * 
 * <p><strong>Relación con el modelo de datos:</strong></p>
 * <pre>
 * TouristActivity (1) ──── (N) TouristOuting (1) ──── (N) Inscription
 *                                    ↑
 *                                    │
 *                      Esta excepción se lanza aquí
 * </pre>
 * 
 * <p><strong>Ejemplo de uso:</strong></p>
 * <pre>
 * try {
 *     DtTouristOuting outing = outingController.getTouristOutingInfo(
 *         activityName, 
 *         outingName
 *     );
 *     displayOutingDetails(outing);
 * } catch (TouristOutingDoesNotExistException e) {
 *     JOptionPane.showMessageDialog(null,
 *         "La salida seleccionada no existe o fue cancelada. " +
 *         "Por favor actualiza la lista de salidas disponibles.",
 *         "Salida no encontrada", JOptionPane.ERROR_MESSAGE);
 *     refreshOutingsList();
 * } catch (ActivityDoesNotExistException e) {
 *     JOptionPane.showMessageDialog(null,
 *         "La actividad asociada no existe.",
 *         "Error", JOptionPane.ERROR_MESSAGE);
 * }
 * </pre>
 * 
 * <p><strong>Consideraciones de diseño:</strong></p>
 * <ul>
 *   <li>Siempre validar existencia antes de operaciones críticas</li>
 *   <li>Implementar soft-delete si se requiere mantener histórico</li>
 *   <li>Cachear listas de salidas con TTL apropiado</li>
 *   <li>Proporcionar búsqueda aproximada para ayudar al usuario</li>
 * </ul>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.entity.TouristOuting
 * @see turismouyapp.core.entity.TouristActivity
 * @see turismouyapp.core.handler.TouristOutingAndInscrptionHandler
 * @see ActivityDoesNotExistException
 */
import jakarta.xml.ws.WebFault;
import turismouyapp.core.exceptions.fault.TouristOutingDoesNotExistFault;

@SuppressWarnings("serial")
@WebFault(name = "TouristOutingDoesNotExistFault", targetNamespace = "http://ws.turismouyapp/schema", faultBean = "turismouyapp.core.exceptions.fault.TouristOutingDoesNotExistFault")
public class TouristOutingDoesNotExistException extends Exception {

	private TouristOutingDoesNotExistFault faultInfo;

	/**
	 * Construye una nueva excepción de salida turística inexistente con el mensaje especificado.
	 * <p>
	 * El mensaje debería proporcionar contexto suficiente para que el usuario
	 * o desarrollador pueda identificar qué salida se intentó buscar y en qué
	 * contexto falló la búsqueda.
	 * </p>
	 * 
	 * <p><strong>Recomendaciones para el mensaje:</strong></p>
	 * <ul>
	 *   <li>Incluir nombre de la actividad y nombre de la salida</li>
	 *   <li>Sugerir acciones correctivas (verificar escritura, actualizar datos)</li>
	 *   <li>Evitar exponer detalles técnicos sensibles</li>
	 * </ul>
	 * 
	 * @param string Mensaje de detalle describiendo qué salida no fue encontrada
	 *               y el contexto de la búsqueda fallida
	 */
	public TouristOutingDoesNotExistException(String string) {
		super(string);
		this.faultInfo = new TouristOutingDoesNotExistFault(string);
	}

	public TouristOutingDoesNotExistException(String message, TouristOutingDoesNotExistFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public TouristOutingDoesNotExistFault getFaultInfo() {
		return faultInfo;
	}
}
