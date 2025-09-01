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

public class Main {

	private JFrame frmTourismUy;       
    private ITouristActivityController IAC;
    private ITouristOutingAndInscriptionController IOIC;
    private TouristOutingRegistration touristOutingRegistrationInternalFrame;
    private ConsultTouristOuting consultTouristOutingInternalFrame;
    private ModifyActivity modifyActivityInternalFrame;
    private ConsultTuristInscription consultInscriptionInternalFrame;
   	
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
        
        IAC = factoryUyTourism.getITouristActivityController();
        IOIC = factoryUyTourism.getITouristOutingAndInscriptionController();
        
        touristOutingRegistrationInternalFrame = new TouristOutingRegistration(IOIC);
        touristOutingRegistrationInternalFrame.setVisible(false);
        frmTourismUy.getContentPane().add(touristOutingRegistrationInternalFrame);
        
        consultTouristOutingInternalFrame = new ConsultTouristOuting(IOIC);
        consultTouristOutingInternalFrame.setVisible(false);
        frmTourismUy.getContentPane().add(consultTouristOutingInternalFrame);
        
        modifyActivityInternalFrame = new ModifyActivity(IAC);
        modifyActivityInternalFrame.setVisible(false);
        frmTourismUy.getContentPane().add(modifyActivityInternalFrame);
        
        
        consultInscriptionInternalFrame = new ConsultTuristInscription(IOIC);
        consultInscriptionInternalFrame.setVisible(false);
        frmTourismUy.getContentPane().add(consultInscriptionInternalFrame);
	}
	private void initialize() {
        
        frmTourismUy = new JFrame();
        frmTourismUy.setTitle("Tourism Uy");
        frmTourismUy.setBounds(100, 100, 450, 400);
        frmTourismUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frmTourismUy.setJMenuBar(menuBar);
        
        JMenu menuUsers = new JMenu("Usuarios");
        menuBar.add(menuUsers);
        
        JMenuItem mnCreateUser = new JMenuItem("Alta Usuario");
        menuUsers.add(mnCreateUser);
        
        JMenuItem mnConsultUser = new JMenuItem("Consultar Usuarios");
        menuUsers.add(mnConsultUser);
        
        JMenuItem mnModifyUser = new JMenuItem("Modificar Datos Usuario");
        menuUsers.add(mnModifyUser);
        
        JMenu mnActivity = new JMenu("Actividades");
        menuBar.add(mnActivity);
        
        JMenuItem mnCreateActivity = new JMenuItem("Alta Actividad");
        mnActivity.add(mnCreateActivity);
        
        JMenuItem mnConsultActivity = new JMenuItem("Consultar Actividad");
        mnActivity.add(mnConsultActivity);
        
        JMenuItem mnModifyActivity = new JMenuItem("Modificar Actividad");
        mnActivity.add(mnModifyActivity);
        mnModifyActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyActivityInternalFrame.setVisible(true);
			}
		});
                
        JMenuItem mnRankingActivity = new JMenuItem("Ranking Actividades");
        mnActivity.add(mnRankingActivity);
        
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

        /*JMenuItem createUser = new JMenuItem("Create User");
        createUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	creUsrInternalFrame.setVisible(true);
            }
        });
        menuUsers.add(createUser);*/
    }
}
