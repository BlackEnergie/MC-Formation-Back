package com.mcformation.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import com.mcformation.model.database.Demande;
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
        }
        if(predicateStatut!=null){
            cq.where(predicateStatut);
        }
        TypedQuery<Demande> query = em.createQuery(cq).setMaxResults(limit).setFirstResult(offset);
        return query.getResultList();
    }
}