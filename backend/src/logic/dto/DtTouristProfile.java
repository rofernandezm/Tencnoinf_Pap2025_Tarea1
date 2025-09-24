package logic.dto;

import java.util.ArrayList;
import java.util.List;

public class DtTouristProfile extends DtUserProfile {

	private List<DtInscriptionTouristOuting> inscriptionTourisOuting = new ArrayList<>();

	public DtTouristProfile() {
	}

	public DtTouristProfile(DtUser user, List<DtInscriptionTouristOuting> inscriptionTourisOuting) {
		super(user);
		this.inscriptionTourisOuting = inscriptionTourisOuting;
	}

	public List<DtInscriptionTouristOuting> getInscriptionTourisOuting() {
		return inscriptionTourisOuting;
	}
}