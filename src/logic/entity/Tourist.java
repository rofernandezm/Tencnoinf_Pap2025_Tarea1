package logic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Tourist extends User {

	@Column(updatable = false)
	private String nationality;

	public Tourist() {
		super();
	}

	public Tourist(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String nationality) {
		super(nickname, name, lastName, email, birthDate);
		this.nationality = nationality;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}
