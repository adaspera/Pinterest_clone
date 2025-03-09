package adaspera.lab1.Models.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GetPostDto {
    private int id;

    private String title;

    private byte[] imageData;

    private String imageType;

    private Set<TopicDto> topics;

    private Set<CommentDto> comments;
}