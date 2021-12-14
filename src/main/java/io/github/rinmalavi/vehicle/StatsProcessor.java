package io.github.rinmalavi.vehicle;

import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.model.VehicleState;
import io.github.rinmalavi.msg.TelemetryDataListener;
import io.github.rinmalavi.store.CurrentVehicleStateStore;
import io.github.rinmalavi.store.TelemetryDataRawStore;
import io.github.rinmalavi.vehicle.calculators.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class StatsProcessor {

    private static final Logger log = LoggerFactory.getLogger(TelemetryDataListener.class);

    final AverageSpeedCalculator averageSpeedCalculator;
    final LastTimeMovingCalculator lastTimeMovingCalculator;
    final LastTimestampCalculator lastTimestampCalculator;
    final MaximumSpeedCalculator maximumSpeedCalculator;
    final NumberOfChargesCalculator numberOfChargesCalculator;
    final TelemetryDataRawStore telemetryDataRawStore;
    final VehicleStateCalculator vehicleStateCalculator;

    final CurrentVehicleStateStore currentVehicleStateStore;


    public void processTelemetry(TelemetryDataRaw tdr) {
        log.info("Processing Telemetry for {} timestamp {}", tdr.vehicleId, tdr.vehicleId);
        telemetryDataRawStore.persist(tdr);
        String vehicleId = tdr.vehicleId;
        Optional<TelemetryDataCalculated> optStatsForVehicle = currentVehicleStateStore.getStatsForVehicle(vehicleId);
        TelemetryDataCalculated statsForVehicle = optStatsForVehicle.orElse(new TelemetryDataCalculated());
        currentVehicleStateStore.setStatsForVehicle(vehicleId,
                new TelemetryDataCalculated(
                        averageSpeedCalculator.calculate(tdr, statsForVehicle),
                        maximumSpeedCalculator.calculate(tdr, statsForVehicle),
                        lastTimestampCalculator.calculate(tdr, statsForVehicle),
                        lastTimeMovingCalculator.calculate(tdr, statsForVehicle),
                        numberOfChargesCalculator.calculate(tdr, statsForVehicle),
                        vehicleStateCalculator.calculate(tdr, statsForVehicle)
                ));
    }

}
