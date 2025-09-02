package presentation;

import java.awt.EventQueue;

import javax.swing.JFrame;   
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logic.FactoryUyTourism;
import logic.interfaces.*;
//Llamo archivos de la capa presentacion que representan un caso de uso

import javax.swing.JMenu;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

public class Main {

	private JFrame frmTourismUy;       
    private IUserController IUC; 
    private ITouristActivityController ITAC;

    private CreateUser creUsrInternalFrame;
    private CreateActivity creActInternalFrame; 
	
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
        creActInternalFrame = new CreateActivity(ITAC, IUC);
        creActInternalFrame.setVisible(false);
        frmTourismUy.getContentPane().add(creActInternalFrame);
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
        
        JMenu menuSystem = new JMenu("System");
        menuBar.add(menuSystem);

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frmTourismUy.setVisible(false);
                frmTourismUy.dispose();
            }
        });
        menuSystem.add(menuExit);
        
        //Menu Users
        JMenu menuUsers = new JMenu("Users");
        menuBar.add(menuUsers);
        
        ActionListener createUserListener = e -> {
        	creUsrInternalFrame.setVisible(true);
        };
        JMenuItem createUser = new JMenuItem("Create User");
        createUser.addActionListener(createUserListener);
        menuUsers.add(createUser);
        
        
        //Menu Activities
        JMenu menuActivities = new JMenu("Activities");
        menuBar.add(menuActivities);

        JMenuItem menuItemAddAct = new JMenuItem("Add Activity");
        menuItemAddAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        creActInternalFrame.setVisible(true);
				
			}
		});
        menuActivities.add(menuItemAddAct);
    }
}
