package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDto;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.mapper.CommentMapper;
import org.example.repo.CommentRepository;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentDto addComment(CommentDto dto){
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = CommentMapper.toEntity(dto);
        comment.setPost(post);
        comment.setUser(user);

        return CommentMapper.toDto(commentRepository.save(comment));
    }
}
