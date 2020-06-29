package db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * User will be created here
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private long id;

    @Column(name = "u_name")
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

}