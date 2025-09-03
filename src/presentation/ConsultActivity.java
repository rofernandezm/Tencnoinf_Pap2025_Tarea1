package presentation;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.dto.DtTouristActivity;
import logic.interfaces.ITouristActivityController;
import logic.interfaces.IUserController;

public class ConsultActivity extends JInternalFrame  {

	private ITouristActivityController iTouristActivityController;

	// Title
	private JLabel labelSubTitle;

	// Basic form
	private JLabel labelNickname;
	private JTextField fieldNickname;
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelLastname;
	private JTextField fieldLastname;
	private JLabel labelEmail;
	private JTextField fieldEmail;
	private JLabel label_birthDate;
	private JSpinner field_birthDate;

	// User type radiobuttons
	private JRadioButton rdbtnTourist;
	private JRadioButton rdbtnSupplier;
	private final ButtonGroup userTypeButtons = new ButtonGroup();

	// Tourist form
	private JLabel labelNationalty;
	private JTextField fieldNationality;

	// Supplier form
	private JLabel labelDescription;
	private JTextArea textAreaDescription;
	private JLabel labelSitioweb;
	private JTextField fieldSitioweb;

	// Common actions
	private JButton btnCancel;
	private JButton btnConfirm;
	private JPanel cl_AdditionalData;
	
	// RESOURCES
	private static final String TITLE = "Consultar Actividad Turística";
	private static final String SUBTITLE = "Complete la información del usuario";
	private static final String NICKNAME = "Nickname:";
	private static final String NICKNAME_TOOLTIP = "Nickname que identificará al usuario dentro del sistema.";
	private static final String NAME = "Nombre:";
	private static final String LASTNAME = "Apellido:";
	private static final String EMAIL = "Correo electrónico:";
	private static final String BIRTH_DATE = "Fecha de nacimiento:";
	private static final String USER_TYPE = "Tipo de usuario:";
	private static final String TOURIST = "Turista";
	private static final String SUPPLIER = "Proveedor";
	private static final String ADDITIONAL_DATA = "Datos adicionales:";
	private static final String TOURIST_NATIONALITY = "Nacionalidad:";
	private static final String SUPPLIER_DESC = "Descripción:";
	private static final String SUPPLIER_WEBSITE = "Sitio web:";
	private static final String BUTTON_CONFIRM = "Confirmar";
	private static final String BUTTON_CANCEL = "Cancelar";
	private static final String TOURIST_FORM = "touristForm";
	private static final String SUPPLIER_FORM = "supplierForm";

