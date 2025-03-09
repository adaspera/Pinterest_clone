package adaspera.lab1.Utils.Mappers;

import adaspera.lab1.Models.Comment;
import adaspera.lab1.Models.DTOs.CommentDto;
import adaspera.lab1.Models.Post;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUsername(comment.getUsername());
        commentDto.setPostId(comment.getPost().getId());

        return commentDto;
    }

    public static Comment toComment(CommentDto commentDto, Post post) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setUsername(commentDto.getUsername());
        comment.setPost(post);

        return comment;
    }
}