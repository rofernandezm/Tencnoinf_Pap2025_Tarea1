package logic.dto;

import java.time.Duration;

public class DtTouristActivity {
	
	private String activityName;
	private String description;
	private Duration duration;
	private float costTurist;
	private String city;
	
	public pubpubliclic() {};
	
	public DtTouristTrip(String tripName, int maxTourist, String exitPoint, LocalDateTime dateTime, LocalDate registrationDate) {
		this.tripName = tripName;
		this.maxTourist = maxTourist;
		this.exitPoint = exitPoint;
		this.dateTime = dateTime;
		this.registrationDate = registrationDate;
	}
