package adaspera.lab1.Resources;

import adaspera.lab1.Services.LongCalculationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Path("/calculation")
public class CalculationResource {

    @Inject
    LongCalculationService longCalculation;

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceStatus() {
        String status = longCalculation.isDone() ? "done" : "not done";
        return Response.ok().entity(Map.of("status", status)).build();
    }

    @POST
    @Path("/start/{nr}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startLongRunningTask(@PathParam("nr") Integer nr) {

        longCalculation.startLongCalculation(nr);
        return Response.accepted().entity("Async operation started").build();
    }

    @GET
    @Path("/result")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkTaskResult() {

        if (!longCalculation.isDone()) {
            return Response.status(Response.Status.ACCEPTED).entity(
                        Map.of("status", "in progress", "message", "Calculation not completed yet")
                ).build();
        }

        return Response.ok().entity(Map.of("result", longCalculation.getResult(), "status", "completed")).build();
    }
}
