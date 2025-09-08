package presentation;

import javax.swing.JInternalFrame;

import logic.interfaces.*;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtTouristActivity;
import logic.dto.DtTouristOuting;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import exceptions.ActivityDoesNotExistException;
import exceptions.TouristOutingDoesNotExistException;

import javax.swing.JTextField;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsultTouristOutingBKP extends JInternalFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JLabel lblTouristOutingName;
	private JLabel lblMaxNumTourists;
	private JLabel lblDeparturePoint;
	private JLabel lblDepartureDate;
	private JLabel lblDischargeDate;
    private JLabel lblInfoTouristOuting;
    private JButton btnSearchTouristOuting;
    private JButton btnCloseWindow;
	private DtActivityWithOutings activityWithOutingsData;

    
    public ConsultTouristOutingBKP(ITouristOutingAndInscriptionController itoic, ITouristActivityController itac) {

        controlTouristOutingAndInscription = itoic;
        controlTouristActivity = itac;
        
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consultar salida turistica");
        setBounds(10, 40, 360, 320);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 180, 105, 105, 5 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

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
        comboBoxTouristActivities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdSelectActivityActionPerformed(e);
			}
		});
        
        lblTouristOutings = new JLabel("Salidas turisticas");
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
        comboBoxTouristOutings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdTouristOutingActionPerformed(e);
			}
        });
        
        
        lblTouristOutingName = new JLabel("Nombre de salida turistica:");
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

        lblMaxNumTourists = new JLabel("Maxima cantidad de turistas:");
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

        lblDeparturePoint = new JLabel("Lugar de salida:");
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
        
        lblDepartureDate = new JLabel("Fecha y hora de salida:");
        lblDepartureDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDepartureDate = new GridBagConstraints();
        gbc_lblDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_lblDepartureDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartureDate.gridx = 0;
        gbc_lblDepartureDate.gridy = 6;
        getContentPane().add(lblDepartureDate, gbc_lblDepartureDate); 

        textFieldDepartureDate = new JTextField();
        textFieldDepartureDate.setEditable(false);
        textFieldDepartureDate.setToolTipText("Enter the date in dd/mm/yyyy format.");
        textFieldDepartureDate.setColumns(10);
        GridBagConstraints gbc_textFieldDepartureDate = new GridBagConstraints();
        gbc_textFieldDepartureDate.gridwidth = 2;
        gbc_textFieldDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldDepartureDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDepartureDate.gridx = 1;
        gbc_textFieldDepartureDate.gridy = 6;
        getContentPane().add(textFieldDepartureDate, gbc_textFieldDepartureDate);
        
        lblDischargeDate = new JLabel("Fecha de alta:");
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
        
        btnCloseWindow = new JButton("Cancelar");
        btnCloseWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
                setVisible(false);
            }
        });
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.fill = GridBagConstraints.BOTH;
        gbc_btnCancel.gridx = 2;
        gbc_btnCancel.gridy = 8;
        getContentPane().add(btnCloseWindow, gbc_btnCancel);
    }
    
    public void loadTouristActivities(){
        DefaultComboBoxModel<String> model; 
        try {                                    
        	String[] data = controlTouristActivity.listTouristActivities();
			System.out.println("Datos de actividades cargados: " + data);
			if (data != null) {
				String[] dataWithNull = new String[data.length + 1];
				dataWithNull[0] = null; // Primera opción nula
				System.arraycopy(data, 0, dataWithNull, 1, data.length);
				model = new DefaultComboBoxModel<String>(dataWithNull); 
				comboBoxTouristActivities.setModel(model);
			}
            
        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        }
    }
    
    protected void cmdSelectActivityActionPerformed(ActionEvent e) {
		String selectedActivity = (String) comboBoxTouristActivities.getSelectedItem();
		clearActivitiData();
		if (selectedActivity != null) {
			try {
				activityWithOutingsData = controlTouristActivity.consultTouristActivityData(selectedActivity);
				DtTouristActivity activityData = activityWithOutingsData.getActivity();
				if (activityData == null) {
					throw new RuntimeException("Error al obtener dtActivity.");
					
				}
				
				loadComboActSelectOutings(activityWithOutingsData.getOutings());
				
			} catch (ActivityDoesNotExistException ex) {
			}
		} 
	}
    
    private void loadComboActSelectOutings(Set<DtTouristOuting> outings) {
		DefaultComboBoxModel<String> model;
		// Crear un array con una opción nula al principio
		String[] data = outings.stream().map(DtTouristOuting::getOutingName).toArray(String[]::new);
		String[] dataWithNull = new String[data.length + 1];
		dataWithNull[0] = null; // Primera opción nula
		System.arraycopy(data, 0, dataWithNull, 1, data.length);
		model = new DefaultComboBoxModel<String>(dataWithNull);
		comboBoxTouristOutings.setModel(model);
	}
    
    protected void cmdTouristOutingActionPerformed(ActionEvent e) {
    	
    	String touristOutingName = (String) comboBoxTouristOutings.getSelectedItem();
    	clearOutingData();
    	
    	if (touristOutingName != null ) {
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		DtTouristOuting to;
    		
    		try {
        	
	            to = controlTouristOutingAndInscription.consultTouristOutingData(touristOutingName);
	            
	            textFieldTouristOutingName.setText(to.getOutingName());
	            textFieldMaxNumTourists.setText(String.valueOf(to.getMaxNumTourists()));
	        	textFieldDeparturePoint.setText(to.getDeparturePoint());
	        	textFieldDepartureDate.setText(to.getDepartureDate().format(formatter));
	        	textFieldDischargeDate.setText(to.getDischargeDate().format(formatter2));
	        	
        	}catch (TouristOutingDoesNotExistException e1) {
	            
	            JOptionPane.showMessageDialog(this, e1.getMessage(), "Consultar salida turistica", JOptionPane.ERROR_MESSAGE);
	            clearForm();
	        }
        }
    }
    
    private void clearForm() {
    	comboBoxTouristActivities.removeAllItems();
    	clearActivitiData();
    }
    private void clearActivitiData() {
    	comboBoxTouristOutings.removeAllItems();
    	clearOutingData();
    }
    
    private void clearOutingData() {
		textFieldTouristOutingName.setText("");
		textFieldMaxNumTourists.setText("");
		textFieldDeparturePoint.setText("");
		textFieldDepartureDate.setText("");
		textFieldDischargeDate.setText("");
	}
    
    public void init() {
    	clearForm();
    	loadTouristActivities();
    	
    }

}
