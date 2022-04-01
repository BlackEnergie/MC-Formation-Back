package com.mcformation.service;

import java.util.List;

import com.mcformation.mapper.DomaineMapper;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.database.Domaine;
import com.mcformation.repository.DomaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonneesService {

    @Autowired
    private DomaineRepository domaineRepository;

    public List<DomaineApi> getAllDomaines() {
        List<Domaine> domaineList = (List<Domaine>) domaineRepository.findAll();
        return DomaineMapper.INSTANCE.domaineDaoListToDomaineApiList(domaineList);
    }

}
