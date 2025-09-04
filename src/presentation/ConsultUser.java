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
	private JPanel centerContainer;
	private JPanel panel;
	private JList list;

	public ConsultUser(IUserController iuc) {

		super(TITLE, true, true, true, true);
		getContentPane().setMinimumSize(new Dimension(780, 540));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		iUserController = iuc;

		// Content panel
		getContentPane().setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, 928, 540);

		// Form and actions container
		JPanel formContainer = new JPanel();
		formContainer.setBorder(null);
		getContentPane().add(formContainer, BorderLayout.CENTER);
		formContainer.setLayout(new BorderLayout(0, 0));

		topContainer = new JPanel();
		topContainer.setBorder(null);
		GridBagLayout gbl_topContainer = new GridBagLayout();
		gbl_topContainer.columnWidths = new int[] { 0, 0, 0, 30 };
		gbl_topContainer.rowHeights = new int[] { 0, 30 };
		gbl_topContainer.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_topContainer.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		topContainer.setLayout(gbl_topContainer);

		formContainer.add(topContainer, BorderLayout.NORTH);
		JPanel nicknameComboContainer = new JPanel();
		GridBagConstraints gbc_nicknameComboContainer = new GridBagConstraints();
		gbc_nicknameComboContainer.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameComboContainer.insets = new Insets(0, 0, 0, 5);
		gbc_nicknameComboContainer.gridx = 1;
		gbc_nicknameComboContainer.gridy = 0;
		topContainer.add(nicknameComboContainer, gbc_nicknameComboContainer);
//		nicknameComboContainer.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5),
//				new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione el nickname de usuario:",
//						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51))));
		GridBagLayout gbl_nicknameComboContainer = new GridBagLayout();
		gbl_nicknameComboContainer.columnWidths = new int[] { 345, 0 };
		gbl_nicknameComboContainer.rowHeights = new int[] { 22, 44, 44, 44 };
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

		JComboBox nickname = new JComboBox();
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.fill = GridBagConstraints.BOTH;
		gbc_nickname.gridx = 0;
		gbc_nickname.gridy = 0;
		nicknameCombo.add(nickname, gbc_nickname);
		nickname.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel basicDataPanel = new JPanel();
		GridBagConstraints gbc_basicDataPanel = new GridBagConstraints();
		gbc_basicDataPanel.gridx = 2;
		gbc_basicDataPanel.gridy = 0;
		topContainer.add(basicDataPanel, gbc_basicDataPanel);
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
		field_birthDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		field_birthDate.setEditor(new JSpinner.DateEditor(field_birthDate, BIRTH_DATE_FORMAT));
		GridBagConstraints gbc_field_birthDate = new GridBagConstraints();
		gbc_field_birthDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_field_birthDate.gridx = GRID_FIELD_POS;
		gbc_field_birthDate.gridy = 5;
		basicDataPanel.add(field_birthDate, gbc_field_birthDate);
		
		centerContainer = new JPanel();
		formContainer.add(centerContainer, BorderLayout.SOUTH);
		centerContainer.setLayout(new CardLayout(0, 0));
		
		panel = new JPanel();
		centerContainer.add(panel, "name_4847026481389");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 30, 30, 30, 30, 30, 30};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 0, 0, 30, 30, 30, 30, 30, 30};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		list = new JList();
		list.setAutoscrolls(false);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"1. Actividad", "\t1.1 Salida", "\t1.2 Salida", "\t1.3 Salida", "2. Actividad", "\t2.1 Salida", "3. Actividad"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 4;
		panel.add(list, gbc_list);

//		// Form: user type
//		formContainer.add(initUserTypeFormPanel(), BorderLayout.CENTER);
//
//		// Form: Action buttons
//		formContainer.add(initActionButtons(), BorderLayout.SOUTH);

//		JPanel nicknameSelector = new JPanel();
//		getContentPane().add(nicknameSelector, BorderLayout.NORTH);
//		nicknameSelector.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione el nickname de usuario:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51))));
//		GridBagLayout gbl_nicknameSelector = new GridBagLayout();
//		gbl_nicknameSelector.columnWidths = new int[]{345, 0};
//		gbl_nicknameSelector.rowHeights = new int[]{44, 0};
//		gbl_nicknameSelector.columnWeights = new double[]{0.0, Double.MIN_VALUE};
//		gbl_nicknameSelector.rowWeights = new double[]{0.0, Double.MIN_VALUE};
//		nicknameSelector.setLayout(gbl_nicknameSelector);
//		
//		JComboBox nickname = new JComboBox();
//		nickname.setBorder(new EmptyBorder(10, 10, 10, 10));
//		GridBagConstraints gbc_nickname = new GridBagConstraints();
//		gbc_nickname.fill = GridBagConstraints.BOTH;
//		gbc_nickname.gridx = 0;
//		gbc_nickname.gridy = 0;
//		nicknameSelector.add(nickname, gbc_nickname);
//		userData.add(initBasicDataFormPanel(), BorderLayout.NORTH);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
