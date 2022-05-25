package com.mcformation.service;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.model.database.*;
import com.mcformation.model.utils.StatutDemande;
import com.mcformation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private DomaineRepository domaineRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private FormationRepository formationRepository;

    @Transactional(rollbackFor = UnsupportedOperationException.class)
    public MessageApi create(DemandeApi demandeApi) {
        MessageApi messageApi = new MessageApi();
        Demande demandeDao = DemandeMapper.INSTANCE.demandeApiToDemandeDao(demandeApi);
        List<Domaine> domaineDaoList = this.getDomainesByCode(demandeApi.getDomaines());
        demandeDao.setDomaines(domaineDaoList);
        Formation formation = new Formation();
        demandeDao.setFormation(formation);
        formationRepository.save(formation);
        demandeDao = demandeRepository.save(demandeDao);
        DemandeApi demandeCree = DemandeMapper.INSTANCE.demandeDaoToDemandeApi(demandeDao);
        String nomUtilisateur = demandeApi.getNomUtilisateur();
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByNomUtilisateur(nomUtilisateur);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            if (utilisateur.getAssociation() != null) {
                Association associationDao = utilisateur.getAssociation();
                associationDao.getDemandes().add(demandeDao);
                associationRepository.save(associationDao);
            } else {
                throw new UnsupportedOperationException("Cet utilisateur ne peut pas créer de demande de formation.");
            }
        } else {
            throw new UnsupportedOperationException("Utilisateur inconnu.");
        }
        messageApi.setMessage(String.format("Votre demande de formation \"%s\" a bien été enregistrée.", demandeCree.getSujet()));
        messageApi.setCode(201);
        return messageApi;
    }

    @Transactional(rollbackFor = UnsupportedOperationException.class)
    public MessageApi delete(Long id) {
        MessageApi messageApi = new MessageApi();
        Demande demande;
        Optional<Demande> demandeOptional = demandeRepository.findById(id);
        if(demandeOptional.isPresent()){
            if(demandeOptional.get().getStatut()== StatutDemande.DEMANDE){
                demande = demandeOptional.get();
                demandeRepository.deleteAssociationDemande(demande.getId());
                demandeRepository.deleteDemandeAssociationsFavorables(demande.getId());
                demandeRepository.deleteDemandeDomaines(demande.getId());
                demandeRepository.deleteById(demande.getId());
                formationRepository.delete(demande.getFormation());
            }
            else{
                throw new UnsupportedOperationException("La formation n'est plus au statut demandé, impossible de la supprimer");
            }
        }
        else{
            throw new UnsupportedOperationException("La demande n'existe pas");
        }
        messageApi.setMessage("La formation "+ demandeOptional.get().getId()+ " a bien été supprimée");
        messageApi.setCode(200);
        return messageApi;
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
