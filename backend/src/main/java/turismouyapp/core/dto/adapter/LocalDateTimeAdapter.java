package turismouyapp.core.dto.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import turismouyapp.webservices.utils.DateUtils;

/**
 * JAXB adapter to marshal/unmarshal java.time.LocalDateTime as ISO date-time string (yyyy-MM-dd'T'HH:mm:ss).
 * Utiliza DateUtils para mantener consistencia en el parseo de fechas-horas en toda la aplicación.
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return DateUtils.parseToLocalDateTime(v);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return DateUtils.formatToString(v);
    }
}
