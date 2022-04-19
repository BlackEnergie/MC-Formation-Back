package com.mcformation.service;


import com.mcformation.mapper.FormationApiMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.api.FormateurApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.Formation;
import com.mcformation.model.utils.StatutDemande;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;
import com.mcformation.repository.FormateurRepository;
import com.mcformation.repository.FormationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private DemandeService demandeService;

    public List<FormationApi> getFormationsAccueil(int offset,int limit,String statut) {
        List<FormationApi> formationApiList = new ArrayList<>();
        List<Demande> demandeList =demandeRepository.findFormations(offset, limit,statut);
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
        Optional<Demande> demande =demandeRepository.findById(id);
        if(demande.isPresent()){
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
    public MessageApi putModification(FormationApi formationApi){
        MessageApi messageApi = new MessageApi();
        Formation formationToSave = FormationApiMapper.INSTANCE.formationApiToFormationDao(formationApi);
        Demande demandeToSave = FormationApiMapper.INSTANCE.formationApiToDemandeDao(formationApi);
        Optional<Formation> formation =formationRepository.findById(formationToSave.getId());
        if(!formation.isPresent()){
            throw new UnsupportedOperationException("Formation non présente");
        }
        Long idDemande = demandeRepository.getDemandeIdByFormationId(formation.get().getId());
        demandeToSave.setId(idDemande);
        List<DomaineApi> domaineApiList=formationApi.getDomaines();
        List<Domaine> domaines = demandeService.getDomainesByCode(domaineApiList);
        demandeToSave.setDomaines(domaines);
        demandeToSave.setFormation(formationToSave);
        demandeRepository.save(demandeToSave);
        List<FormateurApi> formateurApiList=formationApi.getFormateurs();
        if(formateurApiList!=null && !formateurApiList.isEmpty()){
            List<Formateur> formateurs= new ArrayList<>();
            for(FormateurApi formateur:formateurApiList){
                Optional<Formateur> formateurOptional= formateurRepository.findByUtilisateurId(formateur.getId());
                if(formateurOptional.isPresent()){
                    formateurs.add(formateurOptional.get());
                }
                else{
                    throw new UnsupportedOperationException("Le formateur ajouté à la formation n'est pas trouvé");
                }
            
            }
            if(demandeToSave.getStatut()==StatutDemande.A_VENIR||demandeToSave.getStatut()==StatutDemande.PASSEE){
                throw new UnsupportedOperationException("La formation doit contenir au moins un formateur ");
            }
        }
       
        if(demandeToSave.getStatut()==StatutDemande.PASSEE){
            String localDate = LocalDate.now().toString();
            Date date=null;
            String message;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(localDate);
                message="La date de la formation n'est pas passée";
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message="Erreur de comparaison de la date";
            }
            
            if(date==null || formationToSave.getDate().after(date)){
                throw new UnsupportedOperationException(message);
            }
        }
        formationRepository.save(formationToSave);
        messageApi.setMessage("La formation a été modifié");
        messageApi.setCode(200);
        return messageApi;
    }


}