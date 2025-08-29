package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtSupplierProfile extends DtUserProfile {
	
	private Set<DtActivityWithOutings> activities = new HashSet<>();
	
	public DtSupplierProfile() {}

	public DtSupplierProfile(DtUser user, Set<DtActivityWithOutings> activities) {
		super(user);
		this.activities = activities;
	}
	
	public Set<DtActivityWithOutings> getActivities() {
		return activities;
	}
}