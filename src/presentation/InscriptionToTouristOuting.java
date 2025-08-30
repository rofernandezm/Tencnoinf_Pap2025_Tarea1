/*El caso  de  uso  comienza  cuando  el  administrador  desea  realizar  una 
inscripción de un/a turista a una salida de una actividad turística. 
1. En primer  lugar,  el  administrador muestra  las  actividades  disponible.  El 
administrador  elige  una y  el  sistema  muestra  los  datos  básicos  de  las 
salidas vigentes, si existen. 
3. Luego, el sistema muestra la lista de todos/as  los/as turistas y  el  administrador  selecciona  el/la turista  que  quiere 
inscribir, la salida a la que lo/la quiere inscribir, la cantidad de turistas 
para la inscripción y la fecha de inscripción.
 En caso de que ya exista un registro de el/la turista a la salida turística o se 
haya  alcanzado  el  límite máximo  de  turistas,  el  administrador  podrá 
(dependiendo  del  caso): cambiar  la  salida seleccionada,  cambiar  el/la 
turista seleccionado/a o cancelar el caso de uso. Finalmente, el sistema 
realiza la inscripción de el/la turista a la salida en dicha fecha, registrando 
el costo de la inscripción.*/

package presentation;

import javax.swing.JInternalFrame;

import logic.interfaces.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import exceptions.ActivityDoesNotExistException;

import javax.swing.JTextField;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InscriptionToTouristOuting extends JInternalFrame{
	
	private ITouristOutingAndInscriptionController controlTouristOutingAndInscription;
	private ITouristActivityController controlTouristActivity;
	
	private JComboBox<String> comboBoxTouristActivities;
    private JLabel lblTouristActivities;
    
    

}
