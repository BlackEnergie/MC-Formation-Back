/* Formation 1 */

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

/* Formation 2 */

INSERT INTO formation (id, audience, cadre, date, duree, materiels, nom, parties, prerequis, type)
VALUES (2, 'Les associatifs', 'WINTER Nancy 2022', NOW(), 60, 'craies;veleda', 'Formation piou', 'les parties', 'Aucun', 'Atelier');

INSERT INTO demande (id, date_demande, detail, statut, sujet, formation_id)
VALUES (2, NOW(), 'Cest quoi MC au final ?', 'A_ATTRIBUER', 'Découvrir MC', 2);

INSERT INTO demande_domaines (demande_id, domaines_id)
VALUES (2, 20), (2, 9);

INSERT INTO association_demandes (association_utilisateur_id, demandes_id)
VALUES (2, 2);

INSERT INTO demande_associations_favorables (demande_id, associations_favorables_utilisateur_id)
VALUES (2, 2);

/* Formation 3 */

INSERT INTO formation (id, audience, cadre, date, duree, materiels, nom, parties, prerequis, type)
VALUES (3, 'Les anciens', 'MIC Grenoble 2021', NOW(), 80, 'éponges;projecteur', 'Comment aider les nouveaux ?', 'les parties', 'être vieux', 'Atelier');

INSERT INTO demande (id, date_demande, detail, statut, sujet, formation_id)
VALUES (3, NOW(), 'Je suis vieux et inutile aidez moi', 'A_ATTRIBUER', 'Aider les nouveaux reprenants', 3);

INSERT INTO demande_domaines (demande_id, domaines_id)
VALUES (3, 3);

INSERT INTO formation_formateurs (formation_id, formateurs_utilisateur_id)
VALUES (3, 3);

INSERT INTO association_demandes (association_utilisateur_id, demandes_id)
VALUES (2, 3);

INSERT INTO demande_associations_favorables (demande_id, associations_favorables_utilisateur_id)
VALUES (3, 2);