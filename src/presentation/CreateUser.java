package presentation;

import javax.swing.JInternalFrame;

import logic.interfaces.IUserController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;

import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Cursor;
import java.util.Calendar;
import javax.swing.JToggleButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JRadioButton;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class CreateUser extends JInternalFrame {

	private IUserController iUserController;

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
	private static final String TITLE = "Registro de usuario";
	private static final String SUBTITLE = "Complete la información del usuario";
	private static final String NICKNAME = "Nickname:";
	private static final String NICKNAME_TOOLTIP = "Nickname que identificará al usuario dentro del sistema.";
	private static final String NAME = "Nombre:";
	private static final String LASTNAME = "Apellido:";
	private static final String EMAIL = "Correo electrónico:";
	private static final String BIRTH_DATE = "Fecha de nacimiento:";
	private static final String BIRTH_DATE_FORMAT = "dd/MM/yyyy";
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

	public CreateUser(IUserController iuc) {

		super(TITLE, true, true, true, true);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		iUserController = iuc;

		// Content panel
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Title
		labelSubTitle = new JLabel(SUBTITLE);
		labelSubTitle.setBorder(new EmptyBorder(10, 12, 5, 12));
		labelSubTitle.setFont(labelSubTitle.getFont().deriveFont(Font.BOLD, labelSubTitle.getFont().getSize() + 2f));
		getContentPane().add(labelSubTitle, BorderLayout.PAGE_START);

		// BorderLayout basicData
		JPanel formContainer = new JPanel();
		formContainer.setBorder(null);
		getContentPane().add(formContainer, BorderLayout.CENTER);
		formContainer.setLayout(new BorderLayout(0, 0));

		// GridBorderLayout BasicData
		JPanel basicDataContainer = new JPanel();
		basicDataContainer.setBorder(new EmptyBorder(5, 0, 5, 0));
		formContainer.add(basicDataContainer, BorderLayout.NORTH);
		GridBagLayout gbl_basicData = new GridBagLayout();
		gbl_basicData.columnWidths = new int[] { 30, 103, 276, 0 };
		gbl_basicData.rowHeights = new int[] { 0, 30, 30, 34, 0 };
		gbl_basicData.columnWeights = new double[] { 0.0, 1.0, 1.0 };
		gbl_basicData.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

		basicDataContainer.setLayout(gbl_basicData);

		labelNickname = new JLabel(NICKNAME);
		GridBagConstraints gbc_label_Nickname = new GridBagConstraints();
		gbc_label_Nickname.anchor = GridBagConstraints.WEST;
		gbc_label_Nickname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Nickname.gridx = 1;
		gbc_label_Nickname.gridy = 0;
		basicDataContainer.add(labelNickname, gbc_label_Nickname);

		fieldNickname = new JTextField();
		labelNickname.setLabelFor(fieldNickname);
		fieldNickname.setToolTipText(NICKNAME_TOOLTIP);
		GridBagConstraints gbc_field_Nickname = new GridBagConstraints();
		gbc_field_Nickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Nickname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Nickname.gridx = 2;
		gbc_field_Nickname.gridy = 0;
		basicDataContainer.add(fieldNickname, gbc_field_Nickname);
		fieldNickname.setColumns(10);

		labelName = new JLabel(NAME);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		basicDataContainer.add(labelName, gbc_lblNombre);

		fieldName = new JTextField();
		labelName.setLabelFor(fieldName);
		GridBagConstraints gbc_field_Name = new GridBagConstraints();
		gbc_field_Name.insets = new Insets(0, 0, 5, 5);
		gbc_field_Name.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Name.gridx = 2;
		gbc_field_Name.gridy = 1;
		basicDataContainer.add(fieldName, gbc_field_Name);
		fieldName.setColumns(10);

		labelLastname = new JLabel(LASTNAME);
		GridBagConstraints gbc_label_Lastname = new GridBagConstraints();
		gbc_label_Lastname.anchor = GridBagConstraints.WEST;
		gbc_label_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_label_Lastname.gridx = 1;
		gbc_label_Lastname.gridy = 2;
		basicDataContainer.add(labelLastname, gbc_label_Lastname);

		fieldLastname = new JTextField();
		labelLastname.setLabelFor(fieldLastname);
		GridBagConstraints gbc_field_Lastname = new GridBagConstraints();
		gbc_field_Lastname.insets = new Insets(0, 0, 5, 5);
		gbc_field_Lastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Lastname.gridx = 2;
		gbc_field_Lastname.gridy = 2;
		basicDataContainer.add(fieldLastname, gbc_field_Lastname);
		fieldLastname.setColumns(10);

		labelEmail = new JLabel(EMAIL);
		GridBagConstraints gbc_label_Email = new GridBagConstraints();
		gbc_label_Email.insets = new Insets(0, 0, 5, 5);
		gbc_label_Email.anchor = GridBagConstraints.WEST;
		gbc_label_Email.gridx = 1;
		gbc_label_Email.gridy = 3;
		basicDataContainer.add(labelEmail, gbc_label_Email);

		fieldEmail = new JTextField();
		labelEmail.setLabelFor(fieldEmail);
		GridBagConstraints gbc_field_Email = new GridBagConstraints();
		gbc_field_Email.insets = new Insets(0, 0, 5, 5);
		gbc_field_Email.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Email.gridx = 2;
		gbc_field_Email.gridy = 3;
		basicDataContainer.add(fieldEmail, gbc_field_Email);
		fieldEmail.setColumns(10);

		JLabel label_birthDate = new JLabel(BIRTH_DATE);
		GridBagConstraints gbc_label_birthDate = new GridBagConstraints();
		gbc_label_birthDate.anchor = GridBagConstraints.WEST;
		gbc_label_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_label_birthDate.gridx = 1;
		gbc_label_birthDate.gridy = 4;
		basicDataContainer.add(label_birthDate, gbc_label_birthDate);

		JSpinner field_birthDate = new JSpinner();
		label_birthDate.setLabelFor(field_birthDate);
		field_birthDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		field_birthDate.setEditor(new JSpinner.DateEditor(field_birthDate, BIRTH_DATE_FORMAT));
		GridBagConstraints gbc_field_birthDate = new GridBagConstraints();
		gbc_field_birthDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_birthDate.insets = new Insets(0, 0, 0, 5);
		gbc_field_birthDate.gridx = 2;
		gbc_field_birthDate.gridy = 4;
		basicDataContainer.add(field_birthDate, gbc_field_birthDate);

		JPanel gbl_UserType = new JPanel();
		gbl_UserType.setBorder(new EmptyBorder(5, 5, 5, 5));
		formContainer.add(gbl_UserType, BorderLayout.CENTER);
		gbl_UserType.setLayout(new BorderLayout(0, 0));

		JPanel toggle_Panel = new JPanel();
		toggle_Panel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), USER_TYPE, TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(51, 51, 51))));
		gbl_UserType.add(toggle_Panel, BorderLayout.NORTH);
		toggle_Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));

		rdbtnTourist = new JRadioButton(TOURIST);
		userTypeButtons.add(rdbtnTourist);
		rdbtnTourist.setSelected(true);
		toggle_Panel.add(rdbtnTourist);

		rdbtnSupplier = new JRadioButton(SUPPLIER);
		userTypeButtons.add(rdbtnSupplier);
		toggle_Panel.add(rdbtnSupplier);

		cl_AdditionalData = new JPanel();
		cl_AdditionalData.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), ADDITIONAL_DATA, TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(51, 51, 51))));
		gbl_UserType.add(cl_AdditionalData, BorderLayout.SOUTH);
		cl_AdditionalData.setLayout(new CardLayout(0, 0));

		JPanel gbl_TouristForm = new JPanel();
		cl_AdditionalData.add(gbl_TouristForm, TOURIST_FORM);
		gbl_TouristForm.setVisible(true);
		gbl_TouristForm.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagLayout gbl_gbl_TouristForm = new GridBagLayout();
		gbl_gbl_TouristForm.columnWidths = new int[] { 42, 202, 339, -18, 0, 0 };
		gbl_gbl_TouristForm.rowHeights = new int[] { 67, 0 };
		gbl_gbl_TouristForm.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_gbl_TouristForm.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_TouristForm.setLayout(gbl_gbl_TouristForm);

		labelNationalty = new JLabel(TOURIST_NATIONALITY);
		GridBagConstraints gbc_label_Nationalty = new GridBagConstraints();
		gbc_label_Nationalty.anchor = GridBagConstraints.WEST;
		gbc_label_Nationalty.insets = new Insets(0, 0, 0, 5);
		gbc_label_Nationalty.gridx = 1;
		gbc_label_Nationalty.gridy = 0;
		gbl_TouristForm.add(labelNationalty, gbc_label_Nationalty);
		labelNationalty.setBorder(new EmptyBorder(0, 5, 0, 0));
		labelNationalty.setLabelFor(fieldNationality);

		fieldNationality = new JTextField();
		GridBagConstraints gbc_field_Nationality = new GridBagConstraints();
		gbc_field_Nationality.insets = new Insets(0, 0, 0, 5);
		gbc_field_Nationality.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Nationality.gridx = 2;
		gbc_field_Nationality.gridy = 0;
		gbl_TouristForm.add(fieldNationality, gbc_field_Nationality);
		fieldNationality.setColumns(10);

		rdbtnTourist.addActionListener(e -> {
			((CardLayout) cl_AdditionalData.getLayout()).show(cl_AdditionalData, TOURIST_FORM);
		});

		JPanel gbl_SupplierForm = new JPanel();
		cl_AdditionalData.add(gbl_SupplierForm, SUPPLIER_FORM);
		gbl_SupplierForm.setVisible(false);
		gbl_SupplierForm.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagLayout gbl_gbl_SupplierForm = new GridBagLayout();
		gbl_gbl_SupplierForm.columnWidths = new int[] { 42, 202, 339, -18, 0, 0 };
		gbl_gbl_SupplierForm.rowHeights = new int[] { 67, 0, 0 };
		gbl_gbl_SupplierForm.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_gbl_SupplierForm.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_SupplierForm.setLayout(gbl_gbl_SupplierForm);

		labelDescription = new JLabel(SUPPLIER_DESC);
		GridBagConstraints gbc_label_Description = new GridBagConstraints();
		gbc_label_Description.anchor = GridBagConstraints.WEST;
		gbc_label_Description.insets = new Insets(0, 0, 5, 5);
		gbc_label_Description.gridx = 1;
		gbc_label_Description.gridy = 0;
		gbl_SupplierForm.add(labelDescription, gbc_label_Description);

		textAreaDescription = new JTextArea();
		labelDescription.setLabelFor(textAreaDescription);
		GridBagConstraints gbc_textArea_Description = new GridBagConstraints();
		gbc_textArea_Description.insets = new Insets(0, 0, 5, 5);
		gbc_textArea_Description.fill = GridBagConstraints.BOTH;
		gbc_textArea_Description.gridx = 2;
		gbc_textArea_Description.gridy = 0;
		gbl_SupplierForm.add(textAreaDescription, gbc_textArea_Description);

		labelSitioweb = new JLabel(SUPPLIER_WEBSITE);
		GridBagConstraints gbc_label_Sitioweb = new GridBagConstraints();
		gbc_label_Sitioweb.anchor = GridBagConstraints.WEST;
		gbc_label_Sitioweb.insets = new Insets(0, 0, 0, 5);
		gbc_label_Sitioweb.gridx = 1;
		gbc_label_Sitioweb.gridy = 1;
		gbl_SupplierForm.add(labelSitioweb, gbc_label_Sitioweb);

		fieldSitioweb = new JTextField();
		labelSitioweb.setLabelFor(fieldSitioweb);
		GridBagConstraints gbc_field_Sitioweb = new GridBagConstraints();
		gbc_field_Sitioweb.insets = new Insets(0, 0, 0, 5);
		gbc_field_Sitioweb.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Sitioweb.gridx = 2;
		gbc_field_Sitioweb.gridy = 1;
		gbl_SupplierForm.add(fieldSitioweb, gbc_field_Sitioweb);
		fieldSitioweb.setColumns(10);

		rdbtnSupplier.addActionListener(e -> {
			((CardLayout) cl_AdditionalData.getLayout()).show(cl_AdditionalData, SUPPLIER_FORM);
		});

		JPanel fl_Buttons = new JPanel();
		formContainer.add(fl_Buttons, BorderLayout.SOUTH);
		fl_Buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));

		btnCancel = new JButton(BUTTON_CANCEL);
		fl_Buttons.add(btnCancel);

		btnConfirm = new JButton(BUTTON_CONFIRM);
		fl_Buttons.add(btnConfirm);
	}
	
    // GridBagLayout column positions for labels and fields in basic forms
    private static final int GRID_LABEL_COL = 1;
    private static final int GRID_FIELD_COL = 2;

    private JLabel addLabelAndField(JPanel panel, String labelText, JTextField field, int row, String tooltip) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(field);
        if (tooltip != null && !tooltip.isEmpty()) {
            field.setToolTipText(tooltip);
        }
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(0, 0, 5, 5);
        gbcLabel.gridx = GRID_LABEL_COL;
        gbcLabel.gridy = row;
        panel.add(label, gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(0, 0, 5, 5);
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.gridx = GRID_FIELD_COL;
        gbcField.gridy = row;
        panel.add(field, gbcField);
        field.setColumns(10);
        return label;
    }

    private void addLabelAndComponent(JPanel panel, String labelText, java.awt.Component component, int row, int fill) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(component);
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(0, 0, 5, 5);
        gbcLabel.gridx = GRID_LABEL_COL;
        gbcLabel.gridy = row;
        panel.add(label, gbcLabel);

        GridBagConstraints gbcComponent = new GridBagConstraints();
        gbcComponent.insets = new Insets(0, 0, 5, 5);
        gbcComponent.gridx = GRID_FIELD_COL;
        gbcComponent.gridy = row;
        gbcComponent.fill = fill;
        panel.add(component, gbcComponent);
    }
}
