package com.mcformation.service;

import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.*;
import com.mcformation.model.database.*;
import com.mcformation.model.database.auth.CreateUserToken;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.repository.*;
import com.mcformation.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;

@Service
public class UtilisateurService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private MembreBureauNationalRepository membreBureauNationalRepository;

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Utilisateur findUtilisateurByEmail(String email){
        Utilisateur utilisateur;
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            utilisateur = utilisateurOptional.get();
        } else {
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return utilisateur;
    }

    public UtilisateurApi findUtilisateurByToken(String authorization){
        Long id =getIdUtilisateurFromAuthorization(authorization);
        Utilisateur utilisateur;
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        UtilisateurApi utilisateurApi = new UtilisateurApi();
        if (utilisateurOptional.isPresent()) {
            utilisateur = utilisateurOptional.get();
            utilisateurApi.setNomUtilisateur(utilisateur.getNomUtilisateur());
            utilisateurApi.setEmail(utilisateur.getEmail());
            switch (utilisateur.getRole().getNom()){
                case ROLE_ASSO:
                    Optional<Association> associationOptional = associationRepository.findById(id);
                    if (associationOptional.isPresent()) {
                        AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiDetail(associationOptional.get());
                        utilisateurApi.setAssociation(associationApi);
                    }
                    else{
                        throw new UnsupportedOperationException("Association non trouvée");
                    }
                    break;
                case ROLE_FORMATEUR:
                    Optional<Formateur> formateurOptional = formateurRepository.findById(id);
                    if (formateurOptional.isPresent()){
                        FormateurApi formationApi = UtilisateurMapper.INSTANCE.formateurDaoToFormateurApiDetail(formateurOptional.get());
                        utilisateurApi.setFormateur(formationApi);
                    }
                    else{
                        throw new UnsupportedOperationException("Formateur non trouvé");
                    }
                    break;
                case ROLE_BN:
                    Optional<MembreBureauNational> membreBureauNationalOptional = membreBureauNationalRepository.findById(id);
                    if (membreBureauNationalOptional.isPresent()){
                        MembreBureauNationalApi membreBureauNationalApi = UtilisateurMapper.INSTANCE.membreBureauNationalDaoTomembreBureauNationalApiDetail(membreBureauNationalOptional.get());
                        utilisateurApi.setMembreBureauNational(membreBureauNationalApi);
                    }
                    else{
                        throw new UnsupportedOperationException("Bureau national non trouvé");
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Role non trouvé");
            }
        } else {
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return utilisateurApi;
    }

    public UtilisateurDemandeApi findDemandesFavorablesByToken(String authorization) {
        Long id = getIdUtilisateurFromAuthorization(authorization);
        UtilisateurDemandeApi utilisateurDemandeApi = new UtilisateurDemandeApi();
        Optional<Association> association = associationRepository.findById(id);
        if(association.isPresent()) {
            List<Long> demandesFavorables = demandeRepository.getListDemandeInteressé(id);
            utilisateurDemandeApi.setAcronyme(association.get().getAcronyme());
            utilisateurDemandeApi.setDemandesFavorables(demandesFavorables);
        }
        else{
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return  utilisateurDemandeApi;
    }

    public FormateurApi findFormateurInformationsByToken(String authorization) {
        Long id = getIdUtilisateurFromAuthorization(authorization);
        FormateurApi formateurApi = new FormateurApi();
        Optional<Formateur> formateur = formateurRepository.findById(id);
        if(formateur.isPresent()) {
            formateurApi.setPrenom(formateur.get().getPrenom());
            formateurApi.setNom(formateur.get().getNom());
        }
        else{
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return  formateurApi;
    }

    @Transactional(rollbackOn = UnsupportedOperationException.class)
    public MessageApi modificationUtilisateur(String authorization,UtilisateurApi modificationUtilisateur){
        Utilisateur utilisateur;
        Association association;
        String email;
        String newEmail;
        String username;
        String newUsername;
        Formateur formateur;
        MembreBureauNational membreBureauNational;
        MessageApi messageApi=new MessageApi();
        Long id =getIdUtilisateurFromAuthorization(authorization);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if (utilisateurOptional.isPresent()) {
            utilisateur = utilisateurOptional.get();
            Boolean existsEmail=utilisateurRepository.existsByEmail(modificationUtilisateur.getEmail());
            if(!existsEmail) {
                utilisateur.setEmail(modificationUtilisateur.getEmail());
                utilisateurRepository.save(utilisateur);
            }
            else{
                email=utilisateur.getEmail();
                newEmail=modificationUtilisateur.getEmail();
                if(!email.equals(newEmail)){
                    throw new UnsupportedOperationException("Email déjà utilisé");
                }
            }
            Boolean existsNomUtilisateur=utilisateurRepository.existsByNomUtilisateur(modificationUtilisateur.getNomUtilisateur());
            if(!existsNomUtilisateur) {
                utilisateur.setNomUtilisateur(modificationUtilisateur.getNomUtilisateur());
                utilisateurRepository.save(utilisateur);
            }
            else{
                username=utilisateur.getNomUtilisateur();
                newUsername=modificationUtilisateur.getNomUtilisateur();
                if(!username.equals(newUsername)){
                    throw new UnsupportedOperationException("Nom utilisateur déjà utilisé");
                }
            }
            switch (utilisateur.getRole().getNom()) {
                case ROLE_ASSO:
                    Optional<Association> associationOptional = associationRepository.findById(id);
                    if (associationOptional.isPresent()) {
                        association = associationOptional.get();
                        association.setVille(modificationUtilisateur.getAssociation().getVille());
                        association.setNomComplet(modificationUtilisateur.getAssociation().getNomComplet());
                        association.setAcronyme(modificationUtilisateur.getAssociation().getAcronyme());
                        association.setCollege(modificationUtilisateur.getAssociation().getCollege());
                        associationRepository.save(association);
                    } else {
                        throw new UnsupportedOperationException("Association non trouvée");
                    }
                    break;
                case ROLE_FORMATEUR:
                    Optional<Formateur> formateurOptional = formateurRepository.findById(id);
                    if (formateurOptional.isPresent()) {
                        formateur = formateurOptional.get();
                        formateur.setNom(modificationUtilisateur.getFormateur().getNom());
                        formateur.setPrenom(modificationUtilisateur.getFormateur().getPrenom());
                        formateur.setNomComplet(modificationUtilisateur.getFormateur().getPrenom()+" "+modificationUtilisateur.getFormateur().getNom());
                        if(modificationUtilisateur.getFormateur().getDomaines()!=null) {
                            List<DomaineApi> domaineApiList = modificationUtilisateur.getFormateur().getDomaines();
                            List<Domaine> domaines = getDomainesByCode(domaineApiList);
                            formateur.setDomaines(domaines);
                        }
                        formateurRepository.save(formateur);
                    } else {
                        throw new UnsupportedOperationException("Formateur non trouvé");
                    }
                    break;
                case ROLE_BN:
                    Optional<MembreBureauNational> membreBureauNationalOptional = membreBureauNationalRepository.findById(id);
                    if (membreBureauNationalOptional.isPresent()) {
                        membreBureauNational = membreBureauNationalOptional.get();
                        membreBureauNational.setPoste(modificationUtilisateur.getMembreBureauNational().getPoste());
                        membreBureauNationalRepository.save(membreBureauNational);
                    } else {
                        throw new UnsupportedOperationException("Bureau national non trouvé");
                    }
                    break;
            }

        }
         else {
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        messageApi.setMessage("Vos informations ont été mises à jour.");
        messageApi.setCode(200);
        return messageApi;
    }

    public MessageApi modificationUtilisateurPassword(String authorization,UtilisateurChangePasswordApi utilisateurChangePassword){
        MessageApi messageApi=new MessageApi();
        Long id =getIdUtilisateurFromAuthorization(authorization);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if(utilisateurOptional.isPresent()){
            Boolean passwordMatches = passwordEncoder.matches(utilisateurChangePassword.getPassword(),utilisateurOptional.get().getPassword());
            if(passwordMatches){
                changeUserPassword(utilisateurOptional.get(),utilisateurChangePassword.getNewPassword());
            }
            else{
                throw new UnsupportedOperationException("Votre ancien mot de passe ne correspond pas");
            }
        }
        else{
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        messageApi.setMessage("Votre mot de passe à été mis à jour.");
        messageApi.setCode(200);
        return messageApi;
    }

    //PASSWORD

    public void createPasswordResetTokenForUtilisateur(Utilisateur utilisateur, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUtilisateur(utilisateur);
        myToken.setToken(token);
        passwordTokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);


        return !isTokenFound(passToken) ? "Token invalide"
                : isTokenExpired(passToken) ? "Token expiré"
                : !isTokenExpired(passToken) ? "Token valide"
                :null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpirationDate().before(cal.getTime());
    }

    public void changeUserPassword(Utilisateur utilisateur, String password) {
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateurRepository.save(utilisateur);
    }

    public Utilisateur getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        return passwordResetToken.getUtilisateur();
    }

    //EMAIL

    public String validateEmailToken(String token) {
        final CreateUserToken passToken = userTokenRepository.findByToken(token);
        return !isUserTokenFound(passToken) ? "Token invalide"
                : isUserTokenExpired(passToken) ? "Token expiré"
                : null;
    }

    public String getRoleByToken(String token){
        final CreateUserToken passToken = userTokenRepository.findByToken(token);
        return passToken.getRole().toString();
    }

    private boolean isUserTokenFound(CreateUserToken passToken) {
        return passToken != null;
    }

    private boolean isUserTokenExpired(CreateUserToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpirationDate().before(cal.getTime());
    }
    private Long getIdUtilisateurFromAuthorization(String authorization){
        String token = authorization.substring(7, authorization.length());
        return jwtUtils.getIdFromJwtToken(token);
    }
    public List<Domaine> getDomainesByCode(List<DomaineApi> domaineApiList) {
        List<Domaine> domaineDaoList = new ArrayList<>();
        for (DomaineApi domaineApi : domaineApiList) {
            Optional<Domaine> domaineDao = domaineRepository.findByCode(domaineApi.getCode());
            domaineDao.ifPresent(domaineDaoList::add);
        }
        return domaineDaoList;
    }

}
