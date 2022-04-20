package com.mcformation.service.email;

import com.mcformation.model.database.Role;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.utils.Erole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EmailService {

    private final String EMAIL = "mc.formation.web@gmail.com";
    private final String PREFIX_SUJET = "[MC-Formation-Web] | ";
    private final String BASE_URL = "http://localhost:8080";
    private final String CHANGE_PASSWORD_URL = "/api/auth/changePassword";
    private final String SIGNUP_INVITE_URL = "/api/auth/signup/invite";



    @Autowired
    private JavaMailSender emailSender;



    public void sendNewUserNotification(String email, String username, Role role) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        StringBuilder subject = new StringBuilder("Nouvel utilisateur ").append(role).append(" créé : '").append(username).append("' ");
        StringBuilder message = new StringBuilder("Nouvel utilisateur créé le ").append(formatter.format(date)).append(" : '").append(username).append("' ").append("avec email : '").append(email).append("', role : ").append(role);
        sendSimpleMessage("mc.formation.web@gmail.com", subject.toString(), message.toString());
    }

    public void sendResetTokenEmail(String token, Utilisateur utilisateur) {
        String url = BASE_URL + CHANGE_PASSWORD_URL + "?token=" + token;
        String message = "Réinitialisez votre mot de passe";
        sendSimpleMessage(utilisateur.getEmail(), message, message + ": " + url);
    }

    public void sendCreateUserTokenEmail(String token,String email) {
        String url = BASE_URL + SIGNUP_INVITE_URL + "?token=" + token;
        String message = "Veuillez créer votre utilisateur";
        sendSimpleMessage(email, message, message + ": " + url);
    }

    public void confirmCreateUserEmail(String email){
        String message = "Votre compte a bien été crée";
        sendSimpleMessage(email, "Confirmation création compte", message);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL);
        message.setTo(to);
        message.setSubject(PREFIX_SUJET + subject);
        message.setText(text);
        emailSender.send(message);
    }





}
