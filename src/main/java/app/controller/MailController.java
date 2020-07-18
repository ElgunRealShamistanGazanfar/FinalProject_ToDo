package app.controller;

import app.entity.Mail;
import app.service.Sender;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController("/")
public class MailController {
    private Sender sender;

    @GetMapping("/send")
    public String sendMessage_get() {
        return "mail";
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody Mail mail) {
        sender.sendMail(mail.to, mail.subject, mail.text);
    }
}