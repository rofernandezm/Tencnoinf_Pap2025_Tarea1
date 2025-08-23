package logic.entity;

import java.time.LocalDate;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistance.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Inscription {
	
	private int numTourists;
	private float totalRegistrationCost;
	private LocalDate inscriptionDate;
	
	// Relation with one TouristOuting
	// We use an attribute with the name touristOuting as link with the parent
	@ManyToOne
    @JoinColumn(name = "touristOuting_name", nullable = false)
    private TouristOuting touristOuting;
	
	// Relation with one Turist
	// We use a map collection with the name of the TouristOuting as key and the object its self as value
	@ManyToOne
    @JoinColumn(name = "turist_nickname", nullable = false)
    private Turist turist;
    
	public Inscription() {
	};
	
	public Inscription(int numTourists, float totalRegistrationCost, LocalDate inscriptionDate) {
		this.numTourists = numTourists;
		this.totalRegistrationCost = totalRegistrationCost;
		this.inscriptionDate = inscriptionDate;
	};
	
	public int getNumTourists() {
		return numTourists;
	}

	public void setNumTourists(int numTourists) {
		this.numTourists = numTourists;
	}
	
	public int getTotalRegistrationCost() {
		return numTourists;
	}

	public void setTotalRegistrationCost(float totalRegistrationCost) {
		this.totalRegistrationCost = totalRegistrationCost;
	}
	
	public LocalDate getInscriptionDate() {
		return inscriptionDate;
	}

	public void setInscriptionDate(LocalDate inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}
	
	public TouristOuting getTouristOuting() {
		return touristOuting;
	}

	public void setTouristOuting(TouristOuting touristOuting) {
		this.touristOuting = touristOuting;
	}
	
	public Turist getTurist() {
		return turist;
	}

	public void setTurist(Turist turist) {
		this.turist = turist;
	}
	
}
