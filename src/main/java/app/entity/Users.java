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

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;




    @OneToMany(mappedBy = "users")
    private Set<Task> tasks;

    public Users() {
    }


    public Users(String jim, String encode) {
    }
}
