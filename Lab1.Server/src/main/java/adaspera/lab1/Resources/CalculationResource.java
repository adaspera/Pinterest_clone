package adaspera.lab1.Resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.CompletableFuture;

@Path("/calculation")
public class CalculationResource {
    @GET
    @Path("/slow")
    public Response slowAsyncOperation() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return Response.accepted("Async operation started").build();
    }
}
