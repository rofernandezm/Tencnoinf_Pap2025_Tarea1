package logic.controller;

import java.time.Duration;

import logic.dto.DtActivityWithTrips;
import logic.dto.DtTouristActivity;
import logic.entity.TouristActivity;
import logic.TouristActivityHandler;
import logic.interfaces.ITouristActivityController;

public class TouristActivityController implements ITouristActivityController {

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity, String supplierNickname) {
		
	}
	public void cancelRegistration() {
		
	}

	public void confirmRegistration() {
		
	}
	
	public String[] listTouristActivities() {
		return TouristActivityHandler.getIntance().listTouristActivities();
	}
	
	public DtActivityWithTrips consultTouristActivityData(String activityName) {
		
	}
	
	//The method ListarSalidasActividad(nombreActividad: String) : DtActividadConSalida has same response and parameters than consultTouristActivityData method
	
	public void modifyActivityName (String activityName) {
		
	}
	
	public void modifyDescription (String description) {
		
	}
	
	public void modifyDuration (Duration duration) {
		
	}
	
	public void modifyTouristFee (float touristFee) {
		
	}
	
	public void modifyCity (String city) {
		
	}
	
	// public DtRanking consultRankingActivities();
}