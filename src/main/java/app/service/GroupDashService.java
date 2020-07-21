package app.service;

import app.entity.MyGroup;
import app.entity.MyUser;
import app.entity.Task;
import app.repo.GroupRepo;
import app.repo.MyUserRepo;
import app.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupDashService {

    @Autowired
    private final TaskService taskService;

    @Autowired
    private final MyUserRepo myUserRepo;

    @Autowired
    private final TaskRepo taskRepo;

    @Autowired
    private final GroupRepo groupRepo;

    @Autowired
    private final RegisterService registerService;

    public GroupDashService(TaskService taskService, MyUserRepo myUserRepo, TaskRepo taskRepo, GroupRepo groupRepo, RegisterService registerService) {
        this.taskService = taskService;
        this.myUserRepo = myUserRepo;
        this.taskRepo = taskRepo;
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
    public Optional<Task> findTaskById(int id) {

        return taskRepo.findById(id);
    }

    public Boolean isDone(int task_id) {
        Optional<Task> byId = this.findTaskById(task_id);
        return byId.orElse(new Task(false)).getComplement_status();

    }

    public static Boolean isOverdue(LocalDate curr, Date deadline) {
        Date curr_date = java.sql.Date.valueOf(curr);
        return curr_date.compareTo(deadline) > 0;
    }


    public Page<Task> pageForImportant(Pageable pageable, int groupId){
        List<Task> pg = fetchTasksByGroupId(pageable,groupId)
                .stream().filter(t -> t.getStatus().equals("important"))
                .collect(Collectors.toList());
        Page<Task> res =new PageImpl<Task>(pg,pageable, pg.size());

        return res;
    }

    public Page<Task> pageForOverdue(Pageable pageable, int groupId){
        List<Task> pg = fetchTasksByGroupId(pageable,groupId)
                .stream().filter(e -> isOverdue(LocalDate.now(), e.getDeadline()))
                .collect(Collectors.toList());
        Page<Task> res =new PageImpl<Task>(pg,pageable, pg.size());
        return res;
    }

    public Page<Task> pageForDone(Pageable pageable, int groupId){
        List<Task> pg = fetchTasksByGroupId(pageable,groupId)
                .stream().filter(e -> isDone(e.getId()))
                .collect(Collectors.toList());
        Page<Task> res =new PageImpl<Task>(pg,pageable, pg.size());
        return res;
    }
    public Page<Task> pageForToday(Pageable pageable, int groupId){
        List<Task> pg = fetchTasksByGroupId(pageable,groupId)
                .stream().filter(t->t.getDeadline()
                        .equals(java.sql.Date.valueOf(LocalDate.now())))
                .collect(Collectors.toList());
        Page<Task> res =new PageImpl<Task>(pg,pageable, pg.size());
        return res;
    }

}
