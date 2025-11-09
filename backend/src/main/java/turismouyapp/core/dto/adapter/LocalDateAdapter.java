package turismouyapp.core.dto.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import turismouyapp.webservices.utils.DateUtils;

/**
 * JAXB adapter to marshal/unmarshal java.time.LocalDate as ISO date string (yyyy-MM-dd).
 * Utiliza DateUtils para mantener consistencia en el parseo de fechas en toda la aplicación.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return DateUtils.parseToLocalDate(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return DateUtils.formatToString(v);
    }
}
