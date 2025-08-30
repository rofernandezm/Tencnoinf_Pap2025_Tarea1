package logic.dto;

import java.time.LocalDate;

public class DtInscriptionTouristOuting {
	
	private int touristAmount;
	private float totalCost;
	private LocalDate inscriptionDate;
	private DtTouristOuting turistOuting;
	
	public DtInscriptionTouristOuting() {};
	
	public DtInscriptionTouristOuting( int touristAmount, float totalCost, LocalDate inscriptionDate, DtTouristOuting turistOuting) {
		this.touristAmount = touristAmount;
		this.totalCost = totalCost;
		this.inscriptionDate = inscriptionDate;
		this.turistOuting = turistOuting;
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

	public DtTouristOuting getTuristOuting() {
		return turistOuting;
	}	
	
}