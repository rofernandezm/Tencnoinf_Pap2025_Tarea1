package turismouyapp.webservices;

public class Publisher {

	public static void main(String[] args) {
		new UserWebService().publicar();
		new ActivityWebService().publicar();
		new OutingAndInscriptionWebService().publicar();
	}

}