package presentation;

import javax.swing.JInternalFrame;

import logic.interfaces.IUserController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class CreateUser extends JInternalFrame  {

	private IUserController iUserController;
	//seguir
	
    private JLabel label_Nickname;
	private JTextField field_Nickname;
	private JTextField textFieldMaxNumTourists;
	private JTextField textFieldDeparturePoint;
	private JTextField textFieldDepartureDate;
	private JLabel lblEnterTouristOutingName;
	private JLabel lblEnterMaxNumTourists;
	private JLabel lblEnterDeparturePoint;
	private JLabel lblEnterDepartureDate;
	private JButton btnConfirm;
	private JButton btnCancel;
	
	public CreateUser(IUserController iuc) {
		
		iUserController = iuc;
		
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Registro de usuario");
        setBounds(10, 40, 360, 200);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 135, 105, 77, 5 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);
        
        label_Nickname = new JLabel("Nickname");
        label_Nickname.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_label_Nickname = new GridBagConstraints();
        gbc_label_Nickname.fill = GridBagConstraints.BOTH;
        gbc_label_Nickname.insets = new Insets(0, 0, 5, 5);
        gbc_label_Nickname.gridx = 0;
        gbc_label_Nickname.gridy = 0;
        getContentPane().add(label_Nickname, gbc_label_Nickname);
        
        field_Nickname = new JTextField();
        GridBagConstraints gbc_field_Nickname = new GridBagConstraints();
        gbc_field_Nickname.gridwidth = 2;
        gbc_field_Nickname.fill = GridBagConstraints.BOTH;
        gbc_field_Nickname.insets = new Insets(0, 0, 5, 0);
        gbc_field_Nickname.gridx = 1;
        gbc_field_Nickname.gridy = 0;
        getContentPane().add(field_Nickname, gbc_field_Nickname);
        field_Nickname.setColumns(10);
	}
}
