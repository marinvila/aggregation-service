package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.model.VehicleState;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class NumberOfChargesCalculator {
    public Optional<Integer> calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Integer numberOfCharges = lastValue.getNumberOfCharges().orElse(0);

        return Optional.of(
                numberOfCharges +
                        lastValue.getLastTimestamp()
                                .filter(lastTimestamp -> tdr.recordedAt > lastTimestamp && stateChanged(tdr, lastValue))
                                .map(a -> 1)
                                .orElse(0));
    }

    private boolean stateChanged(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        boolean isCharging = tdr.signalValues.getOrDefault(SignalValue.IS_CHARGING, 0d).equals(1d);

        return
                lastValue.getVehicleState().equals(VehicleState.CHARGING) && !isCharging;
    }
}
