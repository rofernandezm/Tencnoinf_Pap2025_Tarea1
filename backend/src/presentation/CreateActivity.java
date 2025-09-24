package presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import exceptions.RepeatedActivityNameException;
import logic.dto.DtTouristActivity;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.IUserController;

public class CreateActivity extends JInternalFrame {

	private ITouristActivityController iTouristActivityController;
	private IUserController iUserController;

	private static final long serialVersionUID = 1L;
	private JLabel lblSupplier;
	private JComboBox<String> cmbSupplier;
	private JLabel lblDescription;
	private JTextField txtDescription;
	private JLabel lblTouristFee;
	private JTextField txtTouristFee;
	private JLabel lblCity;
	private JTextField txtCity;
	private JLabel lblDuration;
	private JTextField txtDuration;
	private JLabel lblActivityName;
	private JTextField txtActivityName;
	private JButton btnAccept;
	private JButton btnCancel;

	public CreateActivity(ITouristActivityController iTouristActivityController, IUserController iUserController) {

		this.iTouristActivityController = iTouristActivityController;
		this.iUserController = iUserController;

		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Crear Actividad");
		setClosable(true);
		setBounds(50, 50, 450, 400);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 101, 101, 101, 10 };
		gridBagLayout.rowHeights = new int[] { 10, 30, 30, 30, 30, 30, 30, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		dataCreateActivity();

	}

