package turismouyapp.core.interfaces;

import java.util.List;

import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtRanking;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;

/**
 * Interfaz que define el contrato para el controlador de gestión de actividades turísticas.
 * <p>
 * Esta interfaz expone las operaciones de negocio para administrar actividades turísticas,
 * incluyendo alta, consulta, modificación, listado y ranking. Las actividades turísticas
 * son la entidad central del sistema, representando los servicios ofrecidos por proveedores
 * que los turistas pueden contratar.
 * </p>
 * 
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Gestión del ciclo de vida de actividades (alta, consulta, modificación)</li>
 *   <li>Validación de unicidad de nombres de actividades</li>
 *   <li>Consulta de actividades con sus salidas asociadas</li>
 *   <li>Generación de rankings por diversos criterios</li>
 *   <li>Cálculo de costos para turistas</li>
 *   <li>Filtrado de actividades por proveedor</li>
 * </ul>
 * 
 * <p><strong>Modelo de dominio relacionado:</strong></p>
 * <pre>
 * Supplier (1) ──── (N) TouristActivity (1) ──── (N) TouristOuting
 *                          ↓
 *                      (M) City
 *                      (M) Category
 * </pre>
 * 
 * <p><strong>Ejemplo de uso completo:</strong></p>
 * <pre>
 * ITouristActivityController activityController = 
 *     FactoryUyTourism.getInstance().getITouristActivityController();
 * 
 * // Crear nueva actividad
 * try {
 *     DtTouristActivity activity = new DtTouristActivity(
 *         "City Tour Montevideo",
 *         "Recorrido por los principales puntos de la ciudad",
 *         Duration.ofHours(3),
 *         2500.0f,
 *         LocalDate.now(),
 *         "Montevideo"
 *     );
 *     activityController.activityDataEntry(activity);
 *     
 *     // Consultar actividad con salidas
 *     DtActivityWithOutings details = 
 *         activityController.consultTouristActivityData("City Tour Montevideo");
 *     
 *     // Ver ranking
 *     DtRanking[] ranking = activityController.getActivityRanking();
 *     
 * } catch (RepeatedActivityNameException e) {
 *     System.err.println("El nombre de actividad ya existe");
 * } catch (ActivityDoesNotExistException e) {
 *     System.err.println("Actividad no encontrada");
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.controller.TouristActivityController
 * @see turismouyapp.core.handler.TouristActivityHandler
 * @see turismouyapp.core.entity.TouristActivity
 * @see turismouyapp.core.entity.Supplier
 */
public interface ITouristActivityController {

	/**
	 * Registra una nueva actividad turística en el sistema.
	 * <p>
	 * Este método crea una nueva actividad asociada a un proveedor, validando que
	 * el nombre sea único en el sistema. La actividad queda disponible para que se
	 * le asocien salidas turísticas posteriormente.
	 * </p>
	 * 
	 * <p><strong>Validaciones realizadas:</strong></p>
	 * <ul>
	 *   <li>Nombre único de actividad en el sistema</li>
	 *   <li>Existencia del proveedor asociado</li>
	 *   <li>Validez de la ciudad especificada</li>
	 *   <li>Costo mayor a cero</li>
	 *   <li>Duración positiva</li>
	 * </ul>
	 * 
	 * @param dtTouristActivity DTO conteniendo los datos de la actividad a crear:
	 *        nombre, descripción, duración, costo, ciudad, fecha alta, proveedor
	 * 
	 * @throws RepeatedActivityNameException Si ya existe una actividad con ese nombre
	 * 
	 * @see #consultTouristActivityData(String)
	 * @see #modifyActivity(DtTouristActivity)
	 */
	public void activityDataEntry(DtTouristActivity dtTouristActivity) throws RepeatedActivityNameException;
	
	/**
	 * Obtiene un listado con los nombres de todas las actividades turísticas registradas.
	 * <p>
	 * Este método retorna los nombres de todas las actividades del sistema sin
	 * filtrar por estado, proveedor o ciudad. Es útil para poblar listas de
	 * selección en interfaces de usuario.
	 * </p>
	 * 
	 * <p><strong>Nota:</strong> El método puede lanzar {@code ActivityDoesNotExistException}
	 * aunque conceptualmente parezca extraño. Esto ocurre en implementaciones donde
	 * se valida que exista al menos una actividad en el sistema.</p>
	 * 
	 * @return Array de strings con los nombres de todas las actividades.
	 *         Retorna array vacío si no hay actividades registradas.
	 *         Nunca retorna {@code null}.
	 * 
	 * @throws ActivityDoesNotExistException Si no hay actividades en el sistema
	 *         (depende de la implementación específica)
	 * 
	 * @see #listTouristActivitiesBySupplierNickname(String)
	 */
	public String[] listTouristActivities() throws ActivityDoesNotExistException;
	
