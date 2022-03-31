package com.mcformation.mapper;

import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.database.Domaine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DomaineMapper {


        @Mapping(source = "source.libelle", target = "libelle")
        @Mapping(source = "source.description", target = "description")
        @Mapping(source = "source.code", target = "code")
        DomaineApi domaineDaoToDomaineApi(Domaine source);


        List<DomaineApi> domaineDaoListToDomaineApiList(List<Domaine> source);

        @Mapping(target = "id", ignore = true)
        @Mapping(source = "source.libelle", target = "libelle")
        @Mapping(source = "source.description", target = "description")
        @Mapping(source = "source.code", target = "code")
        Domaine domaineApiToDomaineDao(DomaineApi source);


}
