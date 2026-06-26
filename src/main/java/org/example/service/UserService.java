package org.example.service;

import jakarta.validation.constraints.Min;
import org.example.dto.UserDto;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    UserRepository userRepository;
    public UserDto getUser( Long id) {
        userRepository.findById(id);


    }
}
