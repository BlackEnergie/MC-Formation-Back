package com.mcformation.service;

import java.util.List;

import com.mcformation.mapper.FormationMapper;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Formation;
import com.mcformation.repository.FormationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    public List<FormationApi> getAllFormations() {
        List<Formation> formationList = (List<Formation>) formationRepository.findAll();
        return FormationMapper.INSTANCE.formationDaoListToFormationApiList(formationList);
    }

    
}
