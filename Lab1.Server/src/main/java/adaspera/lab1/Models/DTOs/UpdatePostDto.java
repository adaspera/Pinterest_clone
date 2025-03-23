package adaspera.lab1.Models.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdatePostDto {
    private int id;

    private String title;

    private Set<Integer> topicIds;
}