package com.mcformation.mapper;

import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.database.Domaine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DomaineMapper {

    DomaineMapper INSTANCE = Mappers.getMapper(DomaineMapper.class);

    DomaineApi domaineDaoToDomaineApi(Domaine source);

    List<DomaineApi> domaineDaoListToDomaineApiList(List<Domaine> source);

    Domaine domaineApiToDomaineDao(DomaineApi source);

    List<Domaine> domaineApiListToDomaineDaoList(List<DomaineApi> source);
}
