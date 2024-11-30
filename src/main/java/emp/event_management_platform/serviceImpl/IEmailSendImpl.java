package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.service.IEmailSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class IEmailSendImpl implements IEmailSend {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String subject, String body) {
     SimpleMailMessage message = new SimpleMailMessage();
     System.out.println("Sending email form send methode");
     message.setTo(to);
     message.setSubject(subject);
     message.setText(body);
     message.setFrom("mohamedstage2023@gmail.com");
     mailSender.send(message);
    }
}
