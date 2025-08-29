package logic.dto;

public class DtRanking {
	
	private String activity;
	private int outingCount;

	public DtRanking() {}

	public DtRanking(String activity, int outingCount) {
		this.activity = activity;
		this.outingCount = outingCount;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public int getOutings() {
		return outingCount;
	}
}
