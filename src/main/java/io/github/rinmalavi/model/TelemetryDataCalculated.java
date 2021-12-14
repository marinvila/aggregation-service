package io.github.rinmalavi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TelemetryDataCalculated {
    private final Double averageSpeed;
    private final Double maximumSpeed;
    private final Long lastTimestamp;
    private final Long lastTimeMoving;
    private final Integer numberOfCharges;
    private final VehicleState vehicleState;

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public Double getMaximumSpeed() {
        return maximumSpeed;
    }

    public Long getLastTimestamp() {
        return lastTimestamp;
    }

    @JsonIgnore
    public Long getLastTimeMoving() {
        return lastTimeMoving;
    }

    public Integer getNumberOfCharges() {
        return numberOfCharges;
    }

    public VehicleState getVehicleState() {
        return vehicleState;
    }

    public TelemetryDataCalculated() {
        this.averageSpeed = null;
        this.maximumSpeed = null;
        this.lastTimestamp = null;
        this.lastTimeMoving = null;
        this.numberOfCharges = null;
        this.vehicleState = VehicleState.UNKNOWN_STATE;
    }
}
