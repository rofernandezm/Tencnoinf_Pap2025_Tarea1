package logic.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import logic.entity.TouristActivity;
import logic.entity.TouristOuting;

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
	        TypedQuery<TouristActivity> query = em.createQuery("SELECT t FROM TouristActivity t", TouristActivity.class);

	        List<TouristActivity> result = query.getResultList();

	        String[] activities = new String[result.size()];
	        for (int i = 0; i < result.size(); i++) {
	            activities[i] = result.get(i).getActivityName();
	        }

	        return activities;
	    } finally {
	        em.close();
	    }
	}

	private Map<String, TouristActivity> updateTouristActivitiesFromDB() {
			
			EntityManager em = PersistenceHandler.getEntityManager();
			TypedQuery<TouristActivity> query = em.createQuery("SELECT ta FROM TouristActivity ta", TouristActivity.class);
			
			List<TouristActivity> result = query.getResultList();
			Map<String, TouristActivity> touristActivities = new HashMap<>();
			for (TouristActivity activity : result) {
				touristActivities.put(activity.getActivityName(), activity);
		    }
			
			em.close();
			return touristActivities;
		}
}
