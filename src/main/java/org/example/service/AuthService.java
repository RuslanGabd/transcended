package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.AuthResponse;
import org.example.dto.LoginRequest;
import org.example.dto.RegisterRequest;
import org.example.entity.User;
import org.example.entity.UserRoles;
import org.example.repo.UserRepository;
import org.example.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByNickname(request.nickname())) {
            throw new IllegalArgumentException(
                    "Nickname is already used"
            );
        }

        if (request.email() != null
                && userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Email is already used"
            );
        }

        User user = new User();

        user.setNickname(request.nickname());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setEmail(request.email());
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setRole(UserRoles.USER);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.nickname(),
                                request.password()
                        )
                );

        User user = userRepository
                .findByNickname(authentication.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}