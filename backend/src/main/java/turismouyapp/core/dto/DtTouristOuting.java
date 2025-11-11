package turismouyapp.core.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import turismouyapp.core.dto.adapter.LocalDateAdapter;
import turismouyapp.core.dto.adapter.LocalDateTimeAdapter;

// JAXB: Salida de turista (outing), usar campos y adapters para fechas
@XmlRootElement(name = "DtTouristOuting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "outingName", "maxNumTourists", "departurePoint", "departureDate", "dischargeDate", "activityName", "imageOutPath" })
public class DtTouristOuting {

	@XmlElement(namespace = "")
	private String outingName;
	@XmlElement(namespace = "")
	private int maxNumTourists;
	@XmlElement(namespace = "")
	private String departurePoint;
	@XmlElement(namespace = "")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime departureDate;
	@XmlElement(namespace = "")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate dischargeDate;
	@XmlElement(namespace = "")
	private String activityName;
	@XmlElement(namespace = "")
	private String imageOutPath;

	public DtTouristOuting() {
	};

	public DtTouristOuting(String outingName, int maxNumTourists, String departurePoint, LocalDateTime departureDate,
			LocalDate dischargeDate, String activityName, String imageOutPath) {
		this.outingName = outingName;
		this.maxNumTourists = maxNumTourists;
		this.departurePoint = departurePoint;
		this.departureDate = departureDate;
		this.dischargeDate = dischargeDate;
		this.activityName = activityName;
		this.imageOutPath = imageOutPath;
	}

	public String getOutingName() {
		return outingName;
	}

	public int getMaxNumTourists() {
		return maxNumTourists;
	}

	public String getDeparturePoint() {
		return departurePoint;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public LocalDate getDischargeDate() {
		return dischargeDate;
	}

	public String getActivityName() {
		return activityName;
	}
    
	public String getImageOutPath() {
		return imageOutPath;
	}

	public void setOutingName(String outingName) {
		this.outingName = outingName;
	}

	public void setMaxNumTourists(int maxNumTourists) {
		this.maxNumTourists = maxNumTourists;
	}

	public void setDeparturePoint(String departurePoint) {
		this.departurePoint = departurePoint;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public void setDischargeDate(LocalDate dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setImageOutPath(String imageOutPath) {
		this.imageOutPath = imageOutPath;
	}
}