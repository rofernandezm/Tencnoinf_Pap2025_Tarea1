package logic.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import logic.entity.Tourist;
import logic.entity.TouristOuting;
import logic.entity.User;

public class DtTouristOuting {
	
	private String outingName;
	private int maxNumTourists;
	private String departurePoint;
	private LocalDateTime departureDate;
	private LocalDate dischargeDate;
	
	public DtTouristOuting() {};
	
	public DtTouristOuting(String outingName, int maxNumTourists, String departurePoint, LocalDateTime departureDate, LocalDate dischargeDate) {
		this.outingName = outingName;
		this.maxNumTourists = maxNumTourists;
		this.departurePoint = departurePoint;
		this.departureDate = departureDate;
		this.dischargeDate = dischargeDate;
	}
	
	public String getTipName() {
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
	
	public TouristOuting toEntity() {
		return new TouristOuting(this.getTipName(), this.getMaxNumTourists(), this.getDeparturePoint(), this.getDepartureDate(), this.getDischargeDate());
	}
}