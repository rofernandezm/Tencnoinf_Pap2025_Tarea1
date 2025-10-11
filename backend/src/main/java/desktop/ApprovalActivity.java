package desktop;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.interfaces.ITouristActivityController;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class ApprovalActivity extends JInternalFrame {

	private JTextField txtActName;
	private JTextField txtActDescription;
	private JTextField txtActDuration;
	private JTextField txtActCost;
	private JTextField txtActCity;
	private JTextField txtActRegDate;
	private JButton rejectButton;
	private JButton approveButton;
	private JComboBox<String> cmbSelActivity;
	private DateTimeFormatter formatter_YYYYMMDD;
	private DateTimeFormatter formatter_YYYYMMDD_HHMM;
	private DtActivityWithOutings activityWithOutingsData;
	ITouristActivityController itac;

	public ApprovalActivity(ITouristActivityController itac) {
		super("Aprobar actividad", true, true, true, true);
		this.itac = itac;
		formatter_YYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter_YYYYMMDD_HHMM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		setBounds(new Rectangle(35, 35, 400, 420));
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getFormContentJPanel(), BorderLayout.CENTER);
	}

	private JPanel getFormContentJPanel() {
		JPanel formContent = new JPanel();

		GridBagLayout gbl_formContent = new GridBagLayout();
		gbl_formContent.columnWidths = new int[] { 746, 0 };
		gbl_formContent.rowHeights = new int[] { 115, 0, 0, 0, 0 };
		gbl_formContent.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_formContent.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		formContent.setLayout(gbl_formContent);

		// ACTIVITY SELECTOR
		formContent.add(getActivitySelectorJPanel(), getActivitySelectorGbc());

		// ACTIVITY FORM
		formContent.add(getActivityFormJPanel(), getActivityFormGbc());

		// FOOTER
		formContent.add(getFooterJPanel(), getFooterGbc());

		return formContent;
	}

	private JPanel getFooterJPanel() {

		JPanel footer = new JPanel();
		FlowLayout flowLayout = (FlowLayout) footer.getLayout();
		flowLayout.setHgap(50);

		rejectButton = new JButton("Rechazar");
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		rejectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_rejectButton = new GridBagConstraints();
		gbc_rejectButton.insets = new Insets(0, 0, 0, 5);
		gbc_rejectButton.gridx = 1;
		gbc_rejectButton.gridy = 0;
		footer.add(rejectButton, gbc_rejectButton);

		approveButton = new JButton("Aprobar");
		approveButton.setPreferredSize(new Dimension(100, 25));
		approveButton.setMinimumSize(new Dimension(100, 25));
		approveButton.setMaximumSize(new Dimension(100, 25));
		GridBagConstraints gbc_approveButton = new GridBagConstraints();
		gbc_approveButton.insets = new Insets(0, 0, 0, 5);
		gbc_approveButton.gridx = 3;
		gbc_approveButton.gridy = 0;
		footer.add(approveButton, gbc_approveButton);

		return footer;
	}

	private GridBagConstraints getFooterGbc() {
		GridBagConstraints gbc_footer = new GridBagConstraints();
		gbc_footer.insets = new Insets(0, 0, 5, 0);
		gbc_footer.fill = GridBagConstraints.VERTICAL;
		gbc_footer.gridx = 0;
		gbc_footer.gridy = 2;
		return gbc_footer;
	}

	private JPanel getActivitySelectorJPanel() {

		JPanel activitySelector = new JPanel();
		GridBagLayout gbl_activitySelector = new GridBagLayout();
		gbl_activitySelector.columnWidths = new int[] { 90, 551, 90, 30 };
		gbl_activitySelector.rowHeights = new int[] { 0, 0, 30 };
		gbl_activitySelector.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_activitySelector.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		activitySelector.setLayout(gbl_activitySelector);

		cmbSelActivity = new JComboBox<String>();
		cmbSelActivity.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione actividad:",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)),
				new EmptyBorder(10, 10, 10, 10)));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		activitySelector.add(cmbSelActivity, gbc_comboBox);

		return activitySelector;
	}

	private GridBagConstraints getActivitySelectorGbc() {

		GridBagConstraints gbc_activitySelector = new GridBagConstraints();
		gbc_activitySelector.insets = new Insets(0, 0, 5, 0);
		gbc_activitySelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_activitySelector.gridx = 0;
		gbc_activitySelector.gridy = 0;

		return gbc_activitySelector;
	}

	private JPanel getActivityFormJPanel() {

		JPanel activityForm = new JPanel();

		GridBagLayout gbl_activityForm = new GridBagLayout();
		gbl_activityForm.columnWidths = new int[] { 546, 0 };
		gbl_activityForm.rowHeights = new int[] { 197, 0 };
		gbl_activityForm.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_activityForm.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		activityForm.setLayout(gbl_activityForm);

		JPanel basicDataActivity = new JPanel();
		GridBagConstraints gbc_basicDataActivity = new GridBagConstraints();
		gbc_basicDataActivity.fill = GridBagConstraints.HORIZONTAL;
		gbc_basicDataActivity.gridx = 0;
		gbc_basicDataActivity.gridy = 0;
		activityForm.add(basicDataActivity, gbc_basicDataActivity);
		basicDataActivity.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Informacion de la actividad",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)),
				new EmptyBorder(10, 10, 10, 10)));
		GridBagLayout gbl_basicDataActivity = new GridBagLayout();
		gbl_basicDataActivity.columnWidths = new int[] { 44, 133, 138, 40, 0 };
		gbl_basicDataActivity.rowHeights = new int[] { 14, 0, 0, 0, 0, 0, 0 };
		gbl_basicDataActivity.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_basicDataActivity.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		basicDataActivity.setLayout(gbl_basicDataActivity);

		JLabel lblActName = new JLabel("Nombre");
		GridBagConstraints gbc_lblActName = new GridBagConstraints();
		gbc_lblActName.insets = new Insets(0, 0, 5, 5);
		gbc_lblActName.anchor = GridBagConstraints.NORTHWEST;
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

		JLabel lblActDuration = new JLabel("Duración (horas):");
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
		gbc_lblActRegDate.insets = new Insets(0, 0, 0, 5);
		gbc_lblActRegDate.gridx = 1;
		gbc_lblActRegDate.gridy = 5;
		basicDataActivity.add(lblActRegDate, gbc_lblActRegDate);

		txtActRegDate = new JTextField();
		txtActRegDate.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 5;
		basicDataActivity.add(txtActRegDate, gbc_textField);
		txtActRegDate.setColumns(10);

		return activityForm;
	}

	private GridBagConstraints getActivityFormGbc() {

		GridBagConstraints gbc_activityForm = new GridBagConstraints();
		gbc_activityForm.insets = new Insets(0, 0, 5, 0);
		gbc_activityForm.gridx = 0;
		gbc_activityForm.gridy = 1;

		return gbc_activityForm;
	}
}
