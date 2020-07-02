package app.session;


import lombok.Data;

import javax.persistence.Column;

@Data
public class TaskDetails {

    public final static String ATTR = "td";

    private String title;

    private String deadline;

    private String curr;

    private String content;

    private String complement_status;
}
