package logic.entity;

import java.time.LocalDate;
import java.util.Map;

import jakarta.persistence.*;
import logic.dto.DtSupplier;
import logic.dto.DtSupplierProfile;
import logic.dto.DtUser;
import logic.dto.DtUserProfile;
import logic.dto.UserType;
import logic.dto.WebSite;

@Entity
@PrimaryKeyJoinColumn(name = "nickname") 
public class Supplier extends User {

	@Column(updatable = false)
	private String description;
	@Column(updatable = false)
	private WebSite webSite;
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = false)
	private Map<String, TouristActivity> activities;

	public Supplier() {
		super();
	}

	public Supplier(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String description, WebSite webSite) {
		super(nickname, name, lastName, email, birthDate);
		this.description = description;
		this.webSite = webSite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WebSite getWebSite() {
		return webSite;
	}

	public void setWebSite(WebSite webSite) {
		this.webSite = webSite;
	}

	public Map<String, TouristActivity> getActivities() {
		return activities;
	}

	public void setActivities(Map<String, TouristActivity> activities) {
		this.activities = activities;
	}

	public String convertWebSiteToString() {
		return webSite.getUrl().toString();
	}

	public DtUser createDtUser() {

		DtUser dt = new DtSupplier(this.getNickname(), this.getName(), this.getLastName(), this.getEmail(),
				this.getBirthDate(), this.getDescription(), this.convertWebSiteToString());

		return dt;
	}

	public DtUserProfile createDtUserProfile() {
		return new DtSupplierProfile();
	}
}
