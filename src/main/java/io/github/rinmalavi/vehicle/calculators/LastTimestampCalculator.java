package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class LastTimestampCalculator {
    public Long calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Long lastTimestamp = Optional.ofNullable(lastValue.getLastTimestamp()).orElse(0l);
        return Math.max(tdr.recordedAt, lastTimestamp);
    }
}
