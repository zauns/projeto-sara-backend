-- 1. Inserção do ADMINISTRADOR
INSERT INTO administrador
(id, email, senha_hash, nome, is_super_admin, endereco, telefone, tipo)
VALUES
(
    UUID_TO_BIN(UUID()),
    'fulanoadmnato@sara.com',
    '$2a$04$7uuQIXKVtnEzGqFZnoPDBeaoYfeoYCgbNzrr2JuYULt9g5WBRRW0G',
    'Fulano Super Admin',
    1,
    'Rua do Alto, 100',
    '123456789',
    'ADMINISTRADOR'
);

-- 2. Inserção da SECRETARIA
INSERT INTO secretaria
(id, email, senha_hash, nome, endereco, municipio, telefone, is_validada, tipo)
VALUES
(
    UUID_TO_BIN(UUID()),
    'secretaria@daqui.com',
    '$2a$04$7uuQIXKVtnEzGqFZnoPDBeaoYfeoYCgbNzrr2JuYULt9g5WBRRW0G',
    'Secretaria Padrão',
    'Rua da Secretaria, 200',
    'Recife',
    '81999991111',
    1,
    'SECRETARIA'
);

-- 3. Inserção da EMPRESA
INSERT INTO empresa
(id, email, senha_hash, nome, endereco, telefone, cnpj, biografia, links, is_validada, tipo)
VALUES
(
    UUID_TO_BIN(UUID()),
    'empresa@sara.com',
    '$2a$04$pSTzM3acOzgvkRtzwy4fMOgjqziA5g8M/6j9HugC/57zp9bJ/8Qyq',
    'Empresa Padrão Ltda',
    'Av. Empresarial, 300',
    '8133334444',
    '92.167.491/0001-88',
    'Uma empresa de tecnologia focada em inovação.',
    'http://empresa-padrao.com',
    1,
    'EMPRESA'
);

-- 4. Inserção do USER (Candidato)
INSERT INTO `user`
(id, email, senha_hash, nome, endereco, telefone, tipo)
VALUES
(
    UUID_TO_BIN(UUID()),
    'candidato.padrao@email.com',
    '$2a$04$7uuQIXKVtnEzGqFZnoPDBeaoYfeoYCgbNzrr2JuYULt9g5WBRRW0G',
    'Candidato Padrão',
    'Rua do Candidato, 500',
    '81988887777',
    'CANDIDATO'
);
