package sandbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rinmalavi.model.TelemetryDataRaw;
import org.apache.kafka.common.serialization.Serializer;

public class TelemetryDataRawSerializer implements Serializer<TelemetryDataRaw> {

    @Override
    public byte[] serialize(String s, TelemetryDataRaw telemetryRawData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(telemetryRawData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
