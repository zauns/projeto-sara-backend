#!/bin/bash
BASE_URL="http://localhost:8080"
EMAIL=$1
SENHA=$2

# login
TOKEN=$(curl -s -X POST "$BASE_URL/token" \
  -H "Content-Type: application/json" \
  -d "{\"username\": \"$EMAIL\", \"password\": \"$SENHA\"}")

# abstrai isso é a decodificação do jwt pra pegar o id do usuário e depois imprimir como json
USER_ID=$(echo "$TOKEN" | awk -F. '{print $2}' | base64 -d | jq -r .userId)

echo "# dados do usuário logado"
curl -s -X GET "$BASE_URL/api/user/$USER_ID" \
  -H "Authorization: Bearer $TOKEN" | jq .

echo "# dados completos do usuário logado"
curl -s -X GET "$BASE_URL/api/user/dados/$USER_ID" \
  -H "Authorization: Bearer $TOKEN" | jq .

echo -e "# todos os users "
curl -s -X GET "$BASE_URL/api/user" \
  -H "Authorization: Bearer $TOKEN" | jq .
