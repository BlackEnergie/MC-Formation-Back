package com.mcformation.service;

import com.mcformation.mapper.FormationApiMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.*;
import com.mcformation.model.database.*;
import com.mcformation.model.utils.Erole;
import com.mcformation.model.utils.StatutDemande;
import com.mcformation.repository.*;
import com.mcformation.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private DemandeService demandeService;
  
    Logger logger = LoggerFactory.getLogger(FormationService.class);

    public List<FormationApi> getFormationsAccueil() {
        List<FormationApi> formationApiList = new ArrayList<>();
        List<Demande> demandeList = (List<Demande>) demandeRepository.findAll();
        for (Demande demande : demandeList) {
            FormationApi formationApi = FormationApiMapper.INSTANCE.demandeDaoToFormationApiAccueil(demande);
            Association association = associationRepository.findByDemandes(demande);
            AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiAccueil(association);
            formationApi.setAssociation(associationApi);
            formationApiList.add(formationApi);
        }
        return formationApiList;
    }

    @Transactional(rollbackFor = UnsupportedOperationException.class)
    public FormationApi getFormation(Long id) {
        FormationApi formationApi;
        Optional<Demande> demande = demandeRepository.findById(id);
        if (demande.isPresent()) {
            formationApi = FormationApiMapper.INSTANCE.demandeDaoToFormationApiDetail(demande.get());
            Association association = associationRepository.findByDemandes(demande.get());
            AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiAccueil(association);
            formationApi.setAssociation(associationApi);
        } else {
            throw new UnsupportedOperationException("La formation/demande n'existe pas.");
        }
        return formationApi;
    }

    @Transactional(rollbackFor = UnsupportedOperationException.class)
    public MessageApi putModification(FormationApi formationApi) {
        MessageApi messageApi = new MessageApi();
        Formation formationToSave = FormationApiMapper.INSTANCE.formationApiToFormationDao(formationApi);
        Demande demandeToSave = FormationApiMapper.INSTANCE.formationApiToDemandeDao(formationApi);
        Optional<Formation> formation = formationRepository.findById(formationToSave.getId());
        if (!formation.isPresent()) {
            throw new UnsupportedOperationException("Formation non présente");
        }
        Long idDemande = demandeRepository.getDemandeIdByFormationId(formation.get().getId());
        demandeToSave.setId(idDemande);
        List<DomaineApi> domaineApiList = formationApi.getDomaines();
        List<Domaine> domaines = demandeService.getDomainesByCode(domaineApiList);
        demandeToSave.setDomaines(domaines);
        demandeToSave.setFormation(formationToSave);
        demandeRepository.save(demandeToSave);
        List<FormateurApi> formateurApiList = formationApi.getFormateurs();
        if (formateurApiList != null && !formateurApiList.isEmpty()) {
            List<Formateur> formateurs = new ArrayList<>();
            for (FormateurApi formateur : formateurApiList) {
                Optional<Formateur> formateurOptional = formateurRepository.findByUtilisateurId(formateur.getId());
                if (formateurOptional.isPresent()) {
                    formateurs.add(formateurOptional.get());
                } else {
                    throw new UnsupportedOperationException("Le formateur ajouté à la formation n'est pas trouvé");
                }
            }
        } else if (demandeToSave.getStatut() == StatutDemande.A_VENIR || demandeToSave.getStatut() == StatutDemande.PASSEE) {
            throw new UnsupportedOperationException("La formation doit contenir au moins un formateur ");
        }

        if (demandeToSave.getStatut() == StatutDemande.PASSEE) {
            String localDate = LocalDate.now().toString();
            Date date = null;
            String message;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(localDate);
                message = "La date de la formation n'est pas passée";
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur de comparaison de la date";
            }

            if (date == null || formationToSave.getDate().after(date)) {
                throw new UnsupportedOperationException(message);
            }
        }
        formationRepository.save(formationToSave);
        messageApi.setMessage("La formation a été modifié");
        messageApi.setCode(200);
        return messageApi;
    }


    public MessageApiDataFormationApi affecterFormateurFormation(Long idUtilisateur, Long idFormation) {
        MessageApiDataFormationApi messageApi = new MessageApiDataFormationApi();

        Optional<Demande> demandeOptional = demandeRepository.findById(idFormation);
        if (!demandeOptional.isPresent()) {
            throw new UnsupportedOperationException("Formation inconnue.");
        }

        Demande demande = demandeOptional.get();
        Formation formation = demande.getFormation();

        if (demande.getStatut() != StatutDemande.A_ATTRIBUER) {
            throw new UnsupportedOperationException("Impossible de modifier les formateurs d'une formation avec un statut différent de 'à attribuer'.");
        }

        Optional<Formateur> formateurOptional = formateurRepository.findByUtilisateurId(idUtilisateur);
        if (!formateurOptional.isPresent()) {
            throw new UnsupportedOperationException("Formateur inconnu.");
        }

        Formateur formateur = formateurOptional.get();
        boolean formateurAffecte = formation.getFormateurs().contains(formateur);
        List<Formateur> formateurList = formation.getFormateurs();

        if (formateurAffecte) {
            formateurList.remove(formateur);
            messageApi.setMessage("Le formateur " + formateur.getNomComplet() + " a été retiré de la formation.");
        } else {
            formateurList.add(formateur);
            messageApi.setMessage("Le formateur " + formateur.getNomComplet() + " a été affecté à la formation.");
        }
        formation = formationRepository.save(formation);
        Demande demandeBdd = demandeRepository.findByFormationId(formation.getId());
        FormationApi formationApi = FormationApiMapper.INSTANCE.demandeDaoToFormationApiAccueil(demandeBdd);
        Association association = associationRepository.findByDemandes(demande);
        AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiAccueil(association);
        formationApi.setAssociation(associationApi);
        messageApi.setFormation(formationApi);
        messageApi.setCode(HttpStatus.OK.value());
        return messageApi;
    }

    public MessageApiDataFormationApi interesserFormation(Long idUtilisateur, Long idFormation) {
        MessageApiDataFormationApi messageApi = new MessageApiDataFormationApi();

        Optional<Demande> demandeOptional = demandeRepository.findById(idFormation);
        if (!demandeOptional.isPresent()) {
            throw new UnsupportedOperationException("Formation inconnue.");
        }
        Demande demande = demandeOptional.get();

        Optional<Association> associationOptional = associationRepository.findByUtilisateurId(idUtilisateur);
        if (!associationOptional.isPresent()) {
            throw new UnsupportedOperationException("Association inconnue.");
        }
        Association association = associationOptional.get();
        List<Association> listAssociationsFavorables = demande.getAssociationsFavorables();
        boolean associationsFavorables = listAssociationsFavorables.contains(associationOptional.get());

        if(association.getDemandes().contains(demande)){
            logger.error("L'association a créé la demande, elle ne peut pas être intéressée par sa demande.");
            throw new UnsupportedOperationException("Une erreur est survenue");
        }
        if (associationsFavorables) {
            listAssociationsFavorables.remove(association);
            messageApi.setMessage("Vous n'êtes plus intéressé par cette formation.");
        } else {
            listAssociationsFavorables.add(association);
            messageApi.setMessage("Vous êtes intéressé par cette formation.");
        }
        demande.setAssociationsFavorables(listAssociationsFavorables);
        demande = demandeRepository.save(demande);
        FormationApi formationApi = FormationApiMapper.INSTANCE.demandeDaoToFormationApiAccueil(demande);
        Association associationDemandeuse = associationRepository.findByDemandes(demande);
        AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiAccueil(associationDemandeuse);
        formationApi.setAssociation(associationApi);
        messageApi.setFormation(formationApi);
        messageApi.setCode(HttpStatus.OK.value());
        return messageApi;
    }

}