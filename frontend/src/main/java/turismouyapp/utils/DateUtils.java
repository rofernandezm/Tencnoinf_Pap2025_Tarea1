package turismouyapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Utilidades para conversión de fechas en el contexto de Web Services.
 * Maneja la conversión entre String (formato SOAP/XML) y LocalDate/LocalDateTime (entidades JPA).
 */
public class DateUtils {

	private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
	private static final DateTimeFormatter ISO_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private static final SimpleDateFormat DATE_LOCAL_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final SimpleDateFormat DATE_LOCAL = new SimpleDateFormat("dd/MM/yyyy");

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

	/**
	 * Convierte un String en formato ISO 8601 Duration (ej: "PT2H", "PT1H30M") a un string formateado de horas.
	 * 
	 * @param durationStr El string de duración en formato ISO 8601 (ej: "PT2H")
	 * @return String formateado con las horas (ej: "2.0 horas", "1.5 horas"), o null si el string es null o vacío
	 * @throws IllegalArgumentException si el formato de duración es inválido
	 */
	public static String parseDurationToHoursString(String durationStr) {
		if (durationStr == null || durationStr.trim().isEmpty()) {
			return null;
		}

		try {
			Duration duration = Duration.parse(durationStr.trim());
			double hours = duration.toMinutes() / 60.0;
			
			// Si es un número entero, no mostrar decimales
			if (hours == (int) hours) {
				return String.format("%d horas", (int) hours);
			}
			return String.format("%.1f horas", hours);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(
				"Formato de duración inválido: '" + durationStr + "'. Se espera formato ISO 8601 (ej: PT2H, PT1H30M)", e);
		}
	}

	/**
	 * Convierte un String en formato ISO LocalDateTime a String formateado.
	 * Ejemplo: "2025-11-10T22:27:00" -> "10/11/2025 22:27"
	 *
	 * @param dateTimeStr El string de fecha-hora en formato ISO (yyyy-MM-dd'T'HH:mm:ss)
	 * @return String formateado (dd/MM/yyyy HH:mm), o null si el string es null o vacío
	 * @throws IllegalArgumentException si el formato es inválido
	 */
	public static String parseIsoStringToFormattedDateTime(String dateTimeStr) {
		if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
			return null;
		}

		try {
			LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr.trim(), ISO_DATETIME_FORMATTER);
			Date date = java.sql.Timestamp.valueOf(localDateTime);
			return DATE_LOCAL_TIME.format(date);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(
				"Formato de fecha-hora inválido: '" + dateTimeStr + "'. Se espera formato ISO (yyyy-MM-dd'T'HH:mm:ss)", e);
		}
	}

	/**
	 * Convierte un String en formato ISO Date a String formateado.
	 * Ejemplo: "2025-11-10" -> "10/11/2025"
	 *
	 * @param dateStr El string de fecha en formato ISO (yyyy-MM-dd)
	 * @return String formateado (dd/MM/yyyy), o null si el string es null o vacío
	 * @throws IllegalArgumentException si el formato es inválido
	 */
	public static String parseIsoStringToFormattedDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}

		try {
			LocalDate localDate = LocalDate.parse(dateStr.trim(), ISO_DATE_FORMATTER);
			Date date = java.sql.Date.valueOf(localDate);
			return DATE_LOCAL.format(date);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(
				"Formato de fecha inválido: '" + dateStr + "'. Se espera formato ISO (yyyy-MM-dd)", e);
		}
	}

	/**
	 * Obtiene la fecha actual como String en formato ISO.
	 * Ejemplo: "2025-11-10"
	 *
	 * @return String con la fecha actual en formato yyyy-MM-dd
	 */
	public static String getCurrentDateIso() {
		return LocalDate.now().format(ISO_DATE_FORMATTER);
	}

	/**
	 * Convierte un String en formato ISO a LocalDate.
	 * Método wrapper para parseToLocalDate que acepta solo String.
	 * Ejemplo: "2025-11-10" -> LocalDate
	 *
	 * @param dateStr El string de fecha en formato ISO (yyyy-MM-dd)
	 * @return LocalDate parseado, o null si el string es null o vacío
	 * @throws IllegalArgumentException si el formato es inválido
	 */
	public static LocalDate parseIsoStringToLocalDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}

		try {
			return LocalDate.parse(dateStr.trim(), ISO_DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(
				"Formato de fecha inválido: '" + dateStr + "'. Se espera formato ISO (yyyy-MM-dd)", e);
		}
	}

	/**
	 * Formatea un Date a String en formato dd/MM/yyyy HH:mm.
	 *
	 * @param date La fecha a formatear
	 * @return String formateado, o null si date es null
	 */
	public static String formatDateLocalTime(Date date) {
		return date != null ? DATE_LOCAL_TIME.format(date) : null;
	}

	/**
	 * Formatea un Date a String en formato dd/MM/yyyy.
	 *
	 * @param date La fecha a formatear
	 * @return String formateado, o null si date es null
	 */
	public static String formatDateLocal(Date date) {
		return date != null ? DATE_LOCAL.format(date) : null;
	}

	/**
	 * Parsea un String en formato dd/MM/yyyy HH:mm a Date.
	 *
	 * @param dateStr El string de fecha a parsear
	 * @return Date parseado, o null si el string es null o vacío
	 * @throws IllegalArgumentException si el formato es inválido
	 */
	public static Date parseDateLocalTime(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}

		try {
			return DATE_LOCAL_TIME.parse(dateStr.trim());
		} catch (ParseException e) {
			throw new IllegalArgumentException(
				"Formato de fecha inválido: '" + dateStr + "'. Se espera formato dd/MM/yyyy HH:mm", e);
		}
	}

	/**
	 * Parsea un String en formato dd/MM/yyyy a Date.
	 *
	 * @param dateStr El string de fecha a parsear
	 * @return Date parseado, o null si el string es null o vacío
	 * @throws IllegalArgumentException si el formato es inválido
	 */
	public static Date parseDateLocal(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}

		try {
			return DATE_LOCAL.parse(dateStr.trim());
		} catch (ParseException e) {
			throw new IllegalArgumentException(
				"Formato de fecha inválido: '" + dateStr + "'. Se espera formato dd/MM/yyyy", e);
		}
	}

	public static DateTimeFormatter getIsoDateFormatter() {
		return ISO_DATE_FORMATTER;
	}

	public static DateTimeFormatter getIsoDatetimeFormatter() {
		return ISO_DATETIME_FORMATTER;
	}
}