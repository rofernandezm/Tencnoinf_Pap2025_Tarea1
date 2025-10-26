package turismouyapp.core.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException;
import turismouyapp.core.exceptions.RepeatedTouristOutingException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;

public interface ITouristOutingAndInscriptionController {

	public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException;

	public void outingDataEntry(DtTouristOuting dtTouristOuting) throws RepeatedTouristOutingException;

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname,
			String outingName) throws RepeatedInscriptionToTouristOutingException;

	public DtInscriptionTouristOuting[] listOutingInscription(String outingName);

	public String getOutingName();

	public String[] listTouristOutings() throws TouristOutingDoesNotExistException;

	public float inscriptionTotalCost(float touristActivityCost, int numTourists);
	
	public String[] listInscriptionTouristOutingByTourist(String nickname);
	
	public String[] listTouristOutingByActivity(String activityName);
	
	public List<DtInscriptionTouristOuting> listDtInscriptionTouristOutingByTouristNickname(String nickname);
	
	public void updateOutingImageName(String outingName, String imageName);
}
