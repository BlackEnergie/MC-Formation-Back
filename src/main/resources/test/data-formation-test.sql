INSERT INTO formation (id, audience, cadre, date, duree, materiels, nom, parties, prerequis, type)
VALUES (1, 'Tout le monde', 'SPRING', NOW(), 80, 'éponges;Tableau blanc', 'Comment administrer la fédération ?', 'les parties', 'Aucun', 'Formation');

INSERT INTO demande (id, date_demande, detail, statut, sujet, formation_id)
VALUES (1, NOW(), 'Je veux savoir quoi faire parce que je suis admin', 'A_VENIR', 'Administration de la fédération', 1);

INSERT INTO demande_domaines (demande_id, domaines_id)
VALUES (1, 1), (1, 9);

INSERT INTO formation_formateurs (formation_id, formateurs_utilisateur_id)
VALUES (1, 3);

INSERT INTO association_demandes (association_utilisateur_id, demandes_id)
VALUES (2, 1);

INSERT INTO demande_associations_favorables (demande_id, associations_favorables_utilisateur_id)
VALUES (1, 2);