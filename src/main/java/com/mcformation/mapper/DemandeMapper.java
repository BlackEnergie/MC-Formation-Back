package com.mcformation.mapper;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Demande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface DemandeMapper {
    @Mappings({
        @Mapping(source = "source.libelle", target = "libelle"),
        @Mapping(source = "source.description", target = "description"),
        @Mapping(source = "source.code", target = "code")
    })
    DemandeApi demandeDaoToDemandeApi(Demande source);


    List<DemandeApi> domaineDaoListToDomaineApiList(List<Demande> source);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "source.libelle", target = "libelle"),
        @Mapping(source = "source.description", target = "description"),
        @Mapping(source = "source.code", target = "code")
    })
    Demande demandeApiToDemandeDao(DemandeApi source);

}
