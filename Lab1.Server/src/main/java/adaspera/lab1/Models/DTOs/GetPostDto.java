package adaspera.lab1.Models.DTOs;

import adaspera.lab1.Models.Comment;
import adaspera.lab1.Models.Topic;
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

    private Set<Topic> topics;

    private Set<Comment> comments;
}