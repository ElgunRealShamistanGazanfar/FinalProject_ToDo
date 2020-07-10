package app.service;

import app.entity.Users;
import app.repo.MyUserRepo;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterService {


    private final MyUserRepo myUserRepo;

    public RegisterService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }



    public boolean hasUsed(String email){
        List<Users> all = myUserRepo.findAll();
        List<String> collect = all.stream().map(Users::getEmail).collect(Collectors.toList());
        return collect.contains(email);

    }
}
