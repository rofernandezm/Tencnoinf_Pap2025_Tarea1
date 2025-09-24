package logic;


import logic.interfaces.*;
import logic.controller.*;

public class FactoryUyTourism {

	private static FactoryUyTourism instance;
	
	private FactoryUyTourism() { 
    };
    
    public static FactoryUyTourism getInstance() {
        if (instance == null) {
            instance = new FactoryUyTourism(); 
        }
        return instance;
    }
    
    public ITouristActivityController getITouristActivityController() {
        return new TouristActivityController();
    }
    
    public IUserController getIUserController() {
        return new UserController();
    }
    
    public ITouristOutingAndInscriptionController getITouristOutingAndInscriptionController() {
        return new TouristOutingAndInscriptionController();
    }
    
}

