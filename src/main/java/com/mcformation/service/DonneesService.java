package com.mcformation.service;

import java.util.List;

import com.mcformation.mapper.DomaineMapper;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.database.Domaine;
import com.mcformation.repository.DomaineRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonneesService {

    @Autowired
    private DomaineRepository domaineRepository;

    private final DomaineMapper domaineMapper = Mappers.getMapper(DomaineMapper.class);

    public List<DomaineApi> getAllDomaines() {
        List<Domaine> domaineList = (List<Domaine>) domaineRepository.findAll();
        return domaineMapper.domaineDaoListToDomaineApiList(domaineList);
    }

}
