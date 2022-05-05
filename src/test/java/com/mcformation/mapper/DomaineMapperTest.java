package com.mcformation.mapper;

import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.database.Domaine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class DomaineMapperTest {

    DomaineMapper mapper = DomaineMapper.INSTANCE;
    Domaine domaine = new Domaine();
    DomaineApi domaineApi = new DomaineApi();

    private Domaine getDomaineRH() {
        Domaine domaineRH = new Domaine();
        domaineRH.setCode("1");
        domaineRH.setLibelle("Ressources Humaines");
        domaineRH.setDescription("Les ressources humaines c'est super !");
        return domaineRH;
    }

    private Domaine getDomaineTreso() {
        Domaine domaineTreso = new Domaine();
        domaineTreso.setCode("2");
        domaineTreso.setLibelle("Trésorerie");
        domaineTreso.setDescription("La tréso c'est fou");
        return domaineTreso;
    }

    private DomaineApi getDomaineApiRH() {
        DomaineApi domaineApi = new DomaineApi();
        domaineApi.setCode("1");
        domaineApi.setLibelle("Ressources Humaines api");
        domaineApi.setDescription("Les ressources humaines c'est super ! api");
        return domaineApi;
    }

    private DomaineApi getDomaineApiTreso() {
        DomaineApi domaineApi = new DomaineApi();
        domaineApi.setCode("2");
        domaineApi.setLibelle("Trésorerie api");
        domaineApi.setDescription("La tréso c'est fou api");
        return domaineApi;
    }

    private boolean compareDomaineDaoAndDomaineApiValues(Domaine domaine, DomaineApi domaineApi) {
        return (
                Objects.equals(domaine.getCode(), domaineApi.getCode()) &&
                        Objects.equals(domaine.getDescription(), domaineApi.getDescription()) &&
                        Objects.equals(domaine.getLibelle(), domaineApi.getLibelle())
        );
    }

    @Test
    void domaineDaoToDomaineApi() {
        domaineApi = mapper.domaineDaoToDomaineApi(getDomaineRH());
        Assertions.assertTrue(compareDomaineDaoAndDomaineApiValues(getDomaineRH(), domaineApi));
    }

    @Test
    void domaineDaoToDomaineApiFalse() {
        domaineApi = mapper.domaineDaoToDomaineApi(getDomaineRH());
        Assertions.assertFalse(compareDomaineDaoAndDomaineApiValues(getDomaineTreso(), domaineApi));
    }

    @Test
    void domaineDaoListToDomaineApiList() {
        List<Domaine> domaineList = new ArrayList<>();
        domaineList.add(getDomaineRH());
        domaineList.add(getDomaineTreso());
        List<DomaineApi> domaineApiList = mapper.domaineDaoListToDomaineApiList(domaineList);
        Assertions.assertTrue(
                compareDomaineDaoAndDomaineApiValues(domaineList.get(0), domaineApiList.get(0)) &&
                        compareDomaineDaoAndDomaineApiValues(domaineList.get(1), domaineApiList.get(1))
        );
    }

    @Test
    void domaineApiToDomaineDao() {
        domaine = mapper.domaineApiToDomaineDao(getDomaineApiRH());
        Assertions.assertTrue(compareDomaineDaoAndDomaineApiValues(domaine, getDomaineApiRH()));
    }

    @Test
    void domaineApiToDomaineDaoFalse() {
        domaine = mapper.domaineApiToDomaineDao(getDomaineApiRH());
        Assertions.assertFalse(compareDomaineDaoAndDomaineApiValues(domaine, getDomaineApiTreso()));
    }

    @Test
    void domaineApiListToDomaineDaoList() {
        List<DomaineApi> domaineApiList = new ArrayList<>();
        domaineApiList.add(getDomaineApiRH());
        domaineApiList.add(getDomaineApiTreso());
        List<Domaine> domaineList = mapper.domaineApiListToDomaineDaoList(domaineApiList);
        Assertions.assertTrue(
                compareDomaineDaoAndDomaineApiValues(domaineList.get(0), domaineApiList.get(0)) &&
                        compareDomaineDaoAndDomaineApiValues(domaineList.get(1), domaineApiList.get(1))
        );
    }
}