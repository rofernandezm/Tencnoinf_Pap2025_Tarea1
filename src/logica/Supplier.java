package logica;

import java.net.URI;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
			String description, URI webSite) {
		super(nickname, name, lastName, email, birthDate);
		this.description = description;
		this.webSite = new WebSite(webSite);
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

	public void setWebSite(URI webSite) {
		this.webSite = new WebSite(webSite);
	}
}
