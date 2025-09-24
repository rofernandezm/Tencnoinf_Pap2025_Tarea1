package logic.entity;

import java.time.LocalDate;
import java.util.Map;

import jakarta.persistence.*;
import logic.dto.DtSupplier;
import logic.dto.DtUser;

@Entity
@PrimaryKeyJoinColumn(name = "nickname")
public class Supplier extends User {

	@Column(updatable = false)
	private String description;
	@Column(updatable = false)
	private String webSite;

	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = false)
	private Map<String, TouristActivity> activities;

	public Supplier() {
		super();
	}

	public Supplier(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String description, String webSite) {
		super(nickname, name, lastName, email, birthDate);
		this.description = description;
		this.webSite = webSite;
	}

	public Supplier(DtSupplier dtSupplier) {
		super(dtSupplier.getNickname(), dtSupplier.getName(), dtSupplier.getLastName(), dtSupplier.getEmail(),
				dtSupplier.getBirthDate());
		this.description = dtSupplier.getDescription();
		this.webSite = dtSupplier.getWebSite();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Map<String, TouristActivity> getActivities() {
		return activities;
	}

	public void setActivities(Map<String, TouristActivity> activities) {
		this.activities = activities;
	}

	public DtUser createDtUser() {

		DtUser dt = new DtSupplier(this.getNickname(), this.getName(), this.getLastName(), this.getEmail(),
				this.getBirthDate(), this.getDescription(), this.getWebSite());

		return dt;
	}
}
