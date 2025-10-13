package turismouyapp.core.dto;

import java.time.LocalDate;

public class DtTourist extends DtUser {

	private String nationality;

	public DtTourist() {
	}

	public DtTourist(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String password, String nationality, String image) {
		super(nickname, name, lastName, email, birthDate, UserType.TOURIST, password, image);
		this.nationality = nationality;
	}

	public String getNationality() {
		return nationality;
	}
}
