package logic.dto;

import java.util.HashSet;
import java.util.Set;

public class DtActivityWithOutings {
	
	private DtTouristActivity activity;
	private Set<DtTouristOuting> outings = new HashSet<>();

	public DtActivityWithOutings() {}

	public DtActivityWithOutings(DtTouristActivity activity, Set<DtTouristOuting> outings) {
		this.activity = activity;
		this.outings = outings;
	}
	
	public DtTouristActivity getActivity() {
		return activity;
	}
	
	public Set<DtTouristOuting> getOutings() {
		return outings;
	}
	
}