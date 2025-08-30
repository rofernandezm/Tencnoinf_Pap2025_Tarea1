package logic.interfaces;

import java.time.LocalDateTime;

import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;

public interface ITouristOutingAndInscriptionController {

	public DtTouristOuting consultTouristOutingData(String outingName);
	
	public void outingDataEntry(DtTouristOuting dtTouristOuting, String activityName);

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname, String outingName);
	
	public void cancelOutingRegistration();

	public void confirmOutingRegistration();
	
	public DtInscriptionTouristOuting[] listOutingInscription(String outingName);
	
	public void modifyOutingName (String outingName);
	
	public void modifyMaxTourist(int maxTourist);
	
	public void modifyExitPoint (String exitPoint);
	
	public void modifydateTime (LocalDateTime dateTime);
	
	public String getOutingName();
	
}
