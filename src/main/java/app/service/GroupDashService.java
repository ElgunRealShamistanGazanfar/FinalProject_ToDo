package app.service;

import app.entity.MyGroup;
import app.entity.MyUser;
import app.entity.Task;
import app.repo.GroupRepo;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupDashService {

    @Autowired
    private final TaskService taskService;

    @Autowired
    private final MyUserRepo myUserRepo;

    @Autowired
    private final GroupRepo groupRepo;

    @Autowired
    private final RegisterService registerService;

    public GroupDashService(TaskService taskService, MyUserRepo myUserRepo, GroupRepo groupRepo, RegisterService registerService) {
        this.taskService = taskService;
        this.myUserRepo = myUserRepo;
        this.groupRepo = groupRepo;
        this.registerService = registerService;
    }


    @Transactional
    public Page<Task> fetchTasksByGroupId(Pageable pageable, int groupId) {

        MyGroup myGroup = groupRepo.findById(groupId).get();
        List<MyUser> usersOfGroup = myUserRepo.findAllByGroups(myGroup);
        List<Task> allTasks = new ArrayList<>();
        for(MyUser user : usersOfGroup){
            allTasks.addAll(user.getTasks());

        }
        Page<Task> res =new PageImpl<Task>(allTasks,pageable, allTasks.size());
        return res;
    }
}
