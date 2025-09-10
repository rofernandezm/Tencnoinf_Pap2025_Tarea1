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
import exceptions.TouristOutingDoesNotExistException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.ITouristOutingAndInscriptionController;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;

import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class ConsultTouristInscription extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> cbActividad;
	private JComboBox<String> cbSalida;
	private JPanel topContainer;
	private JPanel panelSelect;
	private JPanel touristOutingData;
	private JTable tableInscripciones;
	private DefaultTableModel tableModel;

	private ITouristActivityController iActivityController;
	private ITouristOutingAndInscriptionController iOutingController;
	private JTextField fieldOutingName;
	private JTextField fieldOutingMaxCant;
	private JTextField fieldOutingDeparturePoint;
	private JTextField fieldOutingDepartureDate;
	private JTextField fieldOutingDischDate;
	private JPanel borderOutingName;
	private JPanel borderOutingMaxNum;
	private JPanel borderOutingDeparturePoint;
	private JPanel borderOutingDepartureDate;
	private JPanel borderOutingDischargeDate;

	// Patterns
	private static final String DATE_FORMAT_DDMMYYY = "dd/MM/yyyy";
	private static final String DATE_FORMAT_DDMMYYY_HHMM = "dd/MM/yyyy HH:mm";

	public ConsultTouristInscription(ITouristOutingAndInscriptionController iOIC, ITouristActivityController iTAC) {

		iOutingController = iOIC;
		iActivityController = iTAC;

		setTitle("Consulta Inscripciones Turisticas");
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);

		setSize(670, 536);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(10);
		getContentPane().setLayout(borderLayout);

		topContainer = new JPanel();
		getContentPane().add(topContainer, BorderLayout.NORTH);
		GridBagLayout gbl_topContainer = new GridBagLayout();
		gbl_topContainer.columnWidths = new int[] { 30, 626, 30, 0 };
		gbl_topContainer.rowHeights = new int[] { 49, 53, 0 };
		gbl_topContainer.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_topContainer.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		topContainer.setLayout(gbl_topContainer);

		panelSelect = new JPanel();
		GridBagConstraints gbc_panelSelect = new GridBagConstraints();
		gbc_panelSelect.insets = new Insets(0, 0, 10, 5);
		gbc_panelSelect.gridx = 1;
		gbc_panelSelect.gridy = 0;
		topContainer.add(panelSelect, gbc_panelSelect);
		panelSelect.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagLayout gbl_panelSelect = new GridBagLayout();
		gbl_panelSelect.columnWidths = new int[] { 80, 158, 80, 158, 0 };
		gbl_panelSelect.rowHeights = new int[] { 24, 0 };
		gbl_panelSelect.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelSelect.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelSelect.setLayout(gbl_panelSelect);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel label = new JLabel("Actividad:");
		panelSelect.add(label, gbc);
		cbActividad = new JComboBox<>();
		GridBagConstraints gbc_cbActividad = new GridBagConstraints();
		gbc_cbActividad.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbActividad.anchor = GridBagConstraints.NORTH;
		gbc_cbActividad.insets = new Insets(0, 0, 0, 5);
		gbc_cbActividad.gridx = 1;
		gbc_cbActividad.gridy = 0;
		panelSelect.add(cbActividad, gbc_cbActividad);

		cbActividad.addActionListener(e -> {
			checkActivityName();
		});

		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 0, 5);
		gbc_1.gridx = 2;
		gbc_1.gridy = 0;
		JLabel label_1 = new JLabel("Salida:");
		panelSelect.add(label_1, gbc_1);
		cbSalida = new JComboBox<>();
		GridBagConstraints gbc_cbSalida = new GridBagConstraints();
		gbc_cbSalida.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSalida.anchor = GridBagConstraints.NORTH;
		gbc_cbSalida.gridx = 3;
		gbc_cbSalida.gridy = 0;
		panelSelect.add(cbSalida, gbc_cbSalida);
		cbSalida.addActionListener(e -> {
			checkOutingName();
		});

		touristOutingData = new JPanel();
		GridBagConstraints gbc_touristOutingData = new GridBagConstraints();
		gbc_touristOutingData.fill = GridBagConstraints.HORIZONTAL;
		gbc_touristOutingData.insets = new Insets(0, 0, 0, 5);
		gbc_touristOutingData.gridx = 1;
		gbc_touristOutingData.gridy = 1;
		topContainer.add(touristOutingData, gbc_touristOutingData);
		GridBagLayout gbl_touristOutingData = new GridBagLayout();
		gbl_touristOutingData.columnWidths = new int[] { 0, 0 };
		gbl_touristOutingData.rowHeights = new int[] { 0, 0, 0 };
		gbl_touristOutingData.columnWeights = new double[] { 1.0, 1.0 };
		gbl_touristOutingData.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		touristOutingData.setLayout(gbl_touristOutingData);
		touristOutingData.setVisible(false);

		borderOutingName = new JPanel();
		borderOutingName.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Nombre de salida:",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_borderOutingName = new GridBagConstraints();
		gbc_borderOutingName.fill = GridBagConstraints.BOTH;
		gbc_borderOutingName.insets = new Insets(0, 0, 5, 5);
		gbc_borderOutingName.gridx = 0;
		gbc_borderOutingName.gridy = 0;
		touristOutingData.add(borderOutingName, gbc_borderOutingName);
		GridBagLayout gbl_borderOutingName = new GridBagLayout();
		gbl_borderOutingName.columnWidths = new int[] { 0, 0 };
		gbl_borderOutingName.rowHeights = new int[] { 0, 0 };
		gbl_borderOutingName.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_borderOutingName.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		borderOutingName.setLayout(gbl_borderOutingName);

		fieldOutingName = new JTextField();
		fieldOutingName.setBackground(new Color(255, 255, 255));
		fieldOutingName.setEditable(false);
		GridBagConstraints gbc_fieldOutingName = new GridBagConstraints();
		gbc_fieldOutingName.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingName.gridx = 0;
		gbc_fieldOutingName.gridy = 0;
		borderOutingName.add(fieldOutingName, gbc_fieldOutingName);
		fieldOutingName.setColumns(10);

		borderOutingDepartureDate = new JPanel();
		borderOutingDepartureDate.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha y hora de salida:",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_borderOutingDepartureDate = new GridBagConstraints();
		gbc_borderOutingDepartureDate.fill = GridBagConstraints.BOTH;
		gbc_borderOutingDepartureDate.insets = new Insets(0, 0, 5, 0);
		gbc_borderOutingDepartureDate.gridx = 1;
		gbc_borderOutingDepartureDate.gridy = 0;
		touristOutingData.add(borderOutingDepartureDate, gbc_borderOutingDepartureDate);
		GridBagLayout gbl_borderOutingDepartureDate = new GridBagLayout();
		gbl_borderOutingDepartureDate.columnWidths = new int[] { 0, 0 };
		gbl_borderOutingDepartureDate.rowHeights = new int[] { 0, 0 };
		gbl_borderOutingDepartureDate.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_borderOutingDepartureDate.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		borderOutingDepartureDate.setLayout(gbl_borderOutingDepartureDate);

		fieldOutingDepartureDate = new JTextField();
		fieldOutingDepartureDate.setBackground(new Color(255, 255, 255));
		fieldOutingDepartureDate.setEditable(false);
		GridBagConstraints gbc_fieldOutingDepartureDate = new GridBagConstraints();
		gbc_fieldOutingDepartureDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingDepartureDate.gridx = 0;
		gbc_fieldOutingDepartureDate.gridy = 0;
		borderOutingDepartureDate.add(fieldOutingDepartureDate, gbc_fieldOutingDepartureDate);
		fieldOutingDepartureDate.setColumns(10);

		borderOutingMaxNum = new JPanel();
		borderOutingMaxNum.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "M\u00E1xima cantidad de turistas:",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_borderOutingMaxNum = new GridBagConstraints();
		gbc_borderOutingMaxNum.fill = GridBagConstraints.BOTH;
		gbc_borderOutingMaxNum.insets = new Insets(0, 0, 5, 5);
		gbc_borderOutingMaxNum.gridx = 0;
		gbc_borderOutingMaxNum.gridy = 1;
		touristOutingData.add(borderOutingMaxNum, gbc_borderOutingMaxNum);
		GridBagLayout gbl_borderOutingMaxNum = new GridBagLayout();
		gbl_borderOutingMaxNum.columnWidths = new int[] { 0, 0 };
		gbl_borderOutingMaxNum.rowHeights = new int[] { 0, 0 };
		gbl_borderOutingMaxNum.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_borderOutingMaxNum.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		borderOutingMaxNum.setLayout(gbl_borderOutingMaxNum);

		fieldOutingMaxCant = new JTextField();
		fieldOutingMaxCant.setBackground(new Color(255, 255, 255));
		fieldOutingMaxCant.setEditable(false);
		GridBagConstraints gbc_fieldOutingMaxCant = new GridBagConstraints();
		gbc_fieldOutingMaxCant.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingMaxCant.gridx = 0;
		gbc_fieldOutingMaxCant.gridy = 0;
		borderOutingMaxNum.add(fieldOutingMaxCant, gbc_fieldOutingMaxCant);
		fieldOutingMaxCant.setColumns(10);

		borderOutingDischargeDate = new JPanel();
		borderOutingDischargeDate.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha de alta:",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_borderOutingDischargeDate = new GridBagConstraints();
		gbc_borderOutingDischargeDate.fill = GridBagConstraints.BOTH;
		gbc_borderOutingDischargeDate.insets = new Insets(0, 0, 5, 0);
		gbc_borderOutingDischargeDate.gridx = 1;
		gbc_borderOutingDischargeDate.gridy = 1;
		touristOutingData.add(borderOutingDischargeDate, gbc_borderOutingDischargeDate);
		GridBagLayout gbl_borderOutingDischargeDate = new GridBagLayout();
		gbl_borderOutingDischargeDate.columnWidths = new int[] { 0, 0 };
		gbl_borderOutingDischargeDate.rowHeights = new int[] { 0, 0 };
		gbl_borderOutingDischargeDate.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_borderOutingDischargeDate.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		borderOutingDischargeDate.setLayout(gbl_borderOutingDischargeDate);

		fieldOutingDischDate = new JTextField();
		fieldOutingDischDate.setBackground(new Color(255, 255, 255));
		fieldOutingDischDate.setEditable(false);
		GridBagConstraints gbc_fieldOutingDischDate = new GridBagConstraints();
		gbc_fieldOutingDischDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingDischDate.gridx = 0;
		gbc_fieldOutingDischDate.gridy = 0;
		borderOutingDischargeDate.add(fieldOutingDischDate, gbc_fieldOutingDischDate);
		fieldOutingDischDate.setColumns(10);

		borderOutingDeparturePoint = new JPanel();
		borderOutingDeparturePoint.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Lugar de salida:",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_borderOutingDeparturePoint = new GridBagConstraints();
		gbc_borderOutingDeparturePoint.fill = GridBagConstraints.BOTH;
		gbc_borderOutingDeparturePoint.insets = new Insets(0, 0, 0, 5);
		gbc_borderOutingDeparturePoint.gridx = 0;
		gbc_borderOutingDeparturePoint.gridy = 2;
		touristOutingData.add(borderOutingDeparturePoint, gbc_borderOutingDeparturePoint);
		GridBagLayout gbl_borderOutingDeparturePoint = new GridBagLayout();
		gbl_borderOutingDeparturePoint.columnWidths = new int[] { 0, 0 };
		gbl_borderOutingDeparturePoint.rowHeights = new int[] { 0, 0 };
		gbl_borderOutingDeparturePoint.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_borderOutingDeparturePoint.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		borderOutingDeparturePoint.setLayout(gbl_borderOutingDeparturePoint);

		fieldOutingDeparturePoint = new JTextField();
		fieldOutingDeparturePoint.setBackground(new Color(255, 255, 255));
		fieldOutingDeparturePoint.setEditable(false);
		GridBagConstraints gbc_fieldOutingDeparturePoint = new GridBagConstraints();
		gbc_fieldOutingDeparturePoint.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingDeparturePoint.gridx = 0;
		gbc_fieldOutingDeparturePoint.gridy = 0;
		borderOutingDeparturePoint.add(fieldOutingDeparturePoint, gbc_fieldOutingDeparturePoint);
		fieldOutingDeparturePoint.setColumns(10);

		tableModel = new DefaultTableModel();

		tableModel.addColumn("Número de inscriptos");
		tableModel.addColumn("Costo Total");
		tableModel.addColumn("Fecha Inscripción");
		tableInscripciones = new JTable(tableModel);
		tableInscripciones.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tableInscripciones);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		loadTouristActivities();
	}

	private void checkActivityName() {
		String actName = (String) cbActividad.getSelectedItem();
		if (actName != null) {
			loadOutings(actName);
		}
	}

	private void checkOutingName() {
		String outingName = (String) cbSalida.getSelectedItem();
		if (outingName != null ) {
			fillOutingDetailsForm();
			loadInscriptions(outingName);
		}
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
		
		String[] outingsByActivity = iOutingController.listTouristOutingByActivity(activityName);
		outingsByActivity = outingsByActivity == null ? new String[]{"No existen salidas registradas"} : outingsByActivity;
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(outingsByActivity);
		cbSalida.setModel(model);
		cbSalida.setSelectedItem(null); 
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
		cleanOutingDetailsForm();
		tableModel.setRowCount(0);

	}

	private void cleanOutingDetailsForm() {
		touristOutingData.setVisible(false);
		fieldOutingName.setText("");
		fieldOutingMaxCant.setText("");
		fieldOutingDeparturePoint.setText("");
		fieldOutingDepartureDate.setText("");
		fieldOutingDischDate.setText("");
	}

	private void fillOutingDetailsForm() {
		String outingName = (String) cbSalida.getSelectedItem();

		try {
			DtTouristOuting dt = iOutingController.consultTouristOutingData(outingName);

			touristOutingData.setVisible(true);
			
			fieldOutingName.setText(dt.getOutingName());
			fieldOutingMaxCant.setText(String.valueOf(dt.getMaxNumTourists()));
			fieldOutingDeparturePoint.setText(dt.getDeparturePoint());
			fieldOutingDepartureDate
					.setText(dt.getDepartureDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DDMMYYY_HHMM)));
			fieldOutingDischDate
					.setText(dt.getDischargeDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DDMMYYY)));

		} catch (TouristOutingDoesNotExistException e) {
			e.printStackTrace();
		}
	}
}
