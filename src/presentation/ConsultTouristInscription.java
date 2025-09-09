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
import logic.dto.DtActivityWithOutings;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.ITouristOutingAndInscriptionController;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;

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
		panelSelect.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagLayout gbl_panelSelect = new GridBagLayout();
		gbl_panelSelect.columnWidths = new int[] { 60, 80, 158, 80, 158, 48, 0 };
		gbl_panelSelect.rowHeights = new int[] { 24, 0 };
		gbl_panelSelect.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelSelect.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelSelect.setLayout(gbl_panelSelect);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel label = new JLabel("Actividad:");
		panelSelect.add(label, gbc);
		cbActividad = new JComboBox<>();
		GridBagConstraints gbc_cbActividad = new GridBagConstraints();
		gbc_cbActividad.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbActividad.anchor = GridBagConstraints.NORTH;
		gbc_cbActividad.insets = new Insets(0, 0, 0, 5);
		gbc_cbActividad.gridx = 2;
		gbc_cbActividad.gridy = 0;
		panelSelect.add(cbActividad, gbc_cbActividad);

		cbActividad.addActionListener(e -> {
			String actName = (String) cbActividad.getSelectedItem();
			if (actName != null) {
				loadOutings(actName);
			}
		});
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 0, 5);
		gbc_1.gridx = 3;
		gbc_1.gridy = 0;
		JLabel label_1 = new JLabel("Salida:");
		panelSelect.add(label_1, gbc_1);
		cbSalida = new JComboBox<>();
		GridBagConstraints gbc_cbSalida = new GridBagConstraints();
		gbc_cbSalida.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSalida.insets = new Insets(0, 0, 0, 5);
		gbc_cbSalida.anchor = GridBagConstraints.NORTH;
		gbc_cbSalida.gridx = 4;
		gbc_cbSalida.gridy = 0;
		panelSelect.add(cbSalida, gbc_cbSalida);
		cbSalida.addActionListener(e -> {
			String outingName = (String) cbSalida.getSelectedItem();
			if (outingName != null) {
				loadInscriptions(outingName);
			}
		});

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
