package app.service;

import app.entity.MyGroup;
import app.entity.MyUser;
import app.repo.GroupRepo;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;

@Service
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
            MyGroup myGroup = new MyGroup(groupById.getGroupName(), groupById.getGroupPass(), groupById.getGroupDesc());

            myGroup.setUsers_g(Arrays.asList(logged_user));
            groupRepo.save(myGroup);
        }else {
           model.addAttribute("grp_msg", "You are already in this team");
        }

        groupRepo.deleteById(id);

    }


    public void createGroup(String groupName, String groupPass, String groupDesc) {
        MyGroup newGroup = new MyGroup(groupName, groupPass, groupDesc);
        newGroup.setUsers_g(Arrays.asList(registerService.logged_user().get()));
        groupRepo.save(newGroup);
    }
}
