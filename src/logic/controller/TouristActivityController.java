package logic.controller;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedActivityNameException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtTouristActivity;
import logic.dto.DtTouristOuting;
import logic.entity.TouristActivity;
import logic.entity.TouristOuting;
import logic.handler.TouristActivityHandler;
import logic.interfaces.ITouristActivityController;

public class TouristActivityController implements ITouristActivityController {
	

	public Boolean activityDataEntry(DtTouristActivity dtTouristActivity) throws RepeatedActivityNameException {
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
		DtActivityWithOutings rtn = null;
		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);
		if(ta != null) {
			DtTouristActivity dtActivity = new DtTouristActivity(ta.getActivityName(), ta.getDescription(), ta.getDuration(), ta.getTouristFee(), ta.getCity(), ta.getDischargeDate(), ta.getSupplier().getNickname());
			Set<DtTouristOuting> touristOutings = null;new java.util.HashSet<>();
			if(ta.getTouristOutings() != null) {
				Iterator<TouristOuting> it = ta.getTouristOutings().values().iterator();
				if(it != null) {
					touristOutings = new java.util.HashSet<>();
					while(it.hasNext()) {
						TouristOuting to = it.next();
						touristOutings.add(new DtTouristOuting(to.getOutingName(), to.getMaxNumTourists(), to.getDeparturePoint(), to.getDepartureDate(), to.getDischargeDate()));
					}
				}
			}
			rtn = new DtActivityWithOutings(dtActivity, touristOutings);
		}
		return rtn;
	}
	
	//The method ListarSalidasActividad(nombreActividad: String) : DtActividadConSalida has same response and parameters than consultTouristActivityData method
		
	 public DtRanking consultRankingActivities() {
		 return new DtRanking();
	 }
	 
	 public void modifyDescription (String description) {
		 
	 }
		
		public void modifyDuration (Duration duration) {
			
		}
		
		public void modifyTouristFee (float touristFee) {
			
		}
		
		public void modifyCity (String city) {
			
		}
		
		public String getActivityName() {
			return "";
		}

		public float getActivityCostTourist(String activityName) {
			TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);
			if(ta == null) {
				return -1;
			}
			return ta.getTouristFee();
		}
}