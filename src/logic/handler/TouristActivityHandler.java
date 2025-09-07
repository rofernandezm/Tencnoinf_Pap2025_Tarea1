package logic.handler;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import logic.dto.DtTouristActivity;
import logic.entity.Supplier;
import logic.entity.TouristActivity;

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
	
	public void addTouristActivity(DtTouristActivity touristActivity) {
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		 try {
		        tx.begin();
		        em.persist(DtToEntity(touristActivity));
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
	
	public TouristActivity DtToEntity(DtTouristActivity dta) {
		 Supplier supplier = UserHandler.getIntance().getSupplierByNickname(dta.getSupplierNickname());
		    if (supplier == null) {
		        throw new IllegalArgumentException("Supplier no encontrado: " + dta.getSupplierNickname());
		    }

		    TouristActivity ta = new TouristActivity(
		    		dta.getActivityName(),
		    		dta.getDescription(),
		    		dta.getDuration(),
		    		dta.getCostTurist(),
		    		dta.getCity(),
		    		dta.getRegistratioDate()
		    		);

		    ta.setSupplier(supplier);

		    return ta;
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

		
//		EntityManager em = PersistenceHandler.getEntityManager();
//	    TypedQuery<TouristActivity> query = em.createQuery("SELECT t FROM TouristActivity t", TouristActivity.class);
//	    List<TouristActivity> result = query.getResultList();
//	    String[] activities = result.size() > 0 ? new String[result.size()] : null;
//	    for (int i = 0; i < result.size(); i++) {
//	    	activities[i] = result.get(i).getActivityName();
//	    }
//	    em.close();
//		return activities;
//	}

	
	public void updateActivity(DtTouristActivity dto) {
	    EntityManager em = PersistenceHandler.getEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    tx.begin();
	    TouristActivity ta = em.find(TouristActivity.class, dto.getActivityName());
	    if (ta != null) {
	        ta.setDescription(dto.getDescription());
	        ta.setDuration(dto.getDuration());
	        ta.setTouristFee(dto.getCostTurist());
	        ta.setCity(dto.getCity());

	    }
	    tx.commit();
	    em.close();
	}


//	private Map<String, TouristActivity> updateTouristActivitiesFromDB() {
//			
//			EntityManager em = PersistenceHandler.getEntityManager();
//			TypedQuery<TouristActivity> query = em.createQuery("SELECT ta FROM TouristActivity ta", TouristActivity.class);
//			
//			List<TouristActivity> result = query.getResultList();
//	
//			for (TouristActivity activity : result) {
//				touristActivities.put(activity.getActivityName(), activity);
//		    }
//			
//			em.close();
//			return touristActivities;
//		}
}
