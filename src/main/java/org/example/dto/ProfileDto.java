package org.example.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProfileDto(
        Long id,
        Long userId,
        String displayName,
        String bio,
        String avatarUrl,
        String location,
        String websiteUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
