package turismouyapp.core.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;
import turismouyapp.core.dto.DtTourist;
import turismouyapp.core.dto.DtUser;
import turismouyapp.webservices.utils.DateUtils;

@Entity
@PrimaryKeyJoinColumn(name = "nickname")
public class Tourist extends User {

	@Column(updatable = true)
	private String nationality;

	@OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL, orphanRemoval = true)
	private Map<String, Inscription> outingInscriptions;

	public Tourist() {
		super();
		this.outingInscriptions = new HashMap<String, Inscription>();
	}

	public Tourist(String nickname, String name, String lastName, String email, LocalDate birthDate,
			String password, String nationality) {
		super(nickname, name, lastName, email, birthDate, password);
		this.nationality = nationality;
	}

	public Tourist(DtTourist dtTourist) {
		super(dtTourist.getNickname(), dtTourist.getName(), dtTourist.getLastName(), dtTourist.getEmail(),
				DateUtils.parseToLocalDate(dtTourist.getBirthDate()), dtTourist.getPassword(), dtTourist.getImagePath());
		this.nationality = dtTourist.getNationality();
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public DtUser createDtUser() {

		DtUser dt = new DtTourist(this.getNickname(), this.getName(), this.getLastName(), this.getEmail(),
				this.getBirthDate(),this.getPassword(), this.getNationality(), this.getImagePath());

		return dt;
	}

	public Map<String, Inscription> getOutingInscriptions() {
		return outingInscriptions;
	}

	public void setOutingInscriptions(Map<String, Inscription> outingInscriptions) {
		this.outingInscriptions = outingInscriptions;
	}
}
