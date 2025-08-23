package logic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = { @UniqueConstraint(name = "constraint_users_email", columnNames = "email") })
public abstract class User {

	@Id
	@Column(nullable = false, updatable = false)
	private String nickname;
	private String name;
	private String lastName;
	@Column(nullable = false, updatable = false, unique = true)
	private String email;
	private LocalDate birthDate;

	public User() {
	};

	public User(String nickname, String name, String lastName, String email, LocalDate birthDate) {
		this.nickname = nickname;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	};

}
