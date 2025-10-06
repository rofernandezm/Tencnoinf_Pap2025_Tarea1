/**
 * Factory pattern para creación de controladores.
 * <p>
 * Este paquete contiene la implementación del patrón Factory para la creación
 * de instancias de controladores. Proporciona un punto centralizado de acceso
 * a los controladores del sistema.
 * </p>
 * 
 * <h2>Clase Principal:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.factory.FactoryUyTourism} - Factory singleton para controladores</li>
 * </ul>
 * 
 * <h2>Patrón de Diseño:</h2>
 * <p>
 * Implementa el patrón <strong>Abstract Factory</strong> combinado con <strong>Singleton</strong>,
 * permitiendo la creación consistente de objetos relacionados sin especificar sus clases concretas.
 * </p>
 * 
 * <h2>Uso:</h2>
 * <pre>{@code
 * FactoryUyTourism factory = FactoryUyTourism.getInstance();
 * IUserController userCtrl = factory.getIUserController();
 * ITouristActivityController actCtrl = factory.getITouristActivityController();
 * }</pre>
 * 
 * @see turismouyapp.core.interfaces
 * @see turismouyapp.core.controller
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.factory;
