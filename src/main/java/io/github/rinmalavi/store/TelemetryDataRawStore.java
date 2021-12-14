package io.github.rinmalavi.store;

import io.github.rinmalavi.model.TelemetryDataRaw;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TelemetryDataRawStore {

    private final Map<String, List<TelemetryDataRaw>> vehicleToTelemetry = new HashMap<>();

    public void persist(TelemetryDataRaw telemetryDataRaw) {
        String vehicleId = telemetryDataRaw.vehicleId;
        List<TelemetryDataRaw> telemetryDataRaws = vehicleToTelemetry.computeIfAbsent(vehicleId, k -> new ArrayList<>());
        telemetryDataRaws.add(telemetryDataRaw);
    }
}
