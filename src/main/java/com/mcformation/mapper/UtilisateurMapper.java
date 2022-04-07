package com.mcformation.mapper;

import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.database.Association;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UtilisateurMapper {

    UtilisateurMapper INSTANCE = Mappers.getMapper(UtilisateurMapper.class);

    AssociationApi associationDaoToAssociationApi(Association source);

    Association associationApiToAssociationDao(AssociationApi source);

    List<AssociationApi> associationDaoListToAssociationApiList(List<Association> source);

    List<Association> associationApiListToAssociationDaoList(List<AssociationApi> source);
}
