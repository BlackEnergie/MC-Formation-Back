package com.mcformation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.database.Association;

@Mapper
public interface AssociationMapper {

    AssociationMapper INSTANCE = Mappers.getMapper(AssociationMapper.class);

    AssociationApi associationDaoToAssociationApi(Association source);

    List<AssociationApi> associationDaoListToAssociationApiList(List<Association> source);

    Association associationApiToAssociationDao(AssociationApi source);

    List<Association> associationApiListToAssociationDaoList(List<AssociationApi> source);
    
}
