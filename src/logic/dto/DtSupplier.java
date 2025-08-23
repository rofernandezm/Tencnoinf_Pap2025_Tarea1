package logic.dto;

import java.time.LocalDate;

public class DtSupplier extends DtUser {

	private String description;
	private WebSite webSite;
	
	public DtSupplier() {
	}

	public DtSupplier(String nickname, String name, String lastName, String email, LocalDate birthDate,
			UserType userType, String description, String webSite) {
		super(nickname, name, lastName, email, birthDate, userType);
		this.description = description;
		this.webSite = new WebSite(webSite);
	}

	public String getDescription() {
		return description;
	}

	public WebSite getWebSite() {
		return webSite;
	}
}
