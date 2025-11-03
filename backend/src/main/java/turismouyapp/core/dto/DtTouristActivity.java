package turismouyapp.core.dto;

import java.time.Duration;
import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import turismouyapp.core.dto.adapter.DurationAdapter;
import turismouyapp.core.dto.adapter.LocalDateAdapter;

// JAXB: representación de actividad turística, usar getters para serialización
@XmlRootElement(name = "DtTouristActivity")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "activityName", "description", "duration", "costTurist", "city", "registrationDate", "supplierNickname", "status", "imageActPath" })
public class DtTouristActivity {

	private String activityName;
	private String description;
	private Duration duration;
	private float costTurist;
	private String city;
	private LocalDate registratioDate;
	private String supplierNickname;
	private TouristActivityStatus status;
	private String imageActPath;
    
	public DtTouristActivity() {
	};

	public DtTouristActivity(String activityName, String description, Duration duration, float costTurist, String city,
			LocalDate registratioDate, String supplierNickname, TouristActivityStatus status, String imageActPath) {
		this.activityName = activityName;
		this.description = description;
		this.duration = duration;
		this.costTurist = costTurist;
		this.city = city;
		this.registratioDate = registratioDate;
		this.supplierNickname = supplierNickname;
		this.status = status;
		this.imageActPath = imageActPath;
	}

	public String getActivityName() {
		return activityName;
	}

	public String getDescription() {
		return description;
	}

	@XmlJavaTypeAdapter(DurationAdapter.class)
	public Duration getDuration() {
		return duration;
	}

	public float getCostTurist() {
		return costTurist;
	}

	public String getCity() {
		return city;
	}

	// JAXB: adapter para LocalDate
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getRegistrationDate() {
		return registratioDate;
	}

	public String getSupplierNickname() {
		return supplierNickname;
	}

	public TouristActivityStatus getStatus() {
		return status;
	}
    
	public String getImageActPath() {
		return imageActPath;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public void setCostTurist(float costTurist) {
		this.costTurist = costTurist;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registratioDate = registrationDate;
	}

	public void setSupplierNickname(String supplierNickname) {
		this.supplierNickname = supplierNickname;
	}

	public void setStatus(TouristActivityStatus status) {
		this.status = status;
	}

	public void setImageActPath(String imageActPath) {
		this.imageActPath = imageActPath;
	}
}
