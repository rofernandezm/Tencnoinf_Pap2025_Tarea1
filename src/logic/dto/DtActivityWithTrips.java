package logic.dto;

import java.util.HashSet;
import java.util.Set;

public class DtActivityWithTrips {
	
	private DtTouristActivity activity;
	private Set<DtTouristTrip> trips = new HashSet<>();

	public DtActivityWithTrips() {}

	public DtActivityWithTrips(DtTouristActivity activity, Set<DtTouristTrip> trips) {
		this.activity = activity;
		this.trips = trips;
	}
	
	public DtTouristActivity getActivity() {
		return activity;
	}
	
	public Set<DtTouristTrip> getTrips() {
		return trips;
	}
	
}