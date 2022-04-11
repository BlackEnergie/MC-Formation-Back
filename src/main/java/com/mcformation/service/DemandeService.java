package com.mcformation.service;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;
import com.mcformation.repository.DomaineRepository;
import com.mcformation.repository.UtilisateurRepository;
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

    @Transactional(rollbackFor = UnsupportedOperationException.class)
    public MessageApi create(DemandeApi demandeApi) {
        MessageApi messageApi = new MessageApi();
        List<DomaineApi> domaineApiList = demandeApi.getDomaines();
        List<Domaine> domaineDaoList = new ArrayList<>();
        Demande demandeDao = DemandeMapper.INSTANCE.demandeApiToDemandeDao(demandeApi);
        for (DomaineApi domaineApi : domaineApiList) {
            Optional<Domaine> domaineDao = domaineRepository.findByCode(domaineApi.getCode());
            domaineDao.ifPresent(domaineDaoList::add);
        }
        demandeDao.setDomaines(domaineDaoList);
        demandeDao = demandeRepository.save(demandeDao);

        DemandeApi demandeCree = DemandeMapper.INSTANCE.demandeDaoToDemandeApi(demandeDao);

        String emailAssociation = demandeApi.getAssociation().getEmail();
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(emailAssociation);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            if (utilisateur.getAssociation() != null) {
                Association associationDao = utilisateur.getAssociation();
                associationDao.getDemandes().add(demandeDao);
                associationDao = associationRepository.save(associationDao);
                AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiDetail(associationDao);
                demandeCree.setAssociation(associationApi);
            } else {
                throw new UnsupportedOperationException("Cet utilisateur ne peut pas créer de demande de formation.");
            }
        } else {
            throw new UnsupportedOperationException("Cet email ne correspond à aucun utilisateur.");
        }
        messageApi.setMessage(String.format("Votre demande de formation \"%s\" a bien été enregistrée.", demandeCree.getSujet()));
        messageApi.setCode(201);
        return messageApi;
    }

}
