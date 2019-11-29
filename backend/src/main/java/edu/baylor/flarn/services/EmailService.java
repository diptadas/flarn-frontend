package edu.baylor.flarn.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import edu.baylor.flarn.exceptions.EmailSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailService {

    void sendVerificationEmail(String email, int confirmationCode) throws EmailSendingException {
        Email from = new Email("admin@flarn.com"); // just a fake sender email
        Email to = new Email(email);

        String subject = "FLARN: Confirmation Code";
        Content content = new Content("text/plain", "Your confirmation code is " + confirmationCode);

        Mail mail = new Mail(from, subject, to, content);
        log.info("Sending verification email to " + email);

        try {
            sendEmail(mail);
        } catch (IOException e) {
            throw new EmailSendingException(e);
        }
    }

    private void sendEmail(Mail mail) throws IOException {
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
