package logic.interfaces;

import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedActivityNameException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtRanking;
import logic.dto.DtTouristActivity;

public interface ITouristActivityController {

	public void activityDataEntry(DtTouristActivity dtTouristActivity) throws RepeatedActivityNameException;
	
	public String[] listTouristActivities() throws ActivityDoesNotExistException;
	
	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException ;
	
	public DtRanking[]  getActivityRanking();
	
	public DtTouristActivity consultTouristActivityBasicData(String activityName) throws ActivityDoesNotExistException;

	public float getActivityCostTourist(String activityName) throws ActivityDoesNotExistException;
	
	public void modifyActivity(DtTouristActivity dto);
	
	public String[] listTouristActivitiesBySupplierNickname(String nickname);
}

