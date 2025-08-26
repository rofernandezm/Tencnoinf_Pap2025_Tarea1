package logic.entity;

import logic.dto.DtDurationAct;
import java.time.LocalDate;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.OneToMany;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "constraint_touristactivity_name", columnNames = "name") })
public class TouristActivity {

	@Id
	@Column(nullable = false, updatable = false, unique = true)
	private String activityName;
	private String description;
	private DtDurationAct duration;
	private float touristFee;
	private String city;
	private LocalDate dischargeDate;
	
	// Relation with one Supplier
	// We use an attribute with the name supplier as link with the parent
	@ManyToOne
	@JoinColumn(name = "supplier_name", nullable = false)
	private Supplier supplier;
	
	// Relation with one or many TouristOuting
	// We use a map collection with the name of the TouristOuting as key and the object its self as value
	@OneToMany(mappedBy = "parentActivity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, TouristOuting> touristOutings;
	
	public TouristActivity() {
	};
	
	public TouristActivity(String activityName, String description, DtDurationAct duration, float touristFee, String city, LocalDate dischargeDate) {
		this.activityName = activityName;
		this.description = description;
		this.duration = duration;
		this.touristFee = touristFee;
		this.city = city;
		this.dischargeDate = dischargeDate;
	};
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getDescription() {
		return activityName;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public DtDuration getDuration() {
		return duration;
	}

	public void setDuration(DtDuration duration) {
		this.duration = duration;
	}
	
	public float getTouristFee() {
		return touristFee;
	}

	public void setTouristFee(float touristFee) {
		this.touristFee = touristFee;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public LocalDate getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(LocalDate dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public Map<String, TouristOuting> getTouristOutings() {
		return touristOutings;
	}
	
	public void setTouristOutings(Map<String, TouristOuting> touristOutings){
		this.touristOutings = touristOutings;
		}
}