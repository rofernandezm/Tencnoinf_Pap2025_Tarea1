package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import exceptions.ActivityDoesNotExistException;
import logic.dto.DtTouristActivity;
import logic.interfaces.ITouristActivityController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifyActivity extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> cbActivity;
	private JTextField txtActivityName;
	private JTextArea txtDescription;
	private JTextField txtDuration;
	private JTextField txtTouristFee;
	private JTextField txtCity;
	private JTextField txtDischargeDate;

	private DtTouristActivity currentActivity;

	private ITouristActivityController iActivityController;

	public ModifyActivity(ITouristActivityController iTAC) {

		iActivityController = iTAC;

		setTitle("Modificar Actividad");
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);

		setSize(500, 400);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelSelect = new JPanel();
		panelSelect.add(new JLabel("Actividad:"));
		cbActivity = new JComboBox<>();
		panelSelect.add(cbActivity);
		getContentPane().add(panelSelect, BorderLayout.NORTH);

		JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		formPanel.add(new JLabel("Nombre:"));
		txtActivityName = new JTextField();
		txtActivityName.setEnabled(false);
		txtActivityName.setEditable(false);
		formPanel.add(txtActivityName);

		formPanel.add(new JLabel("Descripción:"));
		txtDescription = new JTextArea(3, 20);
		formPanel.add(new JScrollPane(txtDescription));

		formPanel.add(new JLabel("Duración (horas):"));
		txtDuration = new JTextField();
		formPanel.add(txtDuration);

		formPanel.add(new JLabel("Costo por turista:"));
		txtTouristFee = new JTextField();
		formPanel.add(txtTouristFee);

		formPanel.add(new JLabel("Ciudad:"));
		txtCity = new JTextField();
		formPanel.add(txtCity);

		formPanel.add(new JLabel("Fecha Alta:"));
		txtDischargeDate = new JTextField();
		txtDischargeDate.setEnabled(false);
		txtDischargeDate.setEditable(false);
		formPanel.add(txtDischargeDate);

		getContentPane().add(formPanel, BorderLayout.CENTER);

		JPanel panelButtons = new JPanel();
		JButton btnModify = new JButton("Guardar");
		JButton btnCancel = new JButton("Cancelar");
		panelButtons.add(btnModify);
		panelButtons.add(btnCancel);
		getContentPane().add(panelButtons, BorderLayout.SOUTH);

		cbActivity.addActionListener(this::onActivitySelected);
		btnModify.addActionListener(e -> onGuardar(e));
		btnCancel.addActionListener(e -> {
			clearForm();
			dispose();
		});

		loadActivities();
	}

	private void loadActivities() {
		DefaultComboBoxModel<String> model;
		try {

			String[] data = iActivityController.listTouristActivities();
			if (data != null) {
				String[] dataWithNull = new String[data.length + 1];
				dataWithNull[0] = null; // Primera opción nula
				System.arraycopy(data, 0, dataWithNull, 1, data.length);
				model = new DefaultComboBoxModel<String>(dataWithNull);
				cbActivity.setModel(model);
			}

		} catch (ActivityDoesNotExistException e) {
			cbActivity.setModel(new DefaultComboBoxModel<String>());
		}

	}

	private void onActivitySelected(ActionEvent e) {
		String selectedName = (String) cbActivity.getSelectedItem();
		if (selectedName != null) {
			try {
				currentActivity = iActivityController.consultTouristActivityBasicData(selectedName);

				txtActivityName.setText(currentActivity.getActivityName());
				txtDescription.setText(currentActivity.getDescription());
				txtDuration.setText(String.valueOf(currentActivity.getDuration().toHours()));
				txtTouristFee.setText(String.valueOf(currentActivity.getCostTurist()));
				txtCity.setText(currentActivity.getCity());
				txtDischargeDate.setText(
						currentActivity.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "No se pudo cargar la actividad seleccionada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onGuardar(ActionEvent e) {
		if (!validateInputs()) {
			return;
		}

		if (currentActivity != null) {
			try {

				Duration duration = Duration.ofHours(Integer.parseInt(txtDuration.getText()));
				DtTouristActivity updated = new DtTouristActivity(currentActivity.getActivityName(),
						txtDescription.getText(), duration, Float.parseFloat(txtTouristFee.getText()),
						txtCity.getText(), currentActivity.getRegistrationDate(),
						currentActivity.getSupplierNickname());

				iActivityController.modifyActivity(updated);

				JOptionPane.showMessageDialog(this, "Actividad modificada correctamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al modificar la actividad: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void init() {
		clearForm();
		loadActivities();
	}

	private void clearForm() {
		cbActivity.setSelectedItem(null);
		txtActivityName.setText("");
		txtDescription.setText("");
		txtTouristFee.setText("");
		txtCity.setText("");
		txtDischargeDate.setText("");
		txtDuration.setText("");
		currentActivity = null;
	}

	private boolean validateInputs() {
		String description = txtDescription.getText().trim();
		String city = txtCity.getText().trim();
		String costoText = txtTouristFee.getText().trim();
		String duracionValue = txtDuration.getText();

		if (description.isEmpty() || city.isEmpty() || costoText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos son requeridos", "Error de validación",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		int duracionHoras = 0;
		try {
			duracionHoras = Integer.parseInt(duracionValue);
			if (duracionHoras <= 0) {
				JOptionPane.showMessageDialog(this, "La duración debe ser mayor que 0", "Error de validación",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "La duración debe ser un número entero", "Error de validación",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			float costo = Float.parseFloat(costoText);
			if (costo <= 0) {
				JOptionPane.showMessageDialog(this, "El costo debe ser mayor que 0", "Error de validación",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El costo debe ser numérico", "Error de validación",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
