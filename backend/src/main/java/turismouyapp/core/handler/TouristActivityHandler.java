package turismouyapp.core.handler;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.entity.TouristActivity;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;

public class TouristActivityHandler {

	private static TouristActivityHandler instance = null;

	private TouristActivityHandler() {
	}

	public static TouristActivityHandler getIntance() {
		if (instance == null) {
			instance = new TouristActivityHandler();
		}

		return instance;
	}

	public void addTouristActivity(TouristActivity touristActivity) {
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(touristActivity);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public TouristActivity getTouristActivityByName(String activityName) {
		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			return em.find(TouristActivity.class, activityName);
		} finally {
			em.close();
		}
	}

	public Boolean existActivityName(String activityName) {
		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			return em.find(TouristActivity.class, activityName) != null;
		} finally {
			em.close();
		}
	}

	public String[] listTouristActivities() {
		EntityManager em = PersistenceHandler.getEntityManager();
		try {
			TypedQuery<TouristActivity> query = em.createQuery("SELECT t FROM TouristActivity t",
					TouristActivity.class);

			List<TouristActivity> result = query.getResultList();

			String[] activities = result.size() > 0 ? new String[result.size()] : null;
			for (int i = 0; i < result.size(); i++) {
				activities[i] = result.get(i).getActivityName();
			}

			return activities;
		} finally {
			em.close();
		}
	}

	public String[] listTouristActivitiesPendingApproval() {
		return listTouristActivitiesByStatus(TouristActivityStatus.ADDED);
	}

	public String[] listTouristActivitiesConfirmed() {
		return listTouristActivitiesByStatus(TouristActivityStatus.CONFIRMED);
	}

	public String[] listTouristActivitiesRejected() {
		return listTouristActivitiesByStatus(TouristActivityStatus.REJECTED);
	}

	private String[] listTouristActivitiesByStatus(TouristActivityStatus status) {
		EntityManager em = PersistenceHandler.getEntityManager();
		String[] activities = null;
		try {
			TypedQuery<TouristActivity> query = em
					.createQuery("SELECT t FROM TouristActivity t WHERE t.status = :status", TouristActivity.class);
			query.setParameter("status", status);
			List<TouristActivity> result = query.getResultList();

			activities = result.size() > 0 ? new String[result.size()] : null;
			for (int i = 0; i < result.size(); i++) {
				activities[i] = result.get(i).getActivityName();
			}
		} finally {
			em.close();
		}
		return activities;
	}

	public void updateActivityStatus(String activityName, TouristActivityStatus status) throws ActivityDoesNotExistException{

		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			TouristActivity ta = em.find(TouristActivity.class, activityName);
			if (ta == null)
				throw new ActivityDoesNotExistException("No existe actividad para el nombre indicado. Por favor reintente");
			
			ta.setStatus(status);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public void updateActivity(DtTouristActivity dto) {

		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			TouristActivity ta = em.find(TouristActivity.class, dto.getActivityName());
			if (ta != null) {
				ta.setDescription(dto.getDescription());
				ta.setDuration(dto.getDuration());
				ta.setTouristFee(dto.getCostTurist());
				ta.setCity(dto.getCity());

			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public List<String> listTouristActivitiesBySupplierNickname(String nickname) {

		EntityManager em = PersistenceHandler.getEntityManager();
		TypedQuery<String> query = em.createQuery("SELECT ta.activityName " + "FROM TouristActivity ta "
				+ "WHERE ta.supplier.nickname = :nick " + "ORDER BY ta.activityName", String.class);
		query.setParameter("nick", nickname);
		List<String> activitiesName = query.getResultList();
		em.close();
		return activitiesName;
	}
}
