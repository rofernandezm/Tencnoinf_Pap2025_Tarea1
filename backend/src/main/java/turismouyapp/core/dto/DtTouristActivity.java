package turismouyapp.core.dto;

import java.time.Duration;
import java.time.LocalDate;

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

	public Duration getDuration() {
		return duration;
	}

	public float getCostTurist() {
		return costTurist;
	}

	public String getCity() {
		return city;
	}

	public LocalDate getRegistrationDate() {
		return registratioDate;
	}

	public String getSupplierNickname() {
		return supplierNickname;
	}

	public TouristActivityStatus getStatus() {
		return status;
	}
	
	public String getimageActPath() {
		return imageActPath;
	}
}
