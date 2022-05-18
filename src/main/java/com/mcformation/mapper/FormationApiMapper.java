package com.mcformation.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.api.PartieApi;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Formation;

import com.mcformation.utils.JsonUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@Mapper()
public interface FormationApiMapper{

    FormationApiMapper INSTANCE = Mappers.getMapper(FormationApiMapper.class);
    Logger logger = LoggerFactory.getLogger(FormationApiMapper.class);
    
    @Named("demandeDaoToFormationApiDetail")
    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.sujet", target = "sujet")
    @Mapping(source = "source.dateDemande", target = "dateDemande")
    @Mapping(source = "source.detail", target = "detail")
    @Mapping(source = "source.statut", target = "statut")
    @Mapping(source = "source.domaines", target = "domaines")
    @Mapping(source = "source.associationsFavorables", target = "associationsFavorables")
    @Mapping(source = "source.formation.date", target = "date")
    @Mapping(source = "source.formation.duree", target = "duree")
    @Mapping(source = "source.formation.nom", target = "nom")
    @Mapping(source = "source.formation.type", target = "type")
    @Mapping(source = "source.formation.prerequis", target = "prerequis")
    @Mapping(source = "source.formation.audience", target = "audience")
    @Mapping(source = "source.formation.materiels", target = "materiels",qualifiedByName = "stringToListString")
    @Mapping(source = "source.formation.objectifs", target = "objectifs",qualifiedByName= "stringToListString")
    @Mapping(source = "source.formation.parties", target = "parties",qualifiedByName= "jsonPartiesToObjectListPartie")
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
    @Mapping(source = "source.detail", target = "detail")
    @Mapping(source = "source.formation.date",target = "date")
    @Mapping(source = "source.formation.formateurs", target = "formateurs")
    @Mapping(source = "source.associationsFavorables", target = "associationsFavorables")
    @Mapping(target = "dateDemande", ignore = true)
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

    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.sujet", target = "sujet")
    @Mapping(source = "source.dateDemande", target = "dateDemande")
    @Mapping(source = "source.detail", target = "detail")
    @Mapping(source = "source.statut", target = "statut")
    @Mapping(source = "source.domaines", target = "domaines")
    @Mapping(source = "source.id", target = "formation.id")
    @Mapping(source = "source.date", target = "formation.date")
    @Mapping(source = "source.duree", target = "formation.duree")
    @Mapping(source = "source.nom", target = "formation.nom")
    @Mapping(source = "source.type", target = "formation.type")
    @Mapping(source = "source.prerequis", target = "formation.prerequis")
    @Mapping(source = "source.audience", target = "formation.audience")
    @Mapping(source = "source.materiels", target = "formation.materiels",qualifiedByName = "listStringToString")
    @Mapping(source = "source.objectifs", target = "formation.objectifs",qualifiedByName= "listStringToString")
    @Mapping(source = "source.parties", target = "formation.parties", qualifiedByName = "objectListPartieToJsonParties")
    @Mapping(source = "source.cadre", target = "formation.cadre")
    @Mapping(source = "source.formateurs", target = "formation.formateurs")
    Demande formationApiToDemandeDao(FormationApi source);

    @Mapping(source="source.materiels",target="materiels",qualifiedByName = "listStringToString")
    @Mapping(source="source.objectifs",target="objectifs",qualifiedByName = "listStringToString")
    @Mapping(source="source.parties",target="parties",qualifiedByName = "objectListPartieToJsonParties")
    Formation formationApiToFormationDao(FormationApi source);

    @Named("listStringToString")
    default String listStringToString(List<String> stringList){
        String res = null;
        if (stringList != null && !stringList.isEmpty()) {
            res = String.join(";",stringList);
        }
        return res;
    }

    @Named("stringToListString")
    default List<String> stringToListString(String string){
        List<String> res = new ArrayList<>();
        if (string != null) {
            res = new ArrayList<>(Arrays.asList(string.split(";")));
        }
        return res;
    }

    @Named("objectListPartieToJsonParties")
    default String objectListPartieToJsonParties(List<PartieApi> partieApiList) {
        return JsonUtils.objectToJson(partieApiList);
    }

    @Named("jsonPartiesToObjectListPartie")
    default List<PartieApi> jsonPartiesToObjectListPartie(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PartieApi> partieApiList = new ArrayList<>();
        if (StringUtils.hasText(json)) {
            try {
                partieApiList = objectMapper.readValue(json, new TypeReference<List<PartieApi>>() {
                });
            } catch (JsonProcessingException e) {
                logger.error("Erreur: json parties mal formé, il ne correspond pas au model de List<PartieApi>", e);
                throw new RuntimeException("Erreur lors de la récupération des données de l'attribut 'parties' de l'objet formation.");
            }
        }
        return partieApiList;
    }

}
