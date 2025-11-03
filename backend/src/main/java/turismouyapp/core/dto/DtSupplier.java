package turismouyapp.core.dto;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

// JAXB: anotaciones para serializar DtSupplier usando getters (PROPERTY)
@XmlRootElement(name = "DtSupplier")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DtSupplier")
public class DtSupplier extends DtUser {

	private String description;
	private String webSite;

	public DtSupplier() {
	}

	public DtSupplier(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String password, String description, String webSite, String image) {
		super(nickname, name, lastName, email, birthDate, UserType.SUPPLIER, password, image);
		this.description = description;
		this.webSite = webSite != null ? webSite : null;
	}

	public String getDescription() {
		return description;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
}
