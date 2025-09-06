package presentation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logic.FactoryUyTourism;
import logic.interfaces.*;

import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

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

	public static void main(String[] args) {

		System.out.print("Access to main");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
//					window.frmTourismUy.setLayout(null);
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

		frmTourismUy.getContentPane().add(creUsrInternalFrame);

		touristOutingRegistrationInternalFrame = new TouristOutingRegistration(IOIC, ITAC);
		touristOutingRegistrationInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(touristOutingRegistrationInternalFrame);

		consultTouristOutingInternalFrame = new ConsultTouristOuting(IOIC);
		consultTouristOutingInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultTouristOutingInternalFrame);

		creActInternalFrame = new CreateActivity(ITAC, IUC);
		creActInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(creActInternalFrame);
		
		consultActInternalFrame = new ConsultActivity(ITAC);
		consultActInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultActInternalFrame);

		inscriptionToTouristOutingInternalFrame = new InscriptionToTouristOuting(IOIC);
		inscriptionToTouristOutingInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(inscriptionToTouristOutingInternalFrame);

		modifyActivityInternalFrame = new ModifyActivity(ITAC);
		modifyActivityInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(modifyActivityInternalFrame);

		consultInscriptionInternalFrame = new ConsultTouristInscription(IOIC);
		consultInscriptionInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultInscriptionInternalFrame);

	}
	private void initialize() {

		frmTourismUy = new JFrame();
		frmTourismUy.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
				frmTourismUy.pack();
			}
		});
		frmTourismUy.setTitle("Tourism Uy");
		frmTourismUy.setBounds(100, 100, 800, 600);
		frmTourismUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmTourismUy.setJMenuBar(menuBar);

		// Menu Users
		JMenu menuUsers = new JMenu("Usuarios");
		menuBar.add(menuUsers);

		JMenuItem createUser = new JMenuItem("Alta Usuario");
		menuUsers.add(createUser);
		createUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				creUsrInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultUser = new JMenuItem("Consultar Usuarios");
		menuUsers.add(mnConsultUser);

		JMenuItem mnModifyUser = new JMenuItem("Modificar Datos Usuario");
		menuUsers.add(mnModifyUser);

		// Menu Activities
		JMenu menuActivities = new JMenu("Actividades");
		menuBar.add(menuActivities);

		JMenuItem menuItemAddAct = new JMenuItem("Alta Actividad");
		menuActivities.add(menuItemAddAct);
		menuItemAddAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAllComponents();
				creActInternalFrame.setVisible(true);

			}
		});

		JMenuItem mnConsultActivity = new JMenuItem("Consultar Actividad");
		menuActivities.add(mnConsultActivity);
		mnConsultActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultActInternalFrame.setVisible(true);

			}
		});

		JMenuItem mnModifyActivity = new JMenuItem("Modificar Actividad");
		menuActivities.add(mnModifyActivity);
		mnModifyActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				modifyActivityInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnRankingActivity = new JMenuItem("Ranking Actividades");
		menuActivities.add(mnRankingActivity);

		JMenu mnOuting = new JMenu("Salidas");
		menuBar.add(mnOuting);

		JMenuItem mnCreateOuting = new JMenuItem("Alta Salida");
		mnOuting.add(mnCreateOuting);
		mnCreateOuting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				touristOutingRegistrationInternalFrame.init();
				touristOutingRegistrationInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultOuting = new JMenuItem("Consultar Salida");
		mnOuting.add(mnConsultOuting);
		mnConsultOuting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				consultTouristOutingInternalFrame.setVisible(true);
			}
		});

		JMenu mnInscription = new JMenu("Inscripciones");
		menuBar.add(mnInscription);

		JMenuItem mnCreateInscription = new JMenuItem("Nueva Inscripcion");
		mnInscription.add(mnCreateInscription);
		mnCreateInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				inscriptionToTouristOutingInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultInscription = new JMenuItem("Consultar Inscripciones");
		mnInscription.add(mnConsultInscription);
		mnConsultInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllComponents();
				consultInscriptionInternalFrame.setVisible(true);
			}
		});

		JMenu menuSystem = new JMenu("Sistema");
		menuBar.add(menuSystem);

		JMenuItem menuExit = new JMenuItem("Salir");
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
		touristOutingRegistrationInternalFrame.setVisible(false);
		consultTouristOutingInternalFrame.setVisible(false);
		inscriptionToTouristOutingInternalFrame.setVisible(false);
		modifyActivityInternalFrame.setVisible(false);
		consultInscriptionInternalFrame.setVisible(false);
	}
}
