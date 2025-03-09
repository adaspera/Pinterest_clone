package adaspera.lab1.Utils.Mappers;

import adaspera.lab1.Models.DTOs.CommentDto;
import adaspera.lab1.Models.DTOs.CreatePostDto;
import adaspera.lab1.Models.DTOs.GetPostDto;
import adaspera.lab1.Models.DTOs.TopicDto;
import adaspera.lab1.Models.Post;

import java.util.*;
import java.util.stream.Collectors;

public class PostMapper {
    public static Post toPost(CreatePostDto post) {
        Post p = new Post();
        byte[] imageBytes = Base64.getDecoder().decode(post.getImageData().split(",")[1]);
        p.setImageData(imageBytes);
        p.setTitle(post.getTitle());
        p.setImageType(post.getImageType());

        return p;
    }

    public static GetPostDto toGetPostDto(Post post) {
        GetPostDto dto = new GetPostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setImageData(post.getImageData());
        dto.setImageType(post.getImageType());

        Set<TopicDto> topicDtos = Optional.ofNullable(post.getTopics())
                .orElse(Collections.emptySet())
                .stream()
                .map(TopicMapper::toTopicDto)
                .collect(Collectors.toSet());
        dto.setTopics(topicDtos);

        Set<CommentDto> commentDtos = Optional.ofNullable(post.getComments())
                .orElse(Collections.emptySet())
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toSet());
        dto.setComments(commentDtos);

        return dto;
    }
}
