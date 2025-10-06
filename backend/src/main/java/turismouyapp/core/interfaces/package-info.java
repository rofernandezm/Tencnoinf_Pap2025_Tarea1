/**
 * Interfaces de controladores del sistema TurismoUY.
 * <p>
 * Este paquete define las interfaces que exponen la funcionalidad de los controladores
 * de negocio. Estas interfaces implementan el patrón de diseño Facade, proporcionando
 * una API simplificada y desacoplada para la capa de presentación.
 * </p>
 * 
 * <h2>Interfaces Disponibles:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.interfaces.IUserController} - Operaciones de usuarios</li>
 *   <li>{@link turismouyapp.core.interfaces.ITouristActivityController} - Operaciones de actividades</li>
 *   <li>{@link turismouyapp.core.interfaces.ITouristOutingAndInscriptionController} - Operaciones de salidas e inscripciones</li>
 * </ul>
 * 
 * <h2>Ventajas del Uso de Interfaces:</h2>
 * <ul>
 *   <li>Desacoplamiento entre capas</li>
 *   <li>Facilita pruebas unitarias (mocking)</li>
 *   <li>Permite implementaciones alternativas</li>
 *   <li>Define contratos claros de la API</li>
 * </ul>
 * 
 * @see turismouyapp.core.controller
 * @see turismouyapp.core.factory.FactoryUyTourism
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.interfaces;
