package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dislikes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long idPost;
    @Column(name = "comment_id")
    private Long idComment;
    @Column(name = "user_id")
    private Long idUser;
}
