package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostCreateDto;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void test() {

    }

    public Long createPost(PostCreateDto postCreateDto) {
        User user = userRepository.findById(postCreateDto.getUserId()).orElseThrow(() -> new UserNotFoundException(postCreateDto.getUserId()));;
        Post post = postRepository.save(new Post(user, postCreateDto.getTitle(), postCreateDto.getContent()));
        return post.getId();
    }


}
