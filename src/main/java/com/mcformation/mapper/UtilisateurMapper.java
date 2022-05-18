package com.mcformation.mapper;

import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.FormateurApi;
import com.mcformation.model.api.MembreBureauNationalApi;
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

    List<Association> associationApiListToAssociationDaoList(List<AssociationApi> source);

}
