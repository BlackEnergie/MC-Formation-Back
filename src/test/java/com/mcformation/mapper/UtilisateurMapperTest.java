package com.mcformation.mapper;

import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.utils.College;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class UtilisateurMapperTest {

    UtilisateurMapper mapper = UtilisateurMapper.INSTANCE;
    Utilisateur utilisateur = new Utilisateur();
    Association association = new Association();
    AssociationApi associationApi = new AssociationApi();

    private Association getAMBAssociation() {
        association.setAcronyme("AMB");
        association.setCollege(College.A);
        association.setVille("Bordeaux");
        association.setNomComplet("Asso MIAGE Bordeaux");
        utilisateur.setEmail("amb@asso.com");
        association.setUtilisateur(utilisateur);
        return association;
    }

    private Association getMIPAssociation() {
        association.setAcronyme("MIP");
        association.setCollege(College.B);
        association.setVille("Paris");
        association.setNomComplet("Miagistes Importés à Paname");
        utilisateur.setEmail("mip@asso.com");
        association.setUtilisateur(utilisateur);
        return association;
    }

    private AssociationApi getAMBAssociationApi() {
        associationApi.setAcronyme("AMB");
        associationApi.setCollege(College.A);
        associationApi.setEmail("amb@asso.com");
        associationApi.setNomComplet("Asso MIAGE Bordeaux");
        associationApi.setVille("Bordeaux");
        return associationApi;
    }

    private AssociationApi getMIPAssociationApi() {
        associationApi.setAcronyme("MIP");
        associationApi.setCollege(College.B);
        associationApi.setEmail("mip@asso.com");
        associationApi.setNomComplet("Miagistes Importés à Paname");
        associationApi.setVille("Paris");
        return associationApi;
    }

    private boolean compareAssociationAndAssociationApiDetailValues(Association association, AssociationApi associationApi) {
        return Objects.equals(association.getAcronyme(), associationApi.getAcronyme())
                && Objects.equals(association.getNomComplet(), associationApi.getNomComplet())
                && Objects.equals(association.getCollege(), associationApi.getCollege())
                && Objects.equals(association.getVille(), associationApi.getVille())
                && Objects.equals(association.getUtilisateur().getEmail(), associationApi.getEmail());
    }

    private boolean compareAssociationAndAssociationApiAccueilValues(Association association, AssociationApi associationApi) {
        return Objects.equals(association.getAcronyme(), associationApi.getAcronyme())
                && Objects.equals(association.getNomComplet(), associationApi.getNomComplet())
                && Objects.equals(null, associationApi.getCollege())
                && Objects.equals(null, associationApi.getVille())
                && Objects.equals(null, associationApi.getEmail());
    }

    @Test
    void associationDaoToAssociationApiDetail() {
        associationApi = mapper.associationDaoToAssociationApiDetail(getAMBAssociation());
        Assertions.assertTrue(compareAssociationAndAssociationApiDetailValues(getAMBAssociation(), associationApi));
    }

    @Test
    void associationDaoToAssociationApiDetailFailure() {
        associationApi = mapper.associationDaoToAssociationApiDetail(getAMBAssociation());
        Assertions.assertFalse(compareAssociationAndAssociationApiDetailValues(getMIPAssociation(), associationApi));
    }

    @Test
    void associationDaoListToAssociationApiListDetail() {
        ArrayList<Association> associationArrayList = new ArrayList<>();
        associationArrayList.add(getAMBAssociation());
        associationArrayList.add(getMIPAssociation());
        List<AssociationApi> associationApiArrayList = mapper.associationDaoListToAssociationApiListDetail(associationArrayList);
        Assertions.assertTrue(
                compareAssociationAndAssociationApiDetailValues(associationArrayList.get(0), associationApiArrayList.get(0))
                        & compareAssociationAndAssociationApiDetailValues(associationArrayList.get(1), associationApiArrayList.get(1))
        );
    }

    @Test
    void associationDaoToAssociationApiAccueil() {
        associationApi = mapper.associationDaoToAssociationApiAccueil(getAMBAssociation());
        Assertions.assertTrue(compareAssociationAndAssociationApiAccueilValues(getAMBAssociation(), associationApi));
    }

    @Test
    void associationDaoToAssociationApiAccueilFalse() {
        associationApi = mapper.associationDaoToAssociationApiAccueil(getAMBAssociation());
        Assertions.assertFalse(compareAssociationAndAssociationApiAccueilValues(getMIPAssociation(), associationApi));
    }

    @Test
    void associationDaoListToAssociationApiListAccueil() {
        ArrayList<Association> associationArrayList = new ArrayList<>();
        associationArrayList.add(getAMBAssociation());
        associationArrayList.add(getMIPAssociation());
        List<AssociationApi> associationApiArrayList = mapper.associationDaoListToAssociationApiListAccueil(associationArrayList);
        Assertions.assertTrue(
                compareAssociationAndAssociationApiAccueilValues(associationArrayList.get(0), associationApiArrayList.get(0))
                        & compareAssociationAndAssociationApiAccueilValues(associationArrayList.get(1), associationApiArrayList.get(1))
        );
    }

    @Test
    void associationApiToAssociationDao() {
        association = mapper.associationApiToAssociationDao(getAMBAssociationApi());
        Assertions.assertTrue(compareAssociationAndAssociationApiDetailValues(association, getAMBAssociationApi()));
    }

    @Test
    void associationApiToAssociationDaoFailure() {
        association = mapper.associationApiToAssociationDao(getAMBAssociationApi());
        Assertions.assertFalse(compareAssociationAndAssociationApiDetailValues(association, getMIPAssociationApi()));
    }

    @Test
    void associationApiListToAssociationDaoList() {
        ArrayList<AssociationApi> associationApiArrayList = new ArrayList<>();
        associationApiArrayList.add(getAMBAssociationApi());
        associationApiArrayList.add(getMIPAssociationApi());
        List<Association> associationList = mapper.associationApiListToAssociationDaoList(associationApiArrayList);
        Assertions.assertTrue(
                compareAssociationAndAssociationApiDetailValues(associationList.get(0), associationApiArrayList.get(0))
                        && compareAssociationAndAssociationApiDetailValues(associationList.get(1), associationApiArrayList.get(1))
        );
    }


}