﻿package adaspera.lab1.Models.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private int id;

    private String content;

    private String username;

    private int postId;
}
