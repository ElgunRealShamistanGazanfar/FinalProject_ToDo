package app.service;

import app.entity.MyMessage;
import app.entity.MyUser;
import app.repo.GroupRepo;
import app.repo.MessageRepo;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private final MyUserRepo myUserRepo;

    @Autowired
    private final GroupRepo groupRepo;

    @Autowired
    private final MessageRepo messageRepo;

    @Autowired
    private final RegisterService registerService;

    public MessageService(MyUserRepo myUserRepo, GroupRepo groupRepo, MessageRepo messageRepo, RegisterService registerService) {
        this.myUserRepo = myUserRepo;
        this.groupRepo = groupRepo;
        this.messageRepo = messageRepo;
        this.registerService = registerService;
    }

    public void getMessages(int groupId, Model model) {
        final List<MyMessage> allByMyGroup_id = messageRepo.findAllByMyGroup_Id(groupId);

        model.addAttribute("messages", allByMyGroup_id);

    }



    public void send(int groupId, String msg_text) {
        final MyUser loggedUser = registerService.logged_user().get();
        MyMessage myMessage = new MyMessage();
        myMessage.setSender_name(loggedUser.getFullName());
        myMessage.setSender_id((int)loggedUser.getId());
        myMessage.setGroup_id(groupId);
        myMessage.setMsg_text(msg_text);
        messageRepo.save(myMessage);
    }

    public List<MyMessage> fetchAllByGroupId(int groupId) {
        return messageRepo.findAllByMyGroup_Id(groupId);
    }
}
