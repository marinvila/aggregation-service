package io.github.rinmalavi.store;

import io.github.rinmalavi.model.TelemetryDataCalculated;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class CurrentVehicleStateStore {

    private final Map<String, TelemetryDataCalculated> vehicleToStats = new HashMap<>();

    public Optional<TelemetryDataCalculated> getStatsForVehicle(String vehicleId) {
        return Optional.ofNullable(vehicleToStats.get(vehicleId));
    }

    public void setStatsForVehicle(String vehicleId, TelemetryDataCalculated tdc) {
        vehicleToStats.put(vehicleId, tdc);
    }
}