	/**
	 * Consulta los datos completos de una actividad incluyendo todas sus salidas asociadas.
	 * <p>
	 * Este es el método más completo para obtener información de una actividad, ya que
	 * incluye no solo los datos básicos sino también todas las salidas programadas,
	 * con sus fechas, cupos, inscripciones, etc. Es ideal para vistas detalladas
	 * de actividad.
	 * </p>
	 * 
	 * <p><strong>Información incluida en el DTO retornado:</strong></p>
	 * <ul>
	 *   <li>Datos básicos: nombre, descripción, duración, costo, ciudad</li>
	 *   <li>Proveedor asociado</li>
	 *   <li>Lista de categorías asignadas</li>
	 *   <li>Todas las salidas (pasadas y futuras) con:
	 *     <ul>
	 *       <li>Fechas de inicio y fin</li>
	 *       <li>Lugar de encuentro</li>
	 *       <li>Cupo máximo y disponible</li>
	 *       <li>Lista de inscripciones</li>
	 *     </ul>
	 *   </li>
	 * </ul>
	 * 
	 * @param activityName Nombre único de la actividad a consultar
	 * 
	 * @return DTO completo de la actividad con todas sus salidas asociadas
	 * 
	 * @throws ActivityDoesNotExistException Si no existe una actividad con ese nombre
	 * 
	 * @see #consultTouristActivityBasicData(String)
	 * @see DtActivityWithOutings
	 */
	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException;
	
	public List<DtActivityWithOutings> listTouristActivityData() throws ActivityDoesNotExistException;
	
	/**
	 * Obtiene el ranking de actividades turísticas ordenadas por algún criterio.
	 * <p>
	 * Este método genera un listado ordenado de actividades basado en métricas
	 * de popularidad, calidad o engagement. Los criterios típicos incluyen:
	 * </p>
	 * <ul>
	 *   <li>Número total de inscripciones</li>
	 *   <li>Promedio de inscripciones por salida</li>
	 *   <li>Cantidad de salidas programadas</li>
	 *   <li>Valoraciones de turistas (si aplica)</li>
	 * </ul>
	 * 
	 * <p><strong>Uso típico:</strong></p>
	 * <pre>
	 * DtRanking[] ranking = activityController.getActivityRanking();
	 * for (int i = 0; i < Math.min(10, ranking.length); i++) {
	 *     System.out.println((i + 1) + ". " + ranking[i].getActivityName() + 
	 *                        " - " + ranking[i].getScore() + " inscripciones");
	 * }
	 * </pre>
	 * 
	 * @return Array de DTOs de ranking ordenados de mayor a menor puntuación.
	 *         Retorna array vacío si no hay actividades o datos insuficientes.
	 *         Nunca retorna {@code null}.
	 * 
	 * @see DtRanking
	 */
	public DtRanking[] getActivityRanking();
	
	/**
	 * Consulta solo los datos básicos de una actividad turística sin sus salidas.
	 * <p>
	 * Este método es más liviano que {@link #consultTouristActivityData(String)}
	 * ya que no incluye las salidas asociadas. Es útil para:
	 * </p>
	 * <ul>
	 *   <li>Formularios de edición de actividad</li>
	 *   <li>Vistas de lista donde no se necesitan las salidas</li>
	 *   <li>Validaciones rápidas de existencia</li>
	 *   <li>Consultas de performance donde no se necesitan relaciones</li>
	 * </ul>
	 * 
	 * @param activityName Nombre de la actividad a consultar
	 * 
	 * @return DTO con los datos básicos de la actividad (sin salidas)
	 * 
	 * @throws ActivityDoesNotExistException Si no existe la actividad especificada
	 * 
	 * @see #consultTouristActivityData(String)
	 * @see DtTouristActivity
	 */
	public DtTouristActivity consultTouristActivityBasicData(String activityName) throws ActivityDoesNotExistException;

