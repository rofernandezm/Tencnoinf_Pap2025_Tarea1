package turismouyapp.core.dto;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

// JAXB: perfil de proveedor como elemento raíz; usar getters para serialización
@XmlRootElement(name = "DtSupplierProfile")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DtSupplierProfile")
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

	public void setActivities(List<DtActivityWithOutings> activities) {
		this.activities = activities;
	}
}