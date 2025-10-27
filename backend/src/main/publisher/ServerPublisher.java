package publisher;

import serverTurismouy.WebServices;

public class ServerPublisher{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 // Genero los wsdl y publico  WebServices 
        WebServices p = new WebServices();
        p.publicar();
        
	}

}