package app.session;


import app.entity.MyUser;
import lombok.Data;

import javax.persistence.Column;

@Data
public class TaskDetails {

    public final static String ATTR = "td";

    private String title;

    private String content;

}
