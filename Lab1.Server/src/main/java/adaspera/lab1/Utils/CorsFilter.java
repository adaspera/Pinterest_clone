package adaspera.lab1.Utils;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*"); // Allow requests from any origin
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"); // Allowed HTTP methods
        headers.add("Access-Control-Allow-Headers", "Content-Type"); // Allowed headers
    }
}