	/**
	 * Obtiene el costo de una actividad turística aplicado a un turista específico.
	 * <p>
	 * Este método calcula el costo que un turista debe pagar por una actividad,
	 * aplicando posibles descuentos, promociones o ajustes según el perfil del
	 * turista (edad, nacionalidad, historial de compras, etc.).
	 * </p>
	 * 
	 * <p><strong>Factores que pueden afectar el costo:</strong></p>
	 * <ul>
	 *   <li>Costo base de la actividad</li>
	 *   <li>Descuentos por edad (niños, adultos mayores)</li>
	 *   <li>Descuentos por nacionalidad (residentes)</li>
	 *   <li>Promociones vigentes</li>
	 *   <li>Programas de fidelidad</li>
	 * </ul>
	 * 
	 * @param activityName Nombre de la actividad para la cual calcular el costo
	 * 
	 * @return Costo en la moneda del sistema (típicamente pesos uruguayos).
	 *         Siempre retorna un valor positivo o cero.
	 * 
	 * @throws ActivityDoesNotExistException Si la actividad especificada no existe
	 * 
	 * @see #consultTouristActivityBasicData(String)
	 */
	public float getActivityCostTourist(String activityName) throws ActivityDoesNotExistException;
	
	/**
	 * Modifica los datos de una actividad turística existente.
	 * <p>
	 * Este método permite actualizar información de una actividad previamente
	 * registrada. Solo ciertos campos son modificables para mantener la integridad
	 * de datos históricos y relaciones existentes.
	 * </p>
	 * 
	 * <p><strong>Campos típicamente modificables:</strong></p>
	 * <ul>
	 *   <li>Descripción de la actividad</li>
	 *   <li>Duración</li>
	 *   <li>Costo</li>
	 *   <li>Ciudad</li>
	 *   <li>Categorías asignadas</li>
	 * </ul>
	 * 
	 * <p><strong>Campos NO modificables:</strong></p>
	 * <ul>
	 *   <li>Nombre (es identificador único)</li>
	 *   <li>Proveedor asociado</li>
	 *   <li>Fecha de alta</li>
	 * </ul>
	 * 
	 * <p><strong>Nota:</strong> Las modificaciones no afectan salidas ya creadas
	 * ni inscripciones existentes. Solo aplican a nuevas salidas.</p>
	 * 
	 * @param dto DTO con los datos modificados de la actividad.
	 *            Debe incluir el nombre para identificar la actividad a modificar.
	 * 
	 * @see #consultTouristActivityBasicData(String)
	 * @see #activityDataEntry(DtTouristActivity)
	 */
	public void modifyActivity(DtTouristActivity dto);
	
	/**
	 * Lista todas las actividades turísticas creadas por un proveedor específico.
	 * <p>
	 * Este método filtra las actividades por proveedor, útil para vistas de
	 * "mis actividades" desde el perfil de un proveedor o para reportes
	 * administrativos por proveedor.
	 * </p>
	 * 
	 * <p><strong>Casos de uso:</strong></p>
	 * <ul>
	 *   <li>Panel de administración del proveedor</li>
	 *   <li>Reportes de actividad por proveedor</li>
	 *   <li>Filtros de búsqueda de actividades</li>
	 *   <li>Auditoría de contenido por proveedor</li>
	 * </ul>
	 * 
	 * @param nickname Nickname del proveedor cuyas actividades se desean listar
	 * 
	 * @return Array de strings con los nombres de las actividades del proveedor.
	 *         Retorna array vacío si el proveedor no tiene actividades registradas
	 *         o si el proveedor no existe.
	 *         Nunca retorna {@code null}.
	 * 
	 * @see #listTouristActivities()
	 * @see turismouyapp.core.entity.Supplier
	 */
	public String[] listTouristActivitiesBySupplierNickname(String nickname);
	
	public List<DtActivityWithOutings> listTouristActivitiesBySupplierNickName(String nickname) throws ActivityDoesNotExistException;
	
	public String[] listTouristActivitiesByStatus(TouristActivityStatus status) throws IllegalArgumentException;
	
	public void updateTouristActivityStatus(String activityName, TouristActivityStatus status) throws ActivityDoesNotExistException;
}

