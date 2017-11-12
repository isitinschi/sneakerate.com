package com.sneakerate.web.service.mail;

import com.sneakerate.web.service.status.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private StatusService statusService;

    private String sourceEmail;
    private String destEmail;

    private Session session;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public MailService(@Value("${mail.source}") String sourceEmail,
                       @Value("${mail.source.password}") String sourceEmailPassword,
                       @Value("${mail.dest}") String destEmail) {
        this.sourceEmail = sourceEmail;
        this.destEmail = destEmail;

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.username", sourceEmail);
        props.put("mail.smtp.password", sourceEmailPassword);
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.fallback", false);
        props.put("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 5000);

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sourceEmail, sourceEmailPassword);
            }
        });
    }

    public boolean send(String failureMessage) {
        return send("Sneakerate.com: query fail", failureMessage);
    }

    /**
     * Sends notification email about failed query.
     *
     * @param subject
     * @param failureMessage
     * @return true if email was successfully sent
     */
    public boolean send(String subject, String failureMessage) {
        LOGGER.info("Sending email to {} from {} with failure message: {}", destEmail, sourceEmail, failureMessage);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sourceEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destEmail));
            message.setSubject(subject);

            StringBuilder emailText = new StringBuilder();
            emailText.append("Hi, it is an email notification about failed query.");
            emailText.append("\n\nServer build params: ");
            emailText.append(statusService.getBuildVersion());
            emailText.append("\n\nFailure message:\n\n");
            emailText.append(failureMessage);
            message.setText(emailText.toString());

            Transport.send(message);

            LOGGER.info("Email was sent");
            return true;
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.error("Notification email wasn't sent. Please, investigate the problem");
        return false;
    }
}
