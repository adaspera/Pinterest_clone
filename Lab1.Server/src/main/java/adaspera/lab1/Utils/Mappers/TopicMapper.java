package adaspera.lab1.Utils.Mappers;

import adaspera.lab1.Models.DTOs.TopicDto;
import adaspera.lab1.Models.Topic;

public class TopicMapper {

    public static Topic toTopic(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setId(topicDto.getId());
        topic.setName(topicDto.getName());

        return topic;
    }

    public static TopicDto toTopicDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());

        return topicDto;
    }
}
