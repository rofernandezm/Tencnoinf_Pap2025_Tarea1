package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logic.entity.TouristOuting;

public class TouristOutingHandler {
	
	private Map<String, TouristOuting> touristOutings;
	private static TouristOutingHandler instance = null;
	
	private TouristOutingHandler() {
		touristOutings = new HashMap<String, TouristOuting>();
	}
	
	public static TouristOutingHandler getIntance() {
		if (instance == null)
			instance = new TouristOutingHandler();
		return instance;
	}
	
	public void addTouristOuting(TouristOuting touristOuting) {
		String outingName = touristOuting.getOutingName();
		this.touristOutings.put(outingName, touristOuting);
	}
	
	public TouristOuting gerTouristOutingByName(String outingName) {
		return ((TouristOuting) touristOutings.get(outingName));
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

}
