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

import logic.dto.DtTouristOuting;
import logic.interfaces.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedTouristOutingException;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;

public class InscriptionToTouristOuting extends JInternalFrame{
	
	private ITouristOutingAndInscriptionController controlTouristOutingAndInscription;
	private ITouristActivityController controlTouristActivity;
	
	private JComboBox<String> comboBoxTouristActivities;
    private JLabel lblTouristActivities;
    private JComboBox<String> comboBoxTouristOutings;
    private JLabel lblTouristOutings;
    private JTextField textFieldTouristOutingName;
	private JTextField textFieldMaxNumTourists;
	private JTextField textFieldDeparturePoint;
	private JTextField textFieldDepartureDate;
	private JTextField textFieldDischargeDate;
	private JTextField textFieldNumTourists;
	private JTextField textFieldinscriptionDate;
	private JLabel lblTouristOutingName;
	private JLabel lblMaxNumTourists;
	private JLabel lblDeparturePoint;
	private JLabel lblDepartureDate;
	private JLabel lblDischargeDate;
	private JLabel lblEnterNumTourists;
	private JLabel lblinscriptionDate;
    private JLabel lblInfoTouristOuting;
    private JComboBox<String> comboBoxTourists;
    private JLabel lblTourists;
    private JButton btnConfirm;
	private JButton btnCancel;
	
