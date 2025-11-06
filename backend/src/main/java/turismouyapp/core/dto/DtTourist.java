package turismouyapp.core.dto;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

// JAXB: anotaciones para serializar DtTourist usando getters (PROPERTY)
@XmlRootElement(name = "DtTourist")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DtTourist", propOrder = { "nationality"})
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

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}
