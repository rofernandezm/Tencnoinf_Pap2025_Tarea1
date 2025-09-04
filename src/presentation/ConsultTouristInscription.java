package presentation;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

import logic.controller.TouristActivityController;
import logic.controller.TouristOutingAndInscriptionController;
import logic.entity.TouristActivity;
import logic.entity.TouristOuting;

public class ConsultTouristInscription extends JInternalFrame {
	
	
	
	private static final long serialVersionUID = 1L;
	
	    private JComboBox<TouristActivity> cbActividad;
	    private JComboBox<TouristOuting> cbSalida;
	    private JTable tableInscripciones;
	    private DefaultTableModel tableModel;

	    private TouristActivityController activityController;
	    private TouristOutingAndInscriptionController outingController;

	    public ConsultTouristInscription() {
	        super("Consultar Inscripciones", true, true, true, true);
	        setSize(600, 400);
	        setLayout(new BorderLayout());

	        activityController = new TouristActivityController();
	        outingController = new TouristOutingAndInscriptionController();

	        JPanel panelSelect = new JPanel();
	        panelSelect.add(new JLabel("Actividad:"));
	        cbActividad = new JComboBox<>();
	        panelSelect.add(cbActividad);
	        panelSelect.add(new JLabel("Salida:"));
	        cbSalida = new JComboBox<>();
	        panelSelect.add(cbSalida);
	        add(panelSelect, BorderLayout.NORTH);

	
	        tableModel = new DefaultTableModel();
	
	        tableModel.addColumn("Cliente");
	        tableModel.addColumn("Cédula");
	        tableModel.addColumn("Fecha Inscripción");
	        tableInscripciones = new JTable(tableModel);
	        tableInscripciones.setFillsViewportHeight(true);
	        JScrollPane scrollPane = new JScrollPane(tableInscripciones);
	        add(scrollPane, BorderLayout.CENTER);

	        loadActivities();

	        cbActividad.addActionListener(e -> {
	            TouristActivity act = (TouristActivity) cbActividad.getSelectedItem();
	            if (act != null) {
	                loadOutings(act);
	            }
	        });
	        cbSalida.addActionListener(e -> {
	            TouristOuting outing = (TouristOuting) cbSalida.getSelectedItem();
	            if (outing != null) {
	                loadInscriptions(outing);
	            }
	        });
	    }

	    private void loadActivities() {
	       
	    }

	    private void loadOutings(TouristActivity act) {
	        
	    }

	    private void loadInscriptions(TouristOuting outing) {
	     
	    }
	}
