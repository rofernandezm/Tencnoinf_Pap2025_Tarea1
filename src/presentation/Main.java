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

public class Main {

	private JFrame frmTourismUy;       
    private IUserController IUC; 
    private CreateUser creUsrInternalFrame;
	
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
        /*IUC = FactoryUyTourism.getIUserController();
     
        creUsrInternalFrame = new CreateUser(IUC);
        creUsrInternalFrame.setLocation(30, 35);
        creUsrInternalFrame.setVisible(false);

        frmTourismUy.getContentPane().add(creUsrInternalFrame);
    }*/
	}

	private void initialize() {
        
        frmTourismUy = new JFrame();
        frmTourismUy.setTitle("Tourism Uy");
        frmTourismUy.setBounds(100, 100, 450, 400);
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
        
        JMenu menuUsers = new JMenu("Users");
        menuBar.add(menuUsers);

        /*JMenuItem createUser = new JMenuItem("Create User");
        createUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	creUsrInternalFrame.setVisible(true);
            }
        });
        menuUsers.add(createUser);*/
    }
}
