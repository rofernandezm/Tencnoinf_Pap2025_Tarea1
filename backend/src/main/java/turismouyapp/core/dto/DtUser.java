
package turismouyapp.core.dto;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import turismouyapp.core.dto.adapter.LocalDateAdapter;

// JAXB: anotaciones para permitir serialización JAXB usando getters (PROPERTY)
@XmlRootElement(name = "DtUser")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DtUser", propOrder = { "nickname", "name", "lastName", "email", "birthDate", "userType", "password", "imagePath" })
@XmlSeeAlso({DtTourist.class, DtSupplier.class})
public abstract class DtUser {

	private String nickname;
	private String name;
	private String lastName;
	private String email;
	private LocalDate birthDate;
	private UserType userType;
	private String password;
	private String imagePath;

	public DtUser() {
	};

	public DtUser(String nickname, String name, String lastName, String email, LocalDate birthDate, UserType userType, String password, String image) {
		this.nickname = nickname;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.userType = userType;
		this.password = password;
		this.imagePath = image;
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

	// JAXB: usar adapter para LocalDate -> yyyy-MM-dd
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public UserType getUserType() {
		return userType;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	// Setters required for @XmlAccessorType(PROPERTY)
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
