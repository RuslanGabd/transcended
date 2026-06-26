package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostCreateDto;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void test() {

    }

//    public Long createPost(PostCreateDto postCreateDto) {
//       // User user = userRepository.findById(postCreateDto.getUserId()).orElseThrow(() -> new UserNotFoundException(postCreateDto.getUserId()));;
//      //  Post post = postRepository.save(new Post(user, postCreateDto.getTitle(), postCreateDto.getContent()));
//       // return post.getId();
//    }


    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId) ) {
            throw new RuntimeException("You can't delete someone else's post");
        }

        postRepository.changeToDeletedStatus(postId);
    }
}
