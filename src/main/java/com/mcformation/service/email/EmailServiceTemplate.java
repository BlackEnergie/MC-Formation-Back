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

    private final String MIAGE_CONNECTION_LOGO = "https://www.miage-connection.fr/wp-content/uploads/2021/06/cropped-logo_wht_bgblue-1.png";
    private final String LINKEDIN_LOGO = "https://www.miage.net/wp-content/uploads/2020/01/linkedin.png";
    private final String TWITTER_LOGO = "https://www.miage.net/wp-content/uploads/2020/01/twitter.png";
    private final String FACEBOOK_LOGO = "https://www.miage.net/wp-content/uploads/2020/01/facebook.png";
    private final String WEBSITE_LOGO = "https://www.miage.net/wp-content/uploads/2020/01/web.png";

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailServiceTemplate(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public String envoieMailCreationCompte(String email, String token, Erole role) throws MessagingException {
        String url = BASE_URL + SIGNUP_INVITE_URL + token;
        Context context = createContext(url, role.toString(), "");
        String sujet = PREFIX_SUJET + "Inscription à Mc Formation ";
        String process = templateEngine.process("email/templateInscriptionMail", context);
        envoieEmail(email, sujet, process);
        return "Sent";
    }

    public void confirmationCreationCompte(String email, String nomUtilisateur) throws MessagingException {
        String sujet = PREFIX_SUJET + "Confirmation d'inscription à Mc Formation";
        String message = "Votre compte a bien été créé, votre nom d'utilisateur est :";
        Context context = createContext("", "", message);
        context.setVariable("nomUtilisateur", nomUtilisateur);
        String process = templateEngine.process("email/templateConfirmation", context);
        envoieEmail(email, sujet, process);
    }

    public void envoieResetPassowrd(String token, Utilisateur utilisateur) throws MessagingException {
        String url = BASE_URL + CHANGE_PASSWORD_URL + "?token=" + token;
        String sujet = PREFIX_SUJET + "Changement de mot de votre passe";
        String message = "Réinitialisez votre mot de passe";
        Context context = createContext(url,"", message);
        String process = templateEngine.process("email/templateMailPassword", context);
        envoieEmail(utilisateur.getEmail(), sujet, process);
    }

    private Context createContext(String url, String role, String message) {
        Context context = new Context();
        if (!url.equals("")) {
            context.setVariable("url", url);
        }
        if (!role.equals("")) {
            context.setVariable("role", role);
        }
        if (!message.equals("")) {
            context.setVariable("message", message);
        }
        context.setVariable("imgMiageConnection", MIAGE_CONNECTION_LOGO);
        context.setVariable("imgLinkedin", LINKEDIN_LOGO);
        context.setVariable("imgTwitter", TWITTER_LOGO);
        context.setVariable("imgFacebook", FACEBOOK_LOGO);
        context.setVariable("imgWebsite", WEBSITE_LOGO);
        return context;
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
