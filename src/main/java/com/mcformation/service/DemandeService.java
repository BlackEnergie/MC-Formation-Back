package com.mcformation.service;

import com.mcformation.mapper.DemandeMapper;
import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Demande;
import org.springframework.stereotype.Service;
import com.mcformation.repository.DemandeRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DemandeService {
    
    @Autowired
    private DemandeRepository demandeRepository;
    
    private final DemandeMapper demandeMapper = Mappers.getMapper(DemandeMapper.class);

    public Demande create(DemandeApi newDemande) {
        Demande demandedao = demandeMapper.demandeApiToDemandeDao(newDemande);
        return demandeRepository.save(demandedao);
    }
}
