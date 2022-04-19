package com.mcformation.service;

import java.util.List;

import com.mcformation.mapper.DomaineMapper;
import com.mcformation.mapper.FormateurMapper;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.api.auth.FormateurApi;
import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Formateur;
import com.mcformation.repository.DomaineRepository;
import com.mcformation.repository.FormateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonneesService {

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    public List<DomaineApi> getAllDomaines() {
        List<Domaine> domaineList = (List<Domaine>) domaineRepository.findAll();
        return DomaineMapper.INSTANCE.domaineDaoListToDomaineApiList(domaineList);
    }

    public List<FormateurApi> getAllFormateurs() {
        List<Formateur> formateurList = (List<Formateur>) formateurRepository.findAll();
        return FormateurMapper.INSTANCE.formateurDaoListToFormateurApiList(formateurList);
    }

}
