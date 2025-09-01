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
	private TouristOutingRegistration touristOutingRegistrationInternalFrame;
	private ConsultTouristOuting consultTouristOutingInternalFrame;
	private ModifyActivity modifyActivityInternalFrame;
	private ConsultTouristInscription consultInscriptionInternalFrame;

	public static void main(String[] args) {

		System.out.print("Access to main");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmTourismUy.setLayout(null);
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

		creUsrInternalFrame = new CreateUser(IUC);
		creUsrInternalFrame.setLocation(30, 35);
		creUsrInternalFrame.setVisible(false);

		frmTourismUy.getContentPane().add(creUsrInternalFrame);

		ITAC = factoryUyTourism.getITouristActivityController();
		IOIC = factoryUyTourism.getITouristOutingAndInscriptionController();

		touristOutingRegistrationInternalFrame = new TouristOutingRegistration(IOIC);
		touristOutingRegistrationInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(touristOutingRegistrationInternalFrame);

		consultTouristOutingInternalFrame = new ConsultTouristOuting(IOIC);
		consultTouristOutingInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(consultTouristOutingInternalFrame);

		creActInternalFrame = new CreateActivity(ITAC, IUC);
		creActInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(creActInternalFrame);

		modifyActivityInternalFrame = new ModifyActivity(ITAC);
//<<<<<<< HEAD
//        ITAC = factoryUyTourism.getITouristActivityController();
//        creActInternalFrame = new CreateActivity(ITAC, IUC);
//        creActInternalFrame.setVisible(false);
//        frmTourismUy.getContentPane().add(creActInternalFrame);
//        modifyActivityInternalFrame = new ModifyActivity(ITAC);
//=======
//        IAC = factoryUyTourism.getITouristActivityController();
//        IOIC = factoryUyTourism.getITouristOutingAndInscriptionController();
//        
//        touristOutingRegistrationInternalFrame = new TouristOutingRegistration(IOIC);
//        touristOutingRegistrationInternalFrame.setVisible(false);
//        frmTourismUy.getContentPane().add(touristOutingRegistrationInternalFrame);
//        
//        consultTouristOutingInternalFrame = new ConsultTouristOuting(IOIC);
//        consultTouristOutingInternalFrame.setVisible(false);
//        frmTourismUy.getContentPane().add(consultTouristOutingInternalFrame);
//        
//        modifyActivityInternalFrame = new ModifyActivity(IAC);
//>>>>>>> 13eb475 (casos de uso 6 y 7 en el main, con interfaz, compilando, resta probarlos)
		modifyActivityInternalFrame.setVisible(false);
		frmTourismUy.getContentPane().add(modifyActivityInternalFrame);

		IOIC = factoryUyTourism.getITouristOutingAndInscriptionController();
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

		ActionListener createUserListener = e -> {
			creUsrInternalFrame.setVisible(true);
		};

		JMenuItem createUser = new JMenuItem("Alta Usuario");
		createUser.addActionListener(createUserListener);
		menuUsers.add(createUser);

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
				creActInternalFrame.setVisible(true);

			}
		});

		JMenuItem mnConsultActivity = new JMenuItem("Consultar Actividad");
		menuActivities.add(mnConsultActivity);

		JMenuItem mnModifyActivity = new JMenuItem("Modificar Actividad");
		menuActivities.add(mnModifyActivity);
		mnModifyActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				touristOutingRegistrationInternalFrame.setVisible(true);
			}
		});

		JMenuItem mnConsultOuting = new JMenuItem("Consultar Salida");
		mnOuting.add(mnConsultOuting);
		mnConsultOuting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultTouristOutingInternalFrame.setVisible(true);
			}
		});

		JMenu mnInscription = new JMenu("Inscripciones");
		menuBar.add(mnInscription);

		JMenuItem mnCreateInscription = new JMenuItem("Nueva Inscripcion");
		mnInscription.add(mnCreateInscription);

		JMenuItem mnConsultInscription = new JMenuItem("Consultar Inscripciones");
		mnInscription.add(mnConsultInscription);
		mnConsultInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultInscriptionInternalFrame.setVisible(true);
			}
		});

		JMenu menuSystem = new JMenu("Sistema");
		menuBar.add(menuSystem);

		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTourismUy.setVisible(false);
				frmTourismUy.dispose();
			}
		});
		menuSystem.add(menuExit);
