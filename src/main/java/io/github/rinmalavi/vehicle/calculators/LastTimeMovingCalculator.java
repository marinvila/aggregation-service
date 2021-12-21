package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class LastTimeMovingCalculator {

    public Optional<Long> calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        return lastValue.getLastTimestamp()
                .filter(lastTimestamp -> tdr.recordedAt > lastTimestamp)
                .map(
                        lastTimestamp ->
                        Optional
                                .ofNullable(tdr.signalValues.get(SignalValue.CURRENT_SPEED))
                                .filter(currentSpeed -> currentSpeed > 0)
                                        .map (cs -> tdr.recordedAt)
                                .orElse(lastTimestamp));
    }

}

