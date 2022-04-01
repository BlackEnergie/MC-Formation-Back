package com.mcformation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Domaine;

import org.springframework.stereotype.Service;

import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;
import com.mcformation.repository.DomaineRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DemandeService {
    
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private DomaineRepository domaineRepository;
    @Autowired
    private AssociationRepository associationRepository;

    public Demande create(DemandeApi newDemande) {
        List<Domaine> domaines= new ArrayList<>();
        Association association;
        Optional<Domaine> domaineOptional;
        Optional<Association> associationOptional;
        List<Demande> demandesAssociation;
        List <Domaine> listDomaines= newDemande.getDomaines();
        Demande demandedao = DemandeMapper.INSTANCE.demandeApiToDemandeDao(newDemande);
        for(int i=0;i<listDomaines.size();i++){
            domaineOptional= domaineRepository.findByCode(listDomaines.get(i).getCode());
            if (domaineOptional.isPresent()) {
                domaines.add(domaineOptional.get());
            }
        }
        demandedao.setDomaines(domaines);
        Demande demandeEnregistre=demandeRepository.save(demandedao);
       
        String emailAssociation= newDemande.getAssociation().getEmail();
        associationOptional = associationRepository.findByEmail(emailAssociation);
        if(associationOptional.isPresent()){
            demandesAssociation = associationOptional.get().getDemandes();
            demandesAssociation.add(demandedao);
            associationOptional.get().setDemandes(demandesAssociation);
        }
        association= associationOptional.orElse(null);
        associationRepository.save(association);
        return demandeEnregistre;
    }
}
