package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repo.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public void test() {

    }

}