//                JMenuItem mnCreateActivity = new JMenuItem("Alta Actividad");
//                menuActivities.add(mnCreateActivity);
//                
//                JMenuItem mnConsultActivity = new JMenuItem("Consultar Actividad");
//                menuActivities.add(mnConsultActivity);
//                
//                JMenuItem mnModifyActivity = new JMenuItem("Modificar Actividad");
//                menuActivities.add(mnModifyActivity);
//                mnModifyActivity.addActionListener(new ActionListener() {
//        			public void actionPerformed(ActionEvent e) {
//        				modifyActivityInternalFrame.setVisible(true);
//        			}
//        		});
//                        
//                JMenuItem mnRankingActivity = new JMenuItem("Ranking Actividades");
//                menuActivities.add(mnRankingActivity);
//                
//                JMenu mnOuting = new JMenu("Salidas");
//                menuBar.add(mnOuting);
//                
//                JMenuItem mnCreateOuting = new JMenuItem("Alta Salida");
//                mnOuting.add(mnCreateOuting);
//                
//                JMenuItem mnConsultOuting = new JMenuItem("Consultar Salida");
//                mnOuting.add(mnConsultOuting);
//                
//                JMenu mnInscription = new JMenu("Inscripciones");
//                menuBar.add(mnInscription);
//                
//                JMenuItem mnCreateInscription = new JMenuItem("Nueva Inscripcion");
//                mnInscription.add(mnCreateInscription);
//                
//                JMenuItem mnConsultInscription = new JMenuItem("Consultar Inscripciones");
//                mnInscription.add(mnConsultInscription);
//                mnConsultInscription.addActionListener(new ActionListener() {
//             			public void actionPerformed(ActionEvent e) {
//             				consultInscriptionInternalFrame.setVisible(true);
//             			}
//             		});
//                
//                JMenu menuSystem = new JMenu("Sistema");
//                menuBar.add(menuSystem);
//                
//                        JMenuItem menuExit = new JMenuItem("Exit");
//                        menuExit.addActionListener(new ActionListener() {
//                            public void actionPerformed(ActionEvent arg0) {
//                                frmTourismUy.setVisible(false);
//                                frmTourismUy.dispose();
//                            }
//                        });
//                        menuSystem.add(menuExit);
//<<<<<<< HEAD
//        JMenu menuSystem = new JMenu("System");
//        menuBar.add(menuSystem);
//
//        JMenuItem menuExit = new JMenuItem("Exit");
//        menuExit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//                frmTourismUy.setVisible(false);
//                frmTourismUy.dispose();
//            }
//        });
//        menuSystem.add(menuExit);
//        
//        //Menu Users
//        JMenu menuUsers = new JMenu("Users");
//        menuBar.add(menuUsers);
//        
//        ActionListener createUserListener = e -> {
//        	creUsrInternalFrame.setVisible(true);
//        };
//        JMenuItem createUser = new JMenuItem("Create User");
//        createUser.addActionListener(createUserListener);
//        menuUsers.add(createUser);
//        
//        
//        //Menu Activities
//        JMenu menuActivities = new JMenu("Activities");
//        menuBar.add(menuActivities);
//=======
//        JMenu menuUsers = new JMenu("Usuarios");
//        menuBar.add(menuUsers);
//        
//        JMenuItem mnCreateUser = new JMenuItem("Alta Usuario");
//        menuUsers.add(mnCreateUser);
//        
//        JMenuItem mnConsultUser = new JMenuItem("Consultar Usuarios");
//        menuUsers.add(mnConsultUser);
//        
//        JMenuItem mnModifyUser = new JMenuItem("Modificar Datos Usuario");
//        menuUsers.add(mnModifyUser);
//        
//        JMenu mnActivity = new JMenu("Actividades");
//        menuBar.add(mnActivity);
//        
//        JMenuItem mnCreateActivity = new JMenuItem("Alta Actividad");
//        mnActivity.add(mnCreateActivity);
//        
//        JMenuItem mnConsultActivity = new JMenuItem("Consultar Actividad");
//        mnActivity.add(mnConsultActivity);
//        
//        JMenuItem mnModifyActivity = new JMenuItem("Modificar Actividad");
//        mnActivity.add(mnModifyActivity);
//        mnModifyActivity.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				modifyActivityInternalFrame.setVisible(true);
//			}
//		});
//                
//        JMenuItem mnRankingActivity = new JMenuItem("Ranking Actividades");
//        mnActivity.add(mnRankingActivity);
//        
//        JMenu mnOuting = new JMenu("Salidas");
//        menuBar.add(mnOuting);
//        
//        JMenuItem mnCreateOuting = new JMenuItem("Alta Salida");
//        mnOuting.add(mnCreateOuting);
//        
//        JMenuItem mnConsultOuting = new JMenuItem("Consultar Salida");
//        mnOuting.add(mnConsultOuting);
//        
//        JMenu mnInscription = new JMenu("Inscripciones");
//        menuBar.add(mnInscription);
//        
//        JMenuItem mnCreateInscription = new JMenuItem("Nueva Inscripcion");
//        mnInscription.add(mnCreateInscription);
//        
//        JMenuItem mnConsultInscription = new JMenuItem("Consultar Inscripciones");
//        mnInscription.add(mnConsultInscription);
//        mnConsultInscription.addActionListener(new ActionListener() {
//     			public void actionPerformed(ActionEvent e) {
//     				consultInscriptionInternalFrame.setVisible(true);
//     			}
//     		});
//        
//        JMenu menuSystem = new JMenu("Sistema");
//        menuBar.add(menuSystem);
//        
//                JMenuItem menuExit = new JMenuItem("Exit");
//                menuExit.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent arg0) {
//                        frmTourismUy.setVisible(false);
//                        frmTourismUy.dispose();
//                    }
//                });
//                menuSystem.add(menuExit);
//>>>>>>> d7edaf9 (Agrego menus para todos los CU en el main y agrego los internalFrames de los CU 9 y 10)

