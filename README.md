# Spring Boot 

Este é um projeto **Spring Boot 3.5.6** configurado com **Java 21**, **Maven 21** e integração com **RabbitMQ**.

---

## Índice

1. [Stack Tecnológica](#Stack-Tecnológica)  
2. [Começando](#começando)  
3. [Configuração do RabbitMQ](#configuração-do-rabbitmq)  
4. [Configuração do Docker](#configuração-do-docker)
5. [Padrões de Código](#padrões-de-código-e-diretrizes)  
6. [Convenções de Nomenclatura](#convenções-de-nomenclatura)  

---

## Stack Tecnológica

| Tecnologia | Versão | Descrição |
|-------------|---------|-----------|
| **Spring Boot** | 3.5.6 | Framework principal da aplicação |
| **Java** | 21 | Linguagem base |
| **Maven** | 21 | Gerenciador de dependências e build |
| **Spring AMQP / RabbitMQ** | ^3.x | Integração para mensageria assíncrona |
| **Spring Web** | ^3.x | Criação de APIs REST |
| **Spring Validation** | ^3.x | Validação de dados |
| **Spring Boot Test / JUnit 5** | ^3.x | Testes unitários e de integração |

---

## Começando

### 1. Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:
- **Java 21+**
- **Maven 21+**
- **RabbitMQ** (localmente ou via Docker)

### 2. Clonar o repositório

git clone https://github.com/seu-usuario/seu-projeto.git
cd seu-projeto

## 3. Compilar e executar a aplicação
```bash 
mvn clean install 
mvn spring-boot:run
# A aplicação iniciará em http://localhost:8080.
```

## 3.Configuração do RabbitMQ

1. Usando Docker
Se desejar rodar o RabbitMQ via Docker, execute:

```bash
docker run -d
  --name rabbitmq 
  -p 5672:5672
  -p 15672:15672
  rabbitmq:3-management
```
  
Acesse o painel do RabbitMQ em:

http://localhost:15672 \
Usuário padrão: guest \
Senha padrão: guest 

2. Configuração no application.yml
   
```yaml
Copiar código
spring:
  application:
    name: rabbitmq-template
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
  main:
    allow-bean-definition-overriding: true

server:
  port: 8080
```
APIs e Endpoints
Exemplo básico de API que envia e consome mensagens do RabbitMQ:

Enviar mensagem
```bash
POST /api/messages/send
{
  "content": "Mensagem de teste"
}
```

Receber mensagens
As mensagens são consumidas automaticamente pelo listener configurado, mas você pode visualizar logs no console.

## 4. Configuração do Docker

### 1. Primeiramente puxe o repositório do front-end para a raiz do projeto:
```bash
cd projeto-sara-backend #ir a pasta raiz
git clone https://github.com/zauns/projeto-sara-frontend
```

### 2. Adicione as chaves jwt

vá até a pasta `saraEmprega` e crie a pasta jwtkeys, dentro dela adicione uma chave pública e uma privada, respectivamente com os nomes `public_key.pem` e `private_key.pem`.

```bash
saraEmprega/jwtkeys/
├── private_key.pem
└── public_key.pem
```

após isso crie ou copie um arquivo `.env` para a raiz do projeto, ele irá servir para adicionar variáveis sensíveis para cadastro do banco de dados:

```bash
JWT_PUBLIC_KEY_PATH=file:./jwtkeys/public_key.pem
JWT_PRIVATE_KEY_PATH=file:./jwtkeys/private_key.pem
DB_HOST=localhost
DB_PORT=#Porta do banco de dados
DB_NAME=#Nome do banco de dados
DB_USER=#Nome do primeiro usuário administrativo
DB_PASSWORD=#Senha do usuário administrativo
DB_ROOT_PASSWORD=#senha do root
SERVER_PORT=8080
```

### 3. Comandos do docker
Após todas as configurações serem feitas, basta ir no diretório raiz do projeto e iniciar o docker-compose:

```bash
docker-compose up --build -d
```
O docker compose irá iniciar os containers do backend, frontend e banco de dados juntos.

para verificar o status dos containers:
```bash
docker ps
```
E para parar a execução dos containers:
```bash
docker-compose down #precisa ser feito na raiz do projeto
```
Caso você veja dentro do `docker ps` algum container reiniciando frequentemente, é sinal de que esta ocorrendo algum erro na incialização, para verificar o log do container use:
```bash
docker-compose logs -f backend # exemplo do back end
# nomes dos containers ~~v
# - sara-emprega-db
# - sara-emprega-backend
# - sara-emprega-frontend
```

Para acessar o banco de dados via CLI, utilize o comando:
```bash
docker exec -it sara-emprega-db mysql -u $NOME_DO_USUARIO_DO_BANCO -p 
```


>Obs: caso você não esteja conseguindo usar o comando docker, ou ele exige um usuário `sudo`, execute este código no terminal ou wsl:
>```bash
>sudo usermod -aG docker $USER
>sudo groupadd docker # após isso reinicie a sessão ou o computador
>```

## 5.Padrões de Código e Diretrizes

Utilize Lombok para reduzir boilerplate (`@Getter`, `@Setter`, `@Builder`, etc.)

Todas as classes devem seguir Clean Code e arquitetura em camadas:

controller: recebe as requisições HTTP

service: contém a lógica de negócio

config: contém as configurações do Spring

producer / consumer: responsáveis por enviar e receber mensagens RabbitMQ

dto / model: classes de dados

## 6.Convenções de Nomenclatura
| Tipo         | Convenção                 | Exemplo                              |
|---------------|---------------------------|---------------------------------------|
| **Pacotes**   | minúsculo, separado por ponto | `com.seuusuario.rabbitmqtemplate`     |
| **Classes**   | PascalCase                | `MessageProducer`, `MessageConsumer`  |
| **Métodos**   | camelCase                 | `sendMessage()`, `consumeMessage()`   |
| **Constantes**| MAIÚSCULO_COM_UNDERSCORE  | `QUEUE_NAME`                          |
| **Variáveis** | camelCase                 | `messageContent`                      |
