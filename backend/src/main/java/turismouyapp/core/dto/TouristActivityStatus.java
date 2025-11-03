package turismouyapp.core.dto;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "TouristActivityStatus")
@XmlEnum
public enum TouristActivityStatus {
	ADDED,
	CONFIRMED,
	REJECTED
}
