package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtSupplierProfile extends DtUserProfile {
	
	private Set<DtActivityWithTrips> activities = new HashSet<>();
	
	public DtSupplierProfile() {}

	public DtSupplierProfile(DtUser user, Set<DtActivityWithTrips> activities) {
		super(user);
		this.activities = activities;
	}
	
	public Set<DtActivityWithTrips> getActivities() {
		return activities;
	}
}