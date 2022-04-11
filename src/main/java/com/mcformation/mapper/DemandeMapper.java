package com.mcformation.mapper;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Demande;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(uses = {DomaineMapper.class})
public interface DemandeMapper {

    DemandeMapper INSTANCE = Mappers.getMapper(DemandeMapper.class);

    DemandeApi demandeDaoToDemandeApi(Demande source);

    List<DemandeApi> demandeDaoListToDemandeApiList(List<Demande> source);

    Demande demandeApiToDemandeDao(DemandeApi source);

    List<Demande> demandeApiListToDemandeDaoList(List<DemandeApi> source);

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
    @Mapping(source = "source.formation.materiels", target = "materiels")
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
    @Mapping(source = "source.formation.formateurs", target = "formateurs")
    @Mapping(target = "dateDemande", ignore = true)
    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "duree", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "prerequis", ignore = true)
    @Mapping(target = "audience", ignore = true)
    @Mapping(target = "parties", ignore = true)
    @Mapping(target = "materiels", ignore = true)
    FormationApi demandeDaoToFormationApiAccueil(Demande source);

    @IterableMapping(qualifiedByName = "demandeDaoToFormationApiAccueil")
    List<FormationApi> demandeDaoListToFormationApiListAccueil(List<Demande> source);

}
