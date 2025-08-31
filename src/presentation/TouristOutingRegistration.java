package presentation;

import javax.swing.JInternalFrame;

import logic.interfaces.*;
import logic.dto.DtTouristOuting;
import exceptions.RepeatedTouristOutingException;
import exceptions.ActivityDoesNotExistException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
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

public class TouristOutingRegistration extends JInternalFrame{

	private ITouristOutingAndInscriptionController iControlTouristOutingAndInscription;
	private ITouristActivityController iControlTouristActivity;
	
	private JComboBox<String> comboBoxTouristActivities;
    private JLabel lblTouristActivities;
	private JTextField textFieldTouristOutingName;
	private JTextField textFieldMaxNumTourists;
	private JTextField textFieldDeparturePoint;
	private JTextField textFieldDepartureDate;
	private JLabel lblEnterTouristOutingName;
	private JLabel lblEnterMaxNumTourists;
	private JLabel lblEnterDeparturePoint;
	private JLabel lblEnterDepartureDate;
	private JButton btnConfirm;
	private JButton btnCancel;
	
	 public TouristOutingRegistration(ITouristOutingAndInscriptionController itoic) {

        iControlTouristOutingAndInscription = itoic;

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Tourist Outing Registration");
        setBounds(10, 40, 360, 200);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 180, 105, 105, 5 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
        textFieldTouristOutingName.setColumns(10);
        
        lblEnterTouristOutingName = new JLabel("Tourist outing name:");
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

        lblEnterMaxNumTourists = new JLabel("Maximum number of tourists:");
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

        lblEnterDeparturePoint = new JLabel("Departure point:");
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
        
        lblEnterDepartureDate = new JLabel("Departure date:");
        lblEnterDepartureDate.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblEnterDepartureDate = new GridBagConstraints();
        gbc_lblEnterDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_lblEnterDepartureDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnterDepartureDate.gridx = 0;
        gbc_lblEnterDepartureDate.gridy = 4;
        getContentPane().add(lblEnterDepartureDate, gbc_lblEnterDepartureDate); 

        textFieldDepartureDate = new JTextField();
        textFieldDepartureDate.setToolTipText("Enter the date in dd/mm/yyyy format.");
        textFieldDepartureDate.setColumns(10);
        GridBagConstraints gbc_textFieldDepartureDate = new GridBagConstraints();
        gbc_textFieldDepartureDate.gridwidth = 2;
        gbc_textFieldDepartureDate.fill = GridBagConstraints.BOTH;
        gbc_textFieldDepartureDate.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldDepartureDate.gridx = 1;
        gbc_textFieldDepartureDate.gridy = 4;
        getContentPane().add(textFieldDepartureDate, gbc_textFieldDepartureDate);

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
        gbc_btnConfirm.gridy = 5;
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
        gbc_btnCancel.gridy = 5;
        getContentPane().add(btnCancel, gbc_btnCancel);
    }
	 
	
	
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
            
            	iControlTouristOutingAndInscription.outingDataEntry(newTouristOuting, touristActivityName);

                // Success
                JOptionPane.showMessageDialog(this, "The tourist outing has been successfully created.", "Tourist Outing Registration",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (RepeatedTouristOutingException e) {
                // Error message
                JOptionPane.showMessageDialog(this, e.getMessage(), "Tourist Outing Registration", JOptionPane.ERROR_MESSAGE);
            }
            
            /* En  caso  de  que  exista  una  salida con  el  nombre  ingresado, el 
            administrador  puede  modificar  los  datos  o  cancelar  el  caso  de  uso. 
            Finalmente, el sistema da de alta la salida turística.*/
            
            
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
    }

    private void clearForm() {
    	textFieldTouristOutingName.setText("");
    	textFieldMaxNumTourists.setText("");
    	textFieldDeparturePoint.setText("");
    	textFieldDepartureDate.setText("");
    	comboBoxTouristActivities.removeAllItems();
    }
    
    public void loadTouristActivities() {
        DefaultComboBoxModel<String> model; 
        try {                                    
            model = new DefaultComboBoxModel<String>(iControlTouristActivity.listTouristActivities()); 
            comboBoxTouristActivities.setModel(model);        
        } catch (ActivityDoesNotExistException e) {
            // We will not show any tourist activity
        }
    }
}
