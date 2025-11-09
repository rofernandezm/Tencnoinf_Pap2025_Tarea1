package turismouyapp.core.handler;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.entity.Supplier;
import turismouyapp.core.entity.Tourist;
import turismouyapp.core.entity.User;

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
		try {
			em.persist(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	public User getUserByNickname(String nickname) {
		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			return em.find(User.class, nickname);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	public Boolean existNickname(String nickname) {

		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			return em.find(User.class, nickname) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	public Boolean existEmail(String email) {

		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			q.setParameter("email", email);
			return !q.getResultList().isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	public String[] listUsers() {

		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
			List<User> obj_users = q.getResultList();
			String[] nicknames = obj_users.size() > 0 ? new String[obj_users.size()] : null;
			for (int ind = 0; ind < obj_users.size(); ind++) {
				nicknames[ind] = obj_users.get(ind).getNickname();
			}
			return nicknames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	public String[] listSuppliers() {

		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			TypedQuery<Supplier> q = em.createQuery("SELECT s FROM Supplier s", Supplier.class);
			List<Supplier> obj_suppliers = q.getResultList();
			String[] suppliers = obj_suppliers.size() > 0 ? new String[obj_suppliers.size()] : null;
			for (int ind = 0; ind < obj_suppliers.size(); ind++) {
				suppliers[ind] = obj_suppliers.get(ind).getNickname();
			}
			return suppliers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}

	}

	public String[] listTourists() {

		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			TypedQuery<Tourist> q = em.createQuery("SELECT s FROM Tourist s", Tourist.class);
			List<Tourist> obj_tourist = q.getResultList();
			String[] tourist = new String[obj_tourist.size()];
			for (int ind = 0; ind < obj_tourist.size(); ind++) {
				tourist[ind] = obj_tourist.get(ind).getNickname();
			}
			return tourist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	public void updateUser(DtUser dtUser) {
		if (dtUser == null) {
			throw new IllegalArgumentException("DtUser no puede ser null");
		}
		
		String nickname = dtUser.getNickname();
		if (nickname == null || nickname.trim().isEmpty()) {
			throw new IllegalArgumentException("El nickname del usuario no puede ser null o vacío");
		}
		
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			User user = em.find(User.class, nickname);
			if (user != null) {
				user.setName(dtUser.getName());
				user.setLastName(dtUser.getLastName());
				user.setPassword(dtUser.getPassword());
				
				// Convertir birthDate (puede venir como String desde WebService)
				Object birthDateObj = dtUser.getBirthDate();
				if (birthDateObj != null) {
					user.setBirthDate(turismouyapp.webservices.utils.DateUtils.parseToLocalDate(birthDateObj));
				}
				
				user.setImagePath(dtUser.getImagePath());

				if (user instanceof Tourist) {
					((Tourist) user).setNationality(((DtTourist) dtUser).getNationality());
				} else {
					((Supplier) user).setDescription(((DtSupplier) dtUser).getDescription());
					((Supplier) user).setWebSite(((DtSupplier) dtUser).getWebSite());
				}
				em.merge(user);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
		} finally {
			em.close();
		}

	}

	public void updateTourist(DtTourist dtTourist) {
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Tourist user = em.find(Tourist.class, dtTourist.getNickname());
			if (user != null) {
				user.setNationality(dtTourist.getNationality());
			}
			em.merge(user);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void updateSupplier(DtSupplier dtSupplier) {
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Supplier user = em.find(Supplier.class, dtSupplier.getNickname());
			if (user != null) {
				user.setDescription(dtSupplier.getDescription());
				user.setWebSite(dtSupplier.getWebSite());
			}
			em.merge(user);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public User getUserByEmail(String email) {
		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			TypedQuery<User> userByEmail = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			userByEmail.setParameter("email", email);
			List<User> users = userByEmail.getResultList();
			if (users.isEmpty()) {
				return null;
			} else {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

}