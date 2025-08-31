package logic.interfaces;

import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtTouristActivity;
import java.time.Duration;

import exceptions.ActivityDoesNotExistException;

public interface ITouristActivityController {

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity, String supplierNickname);
	
	public void cancelRegistration();

	public void confirmRegistration();
	
	public String[] listTouristActivities();
	
	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException ;
	
	//The method ListarSalidasActividad(nombreActividad: String) : DtActividadConSalida has same response and parameters than consultTouristActivityData method
		
	public void modifyDescription (String description);
	
	public void modifyDuration (Duration duration);
	
	public void modifyTouristFee (float touristFee);
	
	public void modifyCity (String city);
	
	public DtRanking consultRankingActivities();
	
	public String getActivityName();
}

