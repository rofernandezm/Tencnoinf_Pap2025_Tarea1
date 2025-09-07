package presentation;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import logic.interfaces.IUserController;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButtonMenuItem;
import java.awt.List;
import java.awt.Choice;
import java.awt.Label;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import javax.swing.border.BevelBorder;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ConsultUser extends JInternalFrame {
	private IUserController iUserController;

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

	public ConsultUser(IUserController iuc) {

		super(TITLE, true, true, true, true);
		getContentPane().setMinimumSize(new Dimension(780, 540));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		iUserController = iuc;
		setBounds(0, 0, 921, 692);
		getContentPane().setLayout(null);

		// Form and actions container
		JPanel formContainer = new JPanel();
		formContainer.setBounds(0, 0, 920, 677);
		formContainer.setBorder(null);
		getContentPane().add(formContainer);
		formContainer.setLayout(null);

		topContainer = new JPanel();
		topContainer.setBounds(0, 0, 920, 188);
		topContainer.setBorder(null);

		formContainer.add(topContainer);
		topContainer.setLayout(null);
		JPanel nicknameComboContainer = new JPanel();
		nicknameComboContainer.setBounds(23, 11, 372, 155);
		topContainer.add(nicknameComboContainer);
		GridBagLayout gbl_nicknameComboContainer = new GridBagLayout();
		gbl_nicknameComboContainer.columnWidths = new int[] { 345, 0 };
		gbl_nicknameComboContainer.rowHeights = new int[] { 22, 44, 44 };
		gbl_nicknameComboContainer.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_nicknameComboContainer.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		nicknameComboContainer.setLayout(gbl_nicknameComboContainer);

		nicknameCombo = new JPanel();
		GridBagConstraints gbc_nicknameCombo = new GridBagConstraints();
		gbc_nicknameCombo.insets = new Insets(0, 0, 5, 0);
		gbc_nicknameCombo.fill = GridBagConstraints.BOTH;
		gbc_nicknameCombo.gridx = 0;
		gbc_nicknameCombo.gridy = 1;
		nicknameComboContainer.add(nicknameCombo, gbc_nicknameCombo);
		nicknameCombo.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5),
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione el nickname de usuario:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51))));
		GridBagLayout gbl_nicknameCombo = new GridBagLayout();
		gbl_nicknameCombo.columnWidths = new int[] { 345, 0 };
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
		basicDataPanel.setBounds(400, 0, 492, 178);
		topContainer.add(basicDataPanel);
		basicDataPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		GridBagLayout gbl_basicData = new GridBagLayout();
		gbl_basicData.columnWidths = new int[] { 30, 103, 276, 0 };
		gbl_basicData.rowHeights = new int[] { 0, 0, 30, 30, 34, 0 };
		gbl_basicData.columnWeights = new double[] { 0.0, 1.0, 1.0 };
		gbl_basicData.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		basicDataPanel.setLayout(gbl_basicData);

		labelNickname = new JLabel(NICKNAME);
		GridBagConstraints gbc_label_Nickname = new GridBagConstraints();
		gbc_label_Nickname.anchor = GridBagConstraints.WEST;
		gbc_label_Nickname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Nickname.gridx = GRID_LABEL_POS;
		gbc_label_Nickname.gridy = 1;
		basicDataPanel.add(labelNickname, gbc_label_Nickname);

		fieldNickname = new JTextField();
		fieldNickname.setEnabled(false);
		fieldNickname.setEditable(false);
		labelNickname.setLabelFor(fieldNickname);
		fieldNickname.setToolTipText(NICKNAME_TOOLTIP);
		GridBagConstraints gbc_field_Nickname = new GridBagConstraints();
		gbc_field_Nickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Nickname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Nickname.gridx = GRID_FIELD_POS;
		gbc_field_Nickname.gridy = 1;
		basicDataPanel.add(fieldNickname, gbc_field_Nickname);
		fieldNickname.setColumns(10);

		labelName = new JLabel(NAME);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = GRID_LABEL_POS;
		gbc_lblNombre.gridy = 2;
		basicDataPanel.add(labelName, gbc_lblNombre);

		fieldName = new JTextField();
		fieldName.setEnabled(false);
		fieldName.setEditable(false);
		labelName.setLabelFor(fieldName);
		GridBagConstraints gbc_field_Name = new GridBagConstraints();
		gbc_field_Name.insets = new Insets(0, 0, 5, 5);
		gbc_field_Name.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Name.gridx = GRID_FIELD_POS;
		gbc_field_Name.gridy = 2;
		basicDataPanel.add(fieldName, gbc_field_Name);
		fieldName.setColumns(10);

		labelLastname = new JLabel(LASTNAME);
		GridBagConstraints gbc_label_Lastname = new GridBagConstraints();
		gbc_label_Lastname.anchor = GridBagConstraints.WEST;
		gbc_label_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Lastname.gridx = GRID_LABEL_POS;
		gbc_label_Lastname.gridy = 3;
		basicDataPanel.add(labelLastname, gbc_label_Lastname);

		fieldLastname = new JTextField();
		fieldLastname.setEnabled(false);
		fieldLastname.setEditable(false);
		labelLastname.setLabelFor(fieldLastname);
		GridBagConstraints gbc_field_Lastname = new GridBagConstraints();
		gbc_field_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Lastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Lastname.gridx = GRID_FIELD_POS;
		gbc_field_Lastname.gridy = 3;
		basicDataPanel.add(fieldLastname, gbc_field_Lastname);
		fieldLastname.setColumns(10);

		labelEmail = new JLabel(EMAIL);
		GridBagConstraints gbc_label_Email = new GridBagConstraints();
		gbc_label_Email.insets = new Insets(0, 0, 5, 5);
		gbc_label_Email.anchor = GridBagConstraints.WEST;
		gbc_label_Email.gridx = GRID_LABEL_POS;
		gbc_label_Email.gridy = 4;
		basicDataPanel.add(labelEmail, gbc_label_Email);

		fieldEmail = new JTextField();
		fieldEmail.setEnabled(false);
		fieldEmail.setEditable(false);
		labelEmail.setLabelFor(fieldEmail);
		GridBagConstraints gbc_field_Email = new GridBagConstraints();
		gbc_field_Email.insets = new Insets(0, 0, 5, 5);
		gbc_field_Email.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Email.gridx = GRID_FIELD_POS;
		gbc_field_Email.gridy = 4;
		basicDataPanel.add(fieldEmail, gbc_field_Email);
		fieldEmail.setColumns(10);

		label_birthDate = new JLabel(BIRTH_DATE);
		GridBagConstraints gbc_label_birthDate = new GridBagConstraints();
		gbc_label_birthDate.anchor = GridBagConstraints.WEST;
		gbc_label_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_label_birthDate.gridx = GRID_LABEL_POS;
		gbc_label_birthDate.gridy = 5;
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
		gbc_field_birthDate.gridy = 5;
		basicDataPanel.add(field_birthDate, gbc_field_birthDate);
		
		centerContainer = new JPanel();
		centerContainer.setBounds(0, 196, 920, 469);
		formContainer.add(centerContainer);
		centerContainer.setLayout(null);
		
		cl_centerContainer = new JPanel();
		cl_centerContainer.setBounds(0, 0, 920, 457);
		centerContainer.add(cl_centerContainer);
		cl_centerContainer.setLayout(new CardLayout(0, 0));
		
		supplierContainer = new JPanel();
		cl_centerContainer.add(supplierContainer, "name_4419861694870");
		supplierContainer.setLayout(new BorderLayout(12, 0));
		
		selectActivityPanel = new JPanel();
		supplierContainer.add(selectActivityPanel, BorderLayout.NORTH);
		GridBagLayout gbl_selectActivityPanel = new GridBagLayout();
		gbl_selectActivityPanel.columnWidths = new int[]{30, 353, 0, 0};
		gbl_selectActivityPanel.rowHeights = new int[]{34, 0};
		gbl_selectActivityPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_selectActivityPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		selectActivityPanel.setLayout(gbl_selectActivityPanel);
		
		titleContainer = new JPanel();
		titleContainer.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione actividad:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)), new EmptyBorder(10, 10, 10, 10)));
		GridBagConstraints gbc_titleContainer = new GridBagConstraints();
		gbc_titleContainer.insets = new Insets(0, 0, 0, 5);
		gbc_titleContainer.fill = GridBagConstraints.HORIZONTAL;
		gbc_titleContainer.anchor = GridBagConstraints.NORTH;
		gbc_titleContainer.gridx = 1;
		gbc_titleContainer.gridy = 0;
		selectActivityPanel.add(titleContainer, gbc_titleContainer);
		titleContainer.setLayout(new CardLayout(0, 0));
		
		activityComboBox = new JComboBox();
		titleContainer.add(activityComboBox, "name_4625235659058");
		
		JPanel activityAndTouristOutingDetailsContainer = new JPanel();
		activityAndTouristOutingDetailsContainer.setBorder(new EmptyBorder(10, 30, 0, 0));
		supplierContainer.add(activityAndTouristOutingDetailsContainer, BorderLayout.CENTER);
		activityAndTouristOutingDetailsContainer.setLayout(null);
		
		activityDetailsTitle = new JPanel();
		activityDetailsTitle.setBounds(30, 10, 352, 369);
		activityDetailsTitle.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Informaci\u00F3n de la actividad seleccionada:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)), new EmptyBorder(15, 15, 15, 15)));
		activityAndTouristOutingDetailsContainer.add(activityDetailsTitle);
		GridBagLayout gbl_activityDetailsTitle = new GridBagLayout();
		gbl_activityDetailsTitle.columnWidths = new int[] {293};
		gbl_activityDetailsTitle.rowHeights = new int[] {30, 30, 30, 30, 30, 0};
		gbl_activityDetailsTitle.columnWeights = new double[]{1.0};
		gbl_activityDetailsTitle.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		activityDetailsTitle.setLayout(gbl_activityDetailsTitle);
		
		JPanel nameTitlePanel = new JPanel();
		nameTitlePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Nombre:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_nameTitlePanel = new GridBagConstraints();
		gbc_nameTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_nameTitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_nameTitlePanel.gridx = 0;
		gbc_nameTitlePanel.gridy = 0;
		activityDetailsTitle.add(nameTitlePanel, gbc_nameTitlePanel);
		GridBagLayout gbl_nameTitlePanel = new GridBagLayout();
		gbl_nameTitlePanel.columnWidths = new int[]{293, 0};
		gbl_nameTitlePanel.rowHeights = new int[]{30, 0};
		gbl_nameTitlePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_nameTitlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		nameTitlePanel.setLayout(gbl_nameTitlePanel);
		
		fieldActName = new JTextField();
		fieldActName.setEnabled(false);
		fieldActName.setEditable(false);
		GridBagConstraints gbc_fieldActName = new GridBagConstraints();
		gbc_fieldActName.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldActName.gridx = 0;
		gbc_fieldActName.gridy = 0;
		nameTitlePanel.add(fieldActName, gbc_fieldActName);
		fieldActName.setColumns(10);
		
		descTitlePanel = new JPanel();
		descTitlePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Descripci\u00F3n:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_descTitlePanel = new GridBagConstraints();
		gbc_descTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_descTitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_descTitlePanel.gridx = 0;
		gbc_descTitlePanel.gridy = 1;
		activityDetailsTitle.add(descTitlePanel, gbc_descTitlePanel);
		GridBagLayout gbl_descTitlePanel = new GridBagLayout();
		gbl_descTitlePanel.columnWidths = new int[]{293, 0};
		gbl_descTitlePanel.rowHeights = new int[]{30, 0};
		gbl_descTitlePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_descTitlePanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		descTitlePanel.setLayout(gbl_descTitlePanel);
		
		JTextArea fieldActDesc = new JTextArea();
		fieldActDesc.setEnabled(false);
		fieldActDesc.setEditable(false);
		fieldActDesc.setBorder(new EmptyBorder(5, 0, 0, 0));
		GridBagConstraints gbc_fieldActDesc = new GridBagConstraints();
		gbc_fieldActDesc.fill = GridBagConstraints.BOTH;
		gbc_fieldActDesc.gridx = 0;
		gbc_fieldActDesc.gridy = 0;
		descTitlePanel.add(fieldActDesc, gbc_fieldActDesc);
		
		durTitlePanel = new JPanel();
		durTitlePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Duraci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_durTitlePanel = new GridBagConstraints();
		gbc_durTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_durTitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_durTitlePanel.gridx = 0;
		gbc_durTitlePanel.gridy = 2;
		activityDetailsTitle.add(durTitlePanel, gbc_durTitlePanel);
		GridBagLayout gbl_durTitlePanel = new GridBagLayout();
		gbl_durTitlePanel.columnWidths = new int[]{293, 0};
		gbl_durTitlePanel.rowHeights = new int[]{30, 0};
		gbl_durTitlePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_durTitlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		durTitlePanel.setLayout(gbl_durTitlePanel);
		
		fieldDurAct = new JSpinner();
		fieldDurAct.setEnabled(false);
		GridBagConstraints gbc_fieldDurAct = new GridBagConstraints();
		gbc_fieldDurAct.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldDurAct.gridx = 0;
		gbc_fieldDurAct.gridy = 0;
		durTitlePanel.add(fieldDurAct, gbc_fieldDurAct);
		
		costTitlePanel = new JPanel();
		costTitlePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Costo por turista:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_costTitlePanel = new GridBagConstraints();
		gbc_costTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_costTitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_costTitlePanel.gridx = 0;
		gbc_costTitlePanel.gridy = 3;
		activityDetailsTitle.add(costTitlePanel, gbc_costTitlePanel);
		GridBagLayout gbl_costTitlePanel = new GridBagLayout();
		gbl_costTitlePanel.columnWidths = new int[]{293, 0};
		gbl_costTitlePanel.rowHeights = new int[]{30, 0};
		gbl_costTitlePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_costTitlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		costTitlePanel.setLayout(gbl_costTitlePanel);
		
		fieldActCost = new JTextField();
		fieldActCost.setEnabled(false);
		fieldActCost.setEditable(false);
		GridBagConstraints gbc_fieldActCost = new GridBagConstraints();
		gbc_fieldActCost.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldActCost.gridx = 0;
		gbc_fieldActCost.gridy = 0;
		costTitlePanel.add(fieldActCost, gbc_fieldActCost);
		fieldActCost.setColumns(10);
		
		citytitlePanel = new JPanel();
		citytitlePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Ciudad:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_citytitlePanel = new GridBagConstraints();
		gbc_citytitlePanel.fill = GridBagConstraints.BOTH;
		gbc_citytitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_citytitlePanel.gridx = 0;
		gbc_citytitlePanel.gridy = 4;
		activityDetailsTitle.add(citytitlePanel, gbc_citytitlePanel);
		GridBagLayout gbl_citytitlePanel = new GridBagLayout();
		gbl_citytitlePanel.columnWidths = new int[]{293, 0};
		gbl_citytitlePanel.rowHeights = new int[]{30, 0};
		gbl_citytitlePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_citytitlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		citytitlePanel.setLayout(gbl_citytitlePanel);
		
		fieldCity = new JTextField();
		fieldCity.setEnabled(false);
		fieldCity.setEditable(false);
		GridBagConstraints gbc_fieldCity = new GridBagConstraints();
		gbc_fieldCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldCity.gridx = 0;
		gbc_fieldCity.gridy = 0;
		citytitlePanel.add(fieldCity, gbc_fieldCity);
		fieldCity.setColumns(10);
		
		dischDateTitlePanel = new JPanel();
		dischDateTitlePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha de alta:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_dischDateTitlePanel = new GridBagConstraints();
		gbc_dischDateTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_dischDateTitlePanel.gridx = 0;
		gbc_dischDateTitlePanel.gridy = 5;
		activityDetailsTitle.add(dischDateTitlePanel, gbc_dischDateTitlePanel);
		GridBagLayout gbl_dischDateTitlePanel = new GridBagLayout();
		gbl_dischDateTitlePanel.columnWidths = new int[]{293, 0};
		gbl_dischDateTitlePanel.rowHeights = new int[]{0, 0};
		gbl_dischDateTitlePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_dischDateTitlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		dischDateTitlePanel.setLayout(gbl_dischDateTitlePanel);
		
		JSpinner fieldActDischargeDate = new JSpinner();
		fieldActDischargeDate.setEnabled(false);
		GridBagConstraints gbc_fieldActDischargeDate = new GridBagConstraints();
		gbc_fieldActDischargeDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldActDischargeDate.gridx = 0;
		gbc_fieldActDischargeDate.gridy = 0;
		dischDateTitlePanel.add(fieldActDischargeDate, gbc_fieldActDischargeDate);
		
		selectTouristOuting = new JPanel();
		selectTouristOuting.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione salida tur\u00EDstica:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)), new EmptyBorder(10, 10, 10, 10)));
		selectTouristOuting.setBounds(425, 12, 451, 70);
		activityAndTouristOutingDetailsContainer.add(selectTouristOuting);
		GridBagLayout gbl_selectTouristOuting = new GridBagLayout();
		gbl_selectTouristOuting.columnWidths = new int[]{352, 0};
		gbl_selectTouristOuting.rowHeights = new int[]{24, 0};
		gbl_selectTouristOuting.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_selectTouristOuting.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		selectTouristOuting.setLayout(gbl_selectTouristOuting);
		
		JPanel selectContainer = new JPanel();
		GridBagConstraints gbc_selectContainer = new GridBagConstraints();
		gbc_selectContainer.anchor = GridBagConstraints.NORTHWEST;
		gbc_selectContainer.gridx = 0;
		gbc_selectContainer.gridy = 0;
		selectTouristOuting.add(selectContainer, gbc_selectContainer);
		GridBagLayout gbl_selectContainer = new GridBagLayout();
		gbl_selectContainer.columnWidths = new int[]{419, 0};
		gbl_selectContainer.rowHeights = new int[]{24, 0};
		gbl_selectContainer.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_selectContainer.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		selectContainer.setLayout(gbl_selectContainer);
		
		JComboBox outingActComboBox = new JComboBox();
		GridBagConstraints gbc_outingActComboBox = new GridBagConstraints();
		gbc_outingActComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_outingActComboBox.gridx = 0;
		gbc_outingActComboBox.gridy = 0;
		selectContainer.add(outingActComboBox, gbc_outingActComboBox);
		
		touristOutingDetailsTitle = new JPanel();
		touristOutingDetailsTitle.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Informaci\u00F3n de la salida seleccionada:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)), new EmptyBorder(15, 15, 15, 15)));
		touristOutingDetailsTitle.setBounds(425, 94, 451, 285);
		activityAndTouristOutingDetailsContainer.add(touristOutingDetailsTitle);
		GridBagLayout gbl_touristOutingDetailsTitle = new GridBagLayout();
		gbl_touristOutingDetailsTitle.columnWidths = new int[] {403, 30};
		gbl_touristOutingDetailsTitle.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_touristOutingDetailsTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_touristOutingDetailsTitle.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		touristOutingDetailsTitle.setLayout(gbl_touristOutingDetailsTitle);
		
		outingNameTitle = new JPanel();
		outingNameTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Nombre:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_outingNameTitle = new GridBagConstraints();
		gbc_outingNameTitle.fill = GridBagConstraints.BOTH;
		gbc_outingNameTitle.insets = new Insets(0, 0, 5, 0);
		gbc_outingNameTitle.gridx = 0;
		gbc_outingNameTitle.gridy = 0;
		touristOutingDetailsTitle.add(outingNameTitle, gbc_outingNameTitle);
		GridBagLayout gbl_outingNameTitle = new GridBagLayout();
		gbl_outingNameTitle.columnWidths = new int[]{403, 0};
		gbl_outingNameTitle.rowHeights = new int[]{0, 0};
		gbl_outingNameTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_outingNameTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		outingNameTitle.setLayout(gbl_outingNameTitle);
		
		fieldOutingName = new JTextField();
		fieldOutingName.setEnabled(false);
		fieldOutingName.setEditable(false);
		GridBagConstraints gbc_fieldOutingName = new GridBagConstraints();
		gbc_fieldOutingName.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingName.gridx = 0;
		gbc_fieldOutingName.gridy = 0;
		outingNameTitle.add(fieldOutingName, gbc_fieldOutingName);
		fieldOutingName.setColumns(10);
		
		outingCantMaxTitle = new JPanel();
		outingCantMaxTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Cantidad m\u00E1xima:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_outingCantMaxTitle = new GridBagConstraints();
		gbc_outingCantMaxTitle.fill = GridBagConstraints.BOTH;
		gbc_outingCantMaxTitle.insets = new Insets(0, 0, 5, 0);
		gbc_outingCantMaxTitle.gridx = 0;
		gbc_outingCantMaxTitle.gridy = 1;
		touristOutingDetailsTitle.add(outingCantMaxTitle, gbc_outingCantMaxTitle);
		GridBagLayout gbl_outingCantMaxTitle = new GridBagLayout();
		gbl_outingCantMaxTitle.columnWidths = new int[]{403, 0};
		gbl_outingCantMaxTitle.rowHeights = new int[]{0, 0};
		gbl_outingCantMaxTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_outingCantMaxTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		outingCantMaxTitle.setLayout(gbl_outingCantMaxTitle);
		
		fieldOutingCantMax = new JTextField();
		fieldOutingCantMax.setEnabled(false);
		fieldOutingCantMax.setEditable(false);
		GridBagConstraints gbc_fieldOutingCantMax = new GridBagConstraints();
		gbc_fieldOutingCantMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingCantMax.gridx = 0;
		gbc_fieldOutingCantMax.gridy = 0;
		outingCantMaxTitle.add(fieldOutingCantMax, gbc_fieldOutingCantMax);
		fieldOutingCantMax.setColumns(10);
		
		outingPlaceTitle = new JPanel();
		outingPlaceTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Lugar de salida:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_outingPlaceTitle = new GridBagConstraints();
		gbc_outingPlaceTitle.fill = GridBagConstraints.BOTH;
		gbc_outingPlaceTitle.insets = new Insets(0, 0, 5, 0);
		gbc_outingPlaceTitle.gridx = 0;
		gbc_outingPlaceTitle.gridy = 2;
		touristOutingDetailsTitle.add(outingPlaceTitle, gbc_outingPlaceTitle);
		GridBagLayout gbl_outingPlaceTitle = new GridBagLayout();
		gbl_outingPlaceTitle.columnWidths = new int[]{403, 0};
		gbl_outingPlaceTitle.rowHeights = new int[]{0, 0};
		gbl_outingPlaceTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_outingPlaceTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		outingPlaceTitle.setLayout(gbl_outingPlaceTitle);
		
		fieldOutingPlace = new JTextField();
		fieldOutingPlace.setEnabled(false);
		fieldOutingPlace.setEditable(false);
		GridBagConstraints gbc_fieldOutingPlace = new GridBagConstraints();
		gbc_fieldOutingPlace.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutingPlace.gridx = 0;
		gbc_fieldOutingPlace.gridy = 0;
		outingPlaceTitle.add(fieldOutingPlace, gbc_fieldOutingPlace);
		fieldOutingPlace.setColumns(10);
		
		outingDateTitle = new JPanel();
		outingDateTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha y hora:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_outingDateTitle = new GridBagConstraints();
		gbc_outingDateTitle.fill = GridBagConstraints.BOTH;
		gbc_outingDateTitle.insets = new Insets(0, 0, 5, 0);
		gbc_outingDateTitle.gridx = 0;
		gbc_outingDateTitle.gridy = 3;
		touristOutingDetailsTitle.add(outingDateTitle, gbc_outingDateTitle);
		GridBagLayout gbl_outingDateTitle = new GridBagLayout();
		gbl_outingDateTitle.columnWidths = new int[]{403, 0};
		gbl_outingDateTitle.rowHeights = new int[]{0, 0};
		gbl_outingDateTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_outingDateTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		outingDateTitle.setLayout(gbl_outingDateTitle);
		
		fieldDepartureDate = new JSpinner();
		fieldDepartureDate.setEnabled(false);
		GridBagConstraints gbc_fieldDepartureDate = new GridBagConstraints();
		gbc_fieldDepartureDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldDepartureDate.gridx = 0;
		gbc_fieldDepartureDate.gridy = 0;
		outingDateTitle.add(fieldDepartureDate, gbc_fieldDepartureDate);
		
		outingDischDateTitle = new JPanel();
		outingDischDateTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha de alta:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_outingDischDateTitle = new GridBagConstraints();
		gbc_outingDischDateTitle.fill = GridBagConstraints.BOTH;
		gbc_outingDischDateTitle.gridx = 0;
		gbc_outingDischDateTitle.gridy = 4;
		touristOutingDetailsTitle.add(outingDischDateTitle, gbc_outingDischDateTitle);
		GridBagLayout gbl_outingDischDateTitle = new GridBagLayout();
		gbl_outingDischDateTitle.columnWidths = new int[]{403, 0};
		gbl_outingDischDateTitle.rowHeights = new int[]{0, 0};
		gbl_outingDischDateTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_outingDischDateTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		outingDischDateTitle.setLayout(gbl_outingDischDateTitle);
		
		fieldOutDischargeDate = new JSpinner();
		fieldOutDischargeDate.setEnabled(false);
		GridBagConstraints gbc_fieldOutDischargeDate = new GridBagConstraints();
		gbc_fieldOutDischargeDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldOutDischargeDate.gridx = 0;
		gbc_fieldOutDischargeDate.gridy = 0;
		outingDischDateTitle.add(fieldOutDischargeDate, gbc_fieldOutDischargeDate);
		
		touristContainer = new JPanel();
		cl_centerContainer.add(touristContainer, "name_4444534999027");
		touristContainer.setLayout(new BorderLayout(0, 0));
		
		selectRegisteredOutingContainer = new JPanel();
		touristContainer.add(selectRegisteredOutingContainer, BorderLayout.CENTER);
		GridBagLayout gbl_selectRegisteredOutingContainer = new GridBagLayout();
		gbl_selectRegisteredOutingContainer.columnWidths = new int[] {211, 419, 195, 0};
		gbl_selectRegisteredOutingContainer.rowHeights = new int[] {20, 81, 0, 0};
		gbl_selectRegisteredOutingContainer.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_selectRegisteredOutingContainer.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		selectRegisteredOutingContainer.setLayout(gbl_selectRegisteredOutingContainer);
		
		selectRegisteredOutingTitle = new JPanel();
		selectRegisteredOutingTitle.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione salida tur\u00EDstica:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)), new EmptyBorder(10, 10, 10, 10)));
		GridBagConstraints gbc_selectRegisteredOutingTitle = new GridBagConstraints();
		gbc_selectRegisteredOutingTitle.insets = new Insets(0, 0, 5, 5);
		gbc_selectRegisteredOutingTitle.fill = GridBagConstraints.BOTH;
		gbc_selectRegisteredOutingTitle.gridx = 1;
		gbc_selectRegisteredOutingTitle.gridy = 1;
		selectRegisteredOutingContainer.add(selectRegisteredOutingTitle, gbc_selectRegisteredOutingTitle);
		GridBagLayout gbl_selectRegisteredOutingTitle = new GridBagLayout();
		gbl_selectRegisteredOutingTitle.columnWidths = new int[] {419, 0};
		gbl_selectRegisteredOutingTitle.rowHeights = new int[]{24, 0};
		gbl_selectRegisteredOutingTitle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_selectRegisteredOutingTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		selectRegisteredOutingTitle.setLayout(gbl_selectRegisteredOutingTitle);
		
		selectRegisteredOutingComboBox = new JComboBox();
		GridBagConstraints gbc_selectRegisteredOutingComboBox = new GridBagConstraints();
		gbc_selectRegisteredOutingComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectRegisteredOutingComboBox.anchor = GridBagConstraints.NORTH;
		gbc_selectRegisteredOutingComboBox.gridx = 0;
		gbc_selectRegisteredOutingComboBox.gridy = 0;
		selectRegisteredOutingTitle.add(selectRegisteredOutingComboBox, gbc_selectRegisteredOutingComboBox);
		
		registeredOutingDetailsTitle = new JPanel();
		registeredOutingDetailsTitle.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Informaci\u00F3n de salida tur\u00EDstica:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)), new EmptyBorder(10, 10, 10, 10)));
		GridBagConstraints gbc_registeredOutingDetailsTitle = new GridBagConstraints();
		gbc_registeredOutingDetailsTitle.insets = new Insets(0, 0, 0, 5);
		gbc_registeredOutingDetailsTitle.fill = GridBagConstraints.BOTH;
		gbc_registeredOutingDetailsTitle.gridx = 1;
		gbc_registeredOutingDetailsTitle.gridy = 2;
		selectRegisteredOutingContainer.add(registeredOutingDetailsTitle, gbc_registeredOutingDetailsTitle);
		GridBagLayout gbl_registeredOutingDetailsTitle = new GridBagLayout();
		gbl_registeredOutingDetailsTitle.columnWidths = new int[] {350};
		gbl_registeredOutingDetailsTitle.rowHeights = new int[] {30, 30, 30, 30, 30};
		gbl_registeredOutingDetailsTitle.columnWeights = new double[]{1.0};
		gbl_registeredOutingDetailsTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		registeredOutingDetailsTitle.setLayout(gbl_registeredOutingDetailsTitle);
		
		registOutNameTitle = new JPanel();
		registOutNameTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Nombre:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_registOutNameTitle = new GridBagConstraints();
		gbc_registOutNameTitle.fill = GridBagConstraints.BOTH;
		gbc_registOutNameTitle.insets = new Insets(0, 0, 5, 0);
		gbc_registOutNameTitle.gridx = 0;
		gbc_registOutNameTitle.gridy = 0;
		registeredOutingDetailsTitle.add(registOutNameTitle, gbc_registOutNameTitle);
		GridBagLayout gbl_registOutNameTitle = new GridBagLayout();
		gbl_registOutNameTitle.columnWidths = new int[]{350, 0};
		gbl_registOutNameTitle.rowHeights = new int[]{30, 0};
		gbl_registOutNameTitle.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_registOutNameTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		registOutNameTitle.setLayout(gbl_registOutNameTitle);
		
		fieldRegisteredOutName = new JTextField();
		GridBagConstraints gbc_fieldRegisteredOutName = new GridBagConstraints();
		gbc_fieldRegisteredOutName.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldRegisteredOutName.gridx = 0;
		gbc_fieldRegisteredOutName.gridy = 0;
		registOutNameTitle.add(fieldRegisteredOutName, gbc_fieldRegisteredOutName);
		fieldRegisteredOutName.setColumns(10);
		
		registOutCantMaxTitle = new JPanel();
		registOutCantMaxTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Cantidad m\u00E1xima:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_registOutCantMaxTitle = new GridBagConstraints();
		gbc_registOutCantMaxTitle.fill = GridBagConstraints.BOTH;
		gbc_registOutCantMaxTitle.insets = new Insets(0, 0, 5, 0);
		gbc_registOutCantMaxTitle.gridx = 0;
		gbc_registOutCantMaxTitle.gridy = 1;
		registeredOutingDetailsTitle.add(registOutCantMaxTitle, gbc_registOutCantMaxTitle);
		GridBagLayout gbl_registOutCantMaxTitle = new GridBagLayout();
		gbl_registOutCantMaxTitle.columnWidths = new int[]{350, 0};
		gbl_registOutCantMaxTitle.rowHeights = new int[]{30, 0};
		gbl_registOutCantMaxTitle.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_registOutCantMaxTitle.rowWeights = new double[]{4.9E-324, Double.MIN_VALUE};
		registOutCantMaxTitle.setLayout(gbl_registOutCantMaxTitle);
		
		fieldRegisteredOutCantMax = new JTextField();
		GridBagConstraints gbc_fieldRegisteredOutCantMax = new GridBagConstraints();
		gbc_fieldRegisteredOutCantMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldRegisteredOutCantMax.gridx = 0;
		gbc_fieldRegisteredOutCantMax.gridy = 0;
		registOutCantMaxTitle.add(fieldRegisteredOutCantMax, gbc_fieldRegisteredOutCantMax);
		fieldRegisteredOutCantMax.setColumns(10);
		
		registOutPlaceTitle = new JPanel();
		registOutPlaceTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Lugar de salida:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_registOutPlaceTitle = new GridBagConstraints();
		gbc_registOutPlaceTitle.fill = GridBagConstraints.BOTH;
		gbc_registOutPlaceTitle.insets = new Insets(0, 0, 5, 0);
		gbc_registOutPlaceTitle.gridx = 0;
		gbc_registOutPlaceTitle.gridy = 2;
		registeredOutingDetailsTitle.add(registOutPlaceTitle, gbc_registOutPlaceTitle);
		GridBagLayout gbl_registOutPlaceTitle = new GridBagLayout();
		gbl_registOutPlaceTitle.columnWidths = new int[]{350, 0};
		gbl_registOutPlaceTitle.rowHeights = new int[]{30, 0};
		gbl_registOutPlaceTitle.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_registOutPlaceTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		registOutPlaceTitle.setLayout(gbl_registOutPlaceTitle);
		
		fieldRegisteredOutPlace = new JTextField();
		GridBagConstraints gbc_fieldRegisteredOutPlace = new GridBagConstraints();
		gbc_fieldRegisteredOutPlace.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldRegisteredOutPlace.gridx = 0;
		gbc_fieldRegisteredOutPlace.gridy = 0;
		registOutPlaceTitle.add(fieldRegisteredOutPlace, gbc_fieldRegisteredOutPlace);
		fieldRegisteredOutPlace.setColumns(10);
		
		registOutDateTitle = new JPanel();
		registOutDateTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha y hora:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_registOutDateTitle = new GridBagConstraints();
		gbc_registOutDateTitle.fill = GridBagConstraints.BOTH;
		gbc_registOutDateTitle.insets = new Insets(0, 0, 5, 0);
		gbc_registOutDateTitle.gridx = 0;
		gbc_registOutDateTitle.gridy = 3;
		registeredOutingDetailsTitle.add(registOutDateTitle, gbc_registOutDateTitle);
		GridBagLayout gbl_registOutDateTitle = new GridBagLayout();
		gbl_registOutDateTitle.columnWidths = new int[]{350, 0};
		gbl_registOutDateTitle.rowHeights = new int[]{30, 0};
		gbl_registOutDateTitle.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_registOutDateTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		registOutDateTitle.setLayout(gbl_registOutDateTitle);
		
		fieldRegisteredOutDepatureDate = new JSpinner();
		GridBagConstraints gbc_fieldRegisteredOutDepatureDate = new GridBagConstraints();
		gbc_fieldRegisteredOutDepatureDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldRegisteredOutDepatureDate.gridx = 0;
		gbc_fieldRegisteredOutDepatureDate.gridy = 0;
		registOutDateTitle.add(fieldRegisteredOutDepatureDate, gbc_fieldRegisteredOutDepatureDate);
		
		registOutDischDateTitle = new JPanel();
		registOutDischDateTitle.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Fecha de alta:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_registOutDischDateTitle = new GridBagConstraints();
		gbc_registOutDischDateTitle.fill = GridBagConstraints.BOTH;
		gbc_registOutDischDateTitle.gridx = 0;
		gbc_registOutDischDateTitle.gridy = 4;
		registeredOutingDetailsTitle.add(registOutDischDateTitle, gbc_registOutDischDateTitle);
		GridBagLayout gbl_registOutDischDateTitle = new GridBagLayout();
		gbl_registOutDischDateTitle.columnWidths = new int[]{350, 0};
		gbl_registOutDischDateTitle.rowHeights = new int[]{30, 0};
		gbl_registOutDischDateTitle.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_registOutDischDateTitle.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		registOutDischDateTitle.setLayout(gbl_registOutDischDateTitle);
		
		fieldRegisteredOutDischargeDate = new JSpinner();
		GridBagConstraints gbc_fieldRegisteredOutDischargeDate = new GridBagConstraints();
		gbc_fieldRegisteredOutDischargeDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldRegisteredOutDischargeDate.gridx = 0;
		gbc_fieldRegisteredOutDischargeDate.gridy = 0;
		registOutDischDateTitle.add(fieldRegisteredOutDischargeDate, gbc_fieldRegisteredOutDischargeDate);
	}
}
