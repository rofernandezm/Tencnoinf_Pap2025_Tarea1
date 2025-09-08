package logic.handler;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import logic.entity.Supplier;
import logic.entity.Tourist;
import logic.entity.User;

public class UserHandler {
	private static UserHandler instance = null;

	private UserHandler() {
	}

	public static UserHandler getIntance() {

		if (instance == null)
			instance = new UserHandler();

		return instance;
	}

	public void addUser(User user) {

		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		tx.commit();
		em.close();
	}

	public User getUserByNickname(String nickname) {

		EntityManager em = PersistenceHandler.getEntityManager();
		User userByNickname = em.find(User.class, nickname);
		em.close();
		return userByNickname;
	}

	public Boolean existNickname(String nickname) {

		Boolean exist = false;
		EntityManager em = PersistenceHandler.getEntityManager();
		exist = (em.find(User.class, nickname) != null);
		em.close();
		return exist;
	}

	public Boolean existEmail(String email) {

		Boolean exist = false;
		EntityManager em = PersistenceHandler.getEntityManager();
		// A diferencia de Query, no requiere casteo de tipo
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
		q.setParameter("email", email);
		exist = !q.getResultList().isEmpty();

		em.close();
		return exist;
	}

	public String[] listUsers() {

		EntityManager em = PersistenceHandler.getEntityManager();
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
		List<User> obj_users = q.getResultList();
		String[] nicknames = new String[obj_users.size()];
		for (int ind = 0; ind < obj_users.size(); ind++) {
			nicknames[ind] = obj_users.get(ind).getNickname();
		}
		em.close();
		return nicknames;
	}

	public String[] listSuppliers() {

		EntityManager em = PersistenceHandler.getEntityManager();
		TypedQuery<Supplier> q = em.createQuery("SELECT s FROM Supplier s", Supplier.class);
		List<Supplier> obj_suppliers = q.getResultList();
		String[] suppliers = new String[obj_suppliers.size()];
		for (int ind = 0; ind < obj_suppliers.size(); ind++) {
			suppliers[ind] = obj_suppliers.get(ind).getNickname();
		}
		em.close();
		return suppliers;

	}

	public Supplier getSupplierByNickname(String nickname) {

		EntityManager em = PersistenceHandler.getEntityManager();
		Supplier userByNickname = em.find(Supplier.class, nickname);
		em.close();
		return userByNickname;
	}

	public String[] listTourists() {

		EntityManager em = PersistenceHandler.getEntityManager();
		TypedQuery<Tourist> q = em.createQuery("SELECT s FROM Tourist s", Tourist.class);
		List<Tourist> obj_tourist = q.getResultList();
		String[] tourist = new String[obj_tourist.size()];
		for (int ind = 0; ind < obj_tourist.size(); ind++) {
			tourist[ind] = obj_tourist.get(ind).getNickname();
		}
		em.close();
		return tourist;

	}
}