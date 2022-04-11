package com.mcformation.mapper;

import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.database.Association;
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

    @IterableMapping(qualifiedByName = "associationDaoToAssociationApiAccueil")
    List<AssociationApi> associationDaoListToAssociationApiListDetail(List<Association> source);

    @Named("associationDaoToAssociationApiAccueil")
    @Mapping(target = "ville", ignore = true)
    @Mapping(target = "college", ignore = true)
    @Mapping(target = "email", ignore = true)
    AssociationApi associationDaoToAssociationApiAccueil(Association source);

    @IterableMapping(qualifiedByName = "associationDaoToAssociationApiDetail")
    List<AssociationApi> associationDaoListToAssociationApiListAccueil(List<Association> source);

    Association associationApiToAssociationDao(AssociationApi source);

    List<Association> associationApiListToAssociationDaoList(List<AssociationApi> source);


}
