package logic.interfaces;

import logic.dto.DtActivityWithOutings;
import logic.dto.DtTouristActivity;
//import logic.dto.DtRanking; falta!!!!
import java.time.Duration;

import exceptions.ActivityDoesNotExistException;

public interface ITouristActivityController {

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity, String supplierNickname);
	
	public void cancelRegistration();

	public void confirmRegistration();
	
	public String[] listTouristActivities() throws ActivityDoesNotExistException;
	
	public DtActivityWithOutings consultTouristActivityData(String activityName);
	
	//The method ListarSalidasActividad(nombreActividad: String) : DtActividadConSalida has same response and parameters than consultTouristActivityData method
	
	public void modifyActivityName (String activityName);
	
	public void modifyDescription (String description);
	
	public void modifyDuration (Duration duration);
	
	public void modifyTouristFee (float touristFee);
	
	public void modifyCity (String city);
	
	// public DtRanking consultRankingActivities();
}

