package logic.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DtTouristTrip {
	
	private String tripName;
	private int maxTourist;
	private String exitPoint;
	private LocalDateTime dateTime;
	private LocalDate registrationDate;
	
	public DtTouristTrip() {};
	
	public DtTouristTrip(String tripName, int maxTourist, String exitPoint, LocalDateTime dateTime, LocalDate registrationDate) {
		this.tripName = tripName;
		this.maxTourist = maxTourist;
		this.exitPoint = exitPoint;
		this.dateTime = dateTime;
		this.registrationDate = registrationDate;
	}
	
	public String getTipName() {
		return tripName;
	}

	public int getMaxTourist() {
		return maxTourist;
	}

	public String getExitPoint() {
		return exitPoint;
	}

	public LocalDateTime getBirthDate() {
		return dateTime;
	}

	public LocalDate getUserType() {
		return registrationDate;
	}
}