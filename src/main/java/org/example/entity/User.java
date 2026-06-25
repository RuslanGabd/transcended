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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String password;

    private String name;
    private String lastName;
    private String email;
    private String phone;

    @OneToMany
    private List<Post> posts;
    @OneToMany
    private List<Channel> channels;


    public User (String Nickname, String password, String email)
    {
        this.nickname = Nickname;
        this.email = email;
        this.password = password;
    }

}
