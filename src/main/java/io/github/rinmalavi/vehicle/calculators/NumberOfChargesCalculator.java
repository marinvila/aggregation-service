package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.model.VehicleState;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class NumberOfChargesCalculator {
    public Integer calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Integer numberOfCharges = Optional.ofNullable(lastValue.getNumberOfCharges()).orElse(0);
        Long lastTimestamp = Optional.ofNullable(lastValue.getLastTimestamp()).orElse(0L);
        return numberOfCharges +
                ((tdr.recordedAt > lastTimestamp && stateChanged(tdr, lastValue)) ? 1 : 0);
    }

    private boolean stateChanged(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        boolean isCharging = tdr.signalValues.getOrDefault(SignalValue.IS_CHARGING, 0d).equals(1d);

        return
                lastValue.getVehicleState().equals(VehicleState.CHARGING) && ! isCharging;
    }
}
