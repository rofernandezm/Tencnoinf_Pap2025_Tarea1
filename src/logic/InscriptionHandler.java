package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logic.entity.Inscription;

public class InscriptionHandler {
	
	private Map<String, Inscription> inscriptions;
	private static InscriptionHandler instance = null;

	private InscriptionHandler() {
		inscriptions = new HashMap<String, Inscription>();
	}
	
	public static InscriptionHandler getIntance() {
		if (instance == null)
			instance = new InscriptionHandler();
		return instance;
	}
	
	//Define what will be the attribute that difference the inscriptions tourist-touristOuting?
	//Make sense an inscription handler?
	/*
	public void addInscription(Inscription inscription) {
		String activityName = inscription.getActivityName();
		this.inscriptions.put(activityName, Inscription);
	}

	public TouristActivity gerTouristActivityByName(String activityName) {
		return ((TouristActivity) touristActivities.get(activityName));
	}

	public Boolean existInscription(String activityName) {
		return inscriptions.containsKey(activityName);
	}

	public String[] listInscriptions() {
		if (inscriptions.isEmpty())
			return null;
		else {
			Set<String> keySet = inscriptions.keySet();
			Object[] keys = keySet.toArray();
			String[] activitiesNames = new String[inscriptions.size()];
			for (int ind = 0; ind < keys.length; ind++) {
				activitiesNames[ind] = keys[ind].toString();
			}
			return activitiesNames;
		}

	}
	 */

}
