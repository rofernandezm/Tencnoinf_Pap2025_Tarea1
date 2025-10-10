package turismouyapp.core.dto;

import java.time.LocalDate;

public abstract class DtUser {

	private String nickname;
	private String name;
	private String lastName;
	private String email;
	private LocalDate birthDate;
	private UserType userType;
	private String password;

	public DtUser() {
	};

	public DtUser(String nickname, String name, String lastName, String email, LocalDate birthDate, UserType userType, String password) {
		this.nickname = nickname;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.userType = userType;
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public UserType getUserType() {
		return userType;
	}
	
	public String getPassword() {
		return password;
	}
}
