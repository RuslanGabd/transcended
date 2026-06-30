package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class PostDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Post can not be empty") @Size(max = 1024)
    private String content;

    private Long likes;

    private Long dislikes;

    private List<Long> idComments;

    private Long idChannel;

    @NotNull
    private LocalDateTime dataCreated;

    private LocalDateTime dataEdited;
    private LocalDateTime dataDeleted;
}