package logic.interfaces;

import java.time.LocalDateTime;

import exceptions.RepeatedInscriptionToTouristOutingException;
import exceptions.RepeatedTouristOutingException;
import exceptions.TouristOutingDoesNotExistException;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;

public interface ITouristOutingAndInscriptionController {

	public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException;

	public void outingDataEntry(DtTouristOuting dtTouristOuting) throws RepeatedTouristOutingException;

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname,
			String outingName) throws RepeatedInscriptionToTouristOutingException;

	public DtInscriptionTouristOuting[] listOutingInscription(String outingName);

	public void modifyOutingName(String outingName);

	public void modifyMaxTourist(int maxTourist);

	public void modifyExitPoint(String exitPoint);

	public void modifydateTime(LocalDateTime dateTime);

	public String getOutingName();

	public String[] listTouristOutings() throws TouristOutingDoesNotExistException;

	public float inscriptionTotalCost(float touristActivityCost, int numTourists);
	
	public String[] listInscriptionTouristOutingByTourist(String nickname);
	
	public String[] listTouristOutingByActivity(String activityName);
}
