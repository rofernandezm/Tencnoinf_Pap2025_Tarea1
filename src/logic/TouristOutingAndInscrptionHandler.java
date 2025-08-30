package logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logic.entity.TouristOuting;
import logic.entity.Inscription;
import logic.entity.Tourist;

public class TouristOutingAndInscrptionHandler {
	
	private Map<String, TouristOuting> touristOutings;
	//Key nickname, second map outingName as key and inscription as value 
	private Map<String, Inscription> mapOutingTourist;
	private Map<String, Map<String, Inscription>> inscriptions;
	
	private static TouristOutingAndInscrptionHandler instance = null;
	
	private TouristOutingAndInscrptionHandler() {
		touristOutings = new HashMap<String, TouristOuting>();
		mapOutingTourist = new HashMap<String, Inscription>();
		inscriptions = new HashMap<String, Map<String, Inscription>>();
	}
	
	public static TouristOutingAndInscrptionHandler getIntance() {
		if (instance == null)
			instance = new TouristOutingAndInscrptionHandler();
		return instance;
	}
	
	public void addTouristOuting(TouristOuting touristOuting) {
		String outingName = touristOuting.getOutingName();
		this.touristOutings.put(outingName, touristOuting);
	}
	
//	Key is tourist nickname, then outingName as key and inscription as value of internal map
//  Analyze if Tourist and touristOuting as objects in parameters or just string with Tourist nickname and Tourist name
	public void addInscription(Tourist tourist, TouristOuting touristOuting, Inscription inscription) {
		String touristNickname = tourist.getNickname();
		String touristOutingName = touristOuting.getOutingName();
		this.mapOutingTourist.put(touristOutingName, inscription);
		this.inscriptions.put(touristNickname, mapOutingTourist);
	}
	
	public TouristOuting getTouristOutingByName(String outingName) {
		return ((TouristOuting) touristOutings.get(outingName));
	}
	
	public Set<Inscription> getInscriptionsByTouristNickname(String nickname) {
		Map<String, Inscription> auxMap = new HashMap<>();
		auxMap = inscriptions.get(nickname);
		Set<Inscription> inscriptionsSet = new HashSet<>(auxMap.values());
		if (inscriptionsSet.isEmpty()) { 
			return null;
		}
		return inscriptionsSet;
	}

	public Set<Inscription> getInscriptionsByTouristOuting(String touristOutingName) {
		Set<Map<String, Inscription>> auxMapSet = new HashSet<>();
		auxMapSet = (Set<Map<String, Inscription>>) inscriptions.values();
		Set<Inscription> inscriptionsSet = new HashSet<>();
		//Select from the set of maps, ones who has touristOutingName as key
		for (Map<String, Inscription> internalMap : auxMapSet) { 
		    if (internalMap.containsKey(touristOutingName)) {
		    	inscriptionsSet.add(internalMap.get(touristOutingName));
		    }
		}
		if (inscriptionsSet.isEmpty()) { 
			return null;
		}
		return inscriptionsSet;
	}
	
	public Boolean existOutingName(String outingName) {
		return touristOutings.containsKey(outingName);
	}

	public String[] listTouristOutings() {
		if (touristOutings.isEmpty())
			return null;
		else {
			Set<String> keySet = touristOutings.keySet();
			Object[] keys = keySet.toArray();
			String[] outingsNames = new String[touristOutings.size()];
			for (int ind = 0; ind < keys.length; ind++) {
				outingsNames[ind] = keys[ind].toString();
			}
			return outingsNames;
		}

	}

	public Boolean existInscription(String nickname, String touristOutingName) {
		Set<Inscription> inscriptionsSetByNickname = new HashSet<>();
		inscriptionsSetByNickname = getInscriptionsByTouristNickname(nickname);
	
		if (inscriptionsSetByNickname.isEmpty()) { 
			return false;
		}
		
		Set<Inscription> inscriptionsSetByTouristOutingName = new HashSet<>();
		inscriptionsSetByTouristOutingName = getInscriptionsByTouristOuting(touristOutingName);
		
		if (inscriptionsSetByTouristOutingName.isEmpty()) { 
			return false;
		}
		
		Map<String, Inscription> auxMap = new HashMap<>();
		auxMap = inscriptions.get(nickname); 
		if (auxMap.containsKey(touristOutingName)){ 
			return true;
		}
		return false;
	}
}
