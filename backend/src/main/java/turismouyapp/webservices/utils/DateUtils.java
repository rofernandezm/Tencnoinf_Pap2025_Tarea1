package turismouyapp.webservices.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilidades para conversión de fechas en el contexto de Web Services.
 * Maneja la conversión entre String (formato SOAP/XML) y LocalDate/LocalDateTime (entidades JPA).
 */
public class DateUtils {

	private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
	private static final DateTimeFormatter ISO_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	/**
	 * Convierte un objeto fecha (String o LocalDate) a LocalDate.
	 * Útil para parsear fechas recibidas desde Web Services donde pueden venir como String.
	 * 
	 * @param dateValue El valor de la fecha (puede ser String en formato ISO o LocalDate)
	 * @return LocalDate parseado, o null si el valor es null o vacío
	 * @throws IllegalArgumentException si el tipo no es soportado o el formato es inválido
	 */
	public static LocalDate parseToLocalDate(Object dateValue) {
		if (dateValue == null) {
			return null;
		}
		
		if (dateValue instanceof LocalDate) {
			return (LocalDate) dateValue;
		}
		
		if (dateValue instanceof String) {
			String dateStr = ((String) dateValue).trim();
			if (dateStr.isEmpty()) {
				return null;
			}
			try {
				return LocalDate.parse(dateStr, ISO_DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(
					"Formato de fecha inválido: '" + dateStr + "'. Se espera formato ISO (yyyy-MM-dd)", e);
			}
		}
		
		throw new IllegalArgumentException(
			"Tipo de fecha no soportado: " + dateValue.getClass().getName() + 
			". Se esperaba String o LocalDate");
	}
	
	/**
	 * Convierte LocalDate a String en formato ISO.
	 * 
	 * @param date La fecha a convertir
	 * @return String en formato yyyy-MM-dd, o null si date es null
	 */
	public static String formatToString(LocalDate date) {
		return date != null ? date.format(ISO_DATE_FORMATTER) : null;
	}
	
	/**
	 * Convierte un objeto fecha-hora (String o LocalDateTime) a LocalDateTime.
	 * Útil para parsear fechas-horas recibidas desde Web Services donde pueden venir como String.
	 * 
	 * @param dateTimeValue El valor de la fecha-hora (puede ser String en formato ISO o LocalDateTime)
	 * @return LocalDateTime parseado, o null si el valor es null o vacío
	 * @throws IllegalArgumentException si el tipo no es soportado o el formato es inválido
	 */
	public static LocalDateTime parseToLocalDateTime(Object dateTimeValue) {
		if (dateTimeValue == null) {
			return null;
		}
		
		if (dateTimeValue instanceof LocalDateTime) {
			return (LocalDateTime) dateTimeValue;
		}
		
		if (dateTimeValue instanceof String) {
			String dateTimeStr = ((String) dateTimeValue).trim();
			if (dateTimeStr.isEmpty()) {
				return null;
			}
			try {
				return LocalDateTime.parse(dateTimeStr, ISO_DATETIME_FORMATTER);
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(
					"Formato de fecha-hora inválido: '" + dateTimeStr + "'. Se espera formato ISO (yyyy-MM-dd'T'HH:mm:ss)", e);
			}
		}
		
		throw new IllegalArgumentException(
			"Tipo de fecha-hora no soportado: " + dateTimeValue.getClass().getName() + 
			". Se esperaba String o LocalDateTime");
	}
	
	/**
	 * Convierte LocalDateTime a String en formato ISO.
	 * 
	 * @param dateTime La fecha-hora a convertir
	 * @return String en formato yyyy-MM-dd'T'HH:mm:ss, o null si dateTime es null
	 */
	public static String formatToString(LocalDateTime dateTime) {
		return dateTime != null ? dateTime.format(ISO_DATETIME_FORMATTER) : null;
	}

	public static DateTimeFormatter getIsoDateFormatter() {
		return ISO_DATE_FORMATTER;
	}

	public static DateTimeFormatter getIsoDatetimeFormatter() {
		return ISO_DATETIME_FORMATTER;
	}
}
