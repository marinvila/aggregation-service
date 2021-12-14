package io.github.rinmalavi.msg;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataCalculated;
import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.store.CurrentVehicleStateStore;
import io.github.rinmalavi.test.KafkaTestResourceLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
class TelemetryDataListenerTest {

    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    void processEmptyMessage() {
        final String vehicle1 = "vehicle11";
        InMemorySource<Object> source = connector.source("ra-telemetry-data");
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 3l, Map.of())));
        ValidatableResponse response = given()
                .when()
                .queryParam("vehicleId", vehicle1)
                .get("current-vehicle-statistics")
                .then();
        response.statusCode(200);
        TelemetryDataCalculated tdc = response.extract().as(TelemetryDataCalculated.class);
        Assertions.assertEquals(tdc.getLastTimestamp(), 3);
        System.out.println(response.extract().asPrettyString());
    }

    @Test
    void processFewMessage() {
        final String vehicle1 = "vehicle21";
        final String vehicle2 = "vehicle22";
        InMemorySource<Object> source = connector.source("ra-telemetry-data");
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 3l, Map.of(SignalValue.CURRENT_SPEED, 3d, SignalValue.DRIVING_TIME, 4d, SignalValue.ODOMETER, 1000d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle2, 4l, Map.of(SignalValue.CURRENT_SPEED, 333d, SignalValue.DRIVING_TIME, 4d, SignalValue.ODOMETER, 1000d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 5l, Map.of(SignalValue.CURRENT_SPEED, 3d, SignalValue.DRIVING_TIME, 5d, SignalValue.ODOMETER, 1003d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 6l, Map.of(SignalValue.CURRENT_SPEED, 6d, SignalValue.DRIVING_TIME, 6d, SignalValue.ODOMETER, 1004d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 4l, Map.of(SignalValue.CURRENT_SPEED, 7d, SignalValue.DRIVING_TIME, 4d, SignalValue.ODOMETER, 1002d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 7l, Map.of(SignalValue.CURRENT_SPEED, 6d, SignalValue.DRIVING_TIME, 8d, SignalValue.ODOMETER, 1007d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 8l, Map.of(SignalValue.CURRENT_SPEED, 6d, SignalValue.DRIVING_TIME, 8d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 9l, Map.of(SignalValue.CURRENT_SPEED, 6d, SignalValue.ODOMETER, 1007d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 10l, Map.of(SignalValue.DRIVING_TIME, 8d, SignalValue.ODOMETER, 1007d))));

        ValidatableResponse response = given()
                .when()
                .queryParam("vehicleId", vehicle1)
                .get("current-vehicle-statistics")
                .then();
        response.statusCode(200);
        TelemetryDataCalculated tdc = response.extract().as(TelemetryDataCalculated.class);
        System.out.println(response.extract().asPrettyString());
    }

    @Test
    void processCharging() {
        final String vehicle1 = "vehicle31";
        InMemorySource<Object> source = connector.source("ra-telemetry-data");
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 1l, Map.of(SignalValue.IS_CHARGING, 0d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 2l, Map.of(SignalValue.IS_CHARGING, 1d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 3l, Map.of(SignalValue.IS_CHARGING, 1d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 4l, Map.of(SignalValue.IS_CHARGING, 0d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 5l, Map.of(SignalValue.IS_CHARGING, 0d))));
        source.send(Optional.of(new TelemetryDataRaw(vehicle1, 6l, Map.of(SignalValue.IS_CHARGING, 1d))));

        ValidatableResponse response = given()
                .when()
                .queryParam("vehicleId", vehicle1)
                .get("current-vehicle-statistics")
                .then();
        response.statusCode(200);
        TelemetryDataCalculated tdc = response.extract().as(TelemetryDataCalculated.class);
        Assertions.assertEquals(2, tdc.getNumberOfCharges());
        System.out.println(response.extract().asPrettyString());
    }
}