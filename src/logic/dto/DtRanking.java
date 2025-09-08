package logic.dto;

public class DtRanking {

	private String activityName;
	private int outingCount;

	public DtRanking() {
	}

	public DtRanking(String activityName, int outingCount) {
		this.activityName = activityName;
		this.outingCount = outingCount;
	}

	public String getActivityName() {
		return activityName;
	}

	public int getNumberOutings() {
		return outingCount;
	}
}