	private void dataCreateActivity() {
		lblSupplier = new JLabel("Proveedor");
		lblSupplier.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
		gbc_lblSupplier.anchor = GridBagConstraints.EAST;
		gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblSupplier.gridx = 1;
		gbc_lblSupplier.gridy = 1;
		getContentPane().add(lblSupplier, gbc_lblSupplier);

		// Combo supplier
		cmbSupplier = new JComboBox<String>();
		GridBagConstraints gbc_cmbSupplier = new GridBagConstraints();
		gbc_cmbSupplier.gridwidth = 2;
		gbc_cmbSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_cmbSupplier.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbSupplier.gridx = 2;
		gbc_cmbSupplier.gridy = 1;
		getContentPane().add(cmbSupplier, gbc_cmbSupplier);
		
		lblActivityName = new JLabel("Nombre");
		lblActivityName.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblActivityName = new GridBagConstraints();
		gbc_lblActivityName.fill = GridBagConstraints.BOTH;
		gbc_lblActivityName.insets = new Insets(0, 0, 5, 5);
		gbc_lblActivityName.gridx = 1;
		gbc_lblActivityName.gridy = 2;
		getContentPane().add(lblActivityName, gbc_lblActivityName);

		txtActivityName = new JTextField();
		txtActivityName.setColumns(10);
		GridBagConstraints gbc_txfActivityName = new GridBagConstraints();
		gbc_txfActivityName.fill = GridBagConstraints.BOTH;
		gbc_txfActivityName.insets = new Insets(0, 0, 5, 0);
		gbc_txfActivityName.gridwidth = 2;
		gbc_txfActivityName.gridx = 2;
		gbc_txfActivityName.gridy = 2;
		getContentPane().add(txtActivityName, gbc_txfActivityName);

		lblDescription = new JLabel("Descripcion");
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.fill = GridBagConstraints.BOTH;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 3;
		getContentPane().add(lblDescription, gbc_lblDescription);

		txtDescription = new JTextField();
		GridBagConstraints gbc_txtDescription = new GridBagConstraints();
		gbc_txtDescription.fill = GridBagConstraints.BOTH;
		gbc_txtDescription.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescription.gridwidth = 2;
		gbc_txtDescription.gridx = 2;
		gbc_txtDescription.gridy = 3;
		getContentPane().add(txtDescription, gbc_txtDescription);
		txtDescription.setColumns(10);

		lblDuration = new JLabel("Duración (horas):");
		lblDuration.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDuration = new GridBagConstraints();
		gbc_lblDuration.fill = GridBagConstraints.BOTH;
		gbc_lblDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuration.gridx = 1;
		gbc_lblDuration.gridy = 4;
		getContentPane().add(lblDuration, gbc_lblDuration);

		txtDuration = new JTextField();
		txtDuration.setColumns(10);
		GridBagConstraints gbc_txtDuration = new GridBagConstraints();
		gbc_txtDuration.fill = GridBagConstraints.BOTH;
		gbc_txtDuration.insets = new Insets(0, 0, 5, 0);
		gbc_txtDuration.gridwidth = 2;
		gbc_txtDuration.gridx = 2;
		gbc_txtDuration.gridy = 4;
		getContentPane().add(txtDuration, gbc_txtDuration);

		lblTouristFee = new JLabel("Costo por turista");
		lblTouristFee.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTouristFee = new GridBagConstraints();
		gbc_lblTouristFee.fill = GridBagConstraints.BOTH;
		gbc_lblTouristFee.insets = new Insets(0, 0, 5, 5);
		gbc_lblTouristFee.gridx = 1;
		gbc_lblTouristFee.gridy = 5;
		getContentPane().add(lblTouristFee, gbc_lblTouristFee);

		txtTouristFee = new JTextField();
		txtTouristFee.setColumns(10);
		GridBagConstraints gbc_txtTouristFee = new GridBagConstraints();
		gbc_txtTouristFee.fill = GridBagConstraints.BOTH;
		gbc_txtTouristFee.insets = new Insets(0, 0, 5, 0);
		gbc_txtTouristFee.gridwidth = 2;
		gbc_txtTouristFee.gridx = 2;
		gbc_txtTouristFee.gridy = 5;
		getContentPane().add(txtTouristFee, gbc_txtTouristFee);

		lblCity = new JLabel("Ciudad");
		lblCity.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.fill = GridBagConstraints.BOTH;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 1;
		gbc_lblCity.gridy = 6;
		getContentPane().add(lblCity, gbc_lblCity);

		txtCity = new JTextField();
		txtCity.setColumns(10);
		GridBagConstraints gbc_txtCity = new GridBagConstraints();
		gbc_txtCity.fill = GridBagConstraints.BOTH;
		gbc_txtCity.insets = new Insets(0, 0, 5, 0);
		gbc_txtCity.gridwidth = 2;
		gbc_txtCity.gridx = 2;
		gbc_txtCity.gridy = 6;
		getContentPane().add(txtCity, gbc_txtCity);

		btnAccept = new JButton("Aceptar");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmdRegisterActivityActionPerformed(arg0);
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridx = 3;
		gbc_btnAccept.gridy = 8;
		getContentPane().add(btnAccept, gbc_btnAccept);

		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				setVisible(false);
			}

		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 8;
		getContentPane().add(btnCancel, gbc_btnCancel);
		

	}

	public void loadSupplier() {
		DefaultComboBoxModel<String> model;
		String[] data = iUserController.listSuppliers();
		if (data == null) {
			data = new String[] { "No hay proveedores registrados" };
		}
		model = new DefaultComboBoxModel<String>(data);
		cmbSupplier.setModel(model);
		cmbSupplier.setSelectedIndex(-1);
	}

	protected void cmdRegisterActivityActionPerformed(ActionEvent arg0) {
		if (validateInputs()) {
			try {
				DtTouristActivity dtActivity = parseData();

				iTouristActivityController.activityDataEntry(dtActivity);

				JOptionPane.showMessageDialog(null, "Actividad creada correctamente", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				clearForm();
				setVisible(false);

			} catch (RepeatedActivityNameException e) {
				// Error message
				JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de actividad turistica",
						JOptionPane.ERROR_MESSAGE);

				txtActivityName.setText("");
			}
		}
	}

	protected boolean validateInputs() {
		String name = txtActivityName.getText().trim();
		String description = txtDescription.getText().trim();
		String durationText = txtDuration.getText().trim();
		String feeText = txtTouristFee.getText().trim();
		String city = txtCity.getText().trim();

		String supplier = "";
		if (cmbSupplier.getSelectedItem() != null && !((String) cmbSupplier.getSelectedItem()).isEmpty()) {
			supplier = (String) cmbSupplier.getSelectedItem();
		}

		if (supplier.isEmpty() || name.isEmpty() || description.isEmpty() || durationText.isEmpty() || feeText.isEmpty()
				|| city.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Todos los campos son requeridos", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			Integer.parseInt(durationText);
		} catch (NumberFormatException ex) {
			txtDuration.setText("");
			JOptionPane.showMessageDialog(null, "El campo " + lblDuration.getText() + " debe ser numerico", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			Float.parseFloat(feeText);
		} catch (NumberFormatException ex) {
			txtTouristFee.setText("");
			JOptionPane.showMessageDialog(null, "El campo " + lblTouristFee.getText() + " debe ser numerico", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private DtTouristActivity parseData() {
		String name = txtActivityName.getText().trim();
		String description = txtDescription.getText().trim();
		String durationText = txtDuration.getText().trim();
		String feeText = txtTouristFee.getText().trim();
		String city = txtCity.getText().trim();
		String supplier = (String) cmbSupplier.getSelectedItem();

		Duration duration = Duration.ofHours(Integer.parseInt(durationText));
		float fee = Float.parseFloat(feeText);
		LocalDate dischargeDate = LocalDate.now();

		return new DtTouristActivity(name, description, duration, fee, city, dischargeDate, supplier);
	}

	private void clearForm() {
		cmbSupplier.setSelectedItem(null);
		txtActivityName.setText("");
		txtDescription.setText("");
		txtDuration.setText("");
		txtTouristFee.setText("");
		txtCity.setText("");
	}

	public void init() {
		clearForm();
        loadSupplier();
	}
}
