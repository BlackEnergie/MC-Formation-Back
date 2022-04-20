package com.mcformation.mapper;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Demande;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(uses = {DomaineMapper.class})
public interface DemandeMapper {

    DemandeMapper INSTANCE = Mappers.getMapper(DemandeMapper.class);

    DemandeApi demandeDaoToDemandeApi(Demande source);

    List<DemandeApi> demandeDaoListToDemandeApiList(List<Demande> source);

    Demande demandeApiToDemandeDao(DemandeApi source);

    List<Demande> demandeApiListToDemandeDaoList(List<DemandeApi> source);


}
