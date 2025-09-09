package presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import logic.dto.DtSupplier;
import logic.dto.DtTourist;
import logic.dto.DtUser;
import logic.dto.UserType;
import logic.interfaces.IUserController;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

@SuppressWarnings("serial")
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
	private JComboBox<String> nicknameComboBox;

	// RESOURCES
	private static final String TITLE = "Modificación de usuario";
	private static final String NAME = "Nombre:";
	private static final String LASTNAME = "Apellido:";
	private static final String EMAIL = "Correo electrónico:";
	private static final String BIRTH_DATE = "Fecha de nacimiento:";

	private static final String MSG_NO_REGISTERED_USERS = "No hay usuarios registrados";

	// Patterns
	private static final String BIRTH_DATE_FORMAT = "dd/MM/yyyy";

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
		setBounds(0, 0, 595, 410);
		getContentPane().setLayout(null);

		// Form and actions container
		JPanel formContainer = new JPanel();
		formContainer.setBounds(0, 0, 558, 367);
		formContainer.setBorder(null);
		getContentPane().add(formContainer);
		formContainer.setLayout(null);

		formContainer.add(createTopContainer());
		formContainer.add(createActionButtonsPanel());

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				cleanForm();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				loadUsersCombo();
			}
		});
	}

	private JPanel createTopContainer() {

		topContainer = new JPanel();
		topContainer.setBounds(0, 0, 569, 299);
		topContainer.setBorder(null);

		topContainer.setLayout(null);
		JPanel nicknameComboContainer = new JPanel();
		nicknameComboContainer.setBounds(35, 12, 522, 99);
		topContainer.add(nicknameComboContainer);
		GridBagLayout gbl_nicknameComboContainer = new GridBagLayout();
		gbl_nicknameComboContainer.columnWidths = new int[] { 536, 0 };
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
		gbl_nicknameCombo.columnWidths = new int[] { 510, 0 };
		gbl_nicknameCombo.rowHeights = new int[] { 44, 0 };
		gbl_nicknameCombo.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_nicknameCombo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		nicknameCombo.setLayout(gbl_nicknameCombo);

		nicknameComboBox = new JComboBox<String>();
		GridBagConstraints gbc_nicknameComboBox = new GridBagConstraints();
		gbc_nicknameComboBox.fill = GridBagConstraints.BOTH;
		gbc_nicknameComboBox.gridx = 0;
		gbc_nicknameComboBox.gridy = 0;
		nicknameCombo.add(nicknameComboBox, gbc_nicknameComboBox);
		nicknameComboBox.setBorder(new EmptyBorder(10, 10, 10, 10));

		nicknameComboBox.addActionListener(e -> onUserSelected(e));

		JPanel basicDataPanel = new JPanel();
		basicDataPanel.setBounds(38, 123, 522, 159);
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
		fieldName.setBackground(new Color(255, 255, 255));
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
		fieldEmail.setBackground(new Color(255, 255, 255));
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
		actionButtonsPanel.setBounds(73, 311, 431, 47);
		actionButtonsPanel.setLayout(null);

		ActionListener cancelAction = e -> {
			cleanForm();
			setVisible(false);
		};
		ActionListener confirmAction = e -> {
			if (checkForm()) {
				cmdModifyUserActionPerformed(e);
				cleanForm();
				setVisible(false);
			}
		};

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(247, 12, 117, 25);
		actionButtonsPanel.add(btnGuardar);

		btnGuardar.addActionListener(confirmAction);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(65, 12, 117, 25);
		actionButtonsPanel.add(btnCancelar);

		btnCancelar.addActionListener(cancelAction);

		return actionButtonsPanel;
	}

	private void cmdModifyUserActionPerformed(ActionEvent arg0) {

		String selectedName = (String) nicknameComboBox.getSelectedItem();
		DtUser dt = iUserController.consultUserData(selectedName);
		String newName = fieldName.getText();
		String newLastName = fieldLastname.getText();
		LocalDate newBirthDate = jSpinnerValueToLocalDate(field_birthDate);

		DtUser modified = dt.getUserType() == UserType.SUPPLIER
				? new DtSupplier(selectedName, newName, newLastName, null, newBirthDate, null, null)
				: new DtTourist(selectedName, newName, newLastName, null, newBirthDate, null);

		iUserController.modifyUserDate(modified);

		JOptionPane.showMessageDialog(this, "Usuario modificado correctamente.", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private LocalDate jSpinnerValueToLocalDate(JSpinner jspinner) {
		LocalDate date = ((Date) this.field_birthDate.getValue()).toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		return date;
	}

	private void onUserSelected(ActionEvent e) {
		String selectedName = (String) nicknameComboBox.getSelectedItem();
		if (selectedName != null && !selectedName.equals(MSG_NO_REGISTERED_USERS)) {
			DtUser user = iUserController.consultUserData(selectedName);

			fieldName.setText(user.getName());
			fieldLastname.setText(user.getLastName());
			fieldEmail.setText(user.getEmail());
			// Formateo de la fecha de nacimiento obtenida del DT
			Date date = Date.from(user.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			field_birthDate.setValue(date);
		}
	}

	private void loadUsersCombo() {
		DefaultComboBoxModel<String> model;
		String[] users = iUserController.listUsers();
		if (users == null) {
			users = new String[] { MSG_NO_REGISTERED_USERS };
		}
		model = new DefaultComboBoxModel<String>(users);
		nicknameComboBox.setModel(model);
		nicknameComboBox.setSelectedIndex(-1);
	}

	private void cleanForm() {
		fieldName.setText("");
		fieldLastname.setText("");
		fieldEmail.setText("");
	}

	private boolean checkForm() {

		if (nicknameComboBox.getSelectedIndex() == -1
				|| ((String) nicknameComboBox.getSelectedItem()).equals(MSG_NO_REGISTERED_USERS)) {
			JOptionPane.showMessageDialog(this,
					"Debe seleccionar un usuario para modificar, verifique e intente nuevamente.", TITLE,
					JOptionPane.ERROR_MESSAGE);
			this.nicknameComboBox.requestFocusInWindow();
			return false;
		}

		// Email format
		if (isEmptyFormBasicData()) {
			JOptionPane.showMessageDialog(this, "No puede haber campos vacíos.", TITLE, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// At least 18 years old
		if (!isLegalAdult()) {
			JOptionPane.showMessageDialog(this, "La edad mínima requerida es de 18 años.", "Registrar Usuario",
					JOptionPane.ERROR_MESSAGE);
			this.field_birthDate.requestFocusInWindow();
			return false;
		}

		return true;
	}

	// BASIC DATA FORM VALIDATION
	private boolean isEmptyFormBasicData() {

		JTextField[] fields = { this.fieldName, this.fieldLastname };

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
}
