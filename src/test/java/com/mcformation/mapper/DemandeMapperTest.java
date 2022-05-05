package com.mcformation.mapper;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Demande;
import com.mcformation.model.utils.StatutDemande;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class DemandeMapperTest {


    DemandeMapper mapper = DemandeMapper.INSTANCE;
    Demande demande = new Demande();
    DemandeApi demandeApi = new DemandeApi();

    private Demande getDemandeRH() {
        demande.setDateDemande(new Date(1651673640L));
        demande.setSujet("Ressources Humaines");
        demande.setDetail("Comment arriver à motiver les troupes");
        demande.setStatut(StatutDemande.DEMANDE);
        return demande;
    }

    private Demande getDemandeTreso() {
        demande.setDateDemande(new Date(1651673998L));
        demande.setSujet("Trésorerie");
        demande.setDetail("Comment faire de l'évasion fiscale");
        demande.setStatut(StatutDemande.DEMANDE);
        return demande;
    }

    private DemandeApi getDemandeApiRH() {
        demandeApi.setDateDemande(new Date(1651673998L));
        demandeApi.setSujet("Ressources Humaines Api");
        demandeApi.setDetail("Comment arriver à motiver les troupes Api");
        return demandeApi;
    }

    private DemandeApi getDemandeApiTreso() {
        demandeApi.setDateDemande(new Date(1651673938L));
        demandeApi.setSujet("Trésorerie Api");
        demandeApi.setDetail("Comment faire de l'évasion fiscale Api");
        return demandeApi;
    }

    private boolean compareDemandeDaoAndDemandeApiValues(Demande demande, DemandeApi demandeApi) {
        return Objects.equals(demande.getDateDemande(), demandeApi.getDateDemande())
                && Objects.equals(demande.getSujet(), demandeApi.getSujet())
                && Objects.equals(demande.getDetail(), demandeApi.getDetail());
    }

    @Test
    void demandeDaoToDemandeApi() {
        demandeApi = mapper.demandeDaoToDemandeApi(getDemandeRH());
        Assertions.assertTrue(compareDemandeDaoAndDemandeApiValues(getDemandeRH(), demandeApi));
    }

    @Test
    void demandeDaoToDemandeApiFalse() {
        demandeApi = mapper.demandeDaoToDemandeApi(getDemandeTreso());
        Assertions.assertFalse(compareDemandeDaoAndDemandeApiValues(getDemandeRH(), demandeApi));
    }

    @Test
    void demandeDaoListToDemandeApiList() {
        List<Demande> demandeList = new ArrayList<>();
        demandeList.add(getDemandeRH());
        demandeList.add(getDemandeTreso());
        List<DemandeApi> demandeApiList = mapper.demandeDaoListToDemandeApiList(demandeList);
        Assertions.assertTrue(
                compareDemandeDaoAndDemandeApiValues(demandeList.get(0), demandeApiList.get(0)) &&
                        compareDemandeDaoAndDemandeApiValues(demandeList.get(1), demandeApiList.get(1))
        );
    }

    @Test
    void demandeApiToDemandeDao() {
        demande = mapper.demandeApiToDemandeDao(getDemandeApiRH());
        Assertions.assertTrue(compareDemandeDaoAndDemandeApiValues(demande, getDemandeApiRH()));
    }

    @Test
    void demandeApiToDemandeDaoFalse() {
        demande = mapper.demandeApiToDemandeDao(getDemandeApiRH());
        Assertions.assertFalse(compareDemandeDaoAndDemandeApiValues(demande, getDemandeApiTreso()));
    }

    @Test
    void demandeApiListToDemandeDaoList() {
        List<DemandeApi> demandeApiList = new ArrayList<>();
        demandeApiList.add(getDemandeApiRH());
        demandeApiList.add(getDemandeApiTreso());
        List<Demande> demandeList = mapper.demandeApiListToDemandeDaoList(demandeApiList);
        Assertions.assertTrue(
                compareDemandeDaoAndDemandeApiValues(demandeList.get(0), demandeApiList.get(0)) &&
                        compareDemandeDaoAndDemandeApiValues(demandeList.get(1), demandeApiList.get(1))
        );
    }
}