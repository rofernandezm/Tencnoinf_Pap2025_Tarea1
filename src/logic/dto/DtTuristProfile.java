package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtTuristProfile extends DtUserProfile {
	
	private Set<String> activities = new HashSet<>();
	
	public DtTuristProfile() {}

	public DtTuristProfile(DtUser user, Set<String> activities) {
		super(user);
		this.activities = activities;
	}
	
	public Set<String> getActivities() {
		return activities;
	}
}