package logic.controller;

import java.time.Duration;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedActivityNameException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtTouristActivity;
import logic.entity.TouristActivity;
import logic.handler.TouristActivityHandler;
import logic.interfaces.ITouristActivityController;

public class TouristActivityController implements ITouristActivityController {
	

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity, String supplierNickname) throws RepeatedActivityNameException {
		if(TouristActivityHandler.getIntance().existActivityName(dtTouristActivity.getActivityName())) {
			throw new RepeatedActivityNameException("Ya existe una actividad turistica con ese nombre.");
		}
		
		TouristActivityHandler.getIntance().addTouristActivity(dtTouristActivity.toEntity());
		
		return true;
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
		
	 public DtRanking consultRankingActivities() {
		 return new DtRanking();
	 }
	 
}