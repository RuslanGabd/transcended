package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.dto.ProfileDto;
import org.example.dto.ProfileRequest;
import org.example.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ProfileDto> createProfile(
            @PathVariable @Min(1) Long userId,
            @Valid @RequestBody ProfileRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profileService.createProfile(userId, request));
    }

    @GetMapping("/{id}")
    public ProfileDto getProfile(@PathVariable @Min(1) Long id) {
        return profileService.getProfile(id);
    }

    @GetMapping("/user/{userId}")
    public ProfileDto getProfileByUser(@PathVariable @Min(1) Long userId) {
        return profileService.getProfileByUser(userId);
    }

    @PutMapping("/{id}")
    public ProfileDto updateProfile(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody ProfileRequest request
    ) {
        return profileService.updateProfile(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable @Min(1) Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
