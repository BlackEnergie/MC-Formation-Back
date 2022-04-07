package com.mcformation.config;

import com.mcformation.config.properties.YamlDonneesProperties;
import com.mcformation.repository.DomaineRepository;
import com.mcformation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class ConfigurationBaseDeDonnees {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private YamlDonneesProperties donneesProperties;

    @PostConstruct
    private void configurationBDD() {
        configurationRoles();
        configurationDomaines();
    }

    @Transactional
    protected void configurationRoles() {
        roleRepository.saveAll(donneesProperties.getRoles());
    }

    @Transactional
    protected void configurationDomaines() {
        domaineRepository.saveAll(donneesProperties.getDomaines());
    }

}
