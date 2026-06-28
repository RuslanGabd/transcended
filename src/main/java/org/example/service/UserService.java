package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.entity.UserStatus;
import org.example.mapper.UserMapper;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;


    public UserDto getUser(Long id) {
        return (userMapper.toDto(userRepository.findById(id).orElseThrow()));
    }

    public void writeLastSeenAt(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setLastSeenAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Scheduled(fixedRate = 120000) //every two minutes
    public void makeUserOnline() {
        userRepository.findAll().forEach(user -> {
            if
            (user.getLastSeenAt().isBefore(LocalDateTime.now().minusMinutes(1))) {
                user.setOnlineStatus(UserStatus.OFFLINE);
                userRepository.save(user);
            }
        });
    }
}
