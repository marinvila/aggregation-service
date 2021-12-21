package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.model.VehicleState;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VehicleStateCalculator {

    @ConfigProperty(name = "app.moreThanStopTime", defaultValue = "10000l")
    long moreThanStopTime;

    public VehicleState calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        Long lastTimestamp = lastValue.getLastTimestamp().orElse(0L);
        if (tdr.recordedAt < lastTimestamp)
            return lastValue.getVehicleState();
        boolean isCharging = tdr.signalValues.getOrDefault(SignalValue.IS_CHARGING, 0d) == 1d;
        if (isCharging)
            return VehicleState.CHARGING;
        Double currentSpeed = tdr.signalValues.get(SignalValue.CURRENT_SPEED);
        if (currentSpeed == null)
            return lastValue.getVehicleState();
        if (currentSpeed > 0)
            return VehicleState.DRIVING;

        if (tdr.recordedAt - lastValue.getLastTimeMoving().orElse(tdr.recordedAt) > moreThanStopTime)
            return VehicleState.PARKED;

        return VehicleState.UNKNOWN_STATE;
    }
}
