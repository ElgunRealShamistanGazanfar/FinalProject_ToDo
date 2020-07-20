package app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
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

    @ManyToMany(mappedBy = "users_g")
    private Set<MyGroup> groups;

    @OneToMany(mappedBy = "myUser")
    private List<Task> tasks;

    public MyUser() {
    }


    public MyUser(String fullName, String username, String email, String password, String roles, List<Task> tasks) {
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
