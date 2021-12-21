package io.github.rinmalavi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class TelemetryDataCalculated {
    private final Optional<Double> averageSpeed;
    private final Optional<Double> maximumSpeed;
    private final Optional<Long> lastTimestamp;
    private final Optional<Long> lastTimeMoving;
    private final Optional<Integer> numberOfCharges;
    private final VehicleState vehicleState;

    public Optional<Double> getAverageSpeed() {
        return averageSpeed;
    }

    public Optional<Double> getMaximumSpeed() {
        return maximumSpeed;
    }

    public Optional<Long> getLastTimestamp() {
        return lastTimestamp;
    }

    @JsonIgnore
    public Optional<Long> getLastTimeMoving() {
        return lastTimeMoving;
    }

    public Optional<Integer> getNumberOfCharges() {
        return numberOfCharges;
    }

    public VehicleState getVehicleState() {
        return vehicleState;
    }

    public TelemetryDataCalculated() {
        this.averageSpeed = Optional.empty();
        this.maximumSpeed = Optional.empty();;
        this.lastTimestamp = Optional.empty();;
        this.lastTimeMoving = Optional.empty();;
        this.numberOfCharges = Optional.empty();;
        this.vehicleState = VehicleState.UNKNOWN_STATE;
    }
}
