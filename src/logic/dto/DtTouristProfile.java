package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtTouristProfile extends DtUserProfile {
	
	private Set<DtInscriptionTouristOuting> inscriptionTourisOuting = new HashSet<>();
	
	public DtTouristProfile() {}

	public DtTouristProfile(DtUser user, Set<DtInscriptionTouristOuting> inscriptionTourisOuting) {
		super(user);
		this.inscriptionTourisOuting = inscriptionTourisOuting;
	}
	
	public Set<DtInscriptionTouristOuting> getInscriptionTourisOuting() {
		return inscriptionTourisOuting;
	}
}