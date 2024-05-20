package org.martix.blogpost.messages;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The EmailSenderService provided automatic sending messages to admin from user email
 * */

@Service
@AllArgsConstructor
public class EmailSenderService {
    private JavaMailSender mailSender;

    /**
     * Method to send an email.
     * @param toEmail The recipient's email address.
     * @param subject The subject of the email.
     * @param body The body of the email.
     */
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        //message.setFrom("aziteror50957@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail sent successfully");
    }
}
