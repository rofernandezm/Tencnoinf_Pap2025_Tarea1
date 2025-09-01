package logic.dto;

import java.time.LocalDate;

import logic.entity.Supplier;
import logic.entity.User;

public class DtSupplier extends DtUser {

	private String description;
	private WebSite webSite;

	public DtSupplier() {
	}

	public DtSupplier(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String description, String webSite) {
		super(nickname, name, lastName, email, birthDate, UserType.SUPPLIER);
		this.description = description;
		this.webSite = webSite != null ? new WebSite(webSite) : null;
	}

	public String getDescription() {
		return description;
	}

	public WebSite getWebSite() {
		return webSite;
	}

	public User toEntity() {
		return new Supplier(this.getNickname(), this.getName(), this.getLastName(), this.getEmail(),
				this.getBirthDate(), this.getDescription(), this.getWebSite());
	}
}
