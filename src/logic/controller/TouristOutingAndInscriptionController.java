package logic.controller;

import java.time.LocalDateTime;

import logic.TouristOutingAndInscrptionHandler;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class TouristOutingAndInscriptionController implements ITouristOutingAndInscriptionController {
	
	public DtTouristOuting consultTouristOutingData(String outingName) {
		return null;
	}
	
	public void outingDataEntry(DtTouristOuting dtTouristOuting, String activityName) {
	}

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname, String outingName) {
	}
	
	public void cancelOutingRegistration() {	
	}

	public void confirmOutingRegistration() {	
	}
	public DtInscriptionTouristOuting[] listOutingInscription(String outingName) {
		return null;
	}
	
	public void modifyOutingName (String outingName) {
	}
	
	public void modifyMaxTourist(int maxTourist) {	
	}
	
	public void modifyExitPoint (String exitPoint) {	
	}
	
	public void modifydateTime (LocalDateTime dateTime) {	
	}
	
}
