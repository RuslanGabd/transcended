package org.example.dto;

import jakarta.validation.constraints.Size;

public record ProfileRequest(
        @Size(max = 80)
        String displayName,

        @Size(max = 500)
        String bio,

        String avatarUrl,
        String location,
        String websiteUrl
) {
}
