package com.mcformation.mapper;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Demande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {DomaineMapper.class})
public interface DemandeMapper {

        @Mapping(source = "source.dateDemande", target = "dateDemande")
        @Mapping(source = "source.domaines", target = "domaines")
        @Mapping(source = "source.sujet", target = "sujet")
        @Mapping(source = "source.detail", target = "detail")
        DemandeApi demandeDaoToDemandeApi(Demande source);


        List<DemandeApi> demandeDaoListToDemandeApiList(List<Demande> source);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "associationsFavorables", ignore = true)
        @Mapping(source = "source.dateDemande", target = "dateDemande")
        @Mapping(source = "source.domaines", target = "domaines")
        @Mapping(source = "source.sujet", target = "sujet")
        @Mapping(source = "source.detail", target = "detail")
        Demande demandeApiToDemandeDao(DemandeApi source);

}
