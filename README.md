# Spring Boot + Maven + RabbitMQ Project Template

Este é um projeto **Spring Boot 3.5.6** configurado com **Java 21**, **Maven 21** e integração com **RabbitMQ**, ideal como base para desenvolvimento de microsserviços e aplicações backend modernas.

---

## Índice

1. [Stack Tecnológica](#stack-tecnológica)  
2. [Começando](#começando)  
3. [Configuração do RabbitMQ](#configuração-do-rabbitmq)  
4. [Padrões de Código](#padrões-de-código-e-diretrizes)  
5. [Convenções de Nomenclatura](#convenções-de-nomenclatura)  

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
bash \
Copiar código \
mvn clean install \ 
mvn spring-boot:run \
A aplicação iniciará em http://localhost:8080.

## 3.Configuração do RabbitMQ

1. Usando Docker
Se desejar rodar o RabbitMQ via Docker, execute:


docker run -d \

  --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:3-management \
  
Acesse o painel do RabbitMQ em:

http://localhost:15672 \
Usuário padrão: guest \
Senha padrão: guest 

2. Configuração no application.yml
   
yaml
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
APIs e Endpoints
Exemplo básico de API que envia e consome mensagens do RabbitMQ:

Enviar mensagem
POST /api/messages/send

json
Copiar código
{
  "content": "Mensagem de teste"
}

Receber mensagens
As mensagens são consumidas automaticamente pelo listener configurado, mas você pode visualizar logs no console.

## 4.Padrões de Código e Diretrizes

Utilize Lombok para reduzir boilerplate (@Getter, @Setter, @Builder, etc.)

Todas as classes devem seguir Clean Code e arquitetura em camadas:

controller: recebe as requisições HTTP

service: contém a lógica de negócio

config: contém as configurações do Spring

producer / consumer: responsáveis por enviar e receber mensagens RabbitMQ

dto / model: classes de dados

## 5.Convenções de Nomenclatura
| Tipo         | Convenção                 | Exemplo                              |
|---------------|---------------------------|---------------------------------------|
| **Pacotes**   | minúsculo, separado por ponto | `com.seuusuario.rabbitmqtemplate`     |
| **Classes**   | PascalCase                | `MessageProducer`, `MessageConsumer`  |
| **Métodos**   | camelCase                 | `sendMessage()`, `consumeMessage()`   |
| **Constantes**| MAIÚSCULO_COM_UNDERSCORE  | `QUEUE_NAME`                          |
| **Variáveis** | camelCase                 | `messageContent`                      |
