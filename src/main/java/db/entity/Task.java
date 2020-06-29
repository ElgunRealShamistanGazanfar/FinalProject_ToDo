package db.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Tasks will be created here
 */

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tsk_id")
    private long id;

    @Column(name = "tsk_content")
    private String content;

    @ManyToOne
    @JoinTable(name = "tsk_user",
            joinColumns = { @JoinColumn(
                    name = "tsk_id",
                    referencedColumnName = "tsk_id"
            ) },
            inverseJoinColumns = { @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "u_id"
            ) }
    )
    private User user;
}