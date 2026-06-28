package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String name;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus onlineStatus;

    private LocalDateTime lastSeenAt;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Channel> channels;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "followers_id")
    )
    private List<User> followers;

    private boolean bannedStatus;

    private String about;


    public User (String Nickname, String password, String email)
    {
        this.nickname = Nickname;
        this.email = email;
        this.password = password;
        this.role = UserRoles.USER;
        this.bannedStatus = false;
    }

}
