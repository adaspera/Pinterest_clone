package adaspera.lab1.Resources;

import adaspera.lab1.Dao.CommentDao;
import adaspera.lab1.Dao.PostDao;
import adaspera.lab1.Models.Comment;
import adaspera.lab1.Models.DTOs.CommentDto;
import adaspera.lab1.Models.Post;
import adaspera.lab1.Utils.Mappers.CommentMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/comments")
public class CommentResource {

    @Inject
    CommentDao commentDao;

    @Inject
    PostDao postDao;

    @Path("/ofPost/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPostId(@PathParam("id") Integer id) {
        Post post = postDao.findById(id);

        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found with ID: " + id)
                    .build();
        }

        Set<Comment> comments = post.getComments();

        List<CommentDto> commentDtos = comments.stream()
                .map(CommentMapper::toCommentDto)
                .toList();

        return Response.ok(commentDtos).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createComment(CommentDto commentDto) {
        Post post = postDao.findById(commentDto.getPostId());
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found with ID: " + commentDto.getPostId())
                    .build();
        }

        Comment comment = CommentMapper.toComment(commentDto, post);

        commentDao.create(comment);

        CommentDto createCommentDto = CommentMapper.toCommentDto(comment);

        return Response.status(Response.Status.CREATED).entity(createCommentDto).build();
    }
}
