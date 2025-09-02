package logic.handler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import logic.entity.TouristOuting;
import logic.dto.DtInscriptionTouristOuting;
import logic.entity.Inscription;

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
		this.touristOutings = updateTouristOutingsFromDB();
		touristOutings.put(outingName, touristOuting);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("turismoUyDB");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
     	em.persist(touristOuting);
     	tx.commit();
     	em.close();
     	emf.close();
	}
	
//	Key is tourist nickname, then outingName as key and inscription as value of internal map
	public void addInscription(String touristNickname, TouristOuting touristOuting, Inscription inscription) {
		String touristOutingName = touristOuting.getOutingName();
		this.mapOutingTourist.put(touristOutingName, inscription);
		this.inscriptions.put(touristNickname, mapOutingTourist);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("turismoUyDB");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
     	em.persist(inscription);
     	tx.commit();
     	em.close();
     	emf.close();
	}
	
	public TouristOuting getTouristOutingByName(String outingName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("turismoUyDB");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		TouristOuting touristOutingToGet = em.find(TouristOuting.class, outingName);
		tx.commit();
     	em.close();
     	emf.close();
		return (touristOutingToGet);
	}
	
//	public TouristOuting getTouristOutingByName(String outingName) {
//		this.touristOutings = updateTouristOutingsFromDB();
//		return touristOutings.get(outingName);
//	}
	
	//Analizar en base de datos como se guardan los datos 
	public Set<Inscription> getInscriptionsByTouristNickname(String nickname) {
		Map<String, Inscription> auxMap = new HashMap<>();
		auxMap = inscriptions.get(nickname);
		Set<Inscription> inscriptionsSet = new HashSet<>(auxMap.values());
		if (inscriptionsSet.isEmpty()) { 
			return null;
		}
		return inscriptionsSet;
	}


	//Analizar en base de datos como se guardan los datos 
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
		this.touristOutings = updateTouristOutingsFromDB();
		return touristOutings.containsKey(outingName);
	}

	public String[] listTouristOutings() {
		this.touristOutings = updateTouristOutingsFromDB();
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
	
	public Inscription getInscriptionByDtInscriptionTouristOuting(DtInscriptionTouristOuting dtInscriptionTouristOuting) {
		int numTourists = dtInscriptionTouristOuting.getTouristAmount();
		float totalRegistrationCost = dtInscriptionTouristOuting.getTotalCost();
		LocalDate inscriptionDate = dtInscriptionTouristOuting.getInscriptionDate();
		
		Inscription inscription = new Inscription(numTourists, totalRegistrationCost, inscriptionDate);
		return inscription;
	}
	
	private Map<String, TouristOuting> updateTouristOutingsFromDB() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("turismoUyDB");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT to FROM TouristOuting to");
		
		List<TouristOuting> result = query.getResultList();

		for (TouristOuting outing : result) {
	        touristOutings.put(outing.getOutingName(), outing);
	    }
		
		em.close();
		emf.close();
		return touristOutings;
		
	}
}
