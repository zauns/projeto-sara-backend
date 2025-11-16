#!/bin/bash

#Variáveis -- elas são chamadas com $NOME_DA_VARIÀVEL, estão sendo abstraidas aqui por serem muito grandinhas

# --- URL Base ---
BASE_URL="http://localhost:8080"


# Atribuidos ao criar uma conta
ID_ADMIN_COMUM="<ID_do_admin_a_ser_alterado_ou_deletado>"
ID_EMPRESA="<ID_da_empresa_a_ser_aprovada_ou_deletada>"
ID_SECRETARIA="<ID_da_secretaria_a_ser_aprovada_ou_deletada>"

# Atribuidos ao fazer login nas contas
TOKEN_SUPER_ADMIN="<cole_o_token_do_super_admin_aqui>"
TOKEN_ADMIN_COMUM="<cole_o_token_do_admin_comum_aqui>"
TOKEN_EMPRESA="<cole_o_token_da_empresa_aqui>"
TOKEN_SECRETARIA="<cole_o_token_da_secretaria_aqui>"


# Login


# Login Super Admin 
curl -s -X POST $BASE_URL/token \
     -H "Content-Type: application/json" \
     -d '{
           "username": "<email_super_admin>",
           "password": "<senha_super_admin>"
         }'

# Login da empresa, se tiver sido aprovado pelo admin
curl -s -X POST $BASE_URL/token \
     -H "Content-Type: application/json" \
     -d '{
           "username": "<email_da_empresa_aprovada>",
           "password": "<senha_da_empresa>"
         }'

# Login Secretaria, se tiver sido aprovado pelo admin
curl -s -X POST $BASE_URL/token \
     -H "Content-Type: application/json" \
     -d '{
           "username": "<email_da_secretaria_aprovada>",
           "password": "<senha_da_secretaria>"
         }'



# Comandos do administrador


# Cria um novo admin comun, mas precisa estar logado como super admin
# O primeiro super admin é definido em resources/data.sql no projeto do back end
curl -v -X POST $BASE_URL/administrador \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN" \
     -d '{
           "nome": "admineutron",
           "email": "admineutron@sara.com",
           "senha": "senha123",
           "telefone": "12345678",
           "endereco": "Rua do Lado",
           "isSuperAdmin": false
         }'

# O admin ou super admin podem fazer a consulta de todos os admin
# mas para isso precisa estar logado
curl -v -X GET $BASE_URL/administrador \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN"

# O admin ou super admin podem fazer a consulta de uma conta admin
# Apenas precisando estar logado e do ID do admin
curl -v -X GET $BASE_URL/administrador/$ID_ADMIN_COMUM \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN"

# Somente o super admin pode atualizar os dados de um admin, podendo dar a ele o atributo de super admin
curl -v -X PUT $BASE_URL/administrador/$ID_ADMIN_COMUM \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN" \
     -d '{
           "nome": "adminilson",
           "email": "adminilson@sara.com",
           "senha": "novaSenha123",
           "telefone": "87654321",
           "endereco": "Rua do outro lado",
           "isSuperAdmin": true
         }'

# Somente o admin comum pode se deletar
curl -v -X DELETE $BASE_URL/administrador/$ID_ADMIN_COMUM \
     -H "Authorization: Bearer $TOKEN_ADMIN_COMUM"


# Comandos de aprovação, feitos pelo admin ou super admin


# ver Empresas Pendentes
curl -v -X GET $BASE_URL/empresa/pendentes \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN"

# aprovar Empresa
curl -v -X PUT $BASE_URL/empresa/aprovar/$ID_EMPRESA \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN"

# ver Secretarias Pendentes
curl -v -X GET $BASE_URL/secretaria/pendentes \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN"

# aprovar Secretaria
curl -v -X PUT $BASE_URL/secretaria/aprovar/$ID_SECRETARIA \
     -H "Authorization: Bearer $TOKEN_SUPER_ADMIN"


# Comandos da empresa

#Obs: nos testes use este site para criar os cnpjs:https://www.4devs.com.br/gerador_de_cnpj


# Envia a solicitação de cadastro de uma empresa
curl -v -X POST $BASE_URL/empresa \
     -H "Content-Type: application/json" \
     -d '{
           "nome": "empresa do gera",
           "email": "empresa@gera.com",
           "senha": "senha123",
           "telefone": "123456789",
           "endereco": "Rua de cima",
           "cnpj": "92.167.491/0001-88",
           "biografia": "uma empresa cheirosa",
           "links": "http://teste.com"
         }'

# atualizar dados de cadastro, precisa ser validada
curl -v -X PUT $BASE_URL/empresa/$ID_EMPRESA \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN_EMPRESA" \
     -d '{
           "nome": "empresa de geras",
           "email": "empresa@gera.com",
           "senha": "novaSenha123",
           "telefone": "99999999",
           "endereco": "Rua de baixo",
           "cnpj": "92.167.491/0001-88",
           "biografia": "Uma empresa muito cheirosa",
           "links": "http://novolink.com"
         }'

# deletar a si mesma, precisa ser validada
curl -v -X DELETE $BASE_URL/empresa/$ID_EMPRESA \
     -H "Authorization: Bearer $TOKEN_EMPRESA"

# comandos da Secretaria

# envia a solicitação de cadastro de uma secretaria
curl -v -X POST $BASE_URL/secretaria \
     -H "Content-Type: application/json" \
     -d '{
           "nome": "secretaria daqui",
           "email": "secretaria@daqui.com",
           "senha": "senha123",
           "telefone": "987654321",
           "endereco": "rua daqui",
           "municipio": "união"
         }'

# atualizar dados de cadastro, precisa ser validada
curl -v -X PUT $BASE_URL/secretaria/$ID_SECRETARIA \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN_SECRETARIA" \
     -d '{
           "nome": "secretaria dali",
           "email": "secretaria@dali.com",
           "senha": "novaSenha123",
           "telefone": "11112222",
           "endereco": "rua nova dali",
           "municipio": "dois irmãos"
         }'

# deletar a si mesma, precisa ser validada
curl -v -X DELETE $BASE_URL/secretaria/$ID_SECRETARIA \
     -H "Authorization: Bearer $TOKEN_SECRETARIA"


# Comandos do User

