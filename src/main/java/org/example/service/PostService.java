package org.example.service;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostCreateDto;
import org.example.dto.PostDto;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.entity.UserRoles;
import org.example.exception.UserNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public void test() {

    }

    public Long createPost(PostCreateDto postCreateDto) {
        User user = userRepository.findById(postCreateDto.getUserId()).orElseThrow(() -> new UserNotFoundException(postCreateDto.getUserId()));;
        Post post = postRepository.save(new Post(user, postCreateDto.getTitle(), postCreateDto.getContent()));
        return post.getId();
    }


    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId) && !post.getUser().getRole().equals(UserRoles.ADMIN)) {
            throw new RuntimeException("You can't delete someone else's post");
        }
        post.setDataDeleted(LocalDateTime.now());
        postRepository.save(post);
    }

    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found"));

        if (post.getDataDeleted() != null) {
            throw new RuntimeException("Post has been deleted");
        }

        return postMapper.toDto(post);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto).toList();
   }

    public List<PostDto> getPostsByUser(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(postMapper::toDto).toList();
    }

    public PostService getPostsByChannel(Long channelId) {
        return postRepository.findByChannelId(channelId).stream()
                .map(postMapper::toDto).toList();
    }
}
