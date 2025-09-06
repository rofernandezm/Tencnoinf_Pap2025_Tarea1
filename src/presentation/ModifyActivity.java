package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import exceptions.ActivityDoesNotExistException;
import logic.controller.TouristActivityController;
import logic.dto.DtTouristActivity;
import logic.interfaces.ITouristActivityController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ModifyActivity extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> cbActividad;
	private JTextField txtNombre;
	private JTextArea txtDescripcion;
	private JSpinner spnDuracion;
	private JTextField txtCosto;
	private JTextField txtCiudad;
	private JTextField txtFechaAlta;

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
		cbActividad = new JComboBox<>();
		panelSelect.add(cbActividad);
		getContentPane().add(panelSelect, BorderLayout.NORTH);

		JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		formPanel.add(new JLabel("Nombre:"));
		txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		txtNombre.setEditable(false);
		formPanel.add(txtNombre);

		formPanel.add(new JLabel("Descripción:"));
		txtDescripcion = new JTextArea(3, 20);
		formPanel.add(new JScrollPane(txtDescripcion));

		formPanel.add(new JLabel("Duración (horas):"));
		spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		formPanel.add(spnDuracion);

		formPanel.add(new JLabel("Costo por turista:"));
		txtCosto = new JTextField();
		formPanel.add(txtCosto);

		formPanel.add(new JLabel("Ciudad:"));
		txtCiudad = new JTextField();
		formPanel.add(txtCiudad);

		formPanel.add(new JLabel("Fecha Alta:"));
		txtFechaAlta = new JTextField();
		txtFechaAlta.setEnabled(false);
		txtFechaAlta.setEditable(false);
		formPanel.add(txtFechaAlta);

		getContentPane().add(formPanel, BorderLayout.CENTER);

		JPanel panelButtons = new JPanel();
		JButton btnGuardar = new JButton("Guardar");
		JButton btnCancelar = new JButton("Cancelar");
		panelButtons.add(btnGuardar);
		panelButtons.add(btnCancelar);
		getContentPane().add(panelButtons, BorderLayout.SOUTH);

		cbActividad.addActionListener(this::onActivitySelected);
		btnGuardar.addActionListener(this::onGuardar);
		btnCancelar.addActionListener(e -> dispose());

		loadActivities();
	}

	private void loadActivities() {
		DefaultComboBoxModel<String> model;
		try {

			String[] data = iActivityController.listTouristActivities();
			System.out.println("Datos de actividades cargados: " + data);
			if (data != null) {
				String[] dataWithNull = new String[data.length + 1];
				dataWithNull[0] = null; // Primera opción nula
				System.arraycopy(data, 0, dataWithNull, 1, data.length);
				model = new DefaultComboBoxModel<String>(dataWithNull);
				cbActividad.setModel(model);
			}

		} catch (ActivityDoesNotExistException e) {
			cbActividad.setModel(new DefaultComboBoxModel<String>());
		}

	}

	private void onActivitySelected(ActionEvent e) {
		String selectedName = (String) cbActividad.getSelectedItem();
		if (selectedName != null) {
			try {
				currentActivity = iActivityController.consultTouristActivityBasicData(selectedName);

				txtNombre.setText(currentActivity.getActivityName());
				txtDescripcion.setText(currentActivity.getDescription());
				spnDuracion.setValue(currentActivity.getDuration().toHours());
				txtCosto.setText(String.valueOf(currentActivity.getCostTurist()));
				txtCiudad.setText(currentActivity.getCity());
				txtFechaAlta.setText(
						currentActivity.getRegistratioDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
			            "No se pudo cargar la actividad seleccionada.",
			            "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onGuardar(ActionEvent e) {
		if (currentActivity != null) {
			try {
				DtTouristActivity updated = new DtTouristActivity(currentActivity.getActivityName(),
						txtDescripcion.getText(), Duration.ofHours((int) spnDuracion.getValue()),
						Float.parseFloat(txtCosto.getText()), txtCiudad.getText(), currentActivity.getRegistratioDate(),
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

}
