package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AverageSpeedCalculator {

    public Double calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        if (lastValue.getLastTimestamp() != null && tdr.recordedAt > lastValue.getLastTimestamp()) {
            Double odometer = tdr.signalValues.get(SignalValue.ODOMETER);
            Double drivingTime = tdr.signalValues.get(SignalValue.DRIVING_TIME);
            if (odometer != null && drivingTime != null) {
                return getSpeed(odometer, drivingTime);
            }
        }

        return lastValue.getAverageSpeed();
    }

    private Double getSpeed(Double odometer, Double drivingTime) {
        if (drivingTime == 0) {
            return 0d;
        } else {
            return odometer / msToHours(drivingTime);
        }
    }

    private Double msToHours(Double drivingTime) {
        final int msInHour = 360000;
        return drivingTime / msInHour;
    }
}
