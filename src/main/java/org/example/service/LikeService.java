package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Comment;
import org.example.entity.Like;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.repo.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private LikeRepository likeRepository;
    private DislikeRepository dislikeRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    // POSTS
    public void likePost(Long postId, Long userId) {
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) return;

        if (dislikeRepository.existsByPostIdAndUserId(postId, userId)) {
            dislikeRepository.deleteByPostIdAndUserId(postId, userId);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        likeRepository.save(like);
    }

    public void removeLikeFromPost(Long postId, Long userId) {
        likeRepository.deleteByPostIdAndUserId(postId, userId);
    }

    public int getPostLikeCount(Long postId) {
        return likeRepository.countsByPostId(postId);
    }

    // COMMENTS
    public void likeComment(Long commentId, Long userId) {
        if (likeRepository.existsByCommentIdAndUserId(commentId, userId)) return;
        if (dislikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            dislikeRepository.deleteByCommentIdAndUserId(commentId, userId);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Like like = new Like();
        like.setComment(comment);
        like.setUser(user);

        likeRepository.save(like);
    }

    public void removeLikeFromComment(Long commentId, Long userId) {
        likeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }

    public int getCommentLikeCount(Long commentId) {
        return likeRepository.countsByCommentId(commentId);
    }
}
