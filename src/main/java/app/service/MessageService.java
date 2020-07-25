package app.service;

import app.entity.MyGroup;
import app.entity.MyMessage;
import app.entity.MyUser;
import app.repo.GroupRepo;
import app.repo.MessageRepo;
import app.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.acl.Group;
import java.util.Arrays;
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


    public void send(int groupId, String msg_txt) {
        MyMessage myMessage = new MyMessage();

        final MyGroup myGroup = groupRepo.findById(groupId).get();
        myMessage.setMsg_text(msg_txt);
        myMessage.setSender_name(registerService.logged_user().getFullName());
        myMessage.setSender_id((int) registerService.logged_user().getId());
        myMessage.setGroup_id(groupId);
        myGroup.setMessages(Arrays.asList(myMessage));
        myMessage.setMyGroup(myGroup);
        messageRepo.save(myMessage);

    }


}
