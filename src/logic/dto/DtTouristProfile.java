package logic.dto;

import java.util.Set;
import java.util.HashSet;

public class DtTouristProfile extends DtUserProfile {
	
	private Set<DtInscriptionTouristTrip> inscriptionTourisTrip = new HashSet<>();
	
	public DtTouristProfile() {}

	public DtTouristProfile(DtUser user, Set<DtInscriptionTouristTrip> inscriptionTourisTrip) {
		super(user);
		this.inscriptionTourisTrip = inscriptionTourisTrip;
	}
	
	public Set<DtInscriptionTouristTrip> getInscriptionTourisTrip() {
		return inscriptionTourisTrip;
	}
}