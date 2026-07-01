package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.DislikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping("/api/v1/dislike")
@RequiredArgsConstructor
public class DislikeController {

    private final DislikeService dislikeService;
    private final HandlerMapping resourceHandlerMapping;

    // POST
    @PostMapping("/post/{postId}")
    public ResponseEntity<Void> dislikePost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userid");
        dislikeService.dislikePost(postId, userId);

        return  ResponseEntity.ok().build();
    }
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> removeDislikeFromPost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        dislikeService.removeDislikeFromPost(postId, userId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<DislikeService> getPostDislikeCount(@PathVariable Long postId) {
        return ResponseEntity.ok(dislikeService.getPostDislikeCount(postId));
    }

    //COMMENT
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Void> dislikeComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        dislikeService.dislikeComment(commentId, userId);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> removeDislikeFromComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        dislikeService.removeDislikeFromComment(commentId, userId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<DislikeService> getCommentDislikeCount(@PathVariable Long commentId) {
        return ResponseEntity.ok(dislikeService.getCommentDislikeCount(commentId));
    }
}
