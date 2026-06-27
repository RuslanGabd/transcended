package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname)
            throws UsernameNotFoundException {

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + nickname
                        )
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getNickname())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}