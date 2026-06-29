package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDto;
import org.example.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final HandlerMapping resourceHandlerMapping;

    @PostMapping("/new")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto dto) {
        return ResponseEntity.ok(commentService.addComment(dto));
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getCommentsByUserId(userId));
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable Long commentId, @RequestBody CommentDto dto) {
        return ResponseEntity.ok(commentService.editComment(commentId, dto));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
