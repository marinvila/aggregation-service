package io.github.rinmalavi.vehicle.calculators;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class AverageSpeedCalculator {

    public Optional<Double> calculate(TelemetryDataRaw tdr, TelemetryDataCalculated lastValue) {
        return
                lastValue.getLastTimestamp()
                        .filter(lastValueTimestamp -> lastValueTimestamp < tdr.recordedAt).map(
                                lvt -> {
                                    Optional<Double> optOdometer = Optional.ofNullable(tdr.signalValues.get(SignalValue.ODOMETER));
                                    Optional<Double> optDriveTime = Optional.ofNullable(tdr.signalValues.get(SignalValue.DRIVING_TIME));
                                    return optOdometer.flatMap(
                                            odometer ->
                                                    optDriveTime.map(
                                                            driveTime -> getSpeed(odometer, driveTime)
                                                    )
                                    );
                                }).orElse(lastValue.getAverageSpeed());
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
