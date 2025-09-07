package logic.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

	public void activityDataEntry(DtTouristActivity dtTouristActivity) throws RepeatedActivityNameException {
		if (TouristActivityHandler.getIntance().existActivityName(dtTouristActivity.getActivityName())) {
			throw new RepeatedActivityNameException("Ya existe una actividad turistica con ese nombre.");
		}

		TouristActivityHandler.getIntance().addTouristActivity(dtTouristActivity);

	}

	public String[] listTouristActivities() throws ActivityDoesNotExistException {

		String[] rtn = TouristActivityHandler.getIntance().listTouristActivities();

		if (rtn == null)
			throw new ActivityDoesNotExistException("No existen actividades turisticas registradas.");

		return rtn;
	}

	public DtActivityWithOutings consultTouristActivityData(String activityName) {
		DtActivityWithOutings rtn = null;
		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);
		if (ta != null) {
			DtTouristActivity dtActivity = new DtTouristActivity(ta.getActivityName(), ta.getDescription(),
					ta.getDuration(), ta.getTouristFee(), ta.getCity(), ta.getDischargeDate(),
					ta.getSupplier().getNickname());
			Set<DtTouristOuting> touristOutings = null;
			new java.util.HashSet<>();
			if (ta.getTouristOutings() != null) {
				Iterator<TouristOuting> it = ta.getTouristOutings().values().iterator();
				if (it != null) {
					touristOutings = new java.util.HashSet<>();
					while (it.hasNext()) {
						TouristOuting to = it.next();
						touristOutings.add(new DtTouristOuting(to.getOutingName(), to.getMaxNumTourists(),
								to.getDeparturePoint(), to.getDepartureDate(), to.getDischargeDate(), activityName));
					}
				}
			}
			rtn = new DtActivityWithOutings(dtActivity, touristOutings);
		}
		return rtn;
	}

	// The method ListarSalidasActividad(nombreActividad: String) :
	// DtActividadConSalida has same response and parameters than
	// consultTouristActivityData method

	public DtRanking[] getActivityRanking() {
	    String[] activities = TouristActivityHandler.getIntance().listTouristActivities();
	    if (activities == null || activities.length == 0) {
	        return new DtRanking[0];
	    }

	    List<DtRanking> rankingList = new ArrayList<>();

	    for (String activityName : activities) {
	        TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);

	        int outingCount = 0;
	        if (ta != null && ta.getTouristOutings() != null) {
	            outingCount = ta.getTouristOutings().size();
	        }

	        DtRanking newItem = new DtRanking(activityName, outingCount);

	        int pos = 0;
	        while (pos < rankingList.size() && rankingList.get(pos).getOutings() >= newItem.getOutings()) {
	            pos++;
	        }
	        rankingList.add(pos, newItem);
	    }

	    return rankingList.toArray(new DtRanking[0]);
	}
	
	public DtTouristActivity consultTouristActivityBasicData(String activityName) throws ActivityDoesNotExistException {
		DtTouristActivity rtn = null;
		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);
		if (ta != null) {
			rtn = new DtTouristActivity(ta.getActivityName(), ta.getDescription(),
					ta.getDuration(), ta.getTouristFee(), ta.getCity(), ta.getDischargeDate(),
					ta.getSupplier().getNickname());
		}else {
			throw new ActivityDoesNotExistException("No existe una actividad turistica con ese nombre.");
		}
		return rtn;
	}
	
	public float getActivityCostTourist(String activityName) {
		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);
		if (ta == null) {
			return -1;
		}
		return ta.getTouristFee();
	}
	
	public void modifyActivity(DtTouristActivity dto) {
	    TouristActivityHandler.getIntance().updateActivity(dto);
	}
}