package app.session;

import lombok.Data;

@Data
public class UserDetails {

    public final static String ATTR = "ud";

    private long id;
    private String fullName;
    private String mail;
    private String password;
}
