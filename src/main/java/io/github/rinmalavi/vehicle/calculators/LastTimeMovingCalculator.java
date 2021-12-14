package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.model.VehicleState;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class LastTimeMovingCalculator {

    public Long calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Long lastTimestamp = Optional.ofNullable(lastValue.getLastTimestamp()).orElse(0l);
        if (tdr.recordedAt > lastTimestamp) {
            Double currentSpeed = tdr.signalValues.get(SignalValue.CURRENT_SPEED);
            if (currentSpeed != null && currentSpeed > 0) {
                return tdr.recordedAt;
            }
        }
        return lastTimestamp;
    }

    private boolean stateChanged(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        boolean isCharging = tdr.signalValues.get(SignalValue.IS_CHARGING) == 1d;

        return
                lastValue.getVehicleState().equals(VehicleState.CHARGING) && isCharging;
    }
}
