package com.mcformation.mapper;

import com.mcformation.model.api.*;
import com.mcformation.model.api.auth.CreateUserTokenApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.MembreBureauNational;
import com.mcformation.model.database.auth.CreateUserToken;
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

    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.poste", target = "poste")
    @Mapping(source = "source.utilisateur.nomUtilisateur", target = "nomUtilisateur")
    @Mapping(source = "source.utilisateur.email", target = "email")
    @Mapping(source = "source.utilisateur.actif", target = "actif")
    MembreBureauNationalUserApi membreBureauNationalDaoToMembreBureauNationalUserApi(MembreBureauNational source);

    List<MembreBureauNationalUserApi> membreBureauNationalDaoListToMembreBureauNationalUserApiList(List<MembreBureauNational> source);

    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.nom", target = "nom")
    @Mapping(source = "source.prenom", target = "prenom")
    @Mapping(source = "source.dateCreation", target = "dateCreation")
    @Mapping(source = "source.utilisateur.nomUtilisateur", target = "nomUtilisateur")
    @Mapping(source = "source.utilisateur.email", target = "email")
    @Mapping(source = "source.utilisateur.actif", target = "actif")
    FormateurUserApi formateurDaoToFormateurUserApi(Formateur source);

    List<FormateurUserApi> formateurDaoListToFormateurUserApiList(List<Formateur> source);

    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.ville", target = "ville")
    @Mapping(source = "source.college", target = "college")
    @Mapping(source = "source.acronyme", target = "acronyme")
    @Mapping(source = "source.nomComplet", target = "nomComplet")
    @Mapping(source = "source.utilisateur.nomUtilisateur", target = "nomUtilisateur")
    @Mapping(source = "source.utilisateur.email", target = "email")
    @Mapping(source = "source.utilisateur.actif", target = "actif")
    AssociationUserApi associationDaoToAssociationUserApi(Association source);

    List<AssociationUserApi> associationDaoListToAssociationUserApiList(List<Association> source);

    CreateUserTokenApi createUserTokenToCreateUserTokenApi(CreateUserToken source);

    List<CreateUserTokenApi> createUserTokenListToCreateUserTokenApiList(List<CreateUserToken> source);

}
