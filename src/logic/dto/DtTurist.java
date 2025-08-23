package logic.dto;

import java.time.LocalDate;

public class DtTurist extends DtUser {

	private String nationality;

	public DtTurist() {
	}

	public DtTurist(String nickname, String name, String lastName, String email, LocalDate birthDate, UserType userType,
			String nationality) {
		super(nickname, name, lastName, email, birthDate, userType);
		this.nationality = nationality;
	}

	public String getNationality() {
		return nationality;
	}
}
