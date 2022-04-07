package com.mcformation.config;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.mcformation.model.database.Role;
import com.mcformation.model.utils.Erole;
import com.mcformation.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationBaseDeDonnees {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    private void configurationBDD() {
        configurationRoles();
    }

    @Transactional
    private void configurationRoles() {
        Role roleAsso = new Role();
        roleAsso.setId(1L);
        roleAsso.setNom(Erole.ROLE_ASSO);
        Role roleFormateur = new Role();
        roleFormateur.setId(2L);
        roleFormateur.setNom(Erole.ROLE_FORMATEUR);
        Role roleBN = new Role();
        roleBN.setId(3L);
        roleBN.setNom(Erole.ROLE_BN);
        roleRepository.save(roleAsso);
        roleRepository.save(roleFormateur);
        roleRepository.save(roleBN);
    }
}
