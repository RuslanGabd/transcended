package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private Long idPost;
    private Long idUser;
    private String content;
    private List<Like> likes;
    private List<Dislike> dislikes;
    private LocalDateTime dataCreated;
    private LocalDateTime dataEdited;
    private LocalDateTime dataDeleted;
}
