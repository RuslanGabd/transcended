package org.example.dto;

import lombok.Builder;
import org.example.entity.UserRoles;

import java.util.List;

@Builder
public record UserDto(
        Long id,
        String nickname,
        String name,
        String lastName,
        String email,
        String phone,
        UserRoles role,
        List<Long> postIds,
        List<Long> channelIds,
        String about
) {
}