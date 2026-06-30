package org.example.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostCreateDto;
import org.example.dto.PostDto;
import org.example.dto.ProfileDto;
import org.example.entity.Channel;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.entity.UserRoles;
import org.example.exception.UserNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repo.ChannelRepository;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final ChannelRepository channelRepository;

    public Long createPost(PostCreateDto postCreateDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
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

    public List<PostDto> getPostsByChannel(Long channelId) {
        return postRepository.findByChannelId(channelId).stream()
                .map(postMapper::toDto).toList();
    }
@Transactional
    public PostDto updatePost(Long postId, Long userId, @Valid PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("Post not found"));
        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can't edit someone else's post");
        }
        Channel channel = channelRepository.findById(postDto.getIdChannel())
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setChannel(channel);
        post.setDataEdited(LocalDateTime.now());

        return postDto;
    }
}
