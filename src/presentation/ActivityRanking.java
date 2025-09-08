package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import logic.dto.DtRanking;
import logic.interfaces.ITouristActivityController;

import java.awt.*;

public class ActivityRanking extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableRanking;
	private ITouristActivityController iActivityController;

	public ActivityRanking(ITouristActivityController iTAC) {
		setTitle("Ranking Actividades");
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		iActivityController = iTAC;

		setSize(400, 300);
		getContentPane().setLayout(new BorderLayout());
		
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(new Object[] { "Actividad", "Cantidad de Salidas" }, 0) {
		};

		tableRanking = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tableRanking);

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		loadRanking();
	}

	private void loadRanking() {
		DefaultTableModel model = (DefaultTableModel) tableRanking.getModel();
		model.setRowCount(0);

		DtRanking[] ranking = iActivityController.getActivityRanking();

		if (ranking != null && ranking.length > 0) {
			for (DtRanking r : ranking) {
				if (r != null) {
					model.addRow(new Object[] { r.getActivityName(), r.getNumberOutings() });
				}
			}
		}
	}

	public void init() {
		clearForm();
		loadRanking();	
	}

	private void clearForm() {
		DefaultTableModel model = (DefaultTableModel) tableRanking.getModel();
		model.setRowCount(0);
	}

}
