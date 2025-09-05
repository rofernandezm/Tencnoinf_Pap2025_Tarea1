package logic.dto;

import java.time.Duration;
import java.time.LocalDate;

import logic.entity.TouristActivity;

public abstract class DtTouristActivity {
	
	private String activityName;
	private String description;
	private Duration duration;
	private float costTurist;
	private String city;
	private LocalDate registratioDate;
	private String supplierNickname;
	
	public DtTouristActivity() {};
	
	public DtTouristActivity( String activityName, String description, Duration duration, float costTurist, String city, LocalDate registratioDate , String supplierNickname) {
		this.activityName = activityName;
		this.description = description;
		this.duration = duration;
		this.costTurist = costTurist;
		this.city = city;
		this.registratioDate = registratioDate;
		this.supplierNickname = supplierNickname;
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
	
	public LocalDate getRegistratioDate() {
		return registratioDate;
	}
	
	public String getSupplierNickname() {
		return supplierNickname;
	}
	
	public TouristActivity toEntity() {
		 Supplier supplier = SupplierHandler.getIntance().getSupplierByNickname(this.supplierNickname);
		    if (supplier == null) {
		        throw new IllegalArgumentException("Supplier no encontrado: " + this.supplierNickname);
		    }

		    TouristActivity ta = new TouristActivity(
		        this.activityName,
		        this.description,
		        this.duration,
		        this.costTurist,          // tu DTO lo llama costTurist
		        this.city,
		        this.registratioDate      // registratioDate en DTO
		    );

		    ta.setSupplier(supplier);

		    return ta;
	}
}
