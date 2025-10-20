package turismouyapp.core.controller;

import java.util.ArrayList;
import java.util.List;

import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtRanking;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.entity.Supplier;
import turismouyapp.core.entity.TouristActivity;
import turismouyapp.core.entity.TouristOuting;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.handler.TouristActivityHandler;
import turismouyapp.core.handler.TouristOutingAndInscrptionHandler;
import turismouyapp.core.handler.UserHandler;
import turismouyapp.core.interfaces.ITouristActivityController;

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

	public String[] listTouristActivitiesByStatus(TouristActivityStatus status) throws IllegalArgumentException {
		switch (status) {
		case ADDED:
			return TouristActivityHandler.getIntance().listTouristActivitiesPendingApproval();
		case CONFIRMED:
			return TouristActivityHandler.getIntance().listTouristActivitiesConfirmed();
		case REJECTED:
			return TouristActivityHandler.getIntance().listTouristActivitiesRejected();
		default:
			throw new IllegalArgumentException(
					"No existen actividades turisticas registradas para el parámetro indicado '" + status + "'");
		}
	}

	public void updateTouristActivityStatus(String activityName, TouristActivityStatus status)
			throws ActivityDoesNotExistException {
		try {
			TouristActivityHandler.getIntance().updateActivityStatus(activityName, status);
		} catch (ActivityDoesNotExistException ex) {
			throw ex;
		}
	}

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

	public List<DtActivityWithOutings> listTouristActivityData() throws ActivityDoesNotExistException {

		List<DtActivityWithOutings> actWtOuts = new ArrayList<DtActivityWithOutings>();

		String[] taNames = listTouristActivitiesByStatus(TouristActivityStatus.CONFIRMED);

		if (taNames == null)
			throw new ActivityDoesNotExistException("No existen actividades confirmadas");

		for (String activity : taNames) {
			TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activity);
			if (ta == null)
				throw new ActivityDoesNotExistException(
						"No existe actividad para el nombre indicado. Por favor reintente");

			List<String> actOutingNames = TouristOutingAndInscrptionHandler.getIntance()
					.getTouristOutingByActivityName(activity);

			List<DtTouristOuting> dtTouristOutings = new ArrayList<>();

			for (String outing : actOutingNames) {
				TouristOuting to = TouristOutingAndInscrptionHandler.getIntance().getTouristOutingByName(outing);
				dtTouristOutings.add(to.getDtTouristOuting());
			}
			actWtOuts.add(new DtActivityWithOutings(ta.getDtTouristActivity(), dtTouristOutings));
		}

		return actWtOuts;
	}

	public List<DtActivityWithOutings> listTouristActivitiesBySupplierNickName(String nickname)
			throws ActivityDoesNotExistException {

		List<DtActivityWithOutings> actWtOuts = new ArrayList<DtActivityWithOutings>();
		List<String> activitiesList = TouristActivityHandler.getIntance()
				.listTouristActivitiesBySupplierNickname(nickname);

		if (activitiesList == null)
			throw new ActivityDoesNotExistException("No existen actividades confirmadas");

		for (String activity : activitiesList) {
			TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activity);
			if (ta == null)
				throw new ActivityDoesNotExistException(
						"No existe actividad para el nombre indicado. Por favor reintente");

			List<String> actOutingNames = TouristOutingAndInscrptionHandler.getIntance()
					.getTouristOutingByActivityName(activity);

			List<DtTouristOuting> dtTouristOutings = new ArrayList<>();

			for (String outing : actOutingNames) {
				TouristOuting to = TouristOutingAndInscrptionHandler.getIntance().getTouristOutingByName(outing);
				dtTouristOutings.add(to.getDtTouristOuting());
			}
			actWtOuts.add(new DtActivityWithOutings(ta.getDtTouristActivity(), dtTouristOutings));
		}

		return actWtOuts;
	}

	public DtRanking[] getActivityRanking() {
		TouristOutingAndInscrptionHandler toaih = TouristOutingAndInscrptionHandler.getIntance();
		String[] activities = TouristActivityHandler.getIntance().listTouristActivities();
		if (activities == null || activities.length == 0) {
			return new DtRanking[0];
		}

		List<DtRanking> rankingList = new ArrayList<>();

		for (String activityName : activities) {

			List<DtTouristOuting> touristOutings = toaih.getDtTouristOutingListByActivityName(activityName);
			int outingCount = touristOutings.size() > 0 ? touristOutings.size() : 0;

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
	public float getActivityCostTourist(String activityName) throws ActivityDoesNotExistException {
		TouristActivity ta = TouristActivityHandler.getIntance().getTouristActivityByName(activityName);
		if (ta == null) {
			throw new ActivityDoesNotExistException("No existe una actividad turistica con ese nombre.");
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