package app.controller;

import app.entity.Mail;
import app.service.Sender;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController("/")
public class MailController {
    private Sender sender;

    @PostMapping("/send")
    public void sendMessage(@RequestParam("mail") String mail,@RequestParam("subject") String subject ,@RequestParam("text") String text) {
        sender.sendMail(mail, subject, text);

    }
}