package logic.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import logic.entity.User;

public class UserHandler {
	private Map<String, User> users;
	private Map<String, String> emailMapper;
	private static UserHandler instance = null;
	private static final String PERSISTENCE_UNIT = "turismoUyDB";
	
	private UserHandler() {
		users = new HashMap<String, User>();
		emailMapper = new HashMap<String, String>();
	}

	public static UserHandler getIntance() {
		if (instance == null)
			instance = new UserHandler();
		return instance;
	}

	public void addUser(User user) {
		String nickname = user.getNickname();
		String email = user.getEmail();
		this.users.put(nickname, user);
		this.emailMapper.put(email, nickname);
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
    	return (userByNickname);
	}

	public Boolean existNickname(String nickname) {
		return users.containsKey(nickname);
	}

	public Boolean existEmail(String email) {
		return emailMapper.containsKey(email);
	}

	public String[] listUsers() {
		if (users.isEmpty())
			return null;
		else {
			Set<String> keySet = users.keySet();
			Object[] keys = keySet.toArray();
			String[] nicknames = new String[users.size()];
			for (int ind = 0; ind < keys.length; ind++) {
				nicknames[ind] = keys[ind].toString();
			}
			return nicknames;
		}

	}
}
