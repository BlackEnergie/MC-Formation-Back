INSERT INTO create_user_token (id, email, expiration_date, role, token)
VALUES (1, 'test@yopmail.com', NOW()+6000, 'ROLE_ASSO', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9');
INSERT INTO create_user_token (id, email, expiration_date, role, token)
VALUES (2, 'test2@yopmail.com', NOW()+6000, 'ROLE_FORMATEUR', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ10');
INSERT INTO create_user_token (id, email, expiration_date, role, token)
VALUES (3, 'test3@yopmail.com', NOW()+6000, 'ROLE_BN', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ11');
