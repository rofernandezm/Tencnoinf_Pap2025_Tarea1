package presentation;

import javax.swing.JInternalFrame;

import logic.interfaces.*;
import logic.dto.DtTouristOuting;

import java.time.format.DateTimeFormatter;

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

public class ConsultTouristOuting extends JInternalFrame{
	
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
    
    public ConsultTouristOuting(ITouristOutingAndInscriptionController itoic) {

        controlTouristOutingAndInscription = itoic;
        
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consult Tourist Outing");
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
        
        lblInfoTouristOuting = new JLabel("¿Desea informacion de esta salida?");
        lblInfoTouristOuting.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblInfoTouristOuting = new GridBagConstraints();
        gbc_lblInfoTouristOuting.gridwidth = 2;
        gbc_lblInfoTouristOuting.fill = GridBagConstraints.BOTH;
        gbc_lblInfoTouristOuting.insets = new Insets(0, 0, 5, 5);
        gbc_lblInfoTouristOuting.gridx = 0;
        gbc_lblInfoTouristOuting.gridy = 2;
        getContentPane().add(lblInfoTouristOuting, gbc_lblInfoTouristOuting);
        
        btnSearchTouristOuting = new JButton("Consultar");
        btnSearchTouristOuting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdTouristOutingActionPerformed(arg0);
            }
        });
        GridBagConstraints gbc_btnSearchTouristOuting = new GridBagConstraints();
        gbc_btnSearchTouristOuting.fill = GridBagConstraints.BOTH;
        gbc_btnSearchTouristOuting.gridx = 2;
        gbc_btnSearchTouristOuting.gridy = 2;
        getContentPane().add(btnSearchTouristOuting, gbc_btnSearchTouristOuting);
        
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
        
        btnCloseWindow = new JButton("Close");
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
            model = new DefaultComboBoxModel<String>(controlTouristActivity.listTouristActivities()); 
            comboBoxTouristActivities.setModel(model);    
            
        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        }
    }
    
    public void loadTouristOutings() {
        DefaultComboBoxModel<String> model; 
        try {                                    
            model = new DefaultComboBoxModel<String>(controlTouristOutingAndInscription.listTouristOutings()); 
            comboBoxTouristOutings.setModel(model);    
            
        } catch (TouristOutingDoesNotExistException e) {
            // We will not show any tourist outing
        }
    }
    
    protected void cmdTouristOutingActionPerformed(ActionEvent e) {
    	
    	String touristOutingName = (String) comboBoxTouristOutings.getSelectedItem();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	DtTouristOuting to;
    	
    	if (touristOutingName != null ) {
    		
    		try {
        	
	            to = controlTouristOutingAndInscription.consultTouristOutingData(touristOutingName);
	            
	            textFieldTouristOutingName.setText(to.getTipName());
	            textFieldMaxNumTourists.setText(String.valueOf(to.getMaxNumTourists()));
	        	textFieldDeparturePoint.setText(to.getDeparturePoint());
	        	textFieldDepartureDate.setText(to.getDepartureDate().format(formatter));
	        	textFieldDischargeDate.setText(to.getDischargeDate().format(formatter));
	        	
        	}catch (TouristOutingDoesNotExistException e1) {
	            
	            JOptionPane.showMessageDialog(this, e1.getMessage(), "Consult Tourist Outing", JOptionPane.ERROR_MESSAGE);
	            clearForm();
	        }
        }else {
        	JOptionPane.showMessageDialog(this, "Debe seleccionar un valor en el campo «Salidas turistas».", "Consult Tourist Outing",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
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
