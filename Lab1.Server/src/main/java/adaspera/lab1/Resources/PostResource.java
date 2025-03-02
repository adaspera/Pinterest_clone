package adaspera.lab1.Resources;

import adaspera.lab1.Dao.PostDao;
import adaspera.lab1.Models.Post;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Path("/post")
public class PostResource {

    @Inject
    PostDao postDao;

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts() {

        File file = new File("C:\\Users\\jusci\\Downloads\\artworks-D0ZqXIygRxHr7b7Y-LKzWjA-t500x500.jpg");
        byte[] imageData;
        try {
            imageData = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to read image file: " + e.getMessage())
                    .build();
        }

        ArrayList<Post> posts = new ArrayList<Post>();

        for (int i=0;i<10;i++) {
            Post post = new Post();
            post.setImageData(imageData);
            posts.add(post);
        }

        return Response.ok(posts).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostById(@PathParam("id") Integer id) {

        Post post = postDao.findById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(post).build();
    }
}
