package com.mcformation.service;

import java.util.List;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.mapper.FormateurMapper;
import com.mcformation.mapper.FormationMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.FormateurApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.Formation;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.FormateurRepository;
import com.mcformation.repository.FormationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private AssociationRepository associationRepository;

    public List<FormationApi> getAllFormations() {
        List<Formation> formationList = (List<Formation>) formationRepository.findTop5ByOrderByDateDesc();
        List<FormationApi> formationApiList = FormationMapper.INSTANCE.formationDaoListToFormationApiList(formationList);
        for (FormationApi formationApi : formationApiList) {
            Formation formation = FormationMapper.INSTANCE.formationApiToFormationDao(formationApi);
            List<Formateur>formateurs = formateurRepository.findByIdFormation(formation.getId());
            List<FormateurApi>formateursApi=FormateurMapper.INSTANCE.formateurDaoListToFormateurApiList(formateurs);
            formationApi.setFormateurs(formateursApi);
            Demande demande = formation.getDemande();
            Association association=associationRepository.findByIdDemande(demande.getId());
            AssociationApi associationApi=UtilisateurMapper.INSTANCE.associationDaoToAssociationApi(association);
            DemandeApi demandeApi=DemandeMapper.INSTANCE.demandeDaoToDemandeApi(demande);
            demandeApi.setAssociation(associationApi);
            formationApi.setDemande(demandeApi);
        }
        return formationApiList;
    }

    
}
