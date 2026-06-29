package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDto;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.entity.UserRoles;
import org.example.mapper.CommentMapper;
import org.example.repo.CommentRepository;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<CommentDto> getCommentsByPostId(Long postId) {

        List<Comment> comments = commentRepository
                .findByPostIdAndDataDeletedIsNull(postId);

        return comments.stream().map(CommentMapper::toDto).toList();
    }

    public CommentDto editComment(Long commentId, CommentDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(dto.getContent());
        comment.setDataEdited(LocalDateTime.now());

        return CommentMapper.toDto(commentRepository.save(comment));
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId) && !comment.getUser().getRole().equals(UserRoles.ADMIN)) {
            throw new RuntimeException("You cannot delete another user's comment");
        }

        comment.setDataDeleted(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository
                .findByUserIdAndDataDeletedIsNull(userId);

        return comments.stream().map(CommentMapper::toDto).toList();
    }
}
