/**
 * Controladores de lógica de negocio del sistema TurismoUY.
 * <p>
 * Este paquete contiene los controladores que implementan las operaciones de negocio
 * principales del sistema. Cada controlador maneja un dominio específico y actúa como
 * intermediario entre la capa de presentación y los handlers de persistencia.
 * </p>
 * 
 * <h2>Controladores Disponibles:</h2>
 * <ul>
 *   <li>{@link turismouyapp.core.controller.UserController} - Gestión de usuarios (turistas y proveedores)</li>
 *   <li>{@link turismouyapp.core.controller.TouristActivityController} - Gestión de actividades turísticas</li>
 *   <li>{@link turismouyapp.core.controller.TouristOutingAndInscriptionController} - Gestión de salidas e inscripciones</li>
 * </ul>
 * 
 * <h2>Responsabilidades:</h2>
 * <ul>
 *   <li>Validación de reglas de negocio</li>
 *   <li>Coordinación entre múltiples handlers</li>
 *   <li>Transformación entre DTOs y entidades</li>
 *   <li>Manejo de transacciones de negocio</li>
 * </ul>
 * 
 * @see turismouyapp.core.interfaces
 * @see turismouyapp.core.handler
 * @since 1.0.0
 * @version 1.0.0
 * @author TurismoUY Team
 */
package turismouyapp.core.controller;
