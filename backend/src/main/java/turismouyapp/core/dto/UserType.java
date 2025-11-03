package turismouyapp.core.dto;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "UserType")
@XmlEnum
public enum UserType {
	SUPPLIER,
	TOURIST,
	GUEST
}
