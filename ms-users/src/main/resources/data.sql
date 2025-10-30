-- Inserção do usuário
INSERT INTO users (id, email, hashed_password, first_name)
VALUES (
           RANDOM_UUID(),
           'secretaria@pe.com',
           '$2a$04$UlmabbGv8N9C3U1VDwebh.vnnQypNyeJ2gYHy7ID6rq4o3DjTjgum',
           'secretariaPernambuco'
       );

-- Inserção da role associada
INSERT INTO users_roles (user_id, role)
VALUES (
           (SELECT id FROM users WHERE email = 'secretaria@pe.com'),
           'ROLE_SECRETARY'
       );