//        JMenuItem menuItemAddAct = new JMenuItem("Add Activity");
//        menuItemAddAct.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//		        creActInternalFrame.setVisible(true);
//				
//			}
//		});
//
//		menuActivities.add(menuItemAddAct);        
//        JMenuItem mnRankingActivity = new JMenuItem("Ranking Actividades");
//        mnActivity.add(mnRankingActivity);
//        
//        JMenu mnOuting = new JMenu("Salidas");
//        menuBar.add(mnOuting);
//        
//        JMenuItem mnCreateOuting = new JMenuItem("Alta Salida");
//        mnOuting.add(mnCreateOuting);
//        mnCreateOuting.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				touristOutingRegistrationInternalFrame.setVisible(true);
//			}
//		});
//        
//        JMenuItem mnConsultOuting = new JMenuItem("Consultar Salida");
//        mnOuting.add(mnConsultOuting);
//        mnConsultOuting.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				consultTouristOutingInternalFrame.setVisible(true);
//			}
//		});
//        
//        JMenu mnInscription = new JMenu("Inscripciones");
//        menuBar.add(mnInscription);
//        
//        JMenuItem mnCreateInscription = new JMenuItem("Nueva Inscripcion");
//        mnInscription.add(mnCreateInscription);
//        
//        JMenuItem mnConsultInscription = new JMenuItem("Consultar Inscripciones");
//        mnInscription.add(mnConsultInscription);
//        mnConsultInscription.addActionListener(new ActionListener() {
//     			public void actionPerformed(ActionEvent e) {
//     				consultInscriptionInternalFrame.setVisible(true);
//     			}
//     		});
//        
//        JMenu menuSystem = new JMenu("Sistema");
//        menuBar.add(menuSystem);
//        
//                JMenuItem menuExit = new JMenuItem("Exit");
//                menuExit.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent arg0) {
//                        frmTourismUy.setVisible(false);
//                        frmTourismUy.dispose();
//                    }
//                });
//                menuSystem.add(menuExit);

		/*
		 * JMenuItem createUser = new JMenuItem("Create User");
		 * createUser.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent arg0) { creUsrInternalFrame.setVisible(true); }
		 * }); menuUsers.add(createUser);
		 */
	}
}
