package turismouyapp.core.dto;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import turismouyapp.core.dto.adapter.LocalDateAdapter;

// JAXB: inscripción a una salida turística, usar getters y adapter para fecha
@XmlRootElement(name = "DtInscriptionTouristOuting")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "touristAmount", "totalCost", "inscriptionDate", "turistOuting" })
public class DtInscriptionTouristOuting {
    
	private int touristAmount;
	private float totalCost;
	private LocalDate inscriptionDate;
	private DtTouristOuting turistOuting;
    
	public DtInscriptionTouristOuting() {};
    
	public DtInscriptionTouristOuting( int touristAmount, float totalCost, LocalDate inscriptionDate, DtTouristOuting turistOuting) {
		this.touristAmount = touristAmount;
		this.totalCost = totalCost;
		this.inscriptionDate = inscriptionDate;
		this.turistOuting = turistOuting;
	}
    
	public int getTouristAmount() {
		return touristAmount;
	}

	public float getTotalCost() {
		return totalCost;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getInscriptionDate() {
		return inscriptionDate;
	}

	public DtTouristOuting getTuristOuting() {
		return turistOuting;
	}

	public void setTouristAmount(int touristAmount) {
		this.touristAmount = touristAmount;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public void setInscriptionDate(LocalDate inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}

	public void setTuristOuting(DtTouristOuting turistOuting) {
		this.turistOuting = turistOuting;
	}

}