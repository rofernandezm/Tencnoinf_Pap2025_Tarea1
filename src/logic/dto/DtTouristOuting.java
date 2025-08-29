package logic.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DtTouristOuting {
	
	private String outingName;
	private int maxTourist;
	private String exitPoint;
	private LocalDateTime dateTime;
	private LocalDate registrationDate;
	
	public DtTouristOuting() {};
	
	public DtTouristOuting(String outingName, int maxTourist, String exitPoint, LocalDateTime dateTime, LocalDate registrationDate) {
		this.outingName = outingName;
		this.maxTourist = maxTourist;
		this.exitPoint = exitPoint;
		this.dateTime = dateTime;
		this.registrationDate = registrationDate;
	}
	
	public String getTipName() {
		return outingName;
	}

	public int getMaxTourist() {
		return maxTourist;
	}

	public String getExitPoint() {
		return exitPoint;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
}