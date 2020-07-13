package app.service;

import app.entity.Users;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegisterService {

    @Autowired
    private final MyUserRepo myUserRepo;

    public RegisterService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }



    public boolean hasUsed(String email){
        List<Users> all = myUserRepo.findAll();
        List<String> collect = all.stream().map(Users::getEmail).collect(Collectors.toList());
        return collect.contains(email);
    }

    public boolean isCorrect(String fullname, String email){
        Optional<Users> allByFullNameAndEmail = myUserRepo.getAllByFullNameAndEmail(fullname, email);
        return allByFullNameAndEmail.isPresent();
    }

    public Optional<Users> giveMeUser(String fullname, String email){
        Optional<Users> allByFullNameAndEmail = myUserRepo.getAllByFullNameAndEmail(fullname, email);
        return allByFullNameAndEmail;
    }

    public Optional<Users> logged_user(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        return myUserRepo.findByUsername(username);
    }



}
