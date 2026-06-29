package org.example.dto;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}