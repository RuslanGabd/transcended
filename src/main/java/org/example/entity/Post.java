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
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;
    @OneToMany(mappedBy = "post")
    private List<Dislike> dislikes;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    private LocalDateTime dataCreated;
    private LocalDateTime dataEdited;
    private LocalDateTime dataDeleted;

    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
}
