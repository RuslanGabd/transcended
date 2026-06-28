package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    @OneToMany
    private List<Like> likes;
    @OneToMany
    private List<Dislike> dislikes;

    private LocalDateTime dataCreated;
    private LocalDateTime dataEdited;
    private LocalDateTime dataDeleted;
}
