package com.mcformation.repository;

import com.mcformation.model.database.Demande;

import java.util.List;

public interface DemandeRepositoryCustom {

    List<Demande> findFormations(int offset, int limit, String statut);

}
