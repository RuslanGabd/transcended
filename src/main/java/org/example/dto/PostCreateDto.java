package org.example.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PostCreateDto {


        @NotNull(message = "User ID is required")
        private Long userId;

        @NotBlank(message = "Title is required")
        private String title;

        @NotBlank(message = "Post can not be empty") @Size(max = 1024)
        private String content;

}
