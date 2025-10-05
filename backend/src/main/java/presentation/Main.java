package presentation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;
import turismouyapp.core.interfaces.IUserController;

import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.ResourceBundle;

public class Main {

	private JFrame frmTourismUy;
	private IUserController IUC;
	private ITouristOutingAndInscriptionController IOIC;
	private ITouristActivityController ITAC;
	private CreateUser creUsrInternalFrame;
	private CreateActivity creActInternalFrame;
	private ConsultActivity consultActInternalFrame;
	private TouristOutingRegistration touristOutingRegistrationInternalFrame;
	private ConsultTouristOuting consultTouristOutingInternalFrame;
	private InscriptionToTouristOuting inscriptionToTouristOutingInternalFrame;
	private ModifyActivity modifyActivityInternalFrame;
	private ConsultTouristInscription consultInscriptionInternalFrame;
	private ActivityRanking activityRankingInternalFrame;
	private ConsultUser consultUserInternalFrame;
	private ModifyUser modifyUserInternalFrame;

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

		IUC = factoryUyTourism.getIUserController();
		ITAC = factoryUyTourism.getITouristActivityController();
		IOIC = factoryUyTourism.getITouristOutingAndInscriptionController();

		creUsrInternalFrame = new CreateUser(IUC);
		creUsrInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(creUsrInternalFrame);

		consultUserInternalFrame = new ConsultUser(IUC, ITAC, IOIC);
		consultUserInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultUserInternalFrame);

		modifyUserInternalFrame = new ModifyUser(IUC);
		modifyUserInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(modifyUserInternalFrame);

		touristOutingRegistrationInternalFrame = new TouristOutingRegistration(IOIC, ITAC);
		touristOutingRegistrationInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(touristOutingRegistrationInternalFrame);

		consultTouristOutingInternalFrame = new ConsultTouristOuting(IOIC, ITAC);
		consultTouristOutingInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultTouristOutingInternalFrame);

		creActInternalFrame = new CreateActivity(ITAC, IUC);
		creActInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(creActInternalFrame);

		consultActInternalFrame = new ConsultActivity(ITAC);
		consultActInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultActInternalFrame);

		inscriptionToTouristOutingInternalFrame = new InscriptionToTouristOuting(IOIC, ITAC, IUC);
		inscriptionToTouristOutingInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(inscriptionToTouristOutingInternalFrame);

		modifyActivityInternalFrame = new ModifyActivity(ITAC);
		modifyActivityInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(modifyActivityInternalFrame);

		consultInscriptionInternalFrame = new ConsultTouristInscription(IOIC, ITAC);
		consultInscriptionInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultInscriptionInternalFrame);

		activityRankingInternalFrame = new ActivityRanking(ITAC);
		activityRankingInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(activityRankingInternalFrame);

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

		// Menu Users
		JMenu menuUsers = new JMenu(texts.getString("main.menu.users"));
		menuBar.add(menuUsers);

		JMenuItem createUser = new JMenuItem(texts.getString("main.menu.users.create"));
		menuUsers.add(createUser);
		createUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				creUsrInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultUser = new JMenuItem(texts.getString("main.menu.users.consult"));
		menuUsers.add(mnConsultUser);
		mnConsultUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				consultUserInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnModifyUser = new JMenuItem(texts.getString("main.menu.users.modify"));
		menuUsers.add(mnModifyUser);
		mnModifyUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				modifyUserInternalFrame.setVisible(true);
			}
		});

		// Menu Activities
		JMenu menuActivities = new JMenu(texts.getString("main.menu.activities"));
		menuBar.add(menuActivities);

		JMenuItem menuItemAddAct = new JMenuItem(texts.getString("main.menu.activities.create"));
		menuActivities.add(menuItemAddAct);
		menuItemAddAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				creActInternalFrame.init();
				creActInternalFrame.setVisible(true);

			}
		});

		JMenuItem mnConsultActivity = new JMenuItem(texts.getString("main.menu.activities.consult"));
		menuActivities.add(mnConsultActivity);
		mnConsultActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				consultActInternalFrame.init();
				consultActInternalFrame.setVisible(true);

			}
		});

		JMenuItem mnModifyActivity = new JMenuItem(texts.getString("main.menu.activities.modify"));
		menuActivities.add(mnModifyActivity);
		mnModifyActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				modifyActivityInternalFrame.init();
				modifyActivityInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnRankingActivity = new JMenuItem(texts.getString("main.menu.activities.ranking"));
		menuActivities.add(mnRankingActivity);
		mnRankingActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				activityRankingInternalFrame.init();
				activityRankingInternalFrame.setVisible(true);
			}
		});
		
		JMenuItem mnApprovalActivity = new JMenuItem(texts.getString("main.menu.activities.approval"));
		menuActivities.add(mnApprovalActivity);
		mnApprovalActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				hideAllComponents();
//				activityRankingInternalFrame.init();
//				activityRankingInternalFrame.setVisible(true);
			}
		});

		JMenu mnOuting = new JMenu(texts.getString("main.menu.outings"));
		menuBar.add(mnOuting);

		JMenuItem mnCreateOuting = new JMenuItem(texts.getString("main.menu.outings.create"));
		mnOuting.add(mnCreateOuting);
		mnCreateOuting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				touristOutingRegistrationInternalFrame.init();
				touristOutingRegistrationInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultOutingBKP = new JMenuItem(texts.getString("main.menu.outings.consult"));
		mnOuting.add(mnConsultOutingBKP);
		mnConsultOutingBKP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				consultTouristOutingInternalFrame.init();
				consultTouristOutingInternalFrame.setVisible(true);
			}
		});

		JMenu mnInscription = new JMenu(texts.getString("main.menu.inscriptions"));
		menuBar.add(mnInscription);

		JMenuItem mnCreateInscription = new JMenuItem(texts.getString("main.menu.inscriptions.create"));
		mnInscription.add(mnCreateInscription);
		mnCreateInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				inscriptionToTouristOutingInternalFrame.init();
				inscriptionToTouristOutingInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultInscription = new JMenuItem(texts.getString("main.menu.inscriptions.consult"));
		mnInscription.add(mnConsultInscription);
		mnConsultInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				consultInscriptionInternalFrame.init();
				consultInscriptionInternalFrame.setVisible(true);
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
		creUsrInternalFrame.setVisible(false);
		creActInternalFrame.setVisible(false);
		consultActInternalFrame.setVisible(false);
		touristOutingRegistrationInternalFrame.setVisible(false);
		consultTouristOutingInternalFrame.setVisible(false);
		inscriptionToTouristOutingInternalFrame.setVisible(false);
		modifyActivityInternalFrame.setVisible(false);
		consultInscriptionInternalFrame.setVisible(false);
		consultUserInternalFrame.setVisible(false);
		modifyUserInternalFrame.setVisible(false);
		activityRankingInternalFrame.setVisible(false);
	}
}
