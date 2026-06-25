package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    private Long id;

    private String Nickname;
    private String Password;

    private String Name;
    private String LastName;
    private String Email;
    private String Phone;

    public User (String Nickname, String password, String email)
    {
        this.Nickname = Nickname;
        this.Email = email;
        this.Password = password;
    }

}
