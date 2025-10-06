package turismouyapp.core.interfaces;

import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.DtUserProfile;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;

/**
 * Interfaz que define el contrato para el controlador de gestión de usuarios en TurismoUY.
 * <p>
 * Esta interfaz expone las operaciones de negocio relacionadas con usuarios (turistas y proveedores),
 * incluyendo alta, consulta, modificación y listado. Actúa como fachada entre la capa de presentación
 * y la lógica de negocio, siguiendo el patrón de diseño Controller.
 * </p>
 * 
 * <p><strong>Responsabilidades:</strong></p>
 * <ul>
 *   <li>Gestión del ciclo de vida de usuarios (alta, consulta, modificación)</li>
 *   <li>Validación de unicidad de nicknames y emails</li>
 *   <li>Manejo del flujo de registro (entrada, confirmación, cancelación)</li>
 *   <li>Consultas de información de usuarios por diferentes criterios</li>
 *   <li>Listado de usuarios por tipo (turistas, proveedores, todos)</li>
 * </ul>
 * 
 * <p><strong>Patrón de diseño implementado:</strong></p>
 * <pre>
 * desktop Layer    →    IUserController    →    UserHandler    →    JPA/Entity
 *    (Swing/Servlets)           (Interface)        (Business Logic)    (Persistence)
 * </pre>
 * 
 * <p><strong>Ejemplo de uso desde Swing:</strong></p>
 * <pre>
 * IUserController userController = FactoryUyTourism.getInstance().getIUserController();
 * 
 * // Registrar nuevo turista
 * try {
 *     DtTourist dtTourist = new DtTourist(nickname, name, lastName, email, birthDate, nationality);
 *     userController.dataEntry(dtTourist);
 *     userController.confirmRegistration();
 *     JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
 * } catch (RepeatedUserEmailException | RepeatedUserNicknameException e) {
 *     JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
 *     userController.cancelRegistration();
 * }
 * </pre>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.controller.UserController
 * @see turismouyapp.core.handler.UserHandler
 * @see turismouyapp.core.entity.User
 * @see turismouyapp.core.entity.Tourist
 * @see turismouyapp.core.entity.Supplier
 */
public interface IUserController {

