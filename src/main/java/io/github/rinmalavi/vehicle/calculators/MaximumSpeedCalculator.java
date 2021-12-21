package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class MaximumSpeedCalculator {

    public Optional<Double> calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Double currentSpeed = tdr.signalValues.getOrDefault(SignalValue.CURRENT_SPEED, 0d);
        Double lastMaximumSpeed = lastValue.getMaximumSpeed().orElse(0d);
        return Optional.of(Math.max(lastMaximumSpeed, currentSpeed));
    }
}
