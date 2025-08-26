package logic.dto;

import java.time.LocalDate;

public class DtTourist extends DtUser {

	private String nationality;

	public DtTourist() {
	}

	public DtTourist(String nickname, String name, String lastName, String email, LocalDate birthDate, UserType userType,
			String nationality) {
		super(nickname, name, lastName, email, birthDate, userType);
		this.nationality = nationality;
	}

	public String getNationality() {
		return nationality;
	}
}
