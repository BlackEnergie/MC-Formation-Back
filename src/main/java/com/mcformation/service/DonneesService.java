package com.mcformation.service;

import com.mcformation.mapper.DomaineMapper;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.database.Domaine;
import com.mcformation.repository.DomaineRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonneesService {

    @Autowired
    private DomaineRepository domaineRepository;

    private final DomaineMapper domaineMapper = Mappers.getMapper(DomaineMapper.class);

    public List<DomaineApi> getAllDomaine() {
        ArrayList<Domaine> domaineArrayList = (ArrayList<Domaine>) domaineRepository.findAll();
    }

}
