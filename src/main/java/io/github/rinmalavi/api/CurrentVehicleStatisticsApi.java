package io.github.rinmalavi.api;

import io.github.rinmalavi.store.CurrentVehicleStateStore;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("current-vehicle-statistics")
@RequiredArgsConstructor
public class CurrentVehicleStatisticsApi {

    final CurrentVehicleStateStore currentVehicleStatisticsState;

    @GET
    public Response get(@QueryParam("vehicleId") String vehicleIds) {
        return currentVehicleStatisticsState
                .getStatsForVehicle(vehicleIds)
                .map(tdc -> Response.ok(tdc).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
