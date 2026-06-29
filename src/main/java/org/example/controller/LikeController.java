package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // POST
    @PostMapping("/post/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        likeService.likePost(postId, userId);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> removeLikeFromPost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        likeService.removeLikeFromPost(postId, userId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Integer> getPostLikeCount(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.getPostLikeCount(postId));
    }

    // COMMENT
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Void> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        likeService.likePost(commentId, userId);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> removeLikeFromComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        likeService.removeLikeFromComment(commentId, userId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("comment/{commetId}/count")
    public ResponseEntity<Integer> getCommentLikeCount(@PathVariable Long commetId) {
        return ResponseEntity.ok(likeService.getCommentLikeCount(commetId));
    }
}
