package app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

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
    private Date deadline;

    @Column(name = "tsk_curr")
    private LocalDate curr;

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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deadline='" + deadline + '\'' +
                ", curr='" + curr + '\'' +
                ", content='" + content + '\'' +
                ", complement_status='" + complement_status + '\'' +
                '}';
    }
}