package presentation;

import javax.swing.JInternalFrame;

import logic.dto.DtActivityWithOutings;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.interfaces.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedInscriptionToTouristOutingException;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.awt.event.ActionEvent;

public class InscriptionToTouristOuting extends JInternalFrame{
	
	private ITouristOutingAndInscriptionController controlTouristOutingAndInscription;
	private ITouristActivityController controlTouristActivity;
	private IUserController controlUser;
	
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
	private JTextField textFieldTotalRegistrationCost;
	private JLabel lblTouristOutingName;
	private JLabel lblMaxNumTourists;
	private JLabel lblDeparturePoint;
	private JLabel lblDepartureDate;
	private JLabel lblDischargeDate;
	private JLabel lblEnterNumTourists;
	private JLabel lblinscriptionDate;
	private JLabel lblTotalRegistrationCost;
    //private JLabel lblInfoTouristOuting;
    private JComboBox<String> comboBoxTourists;
    private JLabel lblTourists;
    private JButton btnConfirm;
	private JButton btnCancel;
	
	public InscriptionToTouristOuting(ITouristOutingAndInscriptionController itoic, ITouristActivityController itac, IUserController iuc) {

        controlTouristOutingAndInscription = itoic;
        controlTouristActivity = itac;
        controlUser = iuc;
        
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Inscripcion a salida turistica");
        setBounds(10, 40, 360, 400);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 180, 105, 105, 5 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        lblTouristActivities = new JLabel("Actividades turisticas:");
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
        
        lblTouristOutings = new JLabel("Salidas turisticas:");
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
        
        /*
        lblInfoTouristOuting = new JLabel("Tourist Outing Information:");
        lblInfoTouristOuting.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblInfoTouristOuting = new GridBagConstraints();
        gbc_lblInfoTouristOuting.fill = GridBagConstraints.BOTH;
        gbc_lblInfoTouristOuting.insets = new Insets(0, 0, 5, 5);
        gbc_lblInfoTouristOuting.gridx = 0;
        gbc_lblInfoTouristOuting.gridy = 2;
        getContentPane().add(lblInfoTouristOuting, gbc_lblInfoTouristOuting);
        textFieldTouristOutingName.setColumns(10);*/
        
        lblTouristOutingName = new JLabel("Nombre de salida turistica:");
        lblTouristOutingName.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTouristOutingName = new GridBagConstraints();
        gbc_lblTouristOutingName.fill = GridBagConstraints.BOTH;
        gbc_lblTouristOutingName.insets = new Insets(0, 0, 5, 5);
        gbc_lblTouristOutingName.gridx = 0;
        gbc_lblTouristOutingName.gridy = 2;
        getContentPane().add(lblTouristOutingName, gbc_lblTouristOutingName);

        textFieldTouristOutingName = new JTextField();
        textFieldTouristOutingName.setEnabled(false);
        textFieldTouristOutingName.setEditable(false);
        GridBagConstraints gbc_textFieldTouristOutingName = new GridBagConstraints();
        gbc_textFieldTouristOutingName.gridwidth = 2;
        gbc_textFieldTouristOutingName.fill = GridBagConstraints.BOTH;
        gbc_textFieldTouristOutingName.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldTouristOutingName.gridx = 1;
        gbc_textFieldTouristOutingName.gridy = 2;
        getContentPane().add(textFieldTouristOutingName, gbc_textFieldTouristOutingName);
        textFieldTouristOutingName.setColumns(10);

        lblMaxNumTourists = new JLabel("Maxima cantidad de turistas:");
        lblMaxNumTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblMaxNumTourists = new GridBagConstraints();
        gbc_lblMaxNumTourists.fill = GridBagConstraints.BOTH;
        gbc_lblMaxNumTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblMaxNumTourists.gridx = 0;
        gbc_lblMaxNumTourists.gridy = 3;
        getContentPane().add(lblMaxNumTourists, gbc_lblMaxNumTourists);

        textFieldMaxNumTourists = new JTextField();
        textFieldMaxNumTourists.setEnabled(false);
        textFieldMaxNumTourists.setEditable(false);
        GridBagConstraints gbc_textFieldMaxNumTourists = new GridBagConstraints();
        gbc_textFieldMaxNumTourists.gridwidth = 2;
        gbc_textFieldMaxNumTourists.fill = GridBagConstraints.BOTH;
        gbc_textFieldMaxNumTourists.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldMaxNumTourists.gridx = 1;
        gbc_textFieldMaxNumTourists.gridy = 3;
        getContentPane().add(textFieldMaxNumTourists, gbc_textFieldMaxNumTourists);
        textFieldMaxNumTourists.setColumns(10);

        lblDeparturePoint = new JLabel("Lugar de salida:");
        lblDeparturePoint.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDeparturePoint = new GridBagConstraints();
        gbc_lblDeparturePoint.fill = GridBagConstraints.BOTH;
        gbc_lblDeparturePoint.insets = new Insets(0, 0, 5, 5);
        gbc_lblDeparturePoint.gridx = 0;
        gbc_lblDeparturePoint.gridy = 4;
        getContentPane().add(lblDeparturePoint, gbc_lblDeparturePoint); 

        textFieldDeparturePoint = new JTextField();
        textFieldDeparturePoint.setEnabled(false);
        textFieldDeparturePoint.setEditable(false);
        GridBagConstraints gbc_textFieldDeparturePoint = new GridBagConstraints();
        gbc_textFieldDeparturePoint.gridwidth = 2;
        gbc_textFieldDeparturePoint.fill = GridBagConstraints.BOTH;
        gbc_textFieldDeparturePoint.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDeparturePoint.gridx = 1;
        gbc_textFieldDeparturePoint.gridy = 4;
        getContentPane().add(textFieldDeparturePoint, gbc_textFieldDeparturePoint);
        textFieldDeparturePoint.setColumns(10);
        
        lblDepartureDate = new JLabel("Fecha y hora de salida:");
        lblDepartureDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDepartureDate = new GridBagConstraints();
        gbc_lblDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_lblDepartureDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartureDate.gridx = 0;
        gbc_lblDepartureDate.gridy = 5;
        getContentPane().add(lblDepartureDate, gbc_lblDepartureDate); 

        textFieldDepartureDate = new JTextField();
        textFieldDepartureDate.setEnabled(false);
        textFieldDepartureDate.setEditable(false);
        textFieldDepartureDate.setColumns(10);
        GridBagConstraints gbc_textFieldDepartureDate = new GridBagConstraints();
        gbc_textFieldDepartureDate.gridwidth = 2;
        gbc_textFieldDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldDepartureDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDepartureDate.gridx = 1;
        gbc_textFieldDepartureDate.gridy = 5;
        getContentPane().add(textFieldDepartureDate, gbc_textFieldDepartureDate);
        
        lblDischargeDate = new JLabel("Fecha de alta:");
        lblDischargeDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblDischargeDate = new GridBagConstraints();
        gbc_lblDischargeDate.fill = GridBagConstraints.BOTH;
        gbc_lblDischargeDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDischargeDate.gridx = 0;
        gbc_lblDischargeDate.gridy = 6;
        getContentPane().add(lblDischargeDate, gbc_lblDischargeDate); 

        textFieldDischargeDate = new JTextField();
        textFieldDischargeDate.setEnabled(false);
        textFieldDischargeDate.setEditable(false);
        textFieldDischargeDate.setColumns(10);
        GridBagConstraints gbc_textFieldDischargeDate = new GridBagConstraints();
        gbc_textFieldDischargeDate.gridwidth = 2;
        gbc_textFieldDischargeDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldDischargeDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDischargeDate.gridx = 1;
        gbc_textFieldDischargeDate.gridy = 6;
        getContentPane().add(textFieldDischargeDate, gbc_textFieldDischargeDate);
        
        lblTourists = new JLabel("Turistas:");
        lblTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTourists = new GridBagConstraints();
        gbc_lblTourists.fill = GridBagConstraints.BOTH;
        gbc_lblTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblTourists.gridx = 0;
        gbc_lblTourists.gridy = 7;
        getContentPane().add(lblTourists, gbc_lblTourists);
        
        comboBoxTourists = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxTourists = new GridBagConstraints();
        gbc_comboBoxTourists.gridwidth = 2;
        gbc_comboBoxTourists.fill = GridBagConstraints.BOTH;
        gbc_comboBoxTourists.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxTourists.gridx = 1;
        gbc_comboBoxTourists.gridy = 7;
        getContentPane().add(comboBoxTourists, gbc_comboBoxTourists);
        //textFieldTourists.setColumns(10);
        
        lblEnterNumTourists = new JLabel("Numero de turistas:");
        lblEnterNumTourists.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterNumTourists = new GridBagConstraints();
        gbc_lblEnterNumTourists.fill = GridBagConstraints.BOTH;
        gbc_lblEnterNumTourists.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterNumTourists.gridx = 0;
        gbc_lblEnterNumTourists.gridy = 8;
        getContentPane().add(lblEnterNumTourists, gbc_lblEnterNumTourists);

        textFieldNumTourists = new JTextField();
        GridBagConstraints gbc_textFieldNumTourists = new GridBagConstraints();
        gbc_textFieldNumTourists.gridwidth = 2;
        gbc_textFieldNumTourists.fill = GridBagConstraints.BOTH;
        gbc_textFieldNumTourists.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNumTourists.gridx = 1;
        gbc_textFieldNumTourists.gridy = 8;
        getContentPane().add(textFieldNumTourists, gbc_textFieldNumTourists);
        textFieldNumTourists.setColumns(10);
        
        lblinscriptionDate = new JLabel("Fecha de alta:");
        lblinscriptionDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblinscriptionDate = new GridBagConstraints();
        gbc_lblinscriptionDate.fill = GridBagConstraints.BOTH;
        gbc_lblinscriptionDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblinscriptionDate.gridx = 0;
        gbc_lblinscriptionDate.gridy = 9;
        getContentPane().add(lblinscriptionDate, gbc_lblinscriptionDate); 

        textFieldinscriptionDate = new JTextField();
        textFieldinscriptionDate.setEnabled(false);
        textFieldinscriptionDate.setEditable(false);
        textFieldinscriptionDate.setColumns(10);
        GridBagConstraints gbc_textFieldinscriptionDate = new GridBagConstraints();
        gbc_textFieldinscriptionDate.gridwidth = 2;
        gbc_textFieldinscriptionDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldinscriptionDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldinscriptionDate.gridx = 1;
        gbc_textFieldinscriptionDate.gridy = 9;
        getContentPane().add(textFieldinscriptionDate, gbc_textFieldinscriptionDate);
        
        lblTotalRegistrationCost = new JLabel("Costo de la inscripcion:");
        lblTotalRegistrationCost.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblTotalRegistrationCost = new GridBagConstraints();
        gbc_lblTotalRegistrationCost.fill = GridBagConstraints.BOTH;
        gbc_lblTotalRegistrationCost.insets = new Insets(0, 0, 5, 5);
        gbc_lblTotalRegistrationCost.gridx = 0;
        gbc_lblTotalRegistrationCost.gridy = 10;
        getContentPane().add(lblTotalRegistrationCost, gbc_lblTotalRegistrationCost);

        textFieldTotalRegistrationCost = new JTextField();
        textFieldTotalRegistrationCost.setEnabled(false);
        textFieldTotalRegistrationCost.setEditable(false);
        GridBagConstraints gbc_textFieldTotalRegistrationCost = new GridBagConstraints();
        gbc_textFieldTotalRegistrationCost.gridwidth = 2;
        gbc_textFieldTotalRegistrationCost.fill = GridBagConstraints.BOTH;
        gbc_textFieldTotalRegistrationCost.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldTotalRegistrationCost.gridx = 1;
        gbc_textFieldTotalRegistrationCost.gridy = 10;
        getContentPane().add(textFieldTotalRegistrationCost, gbc_textFieldTotalRegistrationCost);
        textFieldTotalRegistrationCost.setColumns(10);
        
        btnConfirm = new JButton("Confirmar");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdInscriptionToTouristOutingActionPerformed(arg0);
            }
        });

        GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
        gbc_btnConfirm.fill = GridBagConstraints.BOTH;
        gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
        gbc_btnConfirm.gridx = 1;
        gbc_btnConfirm.gridy = 11;
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
        gbc_btnCancel.gridy = 11;
        getContentPane().add(btnCancel, gbc_btnCancel);
        
        init();
    }
    
    public void loadTouristActivities() {
        DefaultComboBoxModel<String> model; 
        try {                                    
            model = new DefaultComboBoxModel<String>(controlTouristActivity.listTouristActivities());    
            comboBoxTouristActivities.setModel(model);
        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        	//model = new DefaultComboBoxModel<String>(new String[] {"No existen actividades turísticas registradas."});
        }
        
    }
    
    protected void loadTouristOutings() {
    	
    	String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();
    	
    	if(touristActivityName != null && !touristActivityName.isEmpty()) {
    		
    		DtActivityWithOutings activityWithOutings = null;
    		
    		try {                                    
    			activityWithOutings = controlTouristActivity.consultTouristActivityData(touristActivityName); 
	        } catch (ActivityDoesNotExistException e) {
	            // We will not show any tourist outing
	        }
    		
    		if(activityWithOutings != null) {
    			Set<DtTouristOuting> allTouristOutings = activityWithOutings.getOutings();
    			//Tengo un set de dtTouristOuting, me quiero quedar con una lista de nombres de touristOuting para mostrar en el combo box
    			
    			//y si la actividad no tiene salidas?
    	    	DefaultComboBoxModel<String> model;  
    	    	try {
    	    		model = new DefaultComboBoxModel<String>(controlTouristOutingAndInscription.listTouristOutingsNames(allTouristOutings)); 
    	    		comboBoxTouristOutings.setModel(model);
    	    	} catch (TouristOutingDoesNotExistException e) {
    	    		// We will not show any tourist outing
    	    		//model = new DefaultComboBoxModel<String>(new String[] {"No existen salidas turísticas asociadas."});
    	    	}
    	                
    	        
    		}
    		
    		String touristOutingName = (String) comboBoxTouristOutings.getSelectedItem();
    		
    		if (touristOutingName !=null && !touristOutingName.isEmpty()) {
    			//Tengo el nombre de la salida que quiero, debo mostrar los datos de la misma en los campos deseados
    			DtTouristOuting to = null;
    			
    			try {
    			to = controlTouristOutingAndInscription.consultTouristOutingData(touristOutingName);
    			}catch (TouristOutingDoesNotExistException e) {
    	            // We will not show any tourist activity
    	        } 
    			
    			if (to != null) {
    				textFieldTouristOutingName.setText(to.getTipName());
    		        textFieldMaxNumTourists.setText(String.valueOf(to.getMaxNumTourists()));
    		        textFieldDeparturePoint.setText(to.getDeparturePoint());
    		        LocalDateTime departureDate = to.getDepartureDate();
    		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    		        String fechaHoraStr = departureDate.format(formatter);
    		        textFieldDepartureDate.setText(fechaHoraStr);
    		        LocalDate fecha = to.getDischargeDate(); 
    		        DateTimeFormatter formatterDischargeDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    		        String fechaStr = fecha.format(formatterDischargeDate);
    		        textFieldDischargeDate.setText(fechaStr);
    			}
    		}
    	}
    }
    
    //Se supone tengo actividad turistica y salida seleccionada
	//Lista turistas 
    public void loadTourists() {
        DefaultComboBoxModel<String> model; 
        //try {                                    
            model = new DefaultComboBoxModel<String>(controlUser.listTourists());
        //} catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
            //model = new DefaultComboBoxModel<String>(new String[] {"No existen turistas registrados."});
        //}
        comboBoxTourists.setModel(model);   
    }
   
    protected void cmdInscriptionToTouristOutingActionPerformed(ActionEvent arg0) {
    	//capta datos de inscripcion ingresados por el admin para dar de alta la inscripcion
    	//control chequear que elija usuario y que ingrese todos los datos de la inscripcion
    	String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();
    	String touristOutingName = (String) comboBoxTouristOutings.getSelectedItem();
    	String touristName = (String) comboBoxTourists.getSelectedItem();
    	String numTourists = this.textFieldNumTourists.getText();
        
        if (checkFormulario()) {
        	
        	int textFieldNumTouristsTOint = Integer.parseInt(numTourists); 
        	float costInsc = controlTouristActivity.getActivityCostTourist(touristActivityName);
        	float costTotalInsc = controlTouristOutingAndInscription.inscriptionTotalCost(costInsc,textFieldNumTouristsTOint);
        	
        	LocalDate dischargeDateITO = LocalDate.now(); 
        	DtTouristOuting touristOutingToAddTourist = null;
        	DtInscriptionTouristOuting newInscriptionToTouristOuting = null;
        	
        	try {
        		touristOutingToAddTourist = controlTouristOutingAndInscription.consultTouristOutingData(touristOutingName);
    		}catch (TouristOutingDoesNotExistException e) {
    	        // We will not show any tourist activity
    	    } 
        	
        	if (touristOutingToAddTourist != null) {
        		newInscriptionToTouristOuting = new DtInscriptionTouristOuting(textFieldNumTouristsTOint, costTotalInsc, dischargeDateITO, touristOutingToAddTourist, touristName);
        	}


        	try {
	            
        		controlTouristOutingAndInscription.inscriptionDataEntry(newInscriptionToTouristOuting, touristName, touristOutingName);
	                // Success
	                JOptionPane.showMessageDialog(this, "La inscripcion a la salida turistica fue creada exitosamente.", "Inscripcion a salida turistica",
	                        JOptionPane.INFORMATION_MESSAGE);
	
	            } catch (RepeatedInscriptionToTouristOutingException e) {
	                // Error message
	                JOptionPane.showMessageDialog(this, e.getMessage(), "Inscripcion a salida turistica", JOptionPane.ERROR_MESSAGE);
	                
	                comboBoxTourists.removeAllItems();
	            	comboBoxTouristOutings.removeAllItems();
	            }

        }
    }

    private boolean checkFormulario() {
    
        String touristActivityName = (String) comboBoxTouristActivities.getSelectedItem();
    	String touristOutingName = (String) comboBoxTouristOutings.getSelectedItem();
    	String touristName = (String) comboBoxTourists.getSelectedItem();
    	String numTourists = this.textFieldNumTourists.getText();

        if (touristActivityName == null || touristActivityName.isEmpty() || touristOutingName ==null || touristOutingName.isEmpty() || touristName == null || touristName.isEmpty() || numTourists.isEmpty()){
            JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos", "Inscripcion a salida turistica",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(numTourists);
            int textFieldNumTouristsTOint = Integer.parseInt(numTourists); 
            //Verificar que el numero ingresado sea viable respecto a los cupos disponibles
//            if(textFieldNumTouristsTOint < ) {
//            	
//            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El numero de turistas debe ser un numero", "Inscripcion a salida turistica",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    
    private void clearForm() {
    	comboBoxTouristActivities.removeAllItems();
    	comboBoxTourists.removeAllItems();
    	comboBoxTouristOutings.removeAllItems();
    	textFieldTouristOutingName.setText("");
    	textFieldMaxNumTourists.setText("");
    	textFieldDeparturePoint.setText("");
    	textFieldDepartureDate.setText("");
    	textFieldDischargeDate.setText("");
    	textFieldNumTourists.setText("");
    	textFieldinscriptionDate.setText("");
    	textFieldTotalRegistrationCost.setText("");
    	
    }
    
    public void init() {
    	clearForm();
    	loadTouristActivities();
    	loadTouristOutings();
    	loadTourists();
    }
}
    