package logic.dto;

import java.util.ArrayList;
import java.util.List;

public class DtSupplierProfile extends DtUserProfile {

	private List<DtActivityWithOutings> activities = new ArrayList<>();

	public DtSupplierProfile() {
	}

	public DtSupplierProfile(DtUser user, List<DtActivityWithOutings> activities) {
		super(user);
		this.activities = activities;
	}

	public List<DtActivityWithOutings> getActivities() {
		return activities;
	}
}