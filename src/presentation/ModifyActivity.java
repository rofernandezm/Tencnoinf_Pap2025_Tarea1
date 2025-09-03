package presentation;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

import exceptions.ActivityDoesNotExistException;
import logic.dto.DtActivityWithOutings;
import logic.interfaces.ITouristActivityController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class ModifyActivity extends JInternalFrame {
	
	private ITouristActivityController iTouristActivityController;

	private static final long serialVersionUID = 1L;
	private JTextField txtDescription;
	private JTextField txtTouristFee;
	private JTextField txtCity;
	private JLabel lblCity;
	private JLabel lblDuration;
	private JLabel lblNombreActividad;
	private JTextField txfActivityName;
	private JLabel lblDischargeDate;
	private JButton btnAccept;
	private JSpinner spnDuration;
	private JSpinner spnDischargeDate;
	/**
	 * Create the frame.
	 */
	public ModifyActivity(ITouristActivityController iTouristActivityController) {
		
		this.iTouristActivityController = iTouristActivityController;
		
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Modificar Actividad");
		setClosable(true);
		setBounds(100, 100, 410, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{101, 290, 0};
		gridBagLayout.rowHeights = new int[]{0, 30, 50, 30, 30, 30, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblNombreActividad = new JLabel("Nombre Actividad");
		lblNombreActividad.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNombreActividad = new GridBagConstraints();
		gbc_lblNombreActividad.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombreActividad.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreActividad.gridx = 0;
		gbc_lblNombreActividad.gridy = 1;
		getContentPane().add(lblNombreActividad, gbc_lblNombreActividad);
		
		txfActivityName = new JTextField();
		txfActivityName.setEditable(false);
		txfActivityName.setColumns(10);
		GridBagConstraints gbc_txfActivityName = new GridBagConstraints();
		gbc_txfActivityName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfActivityName.insets = new Insets(0, 0, 5, 0);
		gbc_txfActivityName.gridx = 1;
		gbc_txfActivityName.gridy = 1;
		getContentPane().add(txfActivityName, gbc_txfActivityName);
		
		JLabel lblDescription = new JLabel("Descripcion");
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.fill = GridBagConstraints.BOTH;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 2;
		getContentPane().add(lblDescription, gbc_lblDescription);
		
		txtDescription = new JTextField();
		GridBagConstraints gbc_txtDescription = new GridBagConstraints();
		gbc_txtDescription.fill = GridBagConstraints.BOTH;
		gbc_txtDescription.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescription.gridx = 1;
		gbc_txtDescription.gridy = 2;
		getContentPane().add(txtDescription, gbc_txtDescription);
		txtDescription.setColumns(10);
		
		lblDuration = new JLabel("Duracion");
		lblDuration.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDuration = new GridBagConstraints();
		gbc_lblDuration.fill = GridBagConstraints.BOTH;
		gbc_lblDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuration.gridx = 0;
		gbc_lblDuration.gridy = 3;
		getContentPane().add(lblDuration, gbc_lblDuration);
		
		spnDuration = new JSpinner();
		GridBagConstraints gbc_spnDuration = new GridBagConstraints();
		gbc_spnDuration.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnDuration.insets = new Insets(0, 0, 5, 0);
		gbc_spnDuration.gridx = 1;
		gbc_spnDuration.gridy = 3;
		getContentPane().add(spnDuration, gbc_spnDuration);
		
		JLabel lblTouristFee = new JLabel("Costo por turista");
		lblTouristFee.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTouristFee = new GridBagConstraints();
		gbc_lblTouristFee.fill = GridBagConstraints.BOTH;
		gbc_lblTouristFee.insets = new Insets(0, 0, 5, 5);
		gbc_lblTouristFee.gridx = 0;
		gbc_lblTouristFee.gridy = 4;
		getContentPane().add(lblTouristFee, gbc_lblTouristFee);
		
		txtTouristFee = new JTextField();
		txtTouristFee.setColumns(10);
		GridBagConstraints gbc_txtTouristFee = new GridBagConstraints();
		gbc_txtTouristFee.fill = GridBagConstraints.BOTH;
		gbc_txtTouristFee.insets = new Insets(0, 0, 5, 0);
		gbc_txtTouristFee.gridx = 1;
		gbc_txtTouristFee.gridy = 4;
		getContentPane().add(txtTouristFee, gbc_txtTouristFee);
		
		lblCity = new JLabel("Ciudad");
		lblCity.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.fill = GridBagConstraints.BOTH;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 0;
		gbc_lblCity.gridy = 5;
		getContentPane().add(lblCity, gbc_lblCity);
		
		txtCity = new JTextField();
		txtCity.setColumns(10);
		GridBagConstraints gbc_txtCity = new GridBagConstraints();
		gbc_txtCity.fill = GridBagConstraints.BOTH;
		gbc_txtCity.insets = new Insets(0, 0, 5, 0);
		gbc_txtCity.gridx = 1;
		gbc_txtCity.gridy = 5;
		getContentPane().add(txtCity, gbc_txtCity);
		
		lblDischargeDate = new JLabel("Fecha de Alta");
		lblDischargeDate.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDischargeDate = new GridBagConstraints();
		gbc_lblDischargeDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDischargeDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDischargeDate.gridx = 0;
		gbc_lblDischargeDate.gridy = 6;
		getContentPane().add(lblDischargeDate, gbc_lblDischargeDate);
		
		btnAccept = new JButton("Aceptar");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		spnDischargeDate = new JSpinner();
		spnDischargeDate.setEnabled(false);
		GridBagConstraints gbc_spnDischargeDate = new GridBagConstraints();
		gbc_spnDischargeDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnDischargeDate.insets = new Insets(0, 0, 5, 0);
		gbc_spnDischargeDate.gridx = 1;
		gbc_spnDischargeDate.gridy = 6;
		getContentPane().add(spnDischargeDate, gbc_spnDischargeDate);
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 7;
		getContentPane().add(btnAccept, gbc_btnAccept);
	}
	 
	
	 protected void cmdSearchActivityActionPerformed(ActionEvent e) {
	        DtActivityWithOutings dtActivity;
	        try {
	        	dtActivity = iTouristActivityController.consultTouristActivityData(iTouristActivityController.getActivityName());
	        	txfActivityName.setText(dtActivity.getActivity().getActivityName());
	        	txtDescription.setText(dtActivity.getActivity().getDescription());
	        	txtDuration.setText(dtActivity.getActivity().getDuration().toString());
	        	txtTouristFee.setText(String.valueOf(dtActivity.getActivity().getCostTurist()));
	        	txtCity.setText(dtActivity.getActivity().getCity());
	        	txtDischargeDate.setText(dtActivity.getActivity().getRegistratioDate().toString());
	        } catch (ActivityDoesNotExistException e1) {
	            JOptionPane.showMessageDialog(this, e1.getMessage(), "Busqueda Actividad", JOptionPane.ERROR_MESSAGE);
	            limpiarFormulario();
	        }

	    }
	 
	 private void limpiarFormulario() {
		txfActivityName.setText("");
     	txtDescription.setText("");
     	txtDuration.setText("");
     	txtTouristFee.setText("");
     	txtCity.setText("");
     	txtDischargeDate.setText("");
	    }
	
}
