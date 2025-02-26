package pairing.ideal.member.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class UrlListConverter implements AttributeConverter<List, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List urls) {
        try {
            return mapper.writeValueAsString(urls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List convertToEntityAttribute(String data) {
        try {
            return mapper.readValue(data, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
