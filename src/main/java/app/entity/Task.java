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
@AllArgsConstructor
@Entity
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tsk_id")
    private int id;

    @Column(name = "tsk_title")
    private String title;

    @Column(name = "tsk_deadline")
    private Date deadline;

    @Column(name = "image")
    @Lob
    private byte [] image;

    @Column(name = "tsk_curr")
    private LocalDate curr;

    @Column(name = "tsk_content")
    private String content;

    @Column(name = "status")
    private String status;

    @Column(name = "tsk_complement_status")
    private Boolean complete;


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
    private MyUser myUser;

    public Task(){

    }
    public Task(String title, Date deadline,  LocalDate curr,String status, String content, Boolean complete, MyUser myUser) {
        this.title = title;
        this.deadline = deadline;
        this.curr = curr;
        this.content = content;
        this.status = status;
        this.complete = complete;
        this.myUser = myUser;
    }
    public Task(Boolean complete){
        this.complete = complete;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deadline='" + deadline + '\'' +
                ", curr='" + curr + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", complete='" + complete + '\'' +
                '}';
    }
}