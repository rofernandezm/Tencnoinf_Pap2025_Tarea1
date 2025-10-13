package desktop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristActivityController;

import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.ResourceBundle;

public class Main {

	private JFrame frmTourismUy;
	private ITouristActivityController ITAC;
	private ApprovalActivity approvalActivityFrame;

	public static void main(String[] args) {

		System.out.print("Access to main");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmTourismUy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Main() {

		initialize();

		FactoryUyTourism factoryUyTourism = FactoryUyTourism.getInstance();

		ITAC = factoryUyTourism.getITouristActivityController();

		approvalActivityFrame = new ApprovalActivity(ITAC);
		approvalActivityFrame.setVisible(false);
		frmTourismUy.getContentPane().add(approvalActivityFrame);
	}

	private void initialize() {

		ResourceBundle texts = ResourceBundle.getBundle("texts");

		frmTourismUy = new JFrame();
		frmTourismUy.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
				frmTourismUy.pack();
			}
		});
		frmTourismUy.setTitle(texts.getString("main.title"));
		frmTourismUy.setBounds(100, 100, 800, 600);
		frmTourismUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmTourismUy.setJMenuBar(menuBar);

		// Menu Activities
		JMenu menuActivities = new JMenu(texts.getString("main.menu.activities"));
		menuBar.add(menuActivities);

		JMenuItem mnApprovalActivity = new JMenuItem(texts.getString("main.menu.activities.approval"));
		menuActivities.add(mnApprovalActivity);
		mnApprovalActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				approvalActivityFrame.setVisible(true);
			}
		});

		JMenu menuSystem = new JMenu(texts.getString("main.menu.system"));
		menuBar.add(menuSystem);

		JMenuItem menuExit = new JMenuItem(texts.getString("main.menu.system.exit"));
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				frmTourismUy.setVisible(false);
				frmTourismUy.dispose();
			}
		});

		menuSystem.add(menuExit);
	}

	private void hideAllComponents() {
		approvalActivityFrame.setVisible(false);
	}
}
