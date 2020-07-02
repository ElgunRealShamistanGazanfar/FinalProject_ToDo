package app.entity;

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

    @Column(name = "tsk_title")
    private String title;

    @Column(name = "tsk_deadline")
    private String deadline;

    @Column(name = "tsk_curr")
    private String curr;

    @Column(name = "tsk_content")
    private String content;

    @Column(name = "tsk_complement_status")
    private String complement_status;

    @ManyToOne
    @JoinTable(name = "tsk_user",
            joinColumns = { @JoinColumn(
                    name = "tsk_id",
                    referencedColumnName = "tsk_id"
            ) },
            inverseJoinColumns = { @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "usr_id"
            ) }
    )
    private MyUser my_user;
}