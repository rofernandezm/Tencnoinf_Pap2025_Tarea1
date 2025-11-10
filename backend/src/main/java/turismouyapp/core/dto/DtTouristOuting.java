package turismouyapp.core.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import turismouyapp.core.dto.adapter.LocalDateAdapter;
import turismouyapp.core.dto.adapter.LocalDateTimeAdapter;

// JAXB: Salida de turista (outing), usar getters y adapters para fechas
@XmlRootElement(name = "DtTouristOuting")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "outingName", "maxNumTourists", "departurePoint", "departureDate", "dischargeDate", "activityName", "imageOutPath" })
public class DtTouristOuting {

	private String outingName;
	private int maxNumTourists;
	private String departurePoint;
	private LocalDateTime departureDate;
	private LocalDate dischargeDate;
	private String activityName;
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

	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
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

	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
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