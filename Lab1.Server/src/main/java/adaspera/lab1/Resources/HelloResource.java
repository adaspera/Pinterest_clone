package adaspera.lab1.Resources;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/hello-world")
public class HelloResource {
    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces("text/plain")
    public String hello() {

        return "Hello, World!";
    }
}