package presentation;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
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
	
	private static final long serialVersionUID = 1L;
	private JComboBox cbInscripciones;
	private JLabel lblInscripcionesRegistradas;
	private JButton btnCerrar;

	
	/**
	 * Create the frame.
	 */
	public ConsultTouristInscription(ITouristOutingAndInscriptionController iOutingAndInscriptionController) {
		
		this.iOutingAndInscriptionController = iOutingAndInscriptionController;
		
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(null);
		
		cbInscripciones = new JComboBox();
		cbInscripciones.setBounds(0, 38, 434, 27);
		getContentPane().add(cbInscripciones);
		
	    lblInscripcionesRegistradas = new JLabel("Inscripciones Registradas");
		lblInscripcionesRegistradas.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInscripcionesRegistradas.setHorizontalAlignment(SwingConstants.CENTER);
		lblInscripcionesRegistradas.setBounds(0, 0, 434, 38);
		getContentPane().add(lblInscripcionesRegistradas);
		
		btnCerrar = new JButton("Salir");
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
        cbInscripciones.setModel(model);
        }
}
