package turismouyapp.core.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import jakarta.persistence.*;
import turismouyapp.core.dto.DtTouristOuting;

@Entity
public class TouristOuting {

	@Id
	@Column(nullable = false, updatable = false, unique = true)
	private String outingName;
	private int maxNumTourists;
	private String departurePoint;
	private LocalDateTime departureDate;
	private LocalDate dischargeDate;
	private String imageOutPath;

	// Relation with one TouristActivity
	// We use an attribute with the name activity as link with the parent
	@ManyToOne
	@JoinColumn(name = "activityName", nullable = false)
	private TouristActivity activity;

	// Relation with one or many Tourist
	// We use a map collection with the name of the TouristOuting as key and the
	// object its self as value
	@OneToMany(mappedBy = "touristOuting", cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKey(name = "id") // campo de Inscription que será la clave
	private Map<Long, Inscription> inscriptions;

	public TouristOuting() {
	};

	public TouristOuting(String outingName, int maxNumTourists, String departurePoint, LocalDateTime departureDate,
			LocalDate dischargeDate, String imageOutPath) {
		this.outingName = outingName;
		this.maxNumTourists = maxNumTourists;
		this.departurePoint = departurePoint;
		this.departureDate = departureDate;
		this.dischargeDate = dischargeDate;
		this.imageOutPath = imageOutPath; 
	};

	public TouristOuting(DtTouristOuting dt) {
		this.outingName = dt.getOutingName();
		this.maxNumTourists = dt.getMaxNumTourists();
		this.departurePoint = dt.getDeparturePoint();
		this.departureDate = dt.getDepartureDate();
		this.dischargeDate = dt.getDischargeDate();
		this.imageOutPath = dt.getImageOutPath();
	}

	public String getOutingName() {
		return outingName;
	}

	public void setOutingName(String outingName) {
		this.outingName = outingName;
	}

	public int getMaxNumTourists() {
		return maxNumTourists;
	}

	public void setMaxNumTourists(int maxNumTourists) {
		this.maxNumTourists = maxNumTourists;
	}

	public String getDeparturePoint() {
		return departurePoint;
	}

	public void setDeparturePoint(String departurePoint) {
		this.departurePoint = departurePoint;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(LocalDate dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public TouristActivity getActivity() {
		return activity;
	}

	public void setActivity(TouristActivity activity) {
		this.activity = activity;
	}

	public Map<Long, Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Map<Long, Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public DtTouristOuting getDtTouristOuting() {
		return new DtTouristOuting(this.outingName, this.maxNumTourists, this.departurePoint, this.departureDate,
				this.dischargeDate, this.activity.getActivityName(), this.getImageOutPath());
	}
	
	public String getImageOutPath() {
		return imageOutPath;
	}

	public void setImageOutPath(String image) {
		this.imageOutPath = image;
	}
}