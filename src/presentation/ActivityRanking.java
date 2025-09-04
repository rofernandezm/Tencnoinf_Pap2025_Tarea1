package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import logic.controller.TouristActivityController;
import java.awt.*;

public class ActivityRanking extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableRanking;
    private TouristActivityController activityController;

    public ActivityRanking() {
        super("Ranking de Actividades Turísticas", true, true, true, true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        activityController = new TouristActivityController();

   
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Actividad", "Cantidad de Salidas"}, 0) {};
        
        tableRanking = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableRanking);

        add(scrollPane, BorderLayout.CENTER);

        // Cargar datos
        loadRanking();
    }

    private void loadRanking() {}

}
