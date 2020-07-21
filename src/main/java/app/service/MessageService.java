//package app.service;
//
//import app.entity.MyGroup;
//import app.entity.MyMessage;
//import app.entity.MyUser;
//import app.repo.GroupRepo;
//import app.repo.MessageRepo;
//import app.repo.MyUserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//
//import java.security.acl.Group;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class MessageService {
//
//    @Autowired
//    private final MyUserRepo myUserRepo;
//
//    @Autowired
//    private final GroupRepo groupRepo;
//
//    @Autowired
//    private final MessageRepo messageRepo;
//
//    @Autowired
//    private final RegisterService registerService;
//
//    public MessageService(MyUserRepo myUserRepo, GroupRepo groupRepo, MessageRepo messageRepo, RegisterService registerService) {
//        this.myUserRepo = myUserRepo;
//        this.groupRepo = groupRepo;
//        this.messageRepo = messageRepo;
//        this.registerService = registerService;
//    }
//
//    public void getMessages(int groupId, Model model) {
//        final List<MyMessage> allByMyGroup_id = messageRepo.findAllByMyGroup_Id(groupId);
//
//        allByMyGroup_id.stream().map(m->m.getMyGroup()).forEach(g->g.setActive(true));
//
//        model.addAttribute("messages", allByMyGroup_id);
//
//    }
//
//
//
//
//    public void send(String msg_text) {
//
//    }
//
//    public void setGroupActive(int groupId) {
//        final MyGroup myGroup = groupRepo.findById(groupId).get();
//        myGroup.setActive(true);
//
//    }
//}
