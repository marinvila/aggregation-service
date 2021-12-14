package io.github.rinmalavi.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.github.rinmalavi.model.TelemetryDataRaw;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TelemetryDataRawDeserializer implements Deserializer<Optional<TelemetryDataRaw>> {

    private static final Logger log = LoggerFactory.getLogger(TelemetryDataRawDeserializer.class);

    @Override
    public Optional<TelemetryDataRaw> deserialize(String key, byte[] data) {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
                .build();
        try {
            return Optional.of(
                    mapper.readValue(data, TelemetryDataRaw.class)
            );
        } catch (Exception e) {
            log.error("Unable to deserialize( to TelemetryDataRaw) {}", new String(data), e);
        }
        return Optional.empty();
    }
}