	public public public InscriptionToTouristOuting(ITouristOutingAndInscriptionController itoic) {

        controlTouristOutingAndInscription = itoic;
        
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consult Tourist Outing");
        setBounds(10, 40, 360, 400);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 180, 105, 105, 5 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        lblTouristActivities = new JLabel("Tourist Activities");
        lblTouristActivities.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTouristActivities = new GridBagConstraints();
        gbc_lblTouristActivities.fill = GridBagConstraints.BOTH;
        gbc_lblTouristActivities.insets = new Insets(0, 0, 5, 5);
        gbc_lblTouristActivities.gridx = 0;
        gbc_lblTouristActivities.gridy = 0;
        getContentPane().add(lblTouristActivities, gbc_lblTouristActivities);
        
        comboBoxTouristActivities = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxTouristActivities = new GridBagConstraints();
        gbc_comboBoxTouristActivities.gridwidth = 2;
        gbc_comboBoxTouristActivities.fill = GridBagConstraints.BOTH;
        gbc_comboBoxTouristActivities.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxTouristActivities.gridx = 1;
        gbc_comboBoxTouristActivities.gridy = 0;
        getContentPane().add(comboBoxTouristActivities, gbc_comboBoxTouristActivities);
        //textFieldTouristOutingName.setColumns(10);
        
        lblTouristOutings = new JLabel("Tourist Outings");
        lblTouristOutings.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTouristOutings = new GridBagConstraints();
        gbc_lblTouristOutings.fill = GridBagConstraints.BOTH;
        gbc_lblTouristOutings.insets = new Insets(0, 0, 5, 5);
        gbc_lblTouristOutings.gridx = 0;
        gbc_lblTouristOutings.gridy = 1;
        getContentPane().add(lblTouristOutings , gbc_lblTouristOutings);
        
        comboBoxTouristOutings = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxTouristOutings = new GridBagConstraints();
        gbc_comboBoxTouristOutings.gridwidth = 2;
        gbc_comboBoxTouristOutings.fill = GridBagConstraints.BOTH;
        gbc_comboBoxTouristOutings.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxTouristOutings.gridx = 1;
        gbc_comboBoxTouristOutings.gridy = 1;
        getContentPane().add(comboBoxTouristOutings, gbc_comboBoxTouristOutings);
        //textFieldTouristOutingName.setColumns(10);
        
        lblInfoTouristOuting = new JLabel("Tourist Outing Information:");
        lblInfoTouristOuting.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblInfoTouristOuting = new GridBagConstraints();
        gbc_lblInfoTouristOuting.fill = GridBagConstraints.BOTH;
        gbc_lblInfoTouristOuting.insets = new Insets(0, 0, 5, 5);
        gbc_lblInfoTouristOuting.gridx = 0;
        gbc_lblInfoTouristOuting.gridy = 2;
        getContentPane().add(lblInfoTouristOuting, gbc_lblInfoTouristOuting);
        textFieldTouristOutingName.setColumns(10);
        
        lblTouristOutingName = new JLabel("Tourist outing name:");
        lblTouristOutingName.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTouristOutingName = new GridBagConstraints();
        gbc_lblTouristOutingName.fill = GridBagConstraints.BOTH;
        gbc_lblTouristOutingName.insets = new Insets(0, 0, 5, 5);
        gbc_lblTouristOutingName.gridx = 0;
        gbc_lblTouristOutingName.gridy = 3;
        getContentPane().add(lblTouristOutingName, gbc_lblTouristOutingName);

        textFieldTouristOutingName = new JTextField();
        textFieldTouristOutingName.setEditable(false);
        GridBagConstraints gbc_textFieldTouristOutingName = new GridBagConstraints();
        gbc_textFieldTouristOutingName.gridwidth = 2;
        gbc_textFieldTouristOutingName.fill = GridBagConstraints.BOTH;
        gbc_textFieldTouristOutingName.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldTouristOutingName.gridx = 1;
        gbc_textFieldTouristOutingName.gridy = 3;
        getContentPane().add(textFieldTouristOutingName, gbc_textFieldTouristOutingName);
        textFieldTouristOutingName.setColumns(10);

        lblMaxNumTourists = new JLabel("Maximum number of tourists:");
        lblMaxNumTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblMaxNumTourists = new GridBagConstraints();
        gbc_lblMaxNumTourists.fill = GridBagConstraints.BOTH;
        gbc_lblMaxNumTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblMaxNumTourists.gridx = 0;
        gbc_lblMaxNumTourists.gridy = 4;
        getContentPane().add(lblMaxNumTourists, gbc_lblMaxNumTourists);

        textFieldMaxNumTourists = new JTextField();
        textFieldMaxNumTourists.setEditable(false);
        GridBagConstraints gbc_textFieldMaxNumTourists = new GridBagConstraints();
        gbc_textFieldMaxNumTourists.gridwidth = 2;
        gbc_textFieldMaxNumTourists.fill = GridBagConstraints.BOTH;
        gbc_textFieldMaxNumTourists.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldMaxNumTourists.gridx = 1;
        gbc_textFieldMaxNumTourists.gridy = 4;
        getContentPane().add(textFieldMaxNumTourists, gbc_textFieldMaxNumTourists);
        textFieldMaxNumTourists.setColumns(10);

        lblDeparturePoint = new JLabel("Departure point:");
        lblDeparturePoint.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDeparturePoint = new GridBagConstraints();
        gbc_lblDeparturePoint.fill = GridBagConstraints.BOTH;
        gbc_lblDeparturePoint.insets = new Insets(0, 0, 5, 5);
        gbc_lblDeparturePoint.gridx = 0;
        gbc_lblDeparturePoint.gridy = 5;
        getContentPane().add(lblDeparturePoint, gbc_lblDeparturePoint); 

        textFieldDeparturePoint = new JTextField();
        textFieldDeparturePoint.setEditable(false);
        GridBagConstraints gbc_textFieldDeparturePoint = new GridBagConstraints();
        gbc_textFieldDeparturePoint.gridwidth = 2;
        gbc_textFieldDeparturePoint.fill = GridBagConstraints.BOTH;
        gbc_textFieldDeparturePoint.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDeparturePoint.gridx = 1;
        gbc_textFieldDeparturePoint.gridy = 5;
        getContentPane().add(textFieldDeparturePoint, gbc_textFieldDeparturePoint);
        textFieldDeparturePoint.setColumns(10);
        
        lblDepartureDate = new JLabel("Departure date:");
        lblDepartureDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDepartureDate = new GridBagConstraints();
        gbc_lblDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_lblDepartureDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartureDate.gridx = 0;
        gbc_lblDepartureDate.gridy = 6;
        getContentPane().add(lblDepartureDate, gbc_lblDepartureDate); 

        textFieldDepartureDate = new JTextField();
        textFieldDepartureDate.setEditable(false);
        textFieldDepartureDate.setColumns(10);
        GridBagConstraints gbc_textFieldDepartureDate = new GridBagConstraints();
        gbc_textFieldDepartureDate.gridwidth = 2;
        gbc_textFieldDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldDepartureDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDepartureDate.gridx = 1;
        gbc_textFieldDepartureDate.gridy = 6;
        getContentPane().add(textFieldDepartureDate, gbc_textFieldDepartureDate);
        
        lblDischargeDate = new JLabel("Discharge date:");
        lblDischargeDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDischargeDate = new GridBagConstraints();
        gbc_lblDischargeDate.fill = GridBagConstraints.BOTH;
        gbc_lblDischargeDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDischargeDate.gridx = 0;
        gbc_lblDischargeDate.gridy = 7;
        getContentPane().add(lblDischargeDate, gbc_lblDischargeDate); 

        textFieldDischargeDate = new JTextField();
        textFieldDischargeDate.setEditable(false);
        textFieldDischargeDate.setColumns(10);
        GridBagConstraints gbc_textFieldDischargeDate = new GridBagConstraints();
        gbc_textFieldDischargeDate.gridwidth = 2;
        gbc_textFieldDischargeDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldDischargeDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDischargeDate.gridx = 1;
        gbc_textFieldDischargeDate.gridy = 7;
        getContentPane().add(textFieldDischargeDate, gbc_textFieldDischargeDate);
        
        lblTourists = new JLabel("Selected tourist");
        lblTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTourists = new GridBagConstraints();
        gbc_lblTourists.fill = GridBagConstraints.BOTH;
        gbc_lblTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblTourists.gridx = 0;
        gbc_lblTourists.gridy = 8;
        getContentPane().add(lblTourists, gbc_lblTourists);
        
        comboBoxTourists = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxTourists = new GridBagConstraints();
        gbc_comboBoxTourists.gridwidth = 2;
        gbc_comboBoxTourists.fill = GridBagConstraints.BOTH;
        gbc_comboBoxTourists.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxTourists.gridx = 1;
        gbc_comboBoxTourists.gridy = 8;
        getContentPane().add(comboBoxTourists, gbc_comboBoxTourists);
        //textFieldTourists.setColumns(10);
        
        lblEnterNumTourists = new JLabel("Number of tourists:");
        lblEnterNumTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterNumTourists = new GridBagConstraints();
        gbc_lblEnterNumTourists.fill = GridBagConstraints.BOTH;
        gbc_lblEnterNumTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterNumTourists.gridx = 0;
        gbc_lblEnterNumTourists.gridy = 9;
        getContentPane().add(lblEnterNumTourists, gbc_lblEnterNumTourists);

        textFieldNumTourists = new JTextField();
        GridBagConstraints gbc_textFieldNumTourists = new GridBagConstraints();
        gbc_textFieldNumTourists.gridwidth = 2;
        gbc_textFieldNumTourists.fill = GridBagConstraints.BOTH;
        gbc_textFieldNumTourists.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNumTourists.gridx = 1;
        gbc_textFieldNumTourists.gridy = 9;
        getContentPane().add(textFieldNumTourists, gbc_textFieldNumTourists);
        textFieldNumTourists.setColumns(10);
        
        lblinscriptionDate = new JLabel("Discharge date:");
        lblinscriptionDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblinscriptionDate = new GridBagConstraints();
        gbc_lblinscriptionDate.fill = GridBagConstraints.BOTH;
        gbc_lblinscriptionDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblinscriptionDate.gridx = 0;
        gbc_lblinscriptionDate.gridy = 10;
        getContentPane().add(lblinscriptionDate, gbc_lblinscriptionDate); 

        textFieldinscriptionDate = new JTextField();
        textFieldinscriptionDate.setEditable(false);
        textFieldinscriptionDate.setColumns(10);
        GridBagConstraints gbc_textFieldinscriptionDate = new GridBagConstraints();
        gbc_textFieldinscriptionDate.gridwidth = 2;
        gbc_textFieldinscriptionDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldinscriptionDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldinscriptionDate.gridx = 1;
        gbc_textFieldinscriptionDate.gridy = 10;
        getContentPane().add(textFieldinscriptionDate, gbc_textFieldinscriptionDate);
        
        btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdTouristOutingActionPerformed(arg0);
            }
        });

        GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
        gbc_btnConfirm.fill = GridBagConstraints.BOTH;
        gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
        gbc_btnConfirm.gridx = 1;
        gbc_btnConfirm.gridy = 11;
        getContentPane().add(btnConfirm, gbc_btnConfirm);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
                setVisible(false);
            }
        });
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.fill = GridBagConstraints.BOTH;
        gbc_btnCancel.gridx = 2;
        gbc_btnCancel.gridy = 11;
        getContentPane().add(btnCancel, gbc_btnCancel);
    }
    
    public void loadTouristActivities() {
        DefaultComboBoxModel<String> model; 
        try {                                    
            model = new DefaultComboBoxModel<String>(controlTouristActivity.listTouristActivities()); 
            comboBoxTouristActivities.setModel(model);        
        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        }
    }
    
    public void loadTouristOutings() {
        DefaultComboBoxModel<String> model; 
        try {                                    
            model = new DefaultComboBoxModel<String>(controlTouristActivity.listTouristActivities()); 
            comboBoxTouristActivities.setModel(model);        
        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        }
    }
    
    protected void cmdTouristOutingActionPerformed(ActionEvent e) {
        /*DtActivityWithOuting tawo;
        try {
            tawo = controlUsr.verInfoUsuario(textFieldCI.getText());
            textFieldNombre.setText(du.getNombre());
            textFieldApellido.setText(du.getApellido());
        } catch (UsuarioNoExisteException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage(), "Buscar Usuario", JOptionPane.ERROR_MESSAGE);
            limpiarFormulario();*/
        }
    
    /*
     protected void cmdTouristOutingActionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
	
        String outingNameTO = this.textFieldTouristOutingName.getText();
        String maxNumTouristsTO = this.textFieldMaxNumTourists.getText();
        String departurePointTO = this.textFieldDeparturePoint.getText();
        String departureDateTO = this.textFieldDepartureDate.getText();
        
        LocalDateTime departureDateTOldt = LocalDateTime.parse(departureDateTO);
        LocalDate dischargeDateTO = LocalDate.now();
        int maxNumTouristsTOint = Integer.parseInt(maxNumTouristsTO); 
        
        DtTouristOuting newTouristOuting = new DtTouristOuting(outingNameTO, maxNumTouristsTOint, departurePointTO, departureDateTOldt, dischargeDateTO);
        
        String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();

        if (checkFormulario()) {
            try {
            
            	TouristOutingAndInscriptionController.outingDataEntry(newTouristOuting, touristActivityName);

                // Success
                JOptionPane.showMessageDialog(this, "The tourist outing has been successfully created.", "Tourist Outing Registration",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (RepeatedTouristOutingException e) {
                // Error message
                JOptionPane.showMessageDialog(this, e.getMessage(), "Tourist Outing Registration", JOptionPane.ERROR_MESSAGE);
            }
            
            // I clean the internal frame before closing the window.
            clearForm();
            setVisible(false);
        }
    }

    private boolean checkFormulario() {
    
        String outingNameTO = this.textFieldTouristOutingName.getText();
        String maxNumTouristsTO = this.textFieldMaxNumTourists.getText();
        String departurePointTO = this.textFieldDeparturePoint.getText();
        String departureDateTO = this.textFieldDepartureDate.getText();
        String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();

        if (outingNameTO.isEmpty() || maxNumTouristsTO.isEmpty() || departurePointTO.isEmpty() || departureDateTO.isEmpty() || touristActivityName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill all the fields", "Tourist Outing Registration",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(maxNumTouristsTO);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Maximum number of tourists field should be a number", "Tourist Outing Registration",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }*/
    
    private void clearForm() {
    	comboBoxTouristActivities.removeAllItems();
    	comboBoxTouristOutings.removeAllItems();
    	textFieldTouristOutingName.setText("");
    	textFieldMaxNumTourists.setText("");
    	textFieldDeparturePoint.setText("");
    	textFieldDepartureDate.setText("");
    	textFieldDischargeDate.setText("");
    }
}
    