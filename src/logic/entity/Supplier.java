package logic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import logic.dto.WebSite;

@Entity
public class Supplier extends User {

	@Column(updatable = false)
	private String description;
	@Column(updatable = false)
	private WebSite webSite;

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
}
