package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class LastTimestampCalculator {
    public Optional<Long> calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Long lastTimestamp = lastValue.getLastTimestamp().orElse(0l);
        return Optional.of(Math.max(tdr.recordedAt, lastTimestamp));
    }
}
