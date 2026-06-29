package org.example.service;

import jakarta.validation.Valid;
import org.example.dto.ChangePasswordRequest;
import org.example.dto.ProfileDto;
import org.example.dto.ProfileRequest;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.entity.UserRoles;
import org.example.entity.UserStatus;
import org.example.mapper.UserMapper;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


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

    @Transactional
    public UserDto updateUser(Long userId, @Valid UserDto request) {
        User user = userRepository.findById(userId).orElseThrow();
        if (!userId.equals(request.id()) || user.getRole() != UserRoles.ADMIN)
            throw new IllegalArgumentException("You are not allowed to update this user");
        user.setPhone(request.phone());
        user.setEmail(request.email());
        user.setAbout(request.about());
        user.setName(request.name());
        user.setLastName(request.lastName());
        userRepository.save(user);
        return (userMapper.toDto(user));
    }


    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId).orElseThrow();

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
