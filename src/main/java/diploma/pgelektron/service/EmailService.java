package diploma.pgelektron.service;

import com.sun.mail.smtp.SMTPTransport;
import diploma.pgelektron.constant.EmailConstant;
import diploma.pgelektron.person.dto.domain.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import static diploma.pgelektron.constant.EmailConstant.*;
import static diploma.pgelektron.constant.SecurityConstant.BASE_URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(ELASTIC_EMAIL_HOST,USERNAME,PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    public void sendNewUserEmail(String firstName, String password, String email, String username) throws MessagingException {
        Message message = createNewUserEmail(firstName,password,email, username);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(ELASTIC_EMAIL_HOST,USERNAME,PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    public void sendVerificationEmail(PersonDto dto) throws MessagingException {
        String recipientAddress = dto.getEmail();
        String fromAddress = FROM_EMAIL;
        String senderName = USERNAME;
        String subject = "Erősítse meg az email címét";
        String content = "Tisztelt [[name]], <br>"
                + "Ahhoz meg tudja erősíteni az email címét, és tudja használni a fiókja, kattintson a következő linkre:<br>"
                +"<h3> <a href=\"[[URL]]\" target=\"_self\"> MEGERŐSÍTÉS </a></h3>"
                + "Köszönjük, <br>"
                + "PG - Elektron csapata";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(new InternetAddress(fromAddress));
        messageHelper.setTo(recipientAddress);
        messageHelper.setSubject(subject);
        content = content.replace("[[name]]",dto.getFirstName());
        String verifyURL = BASE_URL + "/verify/?code="+ dto.getVerification();
        content = content.replace("[[URL]]", verifyURL);
        messageHelper.setText(content,true);
        emailSender.send(message);
    }
    private Message createNewUserEmail(String firstName, String password, String email, String username) {
        Message message = new MimeMessage(getEmailSession());
        try {
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_EMAIL, false));
            message.setSubject(EMAIL_SUBJECT);
            message.setText("Hello " + firstName +
                    "\n\n Your new account username is: " + username +
                    "\n\n Your new account password is: " + password + "\n\n The Support Team.");
            message.setSentDate(new Date());
            message.saveChanges();
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private Message createEmail(String firstName, String password, String email) {
        Message message = new MimeMessage(getEmailSession());
        try {
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_EMAIL, false));
            message.setSubject(EMAIL_SUBJECT);
            message.setText("Hello " + firstName +
                    "\n\n Your new account password is: " + password + "\n\n The Support Team.");
            message.setSentDate(new Date());
            message.saveChanges();
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo("sugeakos.spotify@gmail.com");
        message.setSubject("Proba1");
        message.setText("jdfjhsgfjhshjf");
        emailSender.send(message);
    }

        private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, ELASTIC_EMAIL_HOST);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

//    public void send() throws AddressException, MessagingException, IOException {
//        Message msg = new MimeMessage(setSession(setProperties()));
//
//        msg.setSentDate(new Date());
//        msg.setSubject("You're subscribed on newsletter");
//
//        msg.setFrom(new InternetAddress(FROM_EMAIL, false));
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sugeakos.spotify@gmail.com"));
//
//        msg.setContent("sdjhgksdgjsjhf TESZT", "text/html");
//
//        Transport.send(msg);
//    }
//    private Session setSession(Properties props) {
//        return Session.getInstance(props, new javax.mail.Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(USERNAME, PASSWORD);
//            }
//        });
//    }
//
//    private Properties setProperties() {
//        Properties props = new Properties();
//
//        props.put("mail.smtp.port", 587);
//        props.put("mail.smtp.host", "smtp.mailtrap.io");
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//
//        return props;
//    }
}
