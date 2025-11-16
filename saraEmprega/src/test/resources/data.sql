-- Inserção do usuário administrador
INSERT INTO administrador
(id, email, senha_hash, nome, is_super_admin, endereco, telefone)
VALUES
(
    UUID_TO_BIN(UUID()), -- Gera um UUID novo
    'fulanoadmnato@sara.com', -- E-mail para login
    '$2a$04$7uuQIXKVtnEzGqFZnoPDBeaoYfeoYCgbNzrr2JuYULt9g5WBRRW0G', -- Hash para "admin123"
    'Fulano',
    1,
    'Rua do alto',
    '123456789'
);
