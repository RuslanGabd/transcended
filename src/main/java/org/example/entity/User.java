package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
