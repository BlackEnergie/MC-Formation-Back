package com.mcformation.repository;

import java.util.List;

import com.mcformation.model.database.Demande;

public interface DemandeRepositoryCustom {
    
    List<Demande> findFormations(int offset,int limit,String statut);
    
}
