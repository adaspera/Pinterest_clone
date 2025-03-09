package adaspera.lab1.Resources;

import adaspera.lab1.Dao.TopicDao;
import adaspera.lab1.Models.DTOs.CommentDto;
import adaspera.lab1.Models.DTOs.TopicDto;
import adaspera.lab1.Models.Topic;
import adaspera.lab1.Utils.Mappers.CommentMapper;
import adaspera.lab1.Utils.Mappers.TopicMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/topics")
public class TopicResource {
    @Inject
    TopicDao topicDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTopics() {
        List<Topic> topics = topicDao.getAll();

        List<TopicDto> topicDtos = topics.stream()
                .map(TopicMapper::toTopicDto)
                .toList();

        return Response.ok(topicDtos).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTopic(TopicDto topicDto) {
        Topic topic = TopicMapper.toTopic(topicDto);
        topicDao.create(topic);

        return Response.status(Response.Status.CREATED).entity(TopicMapper.toTopicDto(topic)).build();
    }
}
