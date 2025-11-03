package turismouyapp.core.dto;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

// JAXB: perfil de turista como elemento raíz; usar getters para serialización
@XmlRootElement(name = "DtTouristProfile")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DtTouristProfile")
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

	public void setInscriptionTourisOuting(List<DtInscriptionTouristOuting> inscriptionTourisOuting) {
		this.inscriptionTourisOuting = inscriptionTourisOuting;
	}
}