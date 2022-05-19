INSERT INTO utilisateur (id, email, nom_utilisateur, password, roles_id) VALUES (1, 'bn@miage.net', 'bn', '$2a$10$05KqhujXhGC9dz8.PUqu7.fzfVhpoDF1dPNG5WwpiL8dnqgqNT8ba', 3);
INSERT INTO membre_bureau_national (utilisateur_id, poste) VALUES (1, 'VP Formations');

INSERT INTO utilisateur (id, email, nom_utilisateur, password, roles_id) VALUES (2, 'asso@miage.net', 'asso', '$2a$10$05KqhujXhGC9dz8.PUqu7.fzfVhpoDF1dPNG5WwpiL8dnqgqNT8ba', 1);
INSERT INTO association (utilisateur_id, acronyme, college, nom_complet, ville) VALUES (2, 'AMB', 'A', 'Asso MIAGE Bordeaux', 'Bordeaux');

INSERT INTO utilisateur (id, email, nom_utilisateur, password, roles_id) VALUES (3, 'formateur@miage.net', 'formateur', '$2a$10$05KqhujXhGC9dz8.PUqu7.fzfVhpoDF1dPNG5WwpiL8dnqgqNT8ba', 2);
INSERT INTO formateur (utilisateur_id, date_creation, nom, nom_complet, prenom) VALUES (3, NOW(), 'Dupont', 'Dupont Jean', 'Jean');

INSERT INTO utilisateur (id, email, nom_utilisateur, password, roles_id) VALUES (4, 'asso1@miage.net', 'assoa', '$2a$10$05KqhujXhGC9dz8.PUqu7.fzfVhpoDF1dPNG5WwpiL8dnqgqNT8ba', 1);
INSERT INTO association (utilisateur_id, acronyme, college, nom_complet, ville) VALUES (4, 'ASSO', 'B', 'Asso Bordeaux', 'Bordeaux');
