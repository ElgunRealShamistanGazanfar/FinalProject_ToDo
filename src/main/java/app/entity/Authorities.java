package app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;


}
