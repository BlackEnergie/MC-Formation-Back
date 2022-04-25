package com.mcformation.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Formation;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface FormationApiMapper{

    FormationApiMapper INSTANCE = Mappers.getMapper(FormationApiMapper.class);
    
    @Named("demandeDaoToFormationApiDetail")
    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.sujet", target = "sujet")
    @Mapping(source = "source.dateDemande", target = "dateDemande")
    @Mapping(source = "source.detail", target = "detail")
    @Mapping(source = "source.statut", target = "statut")
    @Mapping(source = "source.domaines", target = "domaines")
    @Mapping(source = "source.formation.date", target = "date")
    @Mapping(source = "source.formation.duree", target = "duree")
    @Mapping(source = "source.formation.nom", target = "nom")
    @Mapping(source = "source.formation.type", target = "type")
    @Mapping(source = "source.formation.prerequis", target = "prerequis")
    @Mapping(source = "source.formation.audience", target = "audience")
    @Mapping(source = "source.formation.parties", target = "parties")
    @Mapping(source = "source.formation.materiels", target = "materiels",qualifiedByName = "stringMaterielToList")
    @Mapping(source = "source.formation.objectifs", target = "objectifs",qualifiedByName= "stringObjectifsToList")
    @Mapping(source = "source.formation.cadre", target = "cadre")
    @Mapping(source = "source.formation.formateurs", target = "formateurs")
    FormationApi demandeDaoToFormationApiDetail(Demande source);

    @IterableMapping(qualifiedByName = "demandeDaoToFormationApiDetail")
    List<FormationApi> demandeDaoListToFormationApiListDetail(List<Demande> source);

    @Named("demandeDaoToFormationApiAccueil")
    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.statut", target = "statut")
    @Mapping(source = "source.formation.cadre", target = "cadre")
    @Mapping(source = "source.domaines", target = "domaines")
    @Mapping(source = "source.formation.nom", target = "nom")
    @Mapping(source = "source.sujet", target = "sujet")
    @Mapping(source = "source.formation.date",target = "date")
    @Mapping(source = "source.formation.formateurs", target = "formateurs")
    @Mapping(target = "dateDemande", ignore = true)
    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "duree", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "prerequis", ignore = true)
    @Mapping(target = "audience", ignore = true)
    @Mapping(target = "parties", ignore = true)
    @Mapping(target = "materiels", ignore = true)
    @Mapping(target = "objectifs", ignore = true)
    FormationApi demandeDaoToFormationApiAccueil(Demande source);

    @IterableMapping(qualifiedByName = "demandeDaoToFormationApiAccueil")
    List<FormationApi> demandeDaoListToFormationApiListAccueil(List<Demande> source);

    Demande formationApiToDemandeDao(FormationApi source);
    
    @Mapping(source="source.materiels",target="materiels",qualifiedByName = "listMaterielToString")
    @Mapping(source="source.objectifs",target="objectifs",qualifiedByName = "listObjectifsToString")
    Formation formationApiToFormationDao(FormationApi source);

    @Named("listMaterielToString")
    default String listMaterielToString(List<String> materiels){
        String res = null;
        if (materiels != null && !materiels.isEmpty()) {
            res = String.join(";",materiels);
        }
        return res;
    }

    @Named("stringMaterielToList")
    default List<String> stringMaterielToList(String materiels){
        List<String> res = new ArrayList<>();
        if (materiels != null) {
            res = new ArrayList<>(Arrays.asList(materiels.split(";")));
        }
        return res;
    }
    @Named("listObjectifsToString")
    default String listObjectifsToString(List<String> objectifs){
        String res = null;
        if (objectifs != null && !objectifs.isEmpty()) {
            res = String.join(";",objectifs);
        }
        return res;
    }

    @Named("stringObjectifsToList")
    default List<String> stringObjectifsToList(String objectifs){
        List<String> res = new ArrayList<>();
        if (objectifs != null) {
            res = new ArrayList<>(Arrays.asList(objectifs.split(";")));
        }
        return res;
    }

}
