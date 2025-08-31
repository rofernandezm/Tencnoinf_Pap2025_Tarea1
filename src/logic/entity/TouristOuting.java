package logic.entity;

import java.time.LocalDate;
import java.util.Map;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "constraint_Touristouting_name", columnNames = "outingName") })
public class TouristOuting {

	@Id
	@Column(nullable = false, updatable = false, unique = true)
	private String outingName;
	private int maxNumTourists;
	private String departurePoint;
	private LocalDate departureDate;
	private LocalDate dischargeDate;

	// Relation with one TouristActivity
	// We use an attribute with the name activity as link with the parent
	@ManyToOne
	@JoinColumn(name = "activityName", nullable = false)
	private TouristActivity activity;

	// Relation with one or many Tourist
	// We use a map collection with the name of the TouristOuting as key and the
	// object its self as value
	@OneToMany(mappedBy = "touristOuting", cascade = CascadeType.ALL, orphanRemoval = true)
	private Map<String, Inscription> inscriptions;

	public TouristOuting() {
	};

	public TouristOuting(String outingName, int maxNumTourists, String departurePoint, LocalDate departureDate,
			LocalDate dischargeDate) {
		this.outingName = outingName;
		this.maxNumTourists = maxNumTourists;
		this.departurePoint = departurePoint;
		this.departureDate = departureDate;
		this.dischargeDate = dischargeDate;
	};

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

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
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

	public Map<String, Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setTouristOutings(Map<String, Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}
}