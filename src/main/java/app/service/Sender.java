package app.service;

import app.entity.EmailStatus;
import com.sun.xml.fastinfoset.sax.Properties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Log4j2
@Service
@AllArgsConstructor
public class Sender {
    private final JavaMailSender javaMailSender;

    public EmailStatus sendMail(String to, String subject, String text) {
        try {
            InternetAddress addressFrom = new InternetAddress("username", "Elbrus Garayev");
            MimeMessage mail = javaMailSender.createMimeMessage();

            mail.setFrom(addressFrom);
            mail.setSender(addressFrom);
            mail.setSubject(subject);
            mail.setContent(text, "text/plain");
            mail.addRecipients(Message.RecipientType.TO, to);
            javaMailSender.send(mail);
            java.util.Properties props = new java.util.Properties();
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            log.info("Send email '{}' to: {}", subject, to);

            return new EmailStatus(to, subject, text).success();

        } catch (Exception e) {

            log.error(String.format("Problem with sending email to: %s, error message: %s", to, e.getMessage()));

            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }
}