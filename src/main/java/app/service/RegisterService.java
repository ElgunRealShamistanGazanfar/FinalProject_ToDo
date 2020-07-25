package app.service;

import app.entity.MyUser;
import app.repo.MyUserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegisterService {

    @Autowired
    private final MyUserRepo myUserRepo;

    public RegisterService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }


    public boolean hasUsed(String email) {
        List<MyUser> all = myUserRepo.findAll();
        List<String> collect = all.stream().map(MyUser::getEmail).collect(Collectors.toList());
        return collect.contains(email);
    }

    public boolean isCorrect(String fullname, String email) {
        Optional<MyUser> allByFullNameAndEmail = myUserRepo.getAllByFullNameAndEmail(fullname, email);
        return allByFullNameAndEmail.isPresent();
    }

    public Optional<MyUser> giveMeUser(String fullname, String email) {
        Optional<MyUser> allByFullNameAndEmail = myUserRepo.getAllByFullNameAndEmail(fullname, email);
        return allByFullNameAndEmail;
    }

    public MyUser logged_user() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        return myUserRepo.findByUsername(username).get();
    }

    // Save image to a DB
    @SneakyThrows
    public void addProfileAndSaveToDB(MyUser user, MultipartFile imageFile) {

        try {
            byte[] byteObj = new byte[imageFile.getBytes().length];
            int i = 0;
            for (byte b : imageFile.getBytes()) {
                byteObj[i++] = b;
            }
            user.setProfile(byteObj);
            myUserRepo.save(user);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addProfile(Model model) {
        if (logged_user().getProfile() != null) {
            model.addAttribute("profile", "/showProfile");
        } else {
            model.addAttribute("profile", "/img/user-icon-with-background.svg");
        }
    }


}
