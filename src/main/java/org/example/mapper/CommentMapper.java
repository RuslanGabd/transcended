package org.example.mapper;

import jakarta.validation.constraints.NotNull;
import org.example.dto.CommentDto;
import org.example.entity.Comment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public static CommentDto toDto(@NotNull Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPost().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setContent(comment.getContent());
        dto.setDataCreated(comment.getDataCreated());
        dto.setDataEdited(comment.getDataEdited());
        dto.setDataDeleted(comment.getDataDeleted());
        return dto;
    }

    public static Comment toEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setDataCreated(LocalDateTime.now());
        return comment;
    }
}
