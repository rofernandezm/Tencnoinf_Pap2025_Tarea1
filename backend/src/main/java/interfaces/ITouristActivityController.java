package interfaces;

import dto.DtActivityWithOutings;
import dto.DtRanking;
import dto.DtTouristActivity;
import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedActivityNameException;

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

