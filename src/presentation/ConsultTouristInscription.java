package presentation;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

import logic.controller.TouristActivityController;
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

	public ConsultTouristInscription(ITouristOutingAndInscriptionController iOIC) {

		iOutingController = iOIC;

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
		tableModel.addColumn("Cédula");
		tableModel.addColumn("Fecha Inscripción");
		tableInscripciones = new JTable(tableModel);
		tableInscripciones.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tableInscripciones);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		loadActivities();

		cbActividad.addActionListener(e -> {
			TouristActivity act = (TouristActivity) cbActividad.getSelectedItem();
			if (act != null) {
				loadOutings(act);
			}
		});
		cbSalida.addActionListener(e -> {
			TouristOuting outing = (TouristOuting) cbSalida.getSelectedItem();
			if (outing != null) {
				loadInscriptions(outing);
			}
		});
	}

	private void loadActivities() {

	}

	private void loadOutings(TouristActivity act) {

	}

	private void loadInscriptions(TouristOuting outing) {

	}
}
