package logic.dto;

import java.time.LocalDate;

public class DtSupplier extends DtUser {

	private String description;
	private String webSite;

	public DtSupplier() {
	}

	public DtSupplier(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String description, String webSite) {
		super(nickname, name, lastName, email, birthDate, UserType.SUPPLIER);
		this.description = description;
		this.webSite = webSite != null ? webSite : null;
	}

	public String getDescription() {
		return description;
	}

	public String getWebSite() {
		return webSite;
	}
}
