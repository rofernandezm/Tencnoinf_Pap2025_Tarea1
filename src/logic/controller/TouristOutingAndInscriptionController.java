package logic.controller;

import java.time.LocalDateTime;
import java.util.Set;

import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.entity.Inscription;
import logic.handler.TouristActivityHandler;
import logic.handler.TouristOutingAndInscrptionHandler;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class TouristOutingAndInscriptionController implements ITouristOutingAndInscriptionController {
	
	private String outingName;
	
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
		  Set<Inscription> inscriptionsSet = TouristOutingAndInscrptionHandler.getIntance().getInscriptionsByTouristOuting(outingName);

			    if (inscriptionsSet == null || inscriptionsSet.isEmpty()) {
			        return new DtInscriptionTouristOuting[0];
			    }

			    DtInscriptionTouristOuting[] result = new DtInscriptionTouristOuting[inscriptionsSet.size()];
			   
			    int i = 0;

			    for (Inscription ins : inscriptionsSet) {
			        DtTouristOuting dtoOuting = ins.getTouristOuting().toDT();
			        result[i++] = new DtInscriptionTouristOuting(
			            ins.getNumTourists(),
			            ins.getTotalRegistrationCost(),
			            ins.getInscriptionDate(),
			            dtoOuting
			        );
			    }

			    return result;
	}
	
	public void modifyOutingName (String outingName) {
	}
	
	public void modifyMaxTourist(int maxTourist) {	
	}
	
	public void modifyExitPoint (String exitPoint) {	
	}
	
	public void modifydateTime (LocalDateTime dateTime) {	
	}
	
	public String getOutingName() {
		return outingName;
	}
	
}
