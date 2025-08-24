package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtTuristProfile extends DtUserProfile {
	
	private Set<String> trips = new HashSet<>();
	
	public DtTuristProfile() {}

	public DtTuristProfile(DtUser user, Set<String> trips) {
		super(user);
		this.trips = trips;
	}
	
	public Set<String> getTrips() {
		return trips;
	}
}