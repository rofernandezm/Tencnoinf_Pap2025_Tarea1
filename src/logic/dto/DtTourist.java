package logic.dto;

import java.time.LocalDate;

import logic.entity.Tourist;
import logic.entity.User;

public class DtTourist extends DtUser {

	private String nationality;

	public DtTourist() {
	}

	public DtTourist(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String nationality) {
		super(nickname, name, lastName, email, birthDate, UserType.TOURIST);
		this.nationality = nationality;
	}

	public String getNationality() {
		return nationality;
	}

	public User toEntity() {
		return new Tourist(this.getNickname(), this.getName(), this.getLastName(), this.getEmail(), this.getBirthDate(),
				this.getNationality());
	}
}
