package com.mcformation.mapper;

import com.mcformation.model.api.FormationApi;
import com.mcformation.model.api.PartieApi;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Formation;
import com.mcformation.model.utils.StatutDemande;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class FormationApiMapperTest {

    FormationApiMapper mapper = FormationApiMapper.INSTANCE;
    Demande demande = new Demande();
    Formation formation = new Formation();
    FormationApi formationApi = new FormationApi();

    private String getPartiesString1et2() {
        return "[{\"id\":1,\"plan\":\"plan1\",\"timing\":1,\"contenu\":\"contenu1\",\"methodologie\":\"methodologie1\"},{\"id\":2,\"plan\":\"plan2\",\"timing\":2,\"contenu\":\"contenu2\",\"methodologie\":\"methodologie2\"}]";
    }

    private String getPartiesString3et4() {
        return "[{\"id\":3,\"plan\":\"plan3\",\"timing\":3,\"contenu\":\"contenu3\",\"methodologie\":\"methodologie3\"},{\"id\":4,\"plan\":\"plan4\",\"timing\":4,\"contenu\":\"contenu4\",\"methodologie\":\"methodologie4\"}]";
    }

    private List<PartieApi> getPartieList1et2() {
        List<PartieApi> parties = new ArrayList<>();
        PartieApi partie1 = new PartieApi();
        partie1.setId(1L);
        partie1.setContenu("contenu1");
        partie1.setMethodologie("methodologie1");
        partie1.setPlan("plan1");
        partie1.setTiming(1L);
        parties.add(partie1);
        PartieApi partie2 = new PartieApi();
        partie2.setId(2L);
        partie2.setContenu("contenu2");
        partie2.setMethodologie("methodologie2");
        partie2.setPlan("plan2");
        partie2.setTiming(2L);
        parties.add(partie2);
        return parties;
    }

    private List<PartieApi> getPartieList3et4() {
        List<PartieApi> parties = new ArrayList<>();
        PartieApi partie3 = new PartieApi();
        partie3.setId(3L);
        partie3.setContenu("contenu3");
        partie3.setMethodologie("methodologie3");
        partie3.setPlan("plan3");
        partie3.setTiming(3L);
        parties.add(partie3);
        PartieApi partie4 = new PartieApi();
        partie4.setId(4L);
        partie4.setContenu("contenu4");
        partie4.setMethodologie("methodologie4");
        partie4.setPlan("plan4");
        partie4.setTiming(4L);
        parties.add(partie4);
        return parties;

    }

    private Formation getFormationRH() {
        formation.setId(1L);
        formation.setAudience("Tout le monde");
        formation.setCadre("SPRING Amiens 2022");
        formation.setDate(new Date(1651738073L));
        formation.setType("Formation");
        formation.setDuree(1.5F);
        formation.setPrerequis("être étudiant");
        formation.setMateriels("feutres;projecteur");
        formation.setParties(getPartiesString1et2());
        formation.setObjectifs("définition RH;savoir recruter");
        formation.setNom("Comment devenir un bon RH ?");
        return formation;
    }

    private Formation getFormationTreso() {
        formation.setId(2L);
        formation.setDate(new Date(1651673660L));
        formation.setDuree(1.8F);
        formation.setNom("Arnaque fiscale");
        formation.setType("Atelier");
        formation.setPrerequis("avoir des connaissances dans la compta");
        formation.setAudience("Les Trésoriers et les fans de l'évasion");
        formation.setParties(getPartiesString3et4());
        formation.setMateriels("billets; carte bancaire;chèquier");
        formation.setObjectifs("Apprendre les bases de la comptabilité;maîtriser la stabilité de l'association");
        formation.setCadre("CDH Bordeaux 2023");
        return formation;
    }

    private Demande getDemandeRH() {
        demande.setId(1L);
        demande.setDateDemande(new Date(1651673640L));
        demande.setSujet("Ressources Humaines");
        demande.setDetail("Comment arriver à motiver les troupes");
        demande.setStatut(StatutDemande.A_VENIR);
        demande.setFormation(getFormationRH());
        return demande;
    }

    private Demande getDemandeTreso() {
        demande.setId(2L);
        demande.setDateDemande(new Date(1651673998L));
        demande.setSujet("Trésorerie");
        demande.setDetail("Comment faire de l'évasion fiscale");
        demande.setStatut(StatutDemande.A_VENIR);
        demande.setFormation(getFormationTreso());
        return demande;
    }

    private FormationApi getFormationApiRH() {
        formationApi.setId(3L);
        formationApi.setAudience("Tout le monde api");
        formationApi.setCadre("SPRING Amiens 2022 api");
        formationApi.setDate(new Date(1651738090L));
        formationApi.setType("Formation api");
        formationApi.setDuree(1.9F);
        formationApi.setPrerequis("être étudiant api");

        List<String> materiels = new ArrayList<>();
        materiels.add("feutres api");
        materiels.add("projecteur api");
        formationApi.setMateriels(materiels);

        formationApi.setParties(getPartieList1et2());

        List<String> objectifs = new ArrayList<>();
        objectifs.add("définition RH api");
        objectifs.add("savoir recruter api");
        formationApi.setObjectifs(objectifs);
        formationApi.setNom("Comment devenir un bon RH api ?");

        formationApi.setDateDemande(new Date(1651673333L));
        formationApi.setSujet("Ressources Humaines api");
        formationApi.setDetail("Comment arriver à motiver les troupes api");
        formationApi.setStatut(StatutDemande.A_ATTRIBUER);
        return formationApi;
    }

    private FormationApi getFormationApiTreso() {
        formationApi.setId(4L);
        formationApi.setDate(new Date(1651673660L));
        formationApi.setDuree(1.8F);
        formationApi.setNom("Arnaque fiscale Api");
        formationApi.setType("Atelier Api");
        formationApi.setPrerequis("avoir des connaissances dans la compta Api");
        formationApi.setAudience("Les Trésoriers et les fans de l'évasion Api");

        formationApi.setParties(getPartieList3et4());

        List<String> materiels = new ArrayList<>();
        materiels.add("billets api");
        materiels.add("carte bancaire api");
        materiels.add("chèquier Api");
        formationApi.setMateriels(materiels);

        List<String> objectifs = new ArrayList<>();
        objectifs.add("Apprendre les bases de la comptabilité Api");
        objectifs.add("maîtriser la stabilité de l'association Api");
        formationApi.setObjectifs(objectifs);

        formationApi.setCadre("CDH Bordeaux 2023 Api");

        formationApi.setDateDemande(new Date(1651673680L));
        formationApi.setSujet("Trésorerie");
        formationApi.setDetail("Comment arriver à motiver les troupes à faire de l'évasion fiscale");
        formationApi.setStatut(StatutDemande.A_VENIR);
        return formationApi;
    }

    private boolean compareObjectStringAndObjectList(String objectString, List<String> objectList) {
        String[] objectStringSplitted = objectString.split(";");
        boolean res = (objectList.size() == objectStringSplitted.length);
        int i = 0;
        while (i < objectList.size() && res) {
            res = Objects.equals(objectStringSplitted[i], objectList.get(i));
            i++;
        }
        return res;
    }

    private boolean compareDemandeDaoAndFormationApiValuesDetail(Demande demande, FormationApi formationApi) {
        return (
                Objects.equals(demande.getId(), formationApi.getId()) &&
                        Objects.equals(demande.getDateDemande(), formationApi.getDateDemande()) &&
                        Objects.equals(demande.getDetail(), formationApi.getDetail()) &&
                        Objects.equals(demande.getSujet(), formationApi.getSujet()) &&
                        Objects.equals(demande.getStatut(), formationApi.getStatut()) &&
                        Objects.equals(demande.getFormation().getDate(), formationApi.getDate()) &&
                        Objects.equals(demande.getFormation().getAudience(), formationApi.getAudience()) &&
                        Objects.equals(demande.getFormation().getCadre(), formationApi.getCadre()) &&
                        Objects.equals(demande.getFormation().getDuree(), formationApi.getDuree()) &&
                        Objects.equals(demande.getFormation().getNom(), formationApi.getNom()) &&
                        Objects.equals(demande.getFormation().getType(), formationApi.getType()) &&
                        Objects.equals(demande.getFormation().getPrerequis(), formationApi.getPrerequis()) &&
                        compareObjectStringAndObjectList(demande.getFormation().getObjectifs(), formationApi.getObjectifs()) &&
                        compareObjectStringAndObjectList(demande.getFormation().getMateriels(), formationApi.getMateriels())
        );
    }

    private boolean compareDemandeDaoAndFormationApiValuesAccueil(Demande demande, FormationApi formationApi) {
        return (
                Objects.equals(demande.getId(), formationApi.getId()) &&
                        Objects.equals(demande.getSujet(), formationApi.getSujet()) &&
                        Objects.equals(demande.getStatut(), formationApi.getStatut()) &&
                        Objects.equals(demande.getFormation().getDate(), formationApi.getDate()) &&
                        Objects.equals(demande.getFormation().getCadre(), formationApi.getCadre()) &&
                        Objects.equals(demande.getFormation().getNom(), formationApi.getNom()) &&
                        Objects.equals(demande.getDetail(), formationApi.getDetail()) &&
                        Objects.equals(null, formationApi.getDateDemande()) &&
                        Objects.equals(null, formationApi.getAudience()) &&
                        Objects.equals(null, formationApi.getDuree()) &&
                        Objects.equals(null, formationApi.getType()) &&
                        Objects.equals(null, formationApi.getPrerequis()) &&
                        Objects.equals(null, formationApi.getParties()) &&
                        Objects.equals(null, formationApi.getMateriels()) &&
                        Objects.equals(null, formationApi.getObjectifs())
        );
    }

    private boolean compareFormationDaoAndFormationApiValues(Formation formation, FormationApi formationApi) {
        return (
                Objects.equals(formation.getId(), formationApi.getId()) &&
                        Objects.equals(formation.getDate(), formationApi.getDate()) &&
                        Objects.equals(formation.getCadre(), formationApi.getCadre()) &&
                        Objects.equals(formation.getNom(), formationApi.getNom()) &&
                        Objects.equals(formation.getPrerequis(), formationApi.getPrerequis()) &&
                        Objects.equals(formation.getAudience(), formationApi.getAudience()) &&
                        Objects.equals(formation.getDuree(), formationApi.getDuree()) &&
                        Objects.equals(formation.getType(), formationApi.getType()) &&
                        compareObjectStringAndObjectList(formation.getObjectifs(), formationApi.getObjectifs()) &&
                        compareObjectStringAndObjectList(formation.getMateriels(), formationApi.getMateriels())
        );
    }

    private boolean comparePartieApiListValues(List<PartieApi> a, List<PartieApi> b) {
        boolean res = true;
        for (int i = 0 ; i < a.size() ; i++ ) {
            res = res && comparePartieApiValues(a.get(i), b.get(i));
        }
        return res;
    }

    private boolean comparePartieApiValues(PartieApi a, PartieApi b) {
        return (
                Objects.equals(a.getId(), b.getId()) &&
                        Objects.equals(a.getContenu(), b.getContenu()) &&
                        Objects.equals(a.getMethodologie(), b.getMethodologie()) &&
                        Objects.equals(a.getPlan(), b.getPlan()) &&
                        Objects.equals(a.getTiming(), b.getTiming())
        );
    }

    @Test
    void demandeDaoToFormationApiDetail() {
        formationApi = mapper.demandeDaoToFormationApiDetail(getDemandeRH());
        Assertions.assertTrue(compareDemandeDaoAndFormationApiValuesDetail(getDemandeRH(), formationApi));
    }

    @Test
    void demandeDaoToFormationApiDetailFalse() {
        formationApi = mapper.demandeDaoToFormationApiDetail(getDemandeRH());
        Assertions.assertFalse(compareDemandeDaoAndFormationApiValuesDetail(getDemandeTreso(), formationApi));
    }

    @Test
    void demandeDaoListToFormationApiListDetail() {
        List<Demande> demandeList = new ArrayList<>();
        demandeList.add(getDemandeRH());
        demandeList.add(getDemandeTreso());

        List<FormationApi> formationApiList = mapper.demandeDaoListToFormationApiListDetail(demandeList);
        Assertions.assertTrue(
                compareDemandeDaoAndFormationApiValuesDetail(demandeList.get(0), formationApiList.get(0)) &&
                        compareDemandeDaoAndFormationApiValuesDetail(demandeList.get(1), formationApiList.get(1))
        );
    }

    @Test
    void demandeDaoToFormationApiAccueil() {
        formationApi = mapper.demandeDaoToFormationApiAccueil(getDemandeRH());
        Assertions.assertTrue(compareDemandeDaoAndFormationApiValuesAccueil(getDemandeRH(), formationApi));
    }

    @Test
    void demandeDaoToFormationApiAccueilFalse() {
        formationApi = mapper.demandeDaoToFormationApiAccueil(getDemandeRH());
        Assertions.assertFalse(compareDemandeDaoAndFormationApiValuesAccueil(getDemandeTreso(), formationApi));
    }

    @Test
    void demandeDaoListToFormationApiListAccueil() {
        List<Demande> demandeList = new ArrayList<>();
        demandeList.add(getDemandeRH());
        demandeList.add(getDemandeTreso());

        List<FormationApi> formationApiList = mapper.demandeDaoListToFormationApiListAccueil(demandeList);
        Assertions.assertTrue(
                compareDemandeDaoAndFormationApiValuesAccueil(demandeList.get(0), formationApiList.get(0)) &&
                        compareDemandeDaoAndFormationApiValuesAccueil(demandeList.get(1), formationApiList.get(1))
        );

    }

    @Test
    void formationApiToDemandeDao() {
        demande = mapper.formationApiToDemandeDao(getFormationApiRH());
        Assertions.assertTrue(compareDemandeDaoAndFormationApiValuesDetail(demande, getFormationApiRH()));
    }

    @Test
    void formationApiToDemandeDaoFalse() {
        demande = mapper.formationApiToDemandeDao(getFormationApiRH());
        Assertions.assertFalse(compareDemandeDaoAndFormationApiValuesDetail(demande, getFormationApiTreso()));
    }

    @Test
    void formationApiToFormationDao() {
        formation = mapper.formationApiToFormationDao(getFormationApiTreso());
        Assertions.assertTrue(compareFormationDaoAndFormationApiValues(formation, getFormationApiTreso()));
    }

    @Test
    void listStringToString() {
        List<String> listString = new ArrayList<>();
        listString.add("Premier");
        listString.add("Deuxieme");
        String StringList = mapper.listStringToString(listString);
        Assertions.assertTrue(Objects.equals(StringList, "Premier;Deuxieme"));
    }

    @Test
    void stringToListString() {
        String testString = "1;2;3;4";
        List<String> ListString = mapper.stringToListString(testString);
        Assertions.assertTrue(compareObjectStringAndObjectList(testString,ListString));
    }


    @Test
    void objectListPartieToJsonParties() {
        List<PartieApi> parties = getPartieList1et2();
        String json = mapper.objectListPartieToJsonParties(parties);
        Assertions.assertEquals(json, getPartiesString1et2());
    }

    @Test
    void jsonPartiesToObjectListPartie() {
        String json = getPartiesString3et4();
        List<PartieApi> parties = mapper.jsonPartiesToObjectListPartie(json);
        Assertions.assertTrue(comparePartieApiListValues(parties, getPartieList3et4()));
    }
}