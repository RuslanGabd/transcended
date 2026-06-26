package org.example.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.entity.Post;
import org.example.repo.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public void test() {

    }

}
