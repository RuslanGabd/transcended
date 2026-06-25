package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    private User admin;

    @OneToMany
    private List<User> moderators;
    @OneToMany
    private List<User> members;
    @OneToMany
    private List<Post> posts;

    public Channel(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
