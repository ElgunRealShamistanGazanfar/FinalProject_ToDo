package app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
public class Users {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;




    @OneToMany(mappedBy = "users")
    private Set<Task> tasks;

    public Users() {
    }


    public Users(String fullName, String username, String email, String password, String roles) {
            this.fullName = fullName;
            this.username = username;
            this.email = email;
            this.password = password;
            this.roles = roles;
    }
}
