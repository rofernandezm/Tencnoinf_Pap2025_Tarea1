package logic.handler;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import logic.entity.TouristOuting;
import logic.entity.User;
import logic.entity.Tourist;
import logic.handler.PersistenceHandler;
import logic.dto.DtInscriptionTouristOuting;
import logic.entity.Inscription;

public class TouristOutingAndInscrptionHandler {
	
	//private Map<String, TouristOuting> touristOutings;
	//Key nickname, second map outingName as key and inscription as value 
	private Map<String, Inscription> mapOutingTourist;
	private Map<String, Map<String, Inscription>> inscriptions;
	
	private static TouristOutingAndInscrptionHandler instance = null;
	
	private TouristOutingAndInscrptionHandler() {
		//touristOutings = new HashMap<String, TouristOuting>();
		mapOutingTourist = new HashMap<String, Inscription>();
		inscriptions = new HashMap<String, Map<String, Inscription>>();
	}
	
	public static TouristOutingAndInscrptionHandler getIntance() {
		if (instance == null)
			instance = new TouristOutingAndInscrptionHandler();
		return instance;
	}
	
	public void addTouristOuting(TouristOuting touristOuting) {
//		String outingName = touristOuting.getOutingName();
//		this.touristOutings = updateTouristOutingsFromDB();
//		touristOutings.put(outingName, touristOuting);
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
     	em.persist(touristOuting);
     	tx.commit();
     	em.close();
	}
	
//	Key is tourist nickname, then outingName as key and inscription as value of internal map
	public void addInscription(Inscription inscription) {
//		String touristOutingName = touristOuting.getOutingName();
//		this.mapOutingTourist.put(touristOutingName, inscription);
//		this.inscriptions.put(touristNickname, mapOutingTourist);
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
     	em.persist(inscription);
     	tx.commit();
     	em.close();
	}
	
	public TouristOuting getTouristOutingByName(String outingName) {
		EntityManager em = PersistenceHandler.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		TouristOuting touristOutingToGet = em.find(TouristOuting.class, outingName);
		tx.commit();
     	em.close();
		return (touristOutingToGet);
	}
	
//	public TouristOuting getTouristOutingByName(String outingName) {
//		this.touristOutings = updateTouristOutingsFromDB();
//		return touristOutings.get(outingName);
//	}
	
	public Set<Inscription> getInscriptionsByTouristNickname(String nickname) {
		this.inscriptions = updateInscriptionsFromDB();
		Map<String, Inscription> auxMap = new HashMap<>();
		
		auxMap = inscriptions.get(nickname);
		if (auxMap == null || auxMap.isEmpty()) {
			return Collections.emptySet();
		}
		Set<Inscription> inscriptionsSet = new HashSet<>(auxMap.values());
		if (inscriptionsSet.isEmpty()) { 
			return null;
		}
		return inscriptionsSet;
	}


	//Analizar en base de datos como se guardan los datos 
	public Set<Inscription> getInscriptionsByTouristOuting(String touristOutingName) {
		this.inscriptions = updateInscriptionsFromDB();
//		Set<Map<String, Inscription>> auxMapSet = new HashSet<>();
//		auxMapSet = (Set<Map<String, Inscription>>) inscriptions.values();
		Collection<Map<String, Inscription>> auxMapSet = inscriptions.values();
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
		Boolean exist = false;
		EntityManager em = PersistenceHandler.getEntityManager();
		exist = (em.find(TouristOuting.class, outingName) != null);
		em.close();
		return exist;
	}

	public String[] listTouristOutings() {
		
		EntityManager em = PersistenceHandler.getEntityManager();
		TypedQuery<TouristOuting> q = em.createQuery("SELECT u FROM TouristOuting u", TouristOuting.class);
		List<TouristOuting> obj_touristOuting = q.getResultList();
		String[] touristOutingNames = new String[obj_touristOuting.size()];
		for (int ind = 0; ind < obj_touristOuting.size(); ind++) {
			touristOutingNames[ind] = obj_touristOuting.get(ind).getOutingName();
		}
		em.close();
		return touristOutingNames;
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
	
	//No usar 
	public Inscription getInscriptionByDtInscriptionTouristOuting(DtInscriptionTouristOuting dtInscriptionTouristOuting, Tourist tourist, TouristOuting touristOuting) {
		int numTourists = dtInscriptionTouristOuting.getTouristAmount();
		float totalRegistrationCost = dtInscriptionTouristOuting.getTotalCost();
		LocalDate inscriptionDate = dtInscriptionTouristOuting.getInscriptionDate();
		
		
		Inscription inscription = new Inscription(numTourists, totalRegistrationCost, inscriptionDate, tourist, touristOuting);
		return inscription;
	}
	
//	private Map<String, TouristOuting> updateTouristOutingsFromDB() {
//		
//		EntityManager em = PersistenceHandler.getEntityManager();
//		TypedQuery<TouristOuting> query = em.createQuery("SELECT to FROM TouristOuting to", TouristOuting.class);
//		
//		List<TouristOuting> result = query.getResultList();
//
//		for (TouristOuting outing : result) {
//	        touristOutings.put(outing.getOutingName(), outing);
//	    }
//		
//		em.close();
//		return touristOutings;
//	}
	
	private Map<String, Map<String, Inscription>> updateInscriptionsFromDB() {
		
		EntityManager em = PersistenceHandler.getEntityManager();
		TypedQuery<Inscription> query = em.createQuery("SELECT ins FROM Inscription ins", Inscription.class);
		
		List<Inscription> result = query.getResultList();

		for (Inscription inscr : result) {
			TouristOuting to = inscr.getTouristOuting();
			Tourist ut = inscr.getTourist();

			// Recuperar el mapa ya existente, o crear uno nuevo si no está
			Map<String, Inscription> auxMap = inscriptions.getOrDefault(ut.getNickname(), new HashMap<>());
			auxMap.put(to.getOutingName(), inscr);
			inscriptions.put(ut.getNickname(), auxMap);
	    }
		
		em.close();
		return inscriptions;
	}
}
