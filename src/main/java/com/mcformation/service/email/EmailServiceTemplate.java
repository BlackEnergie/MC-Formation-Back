package com.mcformation.service.email;

import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.utils.Erole;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;


@Service
public class EmailServiceTemplate {

    private final String EMAIL = "mc.formation.web@gmail.com";
    private final String BASE_URL = "http://localhost:3000";
    private final String SIGNUP_INVITE_URL = "/inscription/";
    private final String CHANGE_PASSWORD_URL = "/api/auth/changePassword";
    private final String PREFIX_SUJET = "[MC-Formation-Web] | ";


    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailServiceTemplate(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public String envoieMailCreationCompte(String email, String token, Erole role) throws MessagingException {
        Context context = new Context();
        String url = BASE_URL + SIGNUP_INVITE_URL + token;
        context.setVariable("url", url);
        context.setVariable("role", role);
        String sujet = PREFIX_SUJET + "Inscription à Mc Formation ";

        String process = templateEngine.process("templateInscriptionMail", context);
        envoieEmail(email, sujet, process);
        return "Sent";
    }

    public void confirmationCreationCompte(String email) throws MessagingException {
        Context context = new Context();
        String sujet = PREFIX_SUJET + "Confirmation d'inscription à Mc Formation";
        String message = "Votre compte a bien été crée";
        context.setVariable("message", message);
        String process = templateEngine.process("templateConfirmation", context);

        envoieEmail(email, sujet, process);
    }

    public void envoieResetPassowrd(String token, Utilisateur utilisateur) throws MessagingException {
        Context context = new Context();
        String url = BASE_URL + CHANGE_PASSWORD_URL + "?token=" + token;
        String sujet = PREFIX_SUJET + "Changement de mot de votre passe";
        context.setVariable("url", url);
        String message = "Réinitialisez votre mot de passe";
        String process = templateEngine.process("templateMailPassword", context);
        envoieEmail(utilisateur.getEmail(), sujet, process);
    }


    public void envoieEmail(String email, String sujet, String process) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(EMAIL);
        helper.setSubject(sujet);
        helper.setText(process, true);
        helper.setTo(email);
        javaMailSender.send(mimeMessage);
    }

}
