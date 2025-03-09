package adaspera.lab1.Models.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreatePostDto {
    private String title;

    private String imageData;

    private String imageType;

    private Set<Integer> topicIds;
}