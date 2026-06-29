package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.dto.ChangePasswordRequest;
import org.example.dto.ProfileDto;
import org.example.dto.ProfileRequest;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") @Min(1) Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/me")
    public UserDto getMe(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        return userService.getUser(userId);
    }

    @PostMapping("/online")
    public ResponseEntity<Void> markOnline(
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        userService.writeLastSeenAt(userId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public UserDto updateMyProfile(
            @Valid @RequestBody UserDto request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        return userService.updateUser(userId, request);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ChangePasswordRequest request
    ) {
        Long userId = jwt.getClaim("userId");
        userService.changePassword(userId, request);
        return ResponseEntity.noContent().build();
    }

}


