package presentation;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

import logic.dto.DtInscriptionTouristOuting;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class ConsultTouristInscription extends JInternalFrame {
	
	private ITouristOutingAndInscriptionController iOutingAndInscriptionController;
	
	private JComboBox comboBox;

	private static final long serialVersionUID = 1L;
	/**
	 * Create the frame.
	 */
	public ConsultTouristInscription(ITouristOutingAndInscriptionController iOutingAndInscriptionController) {
		
		this.iOutingAndInscriptionController = iOutingAndInscriptionController;
		
		
		
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(0, 38, 434, 27);
		getContentPane().add(comboBox);
		
		JLabel lblInscripcionesRegistradas = new JLabel("Inscripciones Registradas");
		lblInscripcionesRegistradas.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInscripcionesRegistradas.setHorizontalAlignment(SwingConstants.CENTER);
		lblInscripcionesRegistradas.setBounds(0, 0, 434, 38);
		getContentPane().add(lblInscripcionesRegistradas);
		
		JButton btnCerrar = new JButton("Salir");
		btnCerrar.setBackground(UIManager.getColor("ColorChooser.background"));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCerrar.setBounds(160, 73, 124, 36);
		getContentPane().add(btnCerrar);

	}
	
	public void chargeInscriptions() {
        DefaultComboBoxModel<DtInscriptionTouristOuting> model;
        model = new DefaultComboBoxModel<DtInscriptionTouristOuting>(this.iOutingAndInscriptionController.listOutingInscription(this.iOutingAndInscriptionController.getOutingName()));
        comboBox.setModel(model);
        }
}
