package app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
public class MyUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "profile")
    @Lob
    private byte [] profile;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;


    @OneToMany(mappedBy = "myUser")
    private Set<Task> tasks;

    public MyUser() {
    }


    public MyUser(String fullName, String username, String email, String password, String roles, Set<Task> tasks) {
            this.fullName = fullName;
            this.username = username;
            this.email = email;
            this.password = password;
            this.roles = roles;
            this.tasks=tasks;
    }

    public MyUser(String fullname, String username, String email, String password, String roles) {
        this.fullName = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
