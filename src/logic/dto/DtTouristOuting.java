package logic.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import logic.entity.TouristActivity;
import logic.entity.TouristOuting;
import logic.handler.TouristActivityHandler;
import logic.entity.Tourist;
import logic.entity.User;

public class DtTouristOuting {
	
	private String outingName;
	private int maxNumTourists;
	private String departurePoint;
	private LocalDateTime departureDate;
	private LocalDate dischargeDate;
	private String activityName;
	
	public DtTouristOuting() {};
	
	public DtTouristOuting(String outingName, int maxNumTourists, String departurePoint, LocalDateTime departureDate, LocalDate dischargeDate, String activityName) {
		this.outingName = outingName;
		this.maxNumTourists = maxNumTourists;
		this.departurePoint = departurePoint;
		this.departureDate = departureDate;
		this.dischargeDate = dischargeDate;
		this.activityName = activityName;
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
	public TouristOuting toEntity() {
		TouristActivity activity= TouristActivityHandler.getIntance().getTouristActivityByName(this.activityName);
	    if (activity == null) {
	        throw new IllegalArgumentException("Actividad no encontrada: " + this.activityName);
	    }
		TouristOuting to = new TouristOuting(
	        this.outingName,
	        this.maxNumTourists,
	        this.departurePoint,
	        this.departureDate,
	        this.dischargeDate
	    );

	    to.setActivity(activity); 

	    return to;
	
//	public TouristOuting toEntity() {
//		return new TouristOuting(this.getTipName(), this.getMaxNumTourists(), this.getDeparturePoint(), this.getDepartureDate(), this.getDischargeDate());
	}
}