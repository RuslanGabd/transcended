package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
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
    private UserRoles role;

    @OneToMany
    private List<Post> posts;
    @OneToMany
    private List<Channel> channels;

    private boolean onlineStatus;
    private boolean bannedStatus;


    public User (String Nickname, String password, String email)
    {
        this.nickname = Nickname;
        this.email = email;
        this.password = password;
        this.role = UserRoles.USER;
        this.bannedStatus = false;
    }

}
