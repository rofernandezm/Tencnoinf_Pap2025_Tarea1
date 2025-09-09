package logic.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import logic.dto.DtInscriptionTouristOuting;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uc_tourist_outdating", columnNames = { "tourist_nickname",
		"touristOuting_name" }), indexes = { @Index(name = "index_insc_outdating", columnList = "touristOuting_name") })
public class Inscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int numTourists;
	private float totalRegistrationCost;
	private LocalDate inscriptionDate;

	// Relation with one TouristOuting
	// We use an attribute with the name touristOuting as link with the parent
	@ManyToOne
	@JoinColumn(name = "touristOuting_name", referencedColumnName = "outingName", nullable = false)
	private TouristOuting touristOuting;

	// Relation with one Tourist
	// We use a map collection with the name of the TouristOuting as key and the
	// object its self as value
	@ManyToOne
	@JoinColumn(name = "tourist_nickname", nullable = false)
	private Tourist tourist;

	public Inscription() {
	};

	public Inscription(int numTourists, float totalRegistrationCost, LocalDate inscriptionDate) {
		this.numTourists = numTourists;
		this.totalRegistrationCost = totalRegistrationCost;
		this.inscriptionDate = inscriptionDate;
	};

	public Inscription(DtInscriptionTouristOuting dtInscriptionTouristOuting) {

		this.numTourists = dtInscriptionTouristOuting.getTouristAmount();
		this.totalRegistrationCost = dtInscriptionTouristOuting.getTotalCost();
		this.inscriptionDate = dtInscriptionTouristOuting.getInscriptionDate();

	}

	public int getNumTourists() {
		return numTourists;
	}

	public void setNumTourists(int numTourists) {
		this.numTourists = numTourists;
	}

	public float getTotalRegistrationCost() {
		return totalRegistrationCost;
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

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}
	
	public DtInscriptionTouristOuting getDtInscriptionTouristOuting() {
		return new DtInscriptionTouristOuting(this.numTourists, this.totalRegistrationCost, this.inscriptionDate, touristOuting.getDtTouristOuting());
	}
}
