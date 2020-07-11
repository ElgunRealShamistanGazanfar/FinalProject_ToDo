package app.auth;

import app.entity.Users;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final MyUserRepo myUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(MyUserRepo myUserRepo, PasswordEncoder passwordEncoder) {
        this.myUserRepo = myUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }


    private List<ApplicationUser> getApplicationUsers() {


        List<String> roles = myUserRepo.findAll().stream().map(Users::getRoles).collect(Collectors.toList());

        HashSet<GrantedAuthority> authorities = new HashSet<>(roles.size());
        for (String role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        List<ApplicationUser> applicationUsers =myUserRepo.findAll()
                .stream().map(e-> new ApplicationUser(e.getUsername(), passwordEncoder.encode(e.getPassword()), authorities,true,true,true,true )).collect(Collectors.toList());


        return applicationUsers;
    }



}
