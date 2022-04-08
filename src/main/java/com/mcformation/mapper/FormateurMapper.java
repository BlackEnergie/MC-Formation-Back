package com.mcformation.mapper;

import java.util.List;

import com.mcformation.model.api.FormateurApi;
import com.mcformation.model.database.Formateur;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface FormateurMapper {
    
    FormateurMapper INSTANCE = Mappers.getMapper(FormateurMapper.class);

    FormateurApi formateurDaoToFormateurApi(Formateur source);

    List<FormateurApi> formateurDaoListToFormateurApiList(List<Formateur> source);

    Formateur formateurApiToFormateurDao(FormateurApi source);

    List<Formateur> formateurApiListToFormateurDaoList(List<FormateurApi> source);
}
