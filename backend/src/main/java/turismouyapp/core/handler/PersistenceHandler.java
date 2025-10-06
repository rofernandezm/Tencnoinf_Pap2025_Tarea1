package turismouyapp.core.handler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceHandler {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("turismoUyDB-Server");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void closeEntityConnection() {
		emf.close();
	}
}
