package turismouyapp.core.dto;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

// JAXB: actividad con sus salidas, usar getters para serialización
@XmlRootElement(name = "DtActivityWithOutings")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "activity", "outings" })
public class DtActivityWithOutings {

	private DtTouristActivity activity;
	private List<DtTouristOuting> outings = new ArrayList<>();

	public DtActivityWithOutings() {
	}

	public DtActivityWithOutings(DtTouristActivity activity, List<DtTouristOuting> outings) {
		this.activity = activity;
		this.outings = outings;
	}

	public DtTouristActivity getActivity() {
		return activity;
	}

	// JAXB: envolver la lista en un elemento 'outings' con elementos 'outing'
	@XmlElementWrapper(name = "outings")
	@XmlElement(name = "outing")
	public List<DtTouristOuting> getOutings() {
		return outings;
	}

	public void setActivity(DtTouristActivity activity) {
		this.activity = activity;
	}

	public void setOutings(List<DtTouristOuting> outings) {
		this.outings = outings;
	}

}