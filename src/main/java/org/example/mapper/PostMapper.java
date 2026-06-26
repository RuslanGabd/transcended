package org.example.mapper;

import jakarta.validation.constraints.NotNull;
import org.example.dto.PostDto;
import org.example.entity.Post;
import org.example.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PostMapper {

    public PostDto toDto(@NotNull Post post) {
        PostDto dto = new PostDto();

        dto.setUserId(post.getUser().getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());

         dto.setIdComments(
                post.getComments()
                        .stream()
                        .map(Comment::getId)
                        .collect(Collectors.toList())
        );

        dto.setDataCreated(post.getDataCreated());
        dto.setDataEdited(post.getDataEdited());
        dto.setDataDeleted(post.getDataDeleted());

        return dto;
    }

    public Post toPost(PostDto dto) {
        Post post = new Post();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setDataCreated(dto.getDataCreated());

        return post;
    }


}
