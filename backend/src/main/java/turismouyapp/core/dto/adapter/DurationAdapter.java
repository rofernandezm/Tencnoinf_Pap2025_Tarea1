package turismouyapp.core.dto.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Duration;

/**
 * JAXB adapter to marshal/unmarshal java.time.Duration as ISO-8601 string (e.g., PT1H30M).
 */
// JAXB:
public class DurationAdapter extends XmlAdapter<String, Duration> {

    @Override
    public Duration unmarshal(String v) throws Exception {
        return (v == null || v.isEmpty()) ? null : Duration.parse(v);
    }

    @Override
    public String marshal(Duration v) throws Exception {
        return (v == null) ? null : v.toString();
    }
}
