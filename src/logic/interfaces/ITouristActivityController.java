package logic.interfaces;

import java.time.Duration;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedActivityNameException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtTouristActivity;

public interface ITouristActivityController {

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity) throws RepeatedActivityNameException;
	
	public String[] listTouristActivities() throws ActivityDoesNotExistException;
	
	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException ;
	
	//The method ListarSalidasActividad(nombreActividad: String) : DtActividadConSalida has same response and parameters than consultTouristActivityData method
		
	public void modifyDescription (String description);
	
	public void modifyDuration (Duration duration);
	
	public void modifyTouristFee (float touristFee);
	
	public void modifyCity (String city);
	
	public DtRanking consultRankingActivities();
	
	public String getActivityName();

	public float getActivityCostTourist(String activityName);
}

