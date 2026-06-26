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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    private Long idChannel;
    private String title;
    private String content;

    @OneToMany
    private List<Like> likes;
    @OneToMany
    private List<Dislike> dislikes;
    @OneToMany
    private List<Comment> comments;

    private LocalDateTime dataCreated;
    private LocalDateTime dataEdited;
    private LocalDateTime dataDeleted;

    public Post(String Title, String Text) {
        this.title = Title;
        this.content = Text;
        this.dataCreated = LocalDateTime.now();
    }

    public Post(User user, String title, String content) {
        this.user = user;
    }
}
