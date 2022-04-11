package com.mcformation.service;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormationService {
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private DemandeRepository demandeRepository;

    public List<FormationApi> getFormationsAccueil() {
        List<FormationApi> formationApiList = new ArrayList<>();
        List<Demande> demandeList = demandeRepository.findTop5ByOrderByDateDemandeDesc();
        for (Demande demande : demandeList) {
            FormationApi formationApi = DemandeMapper.INSTANCE.demandeDaoToFormationApiAccueil(demande);
            Association association = associationRepository.findByIdDemande(demande.getId());
            AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiAccueil(association);
            formationApi.setAssociation(associationApi);
            formationApiList.add(formationApi);
        }
        return formationApiList;
    }

}
