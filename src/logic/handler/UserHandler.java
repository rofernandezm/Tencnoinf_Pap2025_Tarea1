package logic.handler;

import java.util.Collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import logic.entity.User;

public class UserHandler {
	private static UserHandler instance = null;
	private static final String PERSISTENCE_UNIT = "turismoUyDB";

	private UserHandler() {
	}

	public static UserHandler getIntance() {

		if (instance == null)
			instance = new UserHandler();
		return instance;
	}

	public void addUser(User user) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		tx.commit();
		em.close();
		emf.close();
	}

	public User getUserByNickname(String nickname) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User userByNickname = em.find(User.class, nickname);
		tx.commit();
		em.close();
		emf.close();
		return userByNickname;
	}

	public Boolean existNickname(String nickname) {

		Boolean exist = false;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		exist = (em.find(User.class, nickname) != null);
		tx.commit();
		em.close();
		emf.close();
		return exist;
	}

	public Boolean existEmail(String email) {

		Boolean exist = false;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		exist = (em.find(User.class, email) != null);
		tx.commit();
		em.close();
		emf.close();
		return exist;
	}

	public String[] listUsers() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT u FROM USERS u");

		@SuppressWarnings("unchecked")
		Collection<User> result = query.getResultList();

		Object[] o = result.toArray();
		String[] nicknames = new String[o.length];
		for (int i = 0; i < o.length; i++) {
			nicknames[i] = ((User) o[i]).getNickname();
		}
		em.close();
		emf.close();
		return nicknames;

	}

	public String[] listSuppliers() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT u FROM USERS u WHERE u.DTYPE = \"Supplier\"");

		@SuppressWarnings("unchecked")
		Collection<User> result = query.getResultList();

		Object[] o = result.toArray();
		String[] suppliers = new String[o.length];
		for (int i = 0; i < o.length; i++) {
			suppliers[i] = ((User) o[i]).getNickname();
		}
		em.close();
		emf.close();
		return suppliers;

	}
}
