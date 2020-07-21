package app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private long id;


    @Column(name = "msg_text")
    private String msg_text;

    @Column(name = "sender_id")
    private int sender_id;

    @Column(name = "group_id")
    private int group_id;

    @Column(name = "sender_name")
    private String sender_name;

    @ManyToOne
    @JoinTable(name = "msg_grp",
            joinColumns = { @JoinColumn(
                    name = "msg_id",
                    referencedColumnName = "msg_id"
            ) },
            inverseJoinColumns = { @JoinColumn(
                    name = "grp_id",
                    referencedColumnName = "grp_id"
            ) }
    )
    private MyGroup myGroup;
}
