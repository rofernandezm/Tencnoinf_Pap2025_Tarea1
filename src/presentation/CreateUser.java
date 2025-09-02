package presentation;

import javax.swing.JInternalFrame;

import logic.dto.DtSupplier;
import logic.dto.DtTourist;
import logic.dto.DtUser;
import logic.interfaces.IUserController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JFrame;
import java.util.Date;
import java.util.regex.Pattern;

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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;

import exceptions.RepeatedUserEmailException;
import exceptions.RepeatedUserNicknameException;

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

	public CreateUser(IUserController iuc) {

		super(TITLE, true, true, true, true);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		iUserController = iuc;

		// Content panel
		getContentPane().setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 450, 600);

		// Title
		labelSubTitle = new JLabel(SUBTITLE);
		labelSubTitle.setBorder(new EmptyBorder(10, 12, 5, 12));
		labelSubTitle.setFont(labelSubTitle.getFont().deriveFont(Font.BOLD, labelSubTitle.getFont().getSize() + 2f));
		getContentPane().add(labelSubTitle, BorderLayout.PAGE_START);

		// Form and actions container
		JPanel formContainer = new JPanel();
		formContainer.setBorder(null);
		getContentPane().add(formContainer, BorderLayout.CENTER);
		formContainer.setLayout(new BorderLayout(0, 0));

		// Form: basic data
		formContainer.add(initBasicDataFormPanel(), BorderLayout.NORTH);

		// Form: user type
		formContainer.add(initUserTypeFormPanel(), BorderLayout.CENTER);

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

	private JPanel initUserTypeFormPanel() {
		JPanel gbl_UserType = new JPanel();
		gbl_UserType.setBorder(new EmptyBorder(5, 5, 5, 5));
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

		// Add the specific forms to the card layout
		cl_AdditionalData.add(initAdditionalDataTouristForm(), TOURIST_FORM);
		cl_AdditionalData.add(initAdditionalDataSupplierForm(), SUPPLIER_FORM);

		// Action listeners for toggling forms
		ActionListener switchListener = e -> {
			String card = rdbtnTourist.isSelected() ? TOURIST_FORM : SUPPLIER_FORM;
			((CardLayout) cl_AdditionalData.getLayout()).show(cl_AdditionalData, card);
		};
		rdbtnTourist.addActionListener(switchListener);
		rdbtnSupplier.addActionListener(switchListener);

		return gbl_UserType;
	}

	private JPanel initAdditionalDataTouristForm() {
		JPanel gbl_TouristForm = new JPanel();
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
		gbc_label_Nationalty.gridx = GRID_LABEL_POS;
		gbc_label_Nationalty.gridy = 0;
		gbl_TouristForm.add(labelNationalty, gbc_label_Nationalty);
		labelNationalty.setBorder(new EmptyBorder(0, 5, 0, 0));
		labelNationalty.setLabelFor(fieldNationality);

		fieldNationality = new JTextField();
		GridBagConstraints gbc_field_Nationality = new GridBagConstraints();
		gbc_field_Nationality.insets = new Insets(0, 0, 0, 5);
		gbc_field_Nationality.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Nationality.gridx = GRID_FIELD_POS;
		gbc_field_Nationality.gridy = 0;
		gbl_TouristForm.add(fieldNationality, gbc_field_Nationality);
		fieldNationality.setColumns(10);

		return gbl_TouristForm;
	}

	private JPanel initAdditionalDataSupplierForm() {
		JPanel gbl_SupplierForm = new JPanel();
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
		gbc_label_Description.gridx = GRID_LABEL_POS;
		gbc_label_Description.gridy = 0;
		gbl_SupplierForm.add(labelDescription, gbc_label_Description);

		textAreaDescription = new JTextArea();
		textAreaDescription.setBorder(new LineBorder(new Color(51, 51, 51), 1, true));
		labelDescription.setLabelFor(textAreaDescription);
		GridBagConstraints gbc_textArea_Description = new GridBagConstraints();
		gbc_textArea_Description.insets = new Insets(0, 0, 5, 5);
		gbc_textArea_Description.fill = GridBagConstraints.BOTH;
		gbc_textArea_Description.gridx = GRID_FIELD_POS;
		gbc_textArea_Description.gridy = 0;
		gbl_SupplierForm.add(textAreaDescription, gbc_textArea_Description);

		labelSitioweb = new JLabel(SUPPLIER_WEBSITE);
		GridBagConstraints gbc_label_Sitioweb = new GridBagConstraints();
		gbc_label_Sitioweb.anchor = GridBagConstraints.WEST;
		gbc_label_Sitioweb.insets = new Insets(0, 0, 0, 5);
		gbc_label_Sitioweb.gridx = GRID_LABEL_POS;
		gbc_label_Sitioweb.gridy = 1;
		gbl_SupplierForm.add(labelSitioweb, gbc_label_Sitioweb);

		fieldSitioweb = new JTextField();
		labelSitioweb.setLabelFor(fieldSitioweb);
		GridBagConstraints gbc_field_Sitioweb = new GridBagConstraints();
		gbc_field_Sitioweb.insets = new Insets(0, 0, 0, 5);
		gbc_field_Sitioweb.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_Sitioweb.gridx = GRID_FIELD_POS;
		gbc_field_Sitioweb.gridy = 1;
		gbl_SupplierForm.add(fieldSitioweb, gbc_field_Sitioweb);
		fieldSitioweb.setColumns(10);

		return gbl_SupplierForm;
	}

	private JPanel initActionButtons() {
		// Action button
		JPanel fl_Buttons = new JPanel();
		fl_Buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));

		ActionListener cancelAction = e -> {
			clearForm();
			setVisible(false);
		};
		ActionListener confirmAction = e -> {
			cmdUserRegisterActionPerformed(e);
		};

		btnCancel = new JButton(BUTTON_CANCEL);
		btnCancel.addActionListener(cancelAction);
		fl_Buttons.add(btnCancel);

		btnConfirm = new JButton(BUTTON_CONFIRM);
		btnConfirm.addActionListener(confirmAction);
		fl_Buttons.add(btnConfirm);

		return fl_Buttons;
	}

	protected void cmdUserRegisterActionPerformed(ActionEvent arg0) {

		if (checkForm()) {

			try {
				DtUser dtUser = rdbtnTourist.isSelected() ? getDtTouristByForm() : getDtSupplierByForm();

				iUserController.dataEntry(dtUser);
				iUserController.confirmRegistration();
				// Muestro éxito de la operación
				JOptionPane.showMessageDialog(this, "El usuario ha sido registrado con éxito.", "Registrar Usuario",
						JOptionPane.INFORMATION_MESSAGE);

				// Limpio el internal frame antes de cerrar la ventana
				clearForm();
				setVisible(false);

			} catch (RepeatedUserNicknameException e) {
				// Muestro error de registro
				JOptionPane.showMessageDialog(this, e.getMessage(), "Registrar Usuario", JOptionPane.ERROR_MESSAGE);

			} catch (RepeatedUserEmailException e) {
				// Muestro error de registro
				JOptionPane.showMessageDialog(this, e.getMessage(), "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private DtUser getDtTouristByForm() {

		String nickname = this.fieldNickname.getText();
		String name = this.fieldName.getText();
		String lastName = this.fieldLastname.getText();
		String email = this.fieldEmail.getText();
		LocalDate birthDate = jSpinnerValueToLocalDate(this.field_birthDate);
		String nationality = this.fieldNationality.getText();

		return new DtTourist(nickname, name, lastName, email, birthDate, nationality);

	}

	private DtUser getDtSupplierByForm() {

		String nickname = this.fieldNickname.getText();
		String name = this.fieldName.getText();
		String lastName = this.fieldLastname.getText();
		String email = this.fieldEmail.getText();
		LocalDate birthDate = jSpinnerValueToLocalDate(this.field_birthDate);
		String supplierDesc = this.textAreaDescription.getText();
		String webSite = this.fieldSitioweb.getText();

		return new DtSupplier(nickname, name, lastName, email, birthDate, supplierDesc, webSite);

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

	private boolean checkForm() {

		// Empty basic fields
		if (isEmptyFormBasicData()) {
			JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Registrar Usuario",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Email format
		if (!isEmail(this.fieldEmail.getText())) {
			JOptionPane.showMessageDialog(this,
					"El formato de correo electrónico es incorrecto. Por favor, verifique y vuelva a intentar.",
					"Registrar Usuario", JOptionPane.ERROR_MESSAGE);
			this.fieldEmail.requestFocusInWindow();
			return false;
		}

		// At least 18 years old
		if (!isLegalAdult()) {
			JOptionPane.showMessageDialog(this, "La edad mínima requerida es de 18 años.", "Registrar Usuario",
					JOptionPane.ERROR_MESSAGE);
			this.field_birthDate.requestFocusInWindow();
			return false;
		}

		if (this.rdbtnTourist.isSelected() && this.fieldNationality.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El campo nacionalidad no puede estar vacío", "Registrar Usuario",
					JOptionPane.ERROR_MESSAGE);
			this.fieldNationality.requestFocusInWindow();
			return false;
		} else if (this.rdbtnSupplier.isSelected() && this.textAreaDescription.getText().isEmpty()) {

			JOptionPane.showMessageDialog(this, "El campo descripción no puede estar vacío", "Registrar Usuario",
					JOptionPane.ERROR_MESSAGE);
			this.textAreaDescription.requestFocusInWindow();
			return false;
		}

		if (this.rdbtnSupplier.isSelected() && !this.fieldSitioweb.getText().isEmpty()) {

			if (!isValidURL()) {
				JOptionPane.showMessageDialog(this,
						"El sitio web ingresado es inválido, por favor verifique y vuelva a intentar.",
						"Registrar Usuario", JOptionPane.ERROR_MESSAGE);
				this.textAreaDescription.requestFocusInWindow();
				return false;
			}
		}

		return true;
	}

	// BASIC DATA FORM VALIDATION
	private boolean isEmptyFormBasicData() {

		JTextField[] fields = { this.fieldNickname, this.fieldName, this.fieldLastname, this.fieldEmail };

		for (JTextField field : fields) {
			if (field.getText().isEmpty()) {
				field.requestFocusInWindow();
				return true;
			}
		}

		return false;
	}

	private boolean isLegalAdult() {

		LocalDate date = jSpinnerValueToLocalDate(this.field_birthDate);
		LocalDate legalAdult = LocalDate.now().minusYears(18);
		return date != null && !date.isAfter(legalAdult);
	}

	private LocalDate jSpinnerValueToLocalDate(JSpinner jspinner) {
		LocalDate date = ((Date) this.field_birthDate.getValue()).toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		return date;
	}

	private boolean isEmail(String email) {
		return email != null && EMAIL_REGEX.matcher(email.trim()).matches();
	}

	private boolean isValidURL() {
		try {
			URI.create(this.fieldSitioweb.getText());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
