package io.github.rinmalavi.model;

import java.util.Map;

public class TelemetryDataRaw {
    public final String vehicleId;
    public final Long recordedAt;
    public final Map<String, Double> signalValues;

    public TelemetryDataRaw(String vehicleId, Long recordedAt, Map<String, Double> signalValues) {
        this.vehicleId = vehicleId;
        this.recordedAt = recordedAt;
        this.signalValues = signalValues;
    }
}
