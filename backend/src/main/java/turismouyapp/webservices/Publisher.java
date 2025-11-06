package turismouyapp.webservices;

public class Publisher {

	public static void main(String[] args) {
		// Metro -> JAXB-RI (no MOXy)
//		System.setProperty("com.sun.xml.ws.spi.db.BindingContextFactory","com.sun.xml.ws.db.glassfish.JAXBRIContextFactory");
		// Cualquier JAXBContext -> RI
//		System.setProperty("jakarta.xml.bind.JAXBContextFactory", "org.glassfish.jaxb.runtime.v2.ContextFactory");

		new UserWebService().publicar();
		new ActivityWebService().publicar();
		new OutingAndInscriptionWebService().publicar();
	}

}