package adaspera.lab1.Resources;

import adaspera.lab1.Dao.PostDao;
import adaspera.lab1.Dao.TopicDao;
import adaspera.lab1.Models.DTOs.CreatePostDto;
import adaspera.lab1.Models.DTOs.GetPostDto;
import adaspera.lab1.Models.DTOs.UpdatePostDto;
import adaspera.lab1.Models.Post;
import adaspera.lab1.Models.Topic;
import adaspera.lab1.Utils.Mappers.PostMapper;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.*;
import jakarta.transaction.NotSupportedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.stream.Collectors;

@Path("/posts")
public class PostResource {

    @Inject
    PostDao postDao;

    @Inject
    TopicDao topicDao;

    @Inject
    UserTransaction ut;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getAllPosts() {

        List<Post> posts = postDao.findAll();
        Set<GetPostDto> postDtos = posts.stream().map(PostMapper::toGetPostDto).collect(Collectors.toSet());

        return Response.ok(postDtos).build();
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
    @Transactional
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
    @Transactional
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

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deletePost(@PathParam("id") Integer id) {

        Post post = postDao.findById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        postDao.delete(post);

        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updatePost(UpdatePostDto postDto) {

        Post post = postDao.findById(postDto.getId());
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        post.setTitle(postDto.getTitle());

        Set<Topic> topics = postDto.getTopicIds().stream().map(topicDao::findById).collect(Collectors.toSet());
        post.setTopics(topics);

        try {
            Thread.sleep(2000);
            postDao.update(post);
            postDao.flush();
        } catch (OptimisticLockException e) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (InterruptedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        GetPostDto updatedPostDto = PostMapper.toGetPostDto(post);

        return Response.ok(updatedPostDto).build();
    }

    @Path("wRetries")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePostWithRetries(UpdatePostDto postDto) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NotSupportedException, RollbackException {
        int retries = 0;
        while (retries < 3) {
            try {
                Thread.sleep(2000);
                ut.begin();
                Post post = postDao.findById(postDto.getId());
                if (post == null) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }

                post.setTitle(postDto.getTitle());
                Set<Topic> topics = postDto.getTopicIds().stream()
                        .map(topicDao::findById)
                        .collect(Collectors.toSet());
                post.setTopics(topics);

                postDao.update(post);
                postDao.flush();

                GetPostDto responseDto = PostMapper.toGetPostDto(post);
                ut.commit();
                return Response.ok(responseDto).build();
            } catch (OptimisticLockException e) {
                if (retries < 3) {
                    retries++;
                    return Response.status(Response.Status.CONFLICT).build();
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
