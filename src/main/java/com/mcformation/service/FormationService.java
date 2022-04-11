package com.mcformation.service;

import java.util.ArrayList;
import java.util.List;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.mapper.FormationMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Formation;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormationService {
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private DemandeRepository demandeRepository;

    public List<FormationApi> getAllFormations() {
        FormationApi formationApi;
        List<FormationApi> formationApiList = new ArrayList<>();
        List<Demande> demandeList =demandeRepository.findTop5ByOrderByDateDemandeDesc();
        for(Demande demande:demandeList){
            Formation formation = demande.getFormation();
            DemandeApi demandeApi=DemandeMapper.INSTANCE.demandeDaoToDemandeApi(demande);
            Association association=associationRepository.findByIdDemande(demande.getId());
            AssociationApi associationApi=UtilisateurMapper.INSTANCE.associationDaoToAssociationApi(association);
            demandeApi.setAssociation(associationApi);
            if(formation!=null){
                formationApi = FormationMapper.INSTANCE.formationDaoToFormationApi(formation);
            }
            else{
                formationApi= new FormationApi();
            }
            formationApi.setDemande(demandeApi);
            formationApiList.add(formationApi);
        }
        return formationApiList;

    }

    
}
