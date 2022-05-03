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

class AssociationMapperTest {

    AssociationMapper mapper = AssociationMapper.INSTANCE;
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

    private boolean compareAssociationAndAssociationApiValues(Association association, AssociationApi associationApi) {
        return Objects.equals(association.getAcronyme(), associationApi.getAcronyme())
                && Objects.equals(association.getNomComplet(), associationApi.getNomComplet())
                && Objects.equals(association.getCollege(), associationApi.getCollege())
                && Objects.equals(association.getVille(), associationApi.getVille())
                && Objects.equals(association.getUtilisateur().getEmail(), associationApi.getEmail());
    }

    @Test
    void associationDaoToAssociationApi() {
        associationApi = mapper.associationDaoToAssociationApi(getAMBAssociation());
        Assertions.assertTrue(compareAssociationAndAssociationApiValues(getAMBAssociation(), associationApi));
    }

    @Test
    void associationDaoToAssociationApiFailure() {
        associationApi = mapper.associationDaoToAssociationApi(getAMBAssociation());
        Assertions.assertFalse(compareAssociationAndAssociationApiValues(getMIPAssociation(), associationApi));
    }

    @Test
    void associationDaoListToAssociationApiList() {
        ArrayList<Association> associationArrayList = new ArrayList<>();
        associationArrayList.add(getAMBAssociation());
        associationArrayList.add(getMIPAssociation());
        List<AssociationApi> associationApiArrayList = mapper.associationDaoListToAssociationApiList(associationArrayList);
        Assertions.assertTrue(
                compareAssociationAndAssociationApiValues(associationArrayList.get(0), associationApiArrayList.get(0))
                        & compareAssociationAndAssociationApiValues(associationArrayList.get(1), associationApiArrayList.get(1))
        );
    }

    @Test
    void associationApiToAssociationDao() {
        association = mapper.associationApiToAssociationDao(getAMBAssociationApi());
        Assertions.assertTrue(compareAssociationAndAssociationApiValues(association, getAMBAssociationApi()));
    }

    @Test
    void associationApiToAssociationDaoFailure() {
        association = mapper.associationApiToAssociationDao(getAMBAssociationApi());
        Assertions.assertFalse(compareAssociationAndAssociationApiValues(association, getMIPAssociationApi()));
    }

    @Test
    void associationApiListToAssociationDaoList() {
        ArrayList<AssociationApi> associationApiArrayList = new ArrayList<>();
        associationApiArrayList.add(getAMBAssociationApi());
        associationApiArrayList.add(getMIPAssociationApi());
        List<Association> associationList = mapper.associationApiListToAssociationDaoList(associationApiArrayList);
        Assertions.assertTrue(
                compareAssociationAndAssociationApiValues(associationList.get(0), associationApiArrayList.get(0))
                && compareAssociationAndAssociationApiValues(associationList.get(1), associationApiArrayList.get(1))
        );
    }
}