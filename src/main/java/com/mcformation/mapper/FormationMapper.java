package com.mcformation.mapper;

import com.mcformation.model.api.FormationApi;
import com.mcformation.model.database.Formation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface FormationMapper {

    FormationMapper INSTANCE = Mappers.getMapper(FormationMapper.class);

    FormationApi formationDaoToFormationApi(Formation source);

    List<FormationApi> formationDaoListToFormationApiList(List<Formation> source);

    Formation formationApiToFormationDao(FormationApi source);

    List<Formation> formationApiListToFormationDaoList(List<FormationApi> source);

}
