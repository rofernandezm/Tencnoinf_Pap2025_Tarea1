package logic.dto;

public abstract class DtUserProfile {
	private DtUser user;
	
	public DtUserProfile() {};
	
	public DtUserProfile(DtUser user) {
		this.user = user;
	}
	
	public DtUser getUser() {
		return user;
	}
}