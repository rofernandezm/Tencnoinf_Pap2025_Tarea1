package logic.controller;

import java.util.ArrayList;
import java.util.List;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedActivityNameException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtTouristActivity;
import logic.dto.DtTouristOuting;
import logic.entity.Supplier;
import logic.entity.TouristActivity;
import logic.entity.TouristOuting;
import logic.handler.TouristActivityHandler;
import logic.handler.TouristOutingAndInscrptionHandler;
import logic.handler.UserHandler;
import logic.interfaces.ITouristActivityController;

public class TouristActivityController implements ITouristActivityController {

	public void activityDataEntry(DtTouristActivity dtTouristActivity) throws RepeatedActivityNameException {

		if (TouristActivityHandler.getIntance().existActivityName(dtTouristActivity.getActivityName())) {
			throw new RepeatedActivityNameException("Ya existe una actividad turistica con ese nombre.");
		}

		TouristActivity touristActivity = new TouristActivity(dtTouristActivity);
		Supplier supplier = (Supplier) UserHandler.getIntance()
				.getUserByNickname(dtTouristActivity.getSupplierNickname());
		touristActivity.setSupplier(supplier);

		TouristActivityHandler.getIntance().addTouristActivity(touristActivity);

	}

	public String[] listTouristActivities() throws ActivityDoesNotExistException {

		String[] rtn = TouristActivityHandler.getIntance().listTouristActivities();

		if (rtn == null)
			throw new ActivityDoesNotExistException("No existen actividades turisticas registradas.");

		return rtn;
	}

	// TODO
	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException {

		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);

		if (ta == null) {
			throw new ActivityDoesNotExistException("No existe actividad para el nombre indicado. Por favor reintente");
		}

		List<String> actOutingNames = TouristOutingAndInscrptionHandler.getIntance()
				.getTouristOutingByActivityName(activityName);
		
		List<DtTouristOuting> dtTouristOuting = new ArrayList<>();

		for (String outing : actOutingNames) {
			TouristOuting to = TouristOutingAndInscrptionHandler.getIntance().getTouristOutingByName(outing);
			dtTouristOuting.add(to.getDtTouristOuting());
		}

		return new DtActivityWithOutings(ta.getDtTouristActivity(), dtTouristOuting);
	}

	// TODO
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
			while (pos < rankingList.size() && rankingList.get(pos).getNumberOutings() >= newItem.getNumberOutings()) {
				pos++;
			}
			rankingList.add(pos, newItem);
		}

		return rankingList.toArray(new DtRanking[0]);
	}

	public DtTouristActivity consultTouristActivityBasicData(String activityName) throws ActivityDoesNotExistException {
		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);

		if (ta == null) {
			throw new ActivityDoesNotExistException("No existe una actividad turistica con ese nombre.");
		}

		return ta.getDtTouristActivity();
	}

	// TODO
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

	public String[] listTouristActivitiesBySupplierNickname(String nickname) {
		List<String> activitiesList = TouristActivityHandler.getIntance()
				.listTouristActivitiesBySupplierNickname(nickname);
		String[] activitiesName = activitiesList.size() > 0 ? new String[activitiesList.size()] : null;

		for (int ind = 0; ind < activitiesList.size(); ind++) {
			activitiesName[ind] = activitiesList.get(ind);
		}
		return activitiesName;
	}
}