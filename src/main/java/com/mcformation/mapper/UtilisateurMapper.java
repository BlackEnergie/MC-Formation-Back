package com.mcformation.mapper;

import com.mcformation.model.api.*;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.MembreBureauNational;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UtilisateurMapper {

    UtilisateurMapper INSTANCE = Mappers.getMapper(UtilisateurMapper.class);

    @Named("associationDaoToAssociationApiDetail")
    AssociationApi associationDaoToAssociationApiDetail(Association source);

    @Mapping(source = "source.domaines", target = "domaines")
    FormateurApi formateurDaoToFormateurApiDetail(Formateur source);

    MembreBureauNationalApi membreBureauNationalDaoTomembreBureauNationalApiDetail(MembreBureauNational source);

    @IterableMapping(qualifiedByName = "associationDaoToAssociationApiDetail")
    List<AssociationApi> associationDaoListToAssociationApiListDetail(List<Association> source);

    @Named("associationDaoToAssociationApiAccueil")
    @Mapping(target = "ville", ignore = true)
    @Mapping(target = "college", ignore = true)
    AssociationApi associationDaoToAssociationApiAccueil(Association source);

    @IterableMapping(qualifiedByName = "associationDaoToAssociationApiAccueil")
    List<AssociationApi> associationDaoListToAssociationApiListAccueil(List<Association> source);

    Association associationApiToAssociationDao(AssociationApi source);

    Formateur formateurApiToFormateurDao(FormateurApi source);

    MembreBureauNational membreBureauNationalApiToMembreBureauNationalDao(MembreBureauNationalApi source);
    List<Association> associationApiListToAssociationDaoList(List<AssociationApi> source);

    @Named("formateurDaoToFormateurUserApi")
    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.nom", target = "nom")
    @Mapping(source = "source.prenom", target = "prenom")
    @Mapping(source = "source.utilisateur.nomUtilisateur", target = "nomUtilisateur")
    @Mapping(source = "source.utilisateur.email", target = "email")
    FormateurUserApi formateurDaoToFormateurUserApi(Formateur source);

    @IterableMapping(qualifiedByName = "formateurDaoToFormateurUserApi")
    List<FormateurUserApi> formateurDaoListToFormateurUserApiList(List<Formateur> source);

    @Named("associationDaoToAssociationUserApi")
    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.ville", target = "ville")
    @Mapping(source = "source.college", target = "college")
    @Mapping(source = "source.acronyme", target = "acronyme")
    @Mapping(source = "source.nomComplet", target = "nomComplet")
    @Mapping(source = "source.utilisateur.nomUtilisateur", target = "nomUtilisateur")
    @Mapping(source = "source.utilisateur.email", target = "email")
    AssociationUserApi associationDaoToAssociationUserApi(Association source);

    @IterableMapping(qualifiedByName = "associationDaoToAssociationUserApi")
    List<AssociationUserApi> associationDaoListToAssociationUserApiList(List<Association> source);

}
