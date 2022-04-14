package com.mcformation.repository;

import java.util.List;

import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Domaine;

public interface DemandeRepositoryCustom {
    
    List<Demande> findFormations(int offset,int limit,String statut,List<String> domaines);
    
}
