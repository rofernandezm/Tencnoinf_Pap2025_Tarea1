package turismouyapp.core.dto;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlSeeAlso;

// JAXB: usar accessors (getters) para serialización y controlar orden de elementos
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "user" })
@XmlSeeAlso({DtSupplierProfile.class, DtTouristProfile.class})
public abstract class DtUserProfile {

	private DtUser user;

	public DtUserProfile() {
	};

	public DtUserProfile(DtUser user) {
		this.user = user;
	}

	public DtUser getUser() {
		return user;
	}

	public void setUser(DtUser user) {
		this.user = user;
	}
}