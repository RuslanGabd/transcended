package org.example.dto;

public record LoginRequest(
        String nickname,
        String password
) {
}