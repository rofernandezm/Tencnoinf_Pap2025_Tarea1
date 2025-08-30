package logic.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logic.entity.TouristActivity;

public class TouristActivityHandler {
	
	private Map<String, TouristActivity> touristActivities;
	private static TouristActivityHandler instance = null;

	private TouristActivityHandler() {
		touristActivities = new HashMap<String, TouristActivity>();
	}
	
	public static TouristActivityHandler getIntance() {
		if (instance == null)
			instance = new TouristActivityHandler();
		return instance;
	}
	
	public void addTouristActivity(TouristActivity touristActivity) {
		String activityName = touristActivity.getActivityName();
		this.touristActivities.put(activityName, touristActivity);
	}

	public TouristActivity gerTouristActivityByName(String activityName) {
		return ((TouristActivity) touristActivities.get(activityName));
	}

	public Boolean existActivityName(String activityName) {
		return touristActivities.containsKey(activityName);
	}

	public String[] listTouristActivities() {
		if (touristActivities.isEmpty())
			return null;
		else {
			Set<String> keySet = touristActivities.keySet();
			Object[] keys = keySet.toArray();
			String[] activitiesNames = new String[touristActivities.size()];
			for (int ind = 0; ind < keys.length; ind++) {
				activitiesNames[ind] = keys[ind].toString();
			}
			return activitiesNames;
		}

	}

}
