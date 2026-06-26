package org.example.repo;

import org.example.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainsIgnoreCase(String title);

    List<Post> findByUser_NicknameContainsIgnoreCase(String userNickname);
}
