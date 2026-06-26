package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostCreateDto;

import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<Long> newPost(@RequestBody PostCreateDto postCreateDto) {
       Long postId = postService.createPost(postCreateDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @DeleteMapping("/delete{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Long userId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getId")


    @GetMapping("/getAll")
    @GetMapping("/getByUser")
    @GetMapping("/getByChannel")


}
