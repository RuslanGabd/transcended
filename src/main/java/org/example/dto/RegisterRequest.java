package org.example.dto;

public record RegisterRequest(
        String nickname,
        String password,
        String email,
        String name,
        String lastName,
        String phone
) {
}