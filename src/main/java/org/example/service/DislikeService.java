package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Comment;
import org.example.entity.Dislike;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.repo.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DislikeService {

    private final DislikeRepository dislikeRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // POST
    public void dislikePost(Long postId, Long userId) {
        if (dislikeRepository.existsByPostIdAndUserId(postId, userId)) return;
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            likeRepository.deleteByPostIdAndUserId(postId, userId);
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Dislike dislike = new Dislike();
        dislike.setPost(post);
        dislike.setUser(user);

        dislikeRepository.save(dislike);
    }
    public void removeDislikeFromPost(Long postId, Long userId) {
        dislikeRepository.deleteByPostIdAndUserId(postId, userId);
    }
    public DislikeService getPostDislikeCount(Long postId) {
        return dislikeRepository.countByPostId(postId);
    }

    // COMMENT
    public void dislikeComment(Long commentId, Long userId) {
        if (dislikeRepository.existsByCommentIdAndUserId(commentId, userId)) return;
        if (likeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            likeRepository.deleteByCommentIdAndUserId(commentId, userId);
        }
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Dislike dislike = new Dislike();
        dislike.setComment(comment);
        dislike.setUser(user);

        dislikeRepository.save(dislike);
    }

    public void removeDislikeFromComment(Long commentId, Long userId) {
        dislikeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }
    public DislikeService getCommentDislikeCount(Long commentId) {
        return dislikeRepository.countByCommentId(commentId);
    }
}