	// Patterns
	private static final String BIRTH_DATE_FORMAT = "dd/MM/yyyy";
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
			Pattern.CASE_INSENSITIVE);

	// GRID POSITION
	private static final int GRID_LABEL_POS = 1;
	private static final int GRID_FIELD_POS = 2;
	
	public ConsultActivity(ITouristActivityController iTouristActivityController) {
		
		super(TITLE, true, true, true, true);
		
		this.iTouristActivityController = iTouristActivityController;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// Content panel
		getContentPane().setLayout(new BorderLayout(0, 0));
		setBounds(35, 35, 450, 500);

		// Form and actions container
		JPanel formContainer = new JPanel();
		formContainer.setBorder(null);
		getContentPane().add(formContainer, BorderLayout.CENTER);
		formContainer.setLayout(new BorderLayout(0, 0));

		// Form: basic data
		formContainer.add(initBasicDataFormPanel(), BorderLayout.NORTH);

		// Form: user type
		//formContainer.add(initUserTypeFormPanel(), BorderLayout.CENTER);

		// Form: Action buttons
		formContainer.add(initActionButtons(), BorderLayout.SOUTH);
	}
	private JPanel initBasicDataFormPanel() {

		JPanel basicDataPanel = new JPanel();
		basicDataPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		GridBagLayout gbl_basicData = new GridBagLayout();
		gbl_basicData.columnWidths = new int[] { 30, 103, 276, 0 };
		gbl_basicData.rowHeights = new int[] { 0, 30, 30, 34, 0 };
		gbl_basicData.columnWeights = new double[] { 0.0, 1.0, 1.0 };
		gbl_basicData.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

		basicDataPanel.setLayout(gbl_basicData);

		labelNickname = new JLabel(NICKNAME);
		GridBagConstraints gbc_label_Nickname = new GridBagConstraints();
		gbc_label_Nickname.anchor = GridBagConstraints.WEST;
		gbc_label_Nickname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Nickname.gridx = GRID_LABEL_POS;
		gbc_label_Nickname.gridy = 0;
		basicDataPanel.add(labelNickname, gbc_label_Nickname);

		fieldNickname = new JTextField();
		labelNickname.setLabelFor(fieldNickname);
		fieldNickname.setToolTipText(NICKNAME_TOOLTIP);
		GridBagConstraints gbc_field_Nickname = new GridBagConstraints();
		gbc_field_Nickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Nickname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Nickname.gridx = GRID_FIELD_POS;
		gbc_field_Nickname.gridy = 0;
		basicDataPanel.add(fieldNickname, gbc_field_Nickname);
		fieldNickname.setColumns(10);

		labelName = new JLabel(NAME);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = GRID_LABEL_POS;
		gbc_lblNombre.gridy = 1;
		basicDataPanel.add(labelName, gbc_lblNombre);

		fieldName = new JTextField();
		labelName.setLabelFor(fieldName);
		GridBagConstraints gbc_field_Name = new GridBagConstraints();
		gbc_field_Name.insets = new Insets(0, 0, 5, 5);
		gbc_field_Name.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Name.gridx = GRID_FIELD_POS;
		gbc_field_Name.gridy = 1;
		basicDataPanel.add(fieldName, gbc_field_Name);
		fieldName.setColumns(10);

		labelLastname = new JLabel(LASTNAME);
		GridBagConstraints gbc_label_Lastname = new GridBagConstraints();
		gbc_label_Lastname.anchor = GridBagConstraints.WEST;
		gbc_label_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Lastname.gridx = GRID_LABEL_POS;
		gbc_label_Lastname.gridy = 2;
		basicDataPanel.add(labelLastname, gbc_label_Lastname);

		fieldLastname = new JTextField();
		labelLastname.setLabelFor(fieldLastname);
		GridBagConstraints gbc_field_Lastname = new GridBagConstraints();
		gbc_field_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Lastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Lastname.gridx = GRID_FIELD_POS;
		gbc_field_Lastname.gridy = 2;
		basicDataPanel.add(fieldLastname, gbc_field_Lastname);
		fieldLastname.setColumns(10);

		labelEmail = new JLabel(EMAIL);
		GridBagConstraints gbc_label_Email = new GridBagConstraints();
		gbc_label_Email.insets = new Insets(0, 0, 5, 5);
		gbc_label_Email.anchor = GridBagConstraints.WEST;
		gbc_label_Email.gridx = GRID_LABEL_POS;
		gbc_label_Email.gridy = 3;
		basicDataPanel.add(labelEmail, gbc_label_Email);

		fieldEmail = new JTextField();
		labelEmail.setLabelFor(fieldEmail);
		GridBagConstraints gbc_field_Email = new GridBagConstraints();
		gbc_field_Email.insets = new Insets(0, 0, 5, 5);
		gbc_field_Email.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Email.gridx = GRID_FIELD_POS;
		gbc_field_Email.gridy = 3;
		basicDataPanel.add(fieldEmail, gbc_field_Email);
		fieldEmail.setColumns(10);

		label_birthDate = new JLabel(BIRTH_DATE);
		GridBagConstraints gbc_label_birthDate = new GridBagConstraints();
		gbc_label_birthDate.anchor = GridBagConstraints.WEST;
		gbc_label_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_label_birthDate.gridx = GRID_LABEL_POS;
		gbc_label_birthDate.gridy = 4;
		basicDataPanel.add(label_birthDate, gbc_label_birthDate);

		field_birthDate = new JSpinner();
		label_birthDate.setLabelFor(field_birthDate);
		field_birthDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		field_birthDate.setEditor(new JSpinner.DateEditor(field_birthDate, BIRTH_DATE_FORMAT));
		GridBagConstraints gbc_field_birthDate = new GridBagConstraints();
		gbc_field_birthDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_field_birthDate.gridx = GRID_FIELD_POS;
		gbc_field_birthDate.gridy = 4;
		basicDataPanel.add(field_birthDate, gbc_field_birthDate);

		return basicDataPanel;
	}
	
	private JPanel initActionButtons() {
		// Action button
		JPanel fl_Buttons = new JPanel();
		fl_Buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));

		ActionListener cancelAction = e -> {
			clearForm();
			setVisible(false);
		};

		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(cancelAction);
		fl_Buttons.add(btnCancel);

		return fl_Buttons;
	}
	
	private void clearForm() {
		this.fieldNickname.setText("");
		this.fieldName.setText("");
		this.fieldLastname.setText("");
		this.fieldEmail.setText("");
		this.fieldNationality.setText("");
		this.textAreaDescription.setText("");
		this.fieldSitioweb.setText("");
	}
}
