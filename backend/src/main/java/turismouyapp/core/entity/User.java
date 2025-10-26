package turismouyapp.core.entity;

import java.time.LocalDate;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import jakarta.persistence.*;
import turismouyapp.core.dto.DtUser;

@Cache(isolation = CacheIsolationType.ISOLATED)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users", uniqueConstraints = {
		@UniqueConstraint(name = "constraint_users_email", columnNames = "email") }, indexes = {
				@Index(name = "index_users_email", columnList = "email") })
public abstract class User {

	@Id
	@Column(name = "nickname", nullable = false, updatable = false, length = 30)
	private String nickname;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false, updatable = false)
	private String email;
	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "image")
	private String imagePath;

	public User() {
	};

	public User(String nickname, String name, String lastName, String email, LocalDate birthDate, String password) {
		this.nickname = nickname;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.password = password;
	}
	
	public User(String nickname, String name, String lastName, String email, LocalDate birthDate, String password, String image) {
		this.nickname = nickname;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.password = password;
		this.imagePath = image;
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

	public abstract DtUser createDtUser();
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String image) {
		this.imagePath = image;
	}
}
