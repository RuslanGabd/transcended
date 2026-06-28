package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.entity.UserStatus;
import org.example.mapper.UserMapper;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void makeUserOnline() {
        userRepository.findAll().forEach(user -> {
            LocalDateTime offlineThreshold = LocalDateTime.now().minusMinutes(5);

            if (user.getLastSeenAt() == null || user.getLastSeenAt().isBefore(offlineThreshold)) {
                user.setOnlineStatus(UserStatus.OFFLINE);
            }
        });
    }
}
