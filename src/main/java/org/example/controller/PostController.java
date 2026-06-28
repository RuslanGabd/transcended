package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostCreateDto;

import org.example.dto.PostDto;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<Long> newPost(@RequestBody PostCreateDto postCreateDto, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
       Long postId = postService.createPost(postCreateDto, userId);
       return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt)
    {
        Long userId = jwt.getClaim("userId");
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
    @GetMapping("/postByUser/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<PostDto>> getPostsByChannel(@PathVariable Long channelId) {
        return ResponseEntity.ok(postService.getPostsByChannel(channelId));
    }
}
