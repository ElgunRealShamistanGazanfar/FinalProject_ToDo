package app.entity;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Data
@Transactional
@Entity
public class MyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grp_id")
    private int id;

    @Column(name = "grp_name")
    private String groupName;

    @Column(name = "grp_password")
    private String groupPass;

    @Column(name = "grp_desc")
    private String groupDesc;



    @ManyToMany
    @JoinTable(name = "group_user",
            joinColumns = { @JoinColumn(
                    name = "grp_id",
                    referencedColumnName = "grp_id"
            ) },
            inverseJoinColumns = { @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "usr_id"
            ) }
    )
    private List<MyUser> users_g;

    public MyGroup() {
    }
    public MyGroup(String groupName, String groupPass, String groupDesc) {
        this.groupName = groupName;
        this.groupPass= groupPass;
        this.groupDesc= groupDesc;
    }
}