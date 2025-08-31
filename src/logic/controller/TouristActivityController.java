package logic.controller;

import java.time.Duration;

import exceptions.ActivityDoesNotExistException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtTouristActivity;
import logic.entity.TouristActivity;
import logic.handler.TouristActivityHandler;
import logic.interfaces.ITouristActivityController;

public class TouristActivityController implements ITouristActivityController {
	
	private String activityName;

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity, String supplierNickname) {
		return true;
	}
	public void cancelRegistration() {
		
	}

	public void confirmRegistration() {
		
	}
	
	public String[] listTouristActivities() throws ActivityDoesNotExistException{
		
		String[] rtn = TouristActivityHandler.getIntance().listTouristActivities();
		
		if(rtn == null) throw new ActivityDoesNotExistException("No existen actividades turisticas registradas.");
		
		return rtn;
	}
	
	public DtActivityWithOutings consultTouristActivityData(String activityName) {
		return new DtActivityWithOutings();
	}
	
	//The method ListarSalidasActividad(nombreActividad: String) : DtActividadConSalida has same response and parameters than consultTouristActivityData method
		
	public void modifyDescription (String description) {
		TouristActivityHandler.getIntance().getTouristActivityByName(activityName).setDescription(description);
	}
	
	public void modifyDuration (Duration duration) {
		TouristActivityHandler.getIntance().getTouristActivityByName(activityName).setDuration(duration);
	}
	
	public void modifyTouristFee (float touristFee) {
		TouristActivityHandler.getIntance().getTouristActivityByName(activityName).setTouristFee(touristFee);
	}
	
	public void modifyCity (String city) {
		TouristActivityHandler.getIntance().getTouristActivityByName(activityName).setCity(city);
		
	}
	
	 public DtRanking consultRankingActivities() {
		 return new DtRanking();
	 }
	 
	 public String getActivityName() {
		return activityName;
	 }
}