package logic.dto;

import java.util.ArrayList;
import java.util.List;

public class DtActivityWithOutings {

	private DtTouristActivity activity;
	private List<DtTouristOuting> outings = new ArrayList<>();

	public DtActivityWithOutings() {
	}

	public DtActivityWithOutings(DtTouristActivity activity, List<DtTouristOuting> outings) {
		this.activity = activity;
		this.outings = outings;
	}

	public DtTouristActivity getActivity() {
		return activity;
	}

	public List<DtTouristOuting> getOutings() {
		return outings;
	}

}