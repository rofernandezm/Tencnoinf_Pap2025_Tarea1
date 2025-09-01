package logic.interfaces;

import java.time.LocalDateTime;

import exceptions.RepeatedTouristOutingException;
import exceptions.TouristOutingDoesNotExistException;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;

public interface ITouristOutingAndInscriptionController {

	public DtTouristOuting consultTouristOutingData(String outingName)throws TouristOutingDoesNotExistException;
	
	public void outingDataEntry(DtTouristOuting dtTouristOuting, String activityName) throws RepeatedTouristOutingException;

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname, String outingName);
	
	public void cancelOutingRegistration();

//	public void confirmOutingRegistration(); not needed
	
	public DtInscriptionTouristOuting[] listOutingInscription(String outingName);
	
	public void modifyOutingName (String outingName);
	
	public void modifyMaxTourist(int maxTourist);
	
	public void modifyExitPoint (String exitPoint);
	
	public void modifydateTime (LocalDateTime dateTime);
	
	public String getOutingName();

	public String[] listTouristOutings() throws TouristOutingDoesNotExistException;
	
}
