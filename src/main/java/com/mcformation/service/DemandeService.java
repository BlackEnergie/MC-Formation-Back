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
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.DemandeRepository;
import com.mcformation.repository.DomaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AssociationRepository associationRepository;

    public MessageApi create(DemandeApi demandeApi) {
        MessageApi messageApi = new MessageApi();
        List<DomaineApi> domaineApiList = demandeApi.getDomaines();
        List<Domaine> domaineDaoList = new ArrayList<>();
        Demande demandeDao = DemandeMapper.INSTANCE.demandeApiToDemandeDao(demandeApi);
        for (DomaineApi domaineApi: domaineApiList) {
            Optional<Domaine> domaineDao = domaineRepository.findByCode(domaineApi.getCode());
            domaineDao.ifPresent(domaineDaoList::add);
        }
        demandeDao.setDomaines(domaineDaoList);
        demandeDao = demandeRepository.save(demandeDao);

        DemandeApi demandeCree = DemandeMapper.INSTANCE.demandeDaoToDemandeApi(demandeDao);

        String emailAssociation = demandeApi.getAssociation().getEmail();
        Optional<Association> associationOptional = associationRepository.findByEmail(emailAssociation);
        if (associationOptional.isPresent()) {
            Association associationDao = associationOptional.get();
            associationDao.getDemandes().add(demandeDao);
            associationDao = associationRepository.save(associationDao);
            AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApi(associationDao);
            demandeCree.setAssociation(associationApi);
        }
        messageApi.setMessage(String.format("Votre demande de formation \"%s\" a bien été enregistrée.", demandeCree.getSujet()));
        messageApi.setCode(201);
        return messageApi;
    }

}
