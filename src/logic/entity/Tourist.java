package logic.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;
import logic.dto.DtTourist;
import logic.dto.DtTouristProfile;
import logic.dto.DtUser;
import logic.dto.DtUserProfile;
import logic.dto.UserType;

@Entity
@PrimaryKeyJoinColumn(name = "nickname")
public class Tourist extends User {

	@Column(updatable = false)
	private String nationality;
	
	@OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL, orphanRemoval = true)
	private Map<String, Inscription> outingInscriptions;

	public Tourist() {
		super();
		this.outingInscriptions = new HashMap<String, Inscription>();
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

	public DtUser createDtUser() {

		DtUser dt = new DtTourist(this.getNickname(), this.getName(), this.getLastName(), this.getEmail(),
				this.getBirthDate(), UserType.TOURIST, this.getNationality());

		return dt;
	}

	public DtUserProfile createDtUserProfile() {
		return new DtTouristProfile();
	}

	public Map<String, Inscription> getOutingInscriptions() {
		return outingInscriptions;
	}

	public void setOutingInscriptions(Map<String, Inscription> outingInscriptions) {
		this.outingInscriptions = outingInscriptions;
	}
}
