package presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import logic.interfaces.IUserController;
import javax.swing.JButton;

public class ModifyUser extends JInternalFrame {

	private IUserController iUserController;
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelLastname;
	private JTextField fieldLastname;
	private JLabel labelEmail;
	private JTextField fieldEmail;
	private JLabel label_birthDate;
	private JSpinner field_birthDate;

	private JPanel topContainer;
	private JPanel nicknameCombo;
	private JComboBox nicknameComboBox;
	private JPanel centerContainer;
	private JPanel cl_centerContainer;
	private JPanel supplierContainer;
	private JPanel touristContainer;
	private JPanel selectActivityPanel;
	private JPanel titleContainer;
	private JComboBox activityComboBox;
	private JPanel activityDetailsTitle;
	private JPanel touristOutingDetailsTitle;
	private JTextField fieldActName;
	private JTextField fieldActCost;
	private JTextField fieldCity;
	private JPanel descTitlePanel;
	private JPanel durTitlePanel;
	private JSpinner fieldDurAct;
	private JPanel costTitlePanel;
	private JPanel citytitlePanel;
	private JPanel dischDateTitlePanel;
	private JPanel selectTouristOuting;
	private JTextField fieldOutingName;
	private JTextField fieldOutingCantMax;
	private JTextField fieldOutingPlace;
	private JSpinner fieldDepartureDate;
	private JSpinner fieldOutDischargeDate;
	private JPanel outingNameTitle;
	private JPanel outingCantMaxTitle;
	private JPanel outingPlaceTitle;
	private JPanel outingDateTitle;
	private JPanel outingDischDateTitle;
	private JPanel selectRegisteredOutingContainer;
	private JPanel selectRegisteredOutingTitle;
	private JComboBox selectRegisteredOutingComboBox;
	private JPanel registeredOutingDetailsTitle;
	private JTextField fieldRegisteredOutName;
	private JTextField fieldRegisteredOutCantMax;
	private JTextField fieldRegisteredOutPlace;
	private JSpinner fieldRegisteredOutDepatureDate;
	private JSpinner fieldRegisteredOutDischargeDate;
	private JPanel registOutNameTitle;
	private JPanel registOutCantMaxTitle;
	private JPanel registOutPlaceTitle;
	private JPanel registOutDateTitle;
	private JPanel registOutDischDateTitle;

	// RESOURCES
	private static final String TITLE = "Registro de usuario";
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
	private JPanel actionButtonsPanel;

	public ModifyUser(IUserController iuc) {
		super(TITLE, true, true, true, true);
		getContentPane().setMinimumSize(new Dimension(780, 540));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		iUserController = iuc;
		setBounds(0, 0, 521, 409);
		getContentPane().setLayout(null);

		// Form and actions container
		JPanel formContainer = new JPanel();
		formContainer.setBounds(0, 0, 501, 367);
		formContainer.setBorder(null);
		getContentPane().add(formContainer);
		formContainer.setLayout(null);

		formContainer.add(createTopContainer());
		formContainer.add(createActionButtonsPanel());

	}
	
