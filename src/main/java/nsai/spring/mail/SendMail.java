package nsai.spring.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Marcin on 27.04.2016.
 */

@Component
public class SendMail {

    @Autowired
    private JavaMailSender mSender;

    public void send(String to,String subject,String body) throws MessagingException{
        MimeMessage message = mSender.createMimeMessage();
        MimeMessageHelper helper;

        helper = new MimeMessageHelper(message,true);

        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(body,true);

        mSender.send(message);
    }
}
