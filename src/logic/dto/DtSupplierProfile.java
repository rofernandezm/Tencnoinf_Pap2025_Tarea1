package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtSupplierProfile extends DtUserProfile {
	
	private Set<String> activities = new HashSet<>();
	
	public DtSupplierProfile() {}

	public DtSupplierProfile(DtUser user, Set<String> activities) {
		super(user);
		this.activities = activities;
	}
	
	public Set<String> getActivities() {
		return activities;
	}
}