	/**
	 * Inicia el proceso de registro de un nuevo usuario (turista o proveedor).
	 * <p>
	 * Este método recibe los datos del usuario a través de un DTO y los valida
	 * contra las restricciones de unicidad de nickname y email. Si las validaciones
	 * pasan, los datos quedan pendientes de confirmación (ver {@link #confirmRegistration()}).
	 * </p>
	 * 
	 * <p><strong>Validaciones realizadas:</strong></p>
	 * <ul>
	 *   <li>Nickname único en el sistema</li>
	 *   <li>Email único en el sistema</li>
	 *   <li>Formato de email válido (realizado en capa de presentación típicamente)</li>
	 *   <li>Campos obligatorios completos</li>
	 * </ul>
	 * 
	 * @param dtUser DTO conteniendo los datos del usuario a registrar.
	 *               Puede ser {@link turismouyapp.core.dto.DtTourist} o
	 *               {@link turismouyapp.core.dto.DtSupplier}
	 * 
	 * @throws RepeatedUserEmailException Si el email ya está registrado en el sistema
	 * @throws RepeatedUserNicknameException Si el nickname ya existe en el sistema
	 * 
	 * @see #confirmRegistration()
	 * @see #cancelRegistration()
	 */
	public void dataEntry(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException;

	/**
	 * Cancela el proceso de registro actual descartando los datos ingresados.
	 * <p>
	 * Este método limpia cualquier dato temporal almacenado durante el proceso
	 * de registro iniciado con {@link #dataEntry(DtUser)}. Permite al usuario
	 * abortar el registro sin persistir cambios.
	 * </p>
	 * 
	 * <p><strong>Casos de uso:</strong></p>
	 * <ul>
	 *   <li>Usuario decide no completar el registro</li>
	 *   <li>Ocurre un error de validación y se debe reintentar</li>
	 *   <li>Usuario cierra el formulario de registro</li>
	 * </ul>
	 * 
	 * @see #dataEntry(DtUser)
	 * @see #confirmRegistration()
	 */
	public void cancelRegistration();

	/**
	 * Confirma y persiste el registro del usuario iniciado con {@link #dataEntry(DtUser)}.
	 * <p>
	 * Este método finaliza el proceso de registro almacenando permanentemente
	 * los datos del usuario en la base de datos. Debe ser llamado después de
	 * {@code dataEntry()} para completar el flujo de alta de usuario.
	 * </p>
	 * 
	 * <p><strong>Flujo de registro completo:</strong></p>
	 * <pre>
	 * 1. dataEntry(dtUser)         → Validar y almacenar temporalmente
	 * 2. confirmRegistration()     → Persistir en BD
	 *    O
	 * 2. cancelRegistration()      → Descartar datos
	 * </pre>
	 * 
	 * @see #dataEntry(DtUser)
	 * @see #cancelRegistration()
	 */
	public void confirmRegistration();

	/**
	 * Obtiene un listado con los nicknames de todos los usuarios del sistema.
	 * <p>
	 * Este método retorna todos los usuarios registrados sin distinción de tipo
	 * (turistas y proveedores juntos). Útil para búsquedas generales y selección
	 * de usuarios sin filtrar por rol.
	 * </p>
	 * 
	 * @return Array de strings con los nicknames de todos los usuarios.
	 *         Retorna array vacío si no hay usuarios registrados.
	 *         Nunca retorna {@code null}.
	 * 
	 * @see #listTourists()
	 * @see #listSuppliers()
	 */
	public String[] listUsers();

	/**
	 * Obtiene un listado con los nicknames de todos los turistas registrados.
	 * <p>
	 * Este método filtra solo los usuarios de tipo Tourist, excluyendo a los
	 * proveedores. Útil para operaciones específicas de turistas como
	 * inscripciones a salidas turísticas.
	 * </p>
	 * 
	 * @return Array de strings con los nicknames de turistas.
	 *         Retorna array vacío si no hay turistas registrados.
	 *         Nunca retorna {@code null}.
	 * 
	 * @see #listUsers()
	 * @see #listSuppliers()
	 * @see turismouyapp.core.entity.Tourist
	 */
	public String[] listTourists();

	/**
	 * Obtiene un listado con los nicknames de todos los proveedores registrados.
	 * <p>
	 * Este método filtra solo los usuarios de tipo Supplier, excluyendo a los
	 * turistas. Útil para operaciones específicas de proveedores como asignación
	 * de actividades turísticas.
	 * </p>
	 * 
	 * @return Array de strings con los nicknames de proveedores.
	 *         Retorna array vacío si no hay proveedores registrados.
	 *         Nunca retorna {@code null}.
	 * 
	 * @see #listUsers()
	 * @see #listTourists()
	 * @see turismouyapp.core.entity.Supplier
	 */
	public String[] listSuppliers();

	/**
	 * Obtiene el perfil completo de un usuario específico por su nickname.
	 * <p>
	 * Este método retorna un DTO con toda la información pública del usuario,
	 * incluyendo datos específicos según sea turista (nacionalidad, fecha nacimiento)
	 * o proveedor (descripción, link). Es el método principal para consultas de perfil.
	 * </p>
	 * 
	 * <p><strong>Información retornada:</strong></p>
	 * <ul>
	 *   <li>Datos comunes: nickname, nombre, apellido, email</li>
	 *   <li>Si es turista: nacionalidad, fecha de nacimiento, inscripciones</li>
	 *   <li>Si es proveedor: descripción, link, actividades</li>
	 * </ul>
	 * 
	 * @param nickname El nickname único del usuario a consultar
	 * 
	 * @return DTO con el perfil del usuario. Puede ser {@link turismouyapp.core.dto.DtTouristProfile}
	 *         o {@link turismouyapp.core.dto.DtSupplierProfile} según el tipo de usuario.
	 *         Retorna {@code null} si el usuario no existe.
	 * 
	 * @see #consultUserData(String)
	 */
	public DtUserProfile selectUser(String nickname);

	/**
	 * Consulta los datos básicos de un usuario para edición o visualización simple.
	 * <p>
	 * Este método es similar a {@link #selectUser(String)} pero retorna un DTO
	 * más liviano, típicamente usado para formularios de edición o vistas resumidas.
	 * </p>
	 * 
	 * @param nickname El nickname del usuario a consultar
	 * 
	 * @return DTO con los datos del usuario.
	 *         Retorna {@code null} si el usuario no existe.
	 * 
	 * @see #selectUser(String)
	 * @see #modifyUserDate(DtUser)
	 */
	public DtUser consultUserData(String nickname);

	/**
	 * Modifica los datos de un usuario existente.
	 * <p>
	 * Este método permite actualizar información de un usuario previamente registrado.
	 * Valida que las modificaciones (especialmente de email) no violen restricciones
	 * de unicidad con otros usuarios.
	 * </p>
	 * 
	 * <p><strong>Campos típicamente modificables:</strong></p>
	 * <ul>
	 *   <li>Nombre y apellido</li>
	 *   <li>Email (validando unicidad)</li>
	 *   <li>Para turistas: nacionalidad</li>
	 *   <li>Para proveedores: descripción, link</li>
	 * </ul>
	 * 
	 * <p><strong>Campos NO modificables:</strong></p>
	 * <ul>
	 *   <li>Nickname (es identificador único inmutable)</li>
	 *   <li>Fecha de nacimiento (para turistas)</li>
	 *   <li>Tipo de usuario (turista/proveedor)</li>
	 * </ul>
	 * 
	 * @param dtUser DTO con los datos modificados del usuario.
	 *               Debe incluir el nickname para identificar al usuario a modificar.
	 * 
	 * @see #consultUserData(String)
	 */
	public void modifyUserDate(DtUser dtUser);
}
