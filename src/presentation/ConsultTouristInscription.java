package presentation;

import javax.swing.JInternalFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

import exceptions.ActivityDoesNotExistException;
import logic.controller.TouristActivityController;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristActivity;
import logic.dto.DtTouristOuting;
import logic.entity.TouristActivity;
import logic.entity.TouristOuting;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class ConsultTouristInscription extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> cbActividad;
	private JComboBox<String> cbSalida;
	private JTable tableInscripciones;
	private DefaultTableModel tableModel;

	private ITouristActivityController iActivityController;
	private ITouristOutingAndInscriptionController iOutingController;

	public ConsultTouristInscription(ITouristOutingAndInscriptionController iOIC, ITouristActivityController iTAC) {

		iOutingController = iOIC;
		iActivityController = iTAC;

		setTitle("Consulta Inscripciones Turisticas");
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);

		setSize(600, 400);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelSelect = new JPanel();
		panelSelect.add(new JLabel("Actividad:"));
		cbActividad = new JComboBox<>();
		panelSelect.add(cbActividad);
		panelSelect.add(new JLabel("Salida:"));
		cbSalida = new JComboBox<>();
		panelSelect.add(cbSalida);
		getContentPane().add(panelSelect, BorderLayout.NORTH);

		tableModel = new DefaultTableModel();

		tableModel.addColumn("Cliente");
		tableModel.addColumn("Costo Total");
		tableModel.addColumn("Fecha Inscripción");
		tableInscripciones = new JTable(tableModel);
		tableInscripciones.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tableInscripciones);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		loadTouristActivities();

		cbActividad.addActionListener(e -> {
			String actName = (String) cbActividad.getSelectedItem();
			if (actName != null) {
				loadOutings(actName);
			}
		});
		cbSalida.addActionListener(e -> {
			String outingName = (String) cbSalida.getSelectedItem();
			if (outingName != null) {
				loadInscriptions(outingName);
			}
		});
	}

	public void loadTouristActivities() {
		cbActividad.removeAllItems();
		DefaultComboBoxModel<String> model;
		try {
			model = new DefaultComboBoxModel<String>(iActivityController.listTouristActivities());
			cbActividad.setModel(model);
		} catch (ActivityDoesNotExistException e) {
			// We will not show any tourist activity
			model = new DefaultComboBoxModel<>(new String[] { "No existen actividades turísticas registradas." });
		}
	}

	private void loadOutings(String activityName) {
		cbSalida.removeAllItems();
		try {
			DtActivityWithOutings dtActivity = iActivityController.consultTouristActivityData(activityName);

			if (dtActivity != null && dtActivity.getOutings() != null) {
				for (DtTouristOuting outing : dtActivity.getOutings()) {
					cbSalida.addItem(outing.getOutingName());
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar salidas: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadInscriptions(String outingName) {
		tableModel.setRowCount(0);

		try {
			DtInscriptionTouristOuting[] inscriptions = iOutingController.listOutingInscription(outingName);

			for (DtInscriptionTouristOuting ins : inscriptions) {
				tableModel
						.addRow(new Object[] { ins.getTouristAmount(), ins.getTotalCost(), ins.getInscriptionDate() });
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar inscripciones: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void init() {
		loadTouristActivities();
		clearForm();
	}

	private void clearForm() {
		cbActividad.setSelectedItem(null);
		cbSalida.setSelectedItem(null);
		cbSalida.removeAllItems();
		tableModel.setRowCount(0);

	}
}
