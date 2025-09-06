package logic.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logic.entity.TouristActivity;
import logic.entity.TouristOuting;

public class TouristActivityHandler {
	
	private Map<String, TouristActivity> touristActivities;
	private EntityManagerFactory emf;
	private static final String PERSISTENCE_UNIT = "turismoUyDB";
	private static TouristActivityHandler instance = null;

	private TouristActivityHandler() {
		touristActivities = new HashMap<String, TouristActivity>();
	}
	
	public static TouristActivityHandler getIntance() {
		if (instance == null) {
			instance = new TouristActivityHandler();
		}
		
		return instance;
	}
	
	public void addTouristActivity(TouristActivity touristActivity) {
		String activityName = touristActivity.getActivityName();
		this.touristActivities.put(activityName, touristActivity);
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		emf.createEntityManager().persist(touristActivity);
	}

	public TouristActivity getTouristActivityByName(String activityName) {
		return ((TouristActivity) touristActivities.get(activityName));
	}

	public Boolean existActivityName(String activityName) {
		return touristActivities.containsKey(activityName);
	}

	public String[] listTouristActivities() {
		
		EntityManager em = PersistenceHandler.getEntityManager();
	    TypedQuery<TouristActivity> query = em.createQuery("SELECT t FROM TouristActivity t", TouristActivity.class);
	    List<TouristActivity> result = query.getResultList();
	    String[] activities = result.size() > 0 ? new String[result.size()] : null;
	    for (int i = 0; i < result.size(); i++) {
	    	activities[i] = result.get(i).getActivityName();
	    }
	    em.close();
		return activities;
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
