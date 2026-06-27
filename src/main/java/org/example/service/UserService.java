package org.example.service;

import jakarta.validation.constraints.Min;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    UserRepository userRepository;
    UserMapper userMapper;


    public UserDto getUser( Long id) {
        return(userMapper.toDto(userRepository.findById(id).orElseThrow()));
    }

}
