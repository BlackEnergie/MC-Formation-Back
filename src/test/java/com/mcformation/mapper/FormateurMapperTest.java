package com.mcformation.mapper;

import com.mcformation.model.api.FormateurApi;
import com.mcformation.model.database.Formateur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FormateurMapperTest {
    FormateurMapper mapper = FormateurMapper.INSTANCE;
    Formateur formateur = new Formateur();
    FormateurApi formateurApi = new FormateurApi();

    private Formateur getFormateurTheo () {
        formateur.setDateCreation(new Date(1651738073L));
        formateur.setNom("Perin");
        formateur.setPrenom("Théo");
        formateur.setId(3L);
        return formateur;
    }

    private Formateur getFormateurJulien() {
        formateur.setDateCreation(new Date(1651738090L));
        formateur.setNom("Dubert");
        formateur.setPrenom("Julien");
        formateur.setId(2L);
        return formateur;
    }

    private FormateurApi getFormateurApiTheo() {
        formateurApi.setId(3L);
        formateurApi.setNom("Perrin API");
        formateurApi.setPrenom("Théo API");
        return formateurApi;
    }

    private FormateurApi getFormateurApiJulien(){
        formateurApi.setId(2L);
        formateurApi.setNom("Dubert API");
        formateurApi.setPrenom("Julien API");
        return formateurApi;
    }

    private boolean compareFormateurDaoAndFormateurApiValues (Formateur formateur, FormateurApi formateurApi){
        return Objects.equals(formateur.getId(), formateurApi.getId())
                && Objects.equals(formateur.getNom(), formateurApi.getNom())
                && Objects.equals(formateur.getPrenom(), formateurApi.getPrenom());
    }

    @Test
    void formateurDaoToFormateurApi() {
        formateurApi = mapper.formateurDaoToFormateurApi(getFormateurJulien());
        Assertions.assertTrue(compareFormateurDaoAndFormateurApiValues(getFormateurJulien(), formateurApi));
    }

    @Test
    void formateurDaoToFormateurApiFalse() {
        formateurApi = mapper.formateurDaoToFormateurApi(getFormateurJulien());
        Assertions.assertFalse(compareFormateurDaoAndFormateurApiValues(getFormateurTheo(), formateurApi));
    }

    @Test
    void formateurDaoListToFormateurApiList() {
        List<Formateur> formateurList = new ArrayList<>();
        formateurList.add(getFormateurJulien());
        formateurList.add(getFormateurTheo());
        List<FormateurApi> formateurApiList = mapper.formateurDaoListToFormateurApiList(formateurList);
        Assertions.assertTrue(
                compareFormateurDaoAndFormateurApiValues(formateurList.get(0), formateurApiList.get(0)) &&
                compareFormateurDaoAndFormateurApiValues(formateurList.get(1), formateurApiList.get(1))
        );
    }

    @Test
    void formateurApiToFormateurDao() {
        formateur = mapper.formateurApiToFormateurDao(getFormateurApiJulien());
        Assertions.assertTrue(compareFormateurDaoAndFormateurApiValues(formateur, getFormateurApiJulien()));
    }

    @Test
    void formateurApiToFormateurDaoFalse() {
        formateur = mapper.formateurApiToFormateurDao(getFormateurApiJulien());
        Assertions.assertFalse(compareFormateurDaoAndFormateurApiValues(formateur, getFormateurApiTheo()));
    }

    @Test
    void formateurApiListToFormateurDaoList() {
        List<FormateurApi> formateurApiList = new ArrayList<>();
        formateurApiList.add(getFormateurApiJulien());
        formateurApiList.add(getFormateurApiTheo());
        List<Formateur> formateurList = mapper.formateurApiListToFormateurDaoList(formateurApiList);
        Assertions.assertTrue(
                compareFormateurDaoAndFormateurApiValues(formateurList.get(0), formateurApiList.get(0)) &&
                        compareFormateurDaoAndFormateurApiValues(formateurList.get(1), formateurApiList.get(1))
        );
    }
}