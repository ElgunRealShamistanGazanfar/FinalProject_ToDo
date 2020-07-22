package app.service;

import app.entity.MyGroup;
import app.entity.MyUser;
import app.repo.GroupRepo;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Arrays;

@Service
@Transactional
public class GroupService {

    @Autowired
    private final MyUserRepo myUserRepo;

    @Autowired
    private final GroupRepo groupRepo;

    @Autowired
    private final RegisterService registerService;

    public GroupService(MyUserRepo myUserRepo, GroupRepo groupRepo, RegisterService registerService) {
        this.myUserRepo = myUserRepo;
        this.groupRepo = groupRepo;
        this.registerService = registerService;
    }

    public void AddUserToGroup(int id, Model model) {

        MyGroup groupById = groupRepo.findById(id).get();

        MyUser logged_user = registerService.logged_user().get();
        if (!groupById.getUsers_g().contains(logged_user)){
                groupById.getUsers_g().add(logged_user);
                groupRepo.save(groupById);

        }else {
           model.addAttribute("grp_msg", "You are already in this team");
        }

    }


    public void createGroup(String groupName, String groupPass, String groupDesc) {
        MyGroup newGroup = new MyGroup(groupName, groupPass, groupDesc);
        newGroup.setUsers_g(Arrays.asList(registerService.logged_user().get()));
        groupRepo.save(newGroup);
    }
}
