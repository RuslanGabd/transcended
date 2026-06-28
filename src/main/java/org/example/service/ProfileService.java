package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProfileDto;
import org.example.dto.ProfileRequest;
import org.example.entity.Profile;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.mapper.ProfileMapper;
import org.example.repo.ProfileRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    public ProfileDto createProfile(Long userId, ProfileRequest request) {
        if (profileRepository.existsByUserId(userId)) {
            throw new IllegalStateException("Profile already exists for user " + userId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Profile profile = new Profile();
        profile.setUser(user);
        applyRequest(profile, request);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        return profileMapper.toDto(profileRepository.save(profile));
    }

    public ProfileDto getProfile(Long id) {
        return profileMapper.toDto(findProfile(id));
    }

    public ProfileDto getProfileByUser(Long userId) {
        return profileMapper.toDto(profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user " + userId)));
    }

    public ProfileDto updateProfile(Long id, ProfileRequest request) {
        Profile profile = findProfile(id);
        applyRequest(profile, request);
        profile.setUpdatedAt(LocalDateTime.now());
        return profileMapper.toDto(profileRepository.save(profile));
    }

    public void deleteProfile(Long id) {
        profileRepository.delete(findProfile(id));
    }

    private Profile findProfile(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found " + id));
    }

    private void applyRequest(Profile profile, ProfileRequest request) {
        profile.setDisplayName(request.displayName());
        profile.setBio(request.bio());
        profile.setAvatarUrl(request.avatarUrl());
        profile.setLocation(request.location());
        profile.setWebsiteUrl(request.websiteUrl());
    }
}
