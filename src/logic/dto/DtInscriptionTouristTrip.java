package logic.dto;

import java.time.LocalDate;

public class DtInscriptionTouristTrip {
	
	private int touristAmount;
	private float totalCost;
	private LocalDate inscriptionDate;
	private DtTouristTrip turistTrip;
	
	public DtInscriptionTouristTrip() {};
	
	public DtInscriptionTouristTrip( int touristAmount, float totalCost, LocalDate inscriptionDate, DtTouristTrip turistTrip) {
		this.touristAmount = touristAmount;
		this.totalCost = totalCost;
		this.inscriptionDate = inscriptionDate;
		this.turistTrip = turistTrip;
	}
	
	public int getTouristAmount() {
		return touristAmount;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public LocalDate getInscriptionDate() {
		return inscriptionDate;
	}

	public DtTouristTrip getTuristTrip() {
		return turistTrip;
	}	
	
}