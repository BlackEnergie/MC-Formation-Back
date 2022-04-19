package com.mcformation.service;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.mapper.FormationMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Formation;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;
import com.mcformation.repository.FormationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<FormationApi> getFormationsAccueil(int offset,int limit,String statut) {
        List<FormationApi> formationApiList = new ArrayList<>();
        List<Demande> demandeList =demandeRepository.findFormations(offset, limit,statut);
        for (Demande demande : demandeList) {
            FormationApi formationApi = DemandeMapper.INSTANCE.demandeDaoToFormationApiAccueil(demande);
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
            formationApi = DemandeMapper.INSTANCE.demandeDaoToFormationApiDetail(demande.get());
            Association association = associationRepository.findByDemandes(demande.get());
            AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiAccueil(association);
            formationApi.setAssociation(associationApi);
        } else {
            throw new UnsupportedOperationException("La formation/demande n'existe pas.");
        }
        return formationApi;
    }
    public MessageApi putModification(FormationApi formationApi){
        MessageApi messageApi = new MessageApi();
        Formation formationToSave = FormationMapper.INSTANCE.formationApiToFormationDao(formationApi);
        Optional<Formation> formation =formationRepository.findById(formationToSave.getId());
        if(formation.isPresent()){
            formationRepository.save(formation.get());
            messageApi.setMessage("La formation a été modifié");
            messageApi.setCode(200);
        }
        messageApi.setMessage("Une erreur est survenu");
        messageApi.setCode(400);
        return messageApi;
    }
}