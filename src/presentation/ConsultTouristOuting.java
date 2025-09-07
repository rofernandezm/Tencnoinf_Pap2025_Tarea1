package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import exceptions.ActivityDoesNotExistException;
import exceptions.TouristOutingDoesNotExistException;
import logic.dto.DtTouristActivity;
import logic.dto.DtTouristOuting;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class ConsultTouristOuting extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOutName;
	private JTextField txtOutMax;
	private JTextField txtOutDepPoint;
	private JTextField txtOutDepDate;
	private JTextField txtOutDesDate;
	private JTextField txtActName;
	private JTextField txtActDescription;
	private JTextField txtActDuration;
	private JTextField txtActCost;
	private JTextField txtActCity;
	private JTextField txtActRegDate;
	private JComboBox<String> cmbSelOuting;
	private DateTimeFormatter formatter;
	private ITouristOutingAndInscriptionController itoic;
	private ITouristActivityController itac;
	
	public ConsultTouristOuting(ITouristOutingAndInscriptionController itoic, ITouristActivityController itac) {
		super("Consultar salida turistica", true, true, true, true);
		
		this.itoic = itoic;
		this.itac = itac;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		setBounds(new Rectangle(35, 35, 400, 420));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel formContent = new JPanel();
		getContentPane().add(formContent, BorderLayout.CENTER);
		formContent.setLayout(new BorderLayout(0, 0));
		
		formContent.add(selectOuting(), BorderLayout.NORTH);
		
		formContent.add(dataOuting(), BorderLayout.CENTER);
		
		
	}
	
	private JPanel selectOuting() {
		
		JPanel selectOuting = new JPanel();
		GridBagLayout gbl_selectOuting = new GridBagLayout();
		gbl_selectOuting.columnWidths = new int[]{74, 116, 95, 40, 0};
		gbl_selectOuting.rowHeights = new int[]{2, 22, 0};
		gbl_selectOuting.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_selectOuting.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		selectOuting.setLayout(gbl_selectOuting);
		
		JLabel lblSelectOuting = new JLabel("Seleccionar salida turistica");
		GridBagConstraints gbc_lblSelectOuting = new GridBagConstraints();
		gbc_lblSelectOuting.anchor = GridBagConstraints.WEST;
		gbc_lblSelectOuting.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectOuting.gridx = 1;
		gbc_lblSelectOuting.gridy = 1;
		selectOuting.add(lblSelectOuting, gbc_lblSelectOuting);
		
		cmbSelOuting = new JComboBox<String>();
		GridBagConstraints gbc_cmbSelOuting = new GridBagConstraints();
		gbc_cmbSelOuting.insets = new Insets(0, 0, 0, 5);
		gbc_cmbSelOuting.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbSelOuting.gridx = 2;
		gbc_cmbSelOuting.gridy = 1;
		selectOuting.add(cmbSelOuting, gbc_cmbSelOuting);
		
		loadComboSelectOuting();
		
		cmbSelOuting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
				cmdSelectOutingActionPerformed(arg0);
			}
		});
		
		return selectOuting;
	}
	
	private void loadComboSelectOuting() {
		DefaultComboBoxModel<String> model; 
		try {
			
			String[] data = itoic.listTouristOutings();
			System.out.println("Datos de salida cargados: " + data);
			if (data != null) {
				String[] dataWithNull = new String[data.length + 1];
				dataWithNull[0] = null; // Primera opción nula
				System.arraycopy(data, 0, dataWithNull, 1, data.length);
				model = new DefaultComboBoxModel<String>(dataWithNull); 
				cmbSelOuting.setModel(model);
			}
				
		}catch (TouristOutingDoesNotExistException e) {
			cmbSelOuting.setModel(new DefaultComboBoxModel<String>());
		}
	}
	
	protected void cmdSelectOutingActionPerformed(ActionEvent e) {
		String selectedOuting = (String) cmbSelOuting.getSelectedItem();
		clearOutingData();
		if (selectedOuting != null) {
			try {
				DtTouristOuting dtoOuting = itoic.consultTouristOutingData(selectedOuting);
				if (dtoOuting != null) {
					txtOutName.setText(dtoOuting.getTipName());
					txtOutMax.setText(String.valueOf(dtoOuting.getMaxNumTourists()));
					txtOutDepPoint.setText(dtoOuting.getDeparturePoint());
					txtOutDepDate.setText(dtoOuting.getDepartureDate().format(formatter));
					txtOutDesDate.setText(dtoOuting.getDischargeDate().format(formatter));
					
					if (dtoOuting.getActivityName() != null) {
						DtTouristActivity dtActivity;
						try {
							dtActivity = itac.consultTouristActivityBasicData(dtoOuting.getActivityName());
							if (dtActivity != null) {
								txtActName.setText(dtActivity.getActivityName());
								txtActDescription.setText(dtActivity.getDescription());
								txtActDuration.setText(String.valueOf(dtActivity.getDuration()));
								txtActCost.setText(String.valueOf(dtActivity.getCostTurist()));
								txtActCity.setText(dtActivity.getCity());
								txtActRegDate.setText(dtActivity.getRegistratioDate().format(formatter));
							}
						} catch (ActivityDoesNotExistException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} 
				
			} catch (TouristOutingDoesNotExistException ex) {
				ex.printStackTrace();
			}
		}
	}
	
//	private void loadComboActSelectOutings(Set<DtTouristOuting> outings) {
//		DefaultComboBoxModel<String> model;
//		// Crear un array con una opción nula al principio
//		String[] data = outings.stream().map(DtTouristOuting::getTipName).toArray(String[]::new);
//		String[] dataWithNull = new String[data.length + 1];
//		dataWithNull[0] = null; // Primera opción nula
//		System.arraycopy(data, 0, dataWithNull, 1, data.length);
//		model = new DefaultComboBoxModel<String>(dataWithNull);
//		cmbActOutings.setModel(model);
//	}
	
	private JPanel dataOuting() {
		
		JPanel dataOuting = new JPanel();
		dataOuting.setLayout(new BorderLayout(0, 0));
		dataOuting.add(basicDataOuting(), BorderLayout.NORTH);
		
		return dataOuting;
	}

	private JPanel basicDataOuting() {
		
		JPanel basicDataOuting = new JPanel();
		basicDataOuting.setBorder(new TitledBorder(null, "Informacion de la Salida turistica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_basicDataOuting = new GridBagLayout();
		gbl_basicDataOuting.columnWidths = new int[]{44, 133, 138, 40, 0};
		gbl_basicDataOuting.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0};
		gbl_basicDataOuting.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_basicDataOuting.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		basicDataOuting.setLayout(gbl_basicDataOuting);
		
		JLabel lblOutName = new JLabel("Nombre");
		GridBagConstraints gbc_lblOutName = new GridBagConstraints();
		gbc_lblOutName.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblOutName.gridx = 1;
		gbc_lblOutName.gridy = 0;
		basicDataOuting.add(lblOutName, gbc_lblOutName);
		
		txtOutName = new JTextField();
		txtOutName.setEditable(false);
		GridBagConstraints gbc_txtOutName = new GridBagConstraints();
		gbc_txtOutName.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOutName.gridx = 2;
		gbc_txtOutName.gridy = 0;
		basicDataOuting.add(txtOutName, gbc_txtOutName);
		txtOutName.setColumns(10);
		
		JLabel lblOutDepPoint = new JLabel("Punto de encuentro");
		GridBagConstraints gbc_lblOutDepPoint = new GridBagConstraints();
		gbc_lblOutDepPoint.anchor = GridBagConstraints.WEST;
		gbc_lblOutDepPoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutDepPoint.gridx = 1;
		gbc_lblOutDepPoint.gridy = 1;
		basicDataOuting.add(lblOutDepPoint, gbc_lblOutDepPoint);
		
		txtOutDepPoint = new JTextField();
		txtOutDepPoint.setEditable(false);
		GridBagConstraints gbc_txtOutDepPoint = new GridBagConstraints();
		gbc_txtOutDepPoint.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutDepPoint.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOutDepPoint.gridx = 2;
		gbc_txtOutDepPoint.gridy = 1;
		basicDataOuting.add(txtOutDepPoint, gbc_txtOutDepPoint);
		txtOutDepPoint.setColumns(10);
		
		JLabel lblOutMax = new JLabel("Maximo de turistas");
		GridBagConstraints gbc_lblOutMax = new GridBagConstraints();
		gbc_lblOutMax.anchor = GridBagConstraints.WEST;
		gbc_lblOutMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutMax.gridx = 1;
		gbc_lblOutMax.gridy = 2;
		basicDataOuting.add(lblOutMax, gbc_lblOutMax);
		
		txtOutMax = new JTextField();
		txtOutMax.setEditable(false);
		GridBagConstraints gbc_txtOutMax = new GridBagConstraints();
		gbc_txtOutMax.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOutMax.gridx = 2;
		gbc_txtOutMax.gridy = 2;
		basicDataOuting.add(txtOutMax, gbc_txtOutMax);
		txtOutMax.setColumns(10);
		
		JLabel lblOutDepDate = new JLabel("Fecha de salida");
		GridBagConstraints gbc_lblOutDepDate = new GridBagConstraints();
		gbc_lblOutDepDate.anchor = GridBagConstraints.WEST;
		gbc_lblOutDepDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutDepDate.gridx = 1;
		gbc_lblOutDepDate.gridy = 3;
		basicDataOuting.add(lblOutDepDate, gbc_lblOutDepDate);
		
		txtOutDepDate = new JTextField();
		txtOutDepDate.setEditable(false);
		GridBagConstraints gbc_txtOutDepDate = new GridBagConstraints();
		gbc_txtOutDepDate.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutDepDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOutDepDate.gridx = 2;
		gbc_txtOutDepDate.gridy = 3;
		basicDataOuting.add(txtOutDepDate, gbc_txtOutDepDate);
		txtOutDepDate.setColumns(10);
		
		JLabel lblOutDesDate = new JLabel("Fecha de llegada");
		GridBagConstraints gbc_lblOutDesDate = new GridBagConstraints();
		gbc_lblOutDesDate.anchor = GridBagConstraints.WEST;
		gbc_lblOutDesDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutDesDate.gridx = 1;
		gbc_lblOutDesDate.gridy = 4;
		basicDataOuting.add(lblOutDesDate, gbc_lblOutDesDate);
		
		txtOutDesDate = new JTextField();
		txtOutDesDate.setEditable(false);
		GridBagConstraints gbc_txtOutDesDate = new GridBagConstraints();
		gbc_txtOutDesDate.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutDesDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOutDesDate.gridx = 2;
		gbc_txtOutDesDate.gridy = 4;
		basicDataOuting.add(txtOutDesDate, gbc_txtOutDesDate);
		txtOutDesDate.setColumns(10);
		
		GridBagConstraints gbc_basicDataActivity = new GridBagConstraints();
		gbc_basicDataActivity.gridheight = 5;
		gbc_basicDataActivity.gridwidth = 2;
		gbc_basicDataActivity.insets = new Insets(0, 0, 0, 5);
		gbc_basicDataActivity.fill = GridBagConstraints.BOTH;
		gbc_basicDataActivity.gridx = 1;
		gbc_basicDataActivity.gridy = 5;
		
		basicDataOuting.add(basicDataActivity(), gbc_basicDataActivity);
		
		return basicDataOuting;
	}
	
	private JPanel basicDataActivity() {
		
		JPanel basicDataActivity = new JPanel();
		basicDataActivity.setBorder(new TitledBorder(new LineBorder(new Color(191, 205, 219)), "Informacion de la Actividad asociada", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagLayout gbl_basicDataActivity = new GridBagLayout();
		gbl_basicDataActivity.columnWidths = new int[]{16, 85, 83, 0, 0};
		gbl_basicDataActivity.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_basicDataActivity.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_basicDataActivity.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		basicDataActivity.setLayout(gbl_basicDataActivity);
		
		JLabel lblActName = new JLabel("Nombre");
		GridBagConstraints gbc_lblActName = new GridBagConstraints();
		gbc_lblActName.insets = new Insets(0, 0, 5, 5);
		gbc_lblActName.anchor = GridBagConstraints.WEST;
		gbc_lblActName.gridx = 1;
		gbc_lblActName.gridy = 0;
		basicDataActivity.add(lblActName, gbc_lblActName);
		
		txtActName = new JTextField();
		txtActName.setEditable(false);
		GridBagConstraints gbc_txtActName = new GridBagConstraints();
		gbc_txtActName.insets = new Insets(0, 0, 5, 5);
		gbc_txtActName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActName.gridx = 2;
		gbc_txtActName.gridy = 0;
		basicDataActivity.add(txtActName, gbc_txtActName);
		txtActName.setColumns(10);
		
		JLabel lblActDescription = new JLabel("Descripcion");
		GridBagConstraints gbc_lblActDescription = new GridBagConstraints();
		gbc_lblActDescription.anchor = GridBagConstraints.WEST;
		gbc_lblActDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblActDescription.gridx = 1;
		gbc_lblActDescription.gridy = 1;
		basicDataActivity.add(lblActDescription, gbc_lblActDescription);
		
		txtActDescription = new JTextField();
		txtActDescription.setEditable(false);
		GridBagConstraints gbc_txtActDescription = new GridBagConstraints();
		gbc_txtActDescription.insets = new Insets(0, 0, 5, 5);
		gbc_txtActDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActDescription.gridx = 2;
		gbc_txtActDescription.gridy = 1;
		basicDataActivity.add(txtActDescription, gbc_txtActDescription);
		txtActDescription.setColumns(10);
		
		JLabel lblActDuration = new JLabel("Duracion");
		GridBagConstraints gbc_lblActDuration = new GridBagConstraints();
		gbc_lblActDuration.anchor = GridBagConstraints.WEST;
		gbc_lblActDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblActDuration.gridx = 1;
		gbc_lblActDuration.gridy = 2;
		basicDataActivity.add(lblActDuration, gbc_lblActDuration);
		
		txtActDuration = new JTextField();
		txtActDuration.setEditable(false);
		GridBagConstraints gbc_txtActDuration = new GridBagConstraints();
		gbc_txtActDuration.insets = new Insets(0, 0, 5, 5);
		gbc_txtActDuration.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActDuration.gridx = 2;
		gbc_txtActDuration.gridy = 2;
		basicDataActivity.add(txtActDuration, gbc_txtActDuration);
		txtActDuration.setColumns(10);
		
		JLabel lblActCost = new JLabel("Costo por turista");
		GridBagConstraints gbc_lblActCost = new GridBagConstraints();
		gbc_lblActCost.anchor = GridBagConstraints.WEST;
		gbc_lblActCost.insets = new Insets(0, 0, 5, 5);
		gbc_lblActCost.gridx = 1;
		gbc_lblActCost.gridy = 3;
		basicDataActivity.add(lblActCost, gbc_lblActCost);
		
		txtActCost = new JTextField();
		txtActCost.setEditable(false);
		GridBagConstraints gbc_txtActCost = new GridBagConstraints();
		gbc_txtActCost.insets = new Insets(0, 0, 5, 5);
		gbc_txtActCost.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActCost.gridx = 2;
		gbc_txtActCost.gridy = 3;
		basicDataActivity.add(txtActCost, gbc_txtActCost);
		txtActCost.setColumns(10);
		
		JLabel lblActCity = new JLabel("Ciudad");
		GridBagConstraints gbc_lblActCity = new GridBagConstraints();
		gbc_lblActCity.anchor = GridBagConstraints.WEST;
		gbc_lblActCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblActCity.gridx = 1;
		gbc_lblActCity.gridy = 4;
		basicDataActivity.add(lblActCity, gbc_lblActCity);
		
		txtActCity = new JTextField();
		txtActCity.setEditable(false);
		GridBagConstraints gbc_txtActCity = new GridBagConstraints();
		gbc_txtActCity.insets = new Insets(0, 0, 5, 5);
		gbc_txtActCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActCity.gridx = 2;
		gbc_txtActCity.gridy = 4;
		basicDataActivity.add(txtActCity, gbc_txtActCity);
		txtActCity.setColumns(10);
		
		JLabel lblActRegDate = new JLabel("Fecha de registro");
		GridBagConstraints gbc_lblActRegDate = new GridBagConstraints();
		gbc_lblActRegDate.anchor = GridBagConstraints.WEST;
		gbc_lblActRegDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblActRegDate.gridx = 1;
		gbc_lblActRegDate.gridy = 5;
		basicDataActivity.add(lblActRegDate, gbc_lblActRegDate);
		
		txtActRegDate = new JTextField();
		txtActRegDate.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 5;
		basicDataActivity.add(txtActRegDate, gbc_textField);
		txtActRegDate.setColumns(10);
		
		return basicDataActivity;
	}
	
	private void clearForm() {
		clearActivityData();
		cmbSelOuting.removeAllItems();
	}
	
	private void clearActivityData() {
		txtActName.setText("");
		txtActDescription.setText("");
		txtActDuration.setText("");
		txtActCost.setText("");
		txtActCity.setText("");
		txtActRegDate.setText("");
	}
	
	private void clearOutingData() {
		txtOutName.setText("");
		txtOutMax.setText("");
		txtOutDepPoint.setText("");
		txtOutDepDate.setText("");
		txtOutDesDate.setText("");
		clearActivityData();
	}
	
	public void init() {
		clearForm();
		loadComboSelectOuting();
	}
	
}