	private JPanel createTopContainer() {
		
		topContainer = new JPanel();
		topContainer.setBounds(0, 0, 504, 299);
		topContainer.setBorder(null);

		topContainer.setLayout(null);
		JPanel nicknameComboContainer = new JPanel();
		nicknameComboContainer.setBounds(28, 12, 442, 99);
		topContainer.add(nicknameComboContainer);
		GridBagLayout gbl_nicknameComboContainer = new GridBagLayout();
		gbl_nicknameComboContainer.columnWidths = new int[] { 447, 0 };
		gbl_nicknameComboContainer.rowHeights = new int[] { 44 };
		gbl_nicknameComboContainer.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_nicknameComboContainer.rowWeights = new double[] { 0.0 };
		nicknameComboContainer.setLayout(gbl_nicknameComboContainer);

		nicknameCombo = new JPanel();
		GridBagConstraints gbc_nicknameCombo = new GridBagConstraints();
		gbc_nicknameCombo.fill = GridBagConstraints.BOTH;
		gbc_nicknameCombo.gridx = 0;
		gbc_nicknameCombo.gridy = 0;
		nicknameComboContainer.add(nicknameCombo, gbc_nicknameCombo);
		nicknameCombo.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5),
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione el nickname de usuario:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51))));
		GridBagLayout gbl_nicknameCombo = new GridBagLayout();
		gbl_nicknameCombo.columnWidths = new int[] { 423, 0 };
		gbl_nicknameCombo.rowHeights = new int[] { 44, 0 };
		gbl_nicknameCombo.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_nicknameCombo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		nicknameCombo.setLayout(gbl_nicknameCombo);

		nicknameComboBox = new JComboBox();
		GridBagConstraints gbc_nicknameComboBox = new GridBagConstraints();
		gbc_nicknameComboBox.fill = GridBagConstraints.BOTH;
		gbc_nicknameComboBox.gridx = 0;
		gbc_nicknameComboBox.gridy = 0;
		nicknameCombo.add(nicknameComboBox, gbc_nicknameComboBox);
		nicknameComboBox.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel basicDataPanel = new JPanel();
		basicDataPanel.setBounds(35, 123, 435, 159);
		topContainer.add(basicDataPanel);
		basicDataPanel
				.setBorder(new CompoundBorder(
						new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Datos a modificar:",
								TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)),
						new EmptyBorder(10, 10, 10, 10)));
		GridBagLayout gbl_basicData = new GridBagLayout();
		gbl_basicData.columnWidths = new int[] { 30, 103, 276, 0 };
		gbl_basicData.rowHeights = new int[] { 30, 30, 34, 0 };
		gbl_basicData.columnWeights = new double[] { 0.0, 1.0, 1.0 };
		gbl_basicData.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };

		basicDataPanel.setLayout(gbl_basicData);

		labelName = new JLabel(NAME);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = GRID_LABEL_POS;
		gbc_lblNombre.gridy = 0;
		basicDataPanel.add(labelName, gbc_lblNombre);

		fieldName = new JTextField();
		fieldName.setEnabled(false);
		fieldName.setEditable(false);
		labelName.setLabelFor(fieldName);
		GridBagConstraints gbc_field_Name = new GridBagConstraints();
		gbc_field_Name.insets = new Insets(0, 0, 5, 5);
		gbc_field_Name.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Name.gridx = GRID_FIELD_POS;
		gbc_field_Name.gridy = 0;
		basicDataPanel.add(fieldName, gbc_field_Name);
		fieldName.setColumns(10);

		labelLastname = new JLabel(LASTNAME);
		GridBagConstraints gbc_label_Lastname = new GridBagConstraints();
		gbc_label_Lastname.anchor = GridBagConstraints.WEST;
		gbc_label_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Lastname.gridx = GRID_LABEL_POS;
		gbc_label_Lastname.gridy = 1;
		basicDataPanel.add(labelLastname, gbc_label_Lastname);

		fieldLastname = new JTextField();
		fieldLastname.setEnabled(false);
		fieldLastname.setEditable(false);
		labelLastname.setLabelFor(fieldLastname);
		GridBagConstraints gbc_field_Lastname = new GridBagConstraints();
		gbc_field_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Lastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Lastname.gridx = GRID_FIELD_POS;
		gbc_field_Lastname.gridy = 1;
		basicDataPanel.add(fieldLastname, gbc_field_Lastname);
		fieldLastname.setColumns(10);

		labelEmail = new JLabel(EMAIL);
		GridBagConstraints gbc_label_Email = new GridBagConstraints();
		gbc_label_Email.insets = new Insets(0, 0, 5, 5);
		gbc_label_Email.anchor = GridBagConstraints.WEST;
		gbc_label_Email.gridx = GRID_LABEL_POS;
		gbc_label_Email.gridy = 2;
		basicDataPanel.add(labelEmail, gbc_label_Email);

		fieldEmail = new JTextField();
		fieldEmail.setEnabled(false);
		fieldEmail.setEditable(false);
		labelEmail.setLabelFor(fieldEmail);
		GridBagConstraints gbc_field_Email = new GridBagConstraints();
		gbc_field_Email.insets = new Insets(0, 0, 5, 5);
		gbc_field_Email.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Email.gridx = GRID_FIELD_POS;
		gbc_field_Email.gridy = 2;
		basicDataPanel.add(fieldEmail, gbc_field_Email);
		fieldEmail.setColumns(10);

		label_birthDate = new JLabel(BIRTH_DATE);
		GridBagConstraints gbc_label_birthDate = new GridBagConstraints();
		gbc_label_birthDate.anchor = GridBagConstraints.WEST;
		gbc_label_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_label_birthDate.gridx = GRID_LABEL_POS;
		gbc_label_birthDate.gridy = 3;
		basicDataPanel.add(label_birthDate, gbc_label_birthDate);

		field_birthDate = new JSpinner();
		field_birthDate.setEnabled(false);
		label_birthDate.setLabelFor(field_birthDate);
		field_birthDate.setModel(new SpinnerDateModel(new Date(1757197346329L), null, null, Calendar.DAY_OF_MONTH));
		field_birthDate.setEditor(new JSpinner.DateEditor(field_birthDate, BIRTH_DATE_FORMAT));
		GridBagConstraints gbc_field_birthDate = new GridBagConstraints();
		gbc_field_birthDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_field_birthDate.gridx = GRID_FIELD_POS;
		gbc_field_birthDate.gridy = 3;
		basicDataPanel.add(field_birthDate, gbc_field_birthDate);
		
		return topContainer;
	}
	
	private JPanel createActionButtonsPanel() {
		
		actionButtonsPanel = new JPanel();
		actionButtonsPanel.setBounds(36, 311, 431, 47);
		actionButtonsPanel.setLayout(null);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(247, 12, 117, 25);
		actionButtonsPanel.add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(65, 12, 117, 25);
		actionButtonsPanel.add(btnCancelar);
		
		return actionButtonsPanel;
	}
}
