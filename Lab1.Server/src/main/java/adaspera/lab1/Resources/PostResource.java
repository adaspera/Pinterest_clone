package adaspera.lab1.Resources;

import adaspera.lab1.Dao.PostDao;
import adaspera.lab1.Dao.TopicDao;
import adaspera.lab1.Models.DTOs.CreatePostDto;
import adaspera.lab1.Models.DTOs.GetPostDto;
import adaspera.lab1.Models.Post;
import adaspera.lab1.Models.Topic;
import adaspera.lab1.Utils.Mappers.PostMapper;
import jakarta.inject.Inject;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/posts")
public class PostResource {

    @Inject
    PostDao postDao;

    @Inject
    TopicDao topicDao;

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

    @Path("/byTopic/{topicId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getPostsByTopicId(@PathParam("topicId") Integer topicId) {

        Topic topic = topicDao.findById(topicId);
        if (topic == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Set<Post> posts = topic.getPosts();

        List<GetPostDto> postDtos = posts.stream().map(PostMapper::toGetPostDto).toList();

        return Response.ok(postDtos).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostById(@PathParam("id") Integer id) {

        Post post = postDao.findById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GetPostDto postDto = PostMapper.toGetPostDto(post);

        return Response.ok(postDto).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(CreatePostDto postDto) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(postDto.getImageData().split(",")[1]);

            Post post = new Post();
            post.setTitle(postDto.getTitle());
            post.setImageData(imageBytes);
            post.setImageType(postDto.getImageType());

            Set<Topic> topics = postDto.getTopicIds().stream().map(topicDao::findById).collect(Collectors.toSet());
            post.setTopics(topics);

            postDao.create(post);

            GetPostDto createdPostDto = PostMapper.toGetPostDto(post);
            return Response.status(Response.Status.CREATED).entity(createdPostDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create post: " + e.getMessage()).build();
        }
    }
}
