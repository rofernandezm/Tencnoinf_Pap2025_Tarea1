package presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedTouristOutingException;
import logic.dto.DtTouristOuting;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class TouristOutingRegistration extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ITouristOutingAndInscriptionController itoic;
	private ITouristActivityController itac;
	
	private JComboBox<String> comboBoxTouristActivities;
    private JLabel lblTouristActivities;
	private JTextField textFieldTouristOutingName;
	private JTextField textFieldMaxNumTourists;
	private JTextField textFieldDeparturePoint;
	private JSpinner spinnerDepartureDate;
	//private JTextField textFieldDepartureDate; Componente a verificar si se elimina
	private JLabel lblEnterTouristOutingName;
	private JLabel lblEnterMaxNumTourists;
	private JLabel lblEnterDeparturePoint;
	private JLabel lblEnterDepartureDate;
	private JButton btnConfirm;
	private JButton btnCancel;

	 public TouristOutingRegistration(ITouristOutingAndInscriptionController itoic, ITouristActivityController itac) {

        this.itoic = itoic;
        this.itac = itac;
//	 public TouristOutingRegistration(ITouristOutingAndInscriptionController itoic, ITouristActivityController icta) {

//        iControlTouristOutingAndInscription = itoic;
//        iControlTouristActivity = icta;


        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Alta de salida turistica");
        setBounds(10, 40, 360, 210);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 180, 105, 105, 5 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        dataCreateOuting();
    }
	 
	private void dataCreateOuting() {
		
		lblTouristActivities = new JLabel("Actividades turisticas");
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
        
        lblEnterTouristOutingName = new JLabel("Nombre de salida turistica:");
        lblEnterTouristOutingName.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterTouristOutingName = new GridBagConstraints();
        gbc_lblEnterTouristOutingName.fill = GridBagConstraints.BOTH;
        gbc_lblEnterTouristOutingName.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterTouristOutingName.gridx = 0;
        gbc_lblEnterTouristOutingName.gridy = 1;
        getContentPane().add(lblEnterTouristOutingName, gbc_lblEnterTouristOutingName);

        textFieldTouristOutingName = new JTextField();
        GridBagConstraints gbc_textFieldTouristOutingName = new GridBagConstraints();
        gbc_textFieldTouristOutingName.gridwidth = 2;
        gbc_textFieldTouristOutingName.fill = GridBagConstraints.BOTH;
        gbc_textFieldTouristOutingName.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldTouristOutingName.gridx = 1;
        gbc_textFieldTouristOutingName.gridy = 1;
        getContentPane().add(textFieldTouristOutingName, gbc_textFieldTouristOutingName);
        textFieldTouristOutingName.setColumns(10);

        lblEnterMaxNumTourists = new JLabel("Maxima cantidad de turistas:");
        lblEnterMaxNumTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterMaxNumTourists = new GridBagConstraints();
        gbc_lblEnterMaxNumTourists.fill = GridBagConstraints.BOTH;
        gbc_lblEnterMaxNumTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterMaxNumTourists.gridx = 0;
        gbc_lblEnterMaxNumTourists.gridy = 2;
        getContentPane().add(lblEnterMaxNumTourists, gbc_lblEnterMaxNumTourists);

        textFieldMaxNumTourists = new JTextField();
        GridBagConstraints gbc_textFieldMaxNumTourists = new GridBagConstraints();
        gbc_textFieldMaxNumTourists.gridwidth = 2;
        gbc_textFieldMaxNumTourists.fill = GridBagConstraints.BOTH;
        gbc_textFieldMaxNumTourists.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldMaxNumTourists.gridx = 1;
        gbc_textFieldMaxNumTourists.gridy = 2;
        getContentPane().add(textFieldMaxNumTourists, gbc_textFieldMaxNumTourists);
        textFieldMaxNumTourists.setColumns(10);

        lblEnterDeparturePoint = new JLabel("Lugar de salida:");
        lblEnterDeparturePoint.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterDeparturePoint = new GridBagConstraints();
        gbc_lblEnterDeparturePoint.fill = GridBagConstraints.BOTH;
        gbc_lblEnterDeparturePoint.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterDeparturePoint.gridx = 0;
        gbc_lblEnterDeparturePoint.gridy = 3;
        getContentPane().add(lblEnterDeparturePoint, gbc_lblEnterDeparturePoint); 

        textFieldDeparturePoint = new JTextField();
        GridBagConstraints gbc_textFieldDeparturePoint = new GridBagConstraints();
        gbc_textFieldDeparturePoint.gridwidth = 2;
        gbc_textFieldDeparturePoint.fill = GridBagConstraints.BOTH;
        gbc_textFieldDeparturePoint.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDeparturePoint.gridx = 1;
        gbc_textFieldDeparturePoint.gridy = 3;
        getContentPane().add(textFieldDeparturePoint, gbc_textFieldDeparturePoint);
        textFieldDeparturePoint.setColumns(10);
        
        lblEnterDepartureDate = new JLabel("Fecha y hora de salida:");
        lblEnterDepartureDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterDepartureDate = new GridBagConstraints();
        gbc_lblEnterDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_lblEnterDepartureDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterDepartureDate.gridx = 0;
        gbc_lblEnterDepartureDate.gridy = 4;
//        getContentPane().add(lblEnterDepartureDate, gbc_lblEnterDepartureDate); 

        // Spinner para LocalDateTime

        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
        spinnerDepartureDate = new JSpinner(dateModel);
        DateEditor timeEditor = new JSpinner.DateEditor(spinnerDepartureDate, "yyyy-MM-dd HH:mm");
        spinnerDepartureDate.setEditor(timeEditor);


        GridBagConstraints gbc_spinnerDepartureDate = new GridBagConstraints();
        gbc_spinnerDepartureDate.gridwidth = 2;
        gbc_spinnerDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_spinnerDepartureDate.insets = new Insets(0, 0, 5, 0);
        gbc_spinnerDepartureDate.gridx = 1;
        gbc_spinnerDepartureDate.gridy = 4;
        getContentPane().add(spinnerDepartureDate, gbc_spinnerDepartureDate);
    
        btnConfirm = new JButton("Confirmar");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdTouristOutingActionPerformed(arg0);
            }
        });

        GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
        gbc_btnConfirm.fill = GridBagConstraints.BOTH;
        gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
        gbc_btnConfirm.gridx = 1;
        gbc_btnConfirm.gridy = 5;
        getContentPane().add(btnConfirm, gbc_btnConfirm);

        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
                setVisible(false);
            }
        });
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.fill = GridBagConstraints.BOTH;
        gbc_btnCancel.gridx = 2;
        gbc_btnCancel.gridy = 5;
        getContentPane().add(btnCancel, gbc_btnCancel);
        
 //       init();
 }
	 
	
	
    protected void cmdTouristOutingActionPerformed(ActionEvent arg0) {
    	
    	//Capture the entered data
        String outingNameTO = this.textFieldTouristOutingName.getText();
        String maxNumTouristsTO = this.textFieldMaxNumTourists.getText();
        String departurePointTO = this.textFieldDeparturePoint.getText();

        Date date = (Date) spinnerDepartureDate.getValue();
        LocalDateTime departureDateTOldt = date.toInstant()
                                                 .atZone(java.time.ZoneId.systemDefault())
                                                 .toLocalDateTime();
        System.out.println("FECHA" + departureDateTOldt);
        
        LocalDate dischargeDateTO = LocalDate.now(); 
        
        String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();

	        if (checkFormulario()) {
	        	
	            int maxNumTouristsTOint = Integer.parseInt(maxNumTouristsTO); 
	        	//Create Dt to assign to the method
	            DtTouristOuting newTouristOuting = new DtTouristOuting(outingNameTO, maxNumTouristsTOint, departurePointTO, departureDateTOldt, dischargeDateTO, touristActivityName);
	            
	            try {
	            
	            	itoic.outingDataEntry(newTouristOuting);
	
	                // Success
	                JOptionPane.showMessageDialog(this, "La salida turistica fue creada exitosamente.", "Alta de salida turistica",
	                        JOptionPane.INFORMATION_MESSAGE);
	                
	             // I clean the internal frame before closing the window.
	                clearForm();
	                setVisible(false);
	
	            } catch (RepeatedTouristOutingException e) {
	                // Error message
	                JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de salida turistica", JOptionPane.ERROR_MESSAGE);
	                
	                textFieldTouristOutingName.setText("");
	            }
	            
	        }
        
    }

    private boolean checkFormulario() {
    
        String outingNameTO = this.textFieldTouristOutingName.getText();
        String maxNumTouristsTO = this.textFieldMaxNumTourists.getText();
        String departurePointTO = this.textFieldDeparturePoint.getText();
        //String departureDateTO = this.textFieldDepartureDate.getText();
        Date date = (Date) spinnerDepartureDate.getValue();
        LocalDateTime departureDateTOldt = date.toInstant()
                                                 .atZone(java.time.ZoneId.systemDefault())
                                                 .toLocalDateTime();
        
        String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();

        if (outingNameTO.isEmpty() || maxNumTouristsTO.isEmpty() || departurePointTO.isEmpty() || departureDateTOldt == null || touristActivityName == null){
            JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos", "Alta de salida turistica",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(maxNumTouristsTO);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El campo «Maxima cantidad de turistas» debe ser un número.", "Alta de salida turistica",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        

        return true;
    }

    private void clearForm() {
    	textFieldTouristOutingName.setText("");
    	textFieldMaxNumTourists.setText("");
    	textFieldDeparturePoint.setText("");
    	spinnerDepartureDate.setValue(new Date());
    	comboBoxTouristActivities.removeAllItems();
    }
    
    public void loadTouristActivities() {
    	DefaultComboBoxModel<String> model; 
		try {
			
			String[] data = itac.listTouristActivities();
			System.out.println("Datos de actividades cargados: " + data);
			if (data != null) {
				String[] dataWithNull = new String[data.length + 1];
				dataWithNull[0] = null; // Primera opción nula
				System.arraycopy(data, 0, dataWithNull, 1, data.length);
				model = new DefaultComboBoxModel<String>(dataWithNull); 
				comboBoxTouristActivities.setModel(model);
			}
				
		}catch (ActivityDoesNotExistException e) {
			comboBoxTouristActivities.setModel(new DefaultComboBoxModel<String>());
		}
    }
   
//        DefaultComboBoxModel<String> model; 
//        try {                                    
//            model = new DefaultComboBoxModel<String>(iControlTouristActivity.listTouristActivities()); 
//            comboBoxTouristActivities.setModel(model);
//        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        	//model = new DefaultComboBoxModel<String>(new String[] {"No existen actividades turísticas registradas."});
//        }
       
//   }
    
    public void init() {
    	clearForm();
    	loadTouristActivities();
    }
    
}
