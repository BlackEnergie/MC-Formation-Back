package com.mcformation.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.Formation;
import com.mcformation.model.utils.StatutDemande;

import org.springframework.stereotype.Repository;

@Repository
class DemandeRepositoryImpl implements DemandeRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Demande> findFormations(int offset, int limit,String statut) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Demande> cq = cb.createQuery(Demande.class);
        Root<Demande> root = cq.from(Demande.class);
        Join<Demande, Formation> formation = root.join("formation",JoinType.LEFT);
        Predicate predicateStatut=null;
        Path<Object> statutDemande=root.get("statut");
        switch(statut){
            case "DEMANDE":
                predicateStatut= cb.equal(statutDemande,StatutDemande.DEMANDE);
                break;
            case "A_ATTRIBUER":
                predicateStatut= cb.equal(statutDemande,StatutDemande.A_ATTRIBUER);
                break;
            case "A_VENIR":
                predicateStatut= cb.equal(statutDemande,StatutDemande.A_VENIR);
                break;
            case "PASSEE":
                predicateStatut= cb.equal(statutDemande,StatutDemande.PASSEE);
                break;
            default:
                predicateStatut= cb.isNotNull(statutDemande);
        }
        /* Expression<Date> dateFormation= formation.get("date");
        Date date;
        Date dateF;
        Predicate predicateDate=null;
        if(dateDebut.length()==0 && dateFin.length()==0){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate localDate = LocalDate.now();
           dateDebut = "2022-01-01";
           dateFin = dtf.format(localDate);
        }
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebut);
            dateF = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
            predicateDate= cb.between(dateFormation, date, dateF);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        Expression<String> cadreFormation= formation.get("cadre");
        Predicate predicateCadre = cb.like(cadreFormation, cadre+"%"); */
      /*   In<String>predicateDomaines=null;
        if(!domainesFiltres.isEmpty()){
            predicateDomaines = cb.in(domaines.get("code"));
            for (String domaineCode:domainesFiltres) {
                predicateDomaines.value(domaineCode);
            }
          
        }else{
            Predicate predicateDomainesNotNull = cb.isNotNull(domaines.get("code"));
            cq.where(predicateStatut,predicateDate,predicateDomainesNotNull,predicateCadre);
        } */
        cq.where(predicateStatut);
        TypedQuery<Demande> query = em.createQuery(cq).setMaxResults(limit).setFirstResult(offset);
        return query.getResultList();
    }
}