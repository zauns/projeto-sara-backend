# SARA Emprega - Spring Project

Este é um projeto **Spring Boot 3.5.6** configurado com **Java 21**, **Maven 21**.

---

## Índice

1. **Stack Tecnológica**
2. **Começando**
3. **Configuração do Docker**]
4. **Padrões de Código**
5. **Convenções de Nomenclatura**

---

## Stack Tecnológica

| Tecnologia                     | Versão | Descrição                           |
| ------------------------------ | ------ | ----------------------------------- |
| **Spring Boot**                | 3.5.6  | Framework principal da aplicação    |
| **Java**                       | 21     | Linguagem base                      |
| **Maven**                      | 21     | Gerenciador de dependências e build |
| **Spring Web**                 | ^3.x   | Criação de APIs REST                |
| **Spring Validation**          | ^3.x   | Validação de dados                  |
| **Spring Boot Test / JUnit 5** | ^3.x   | Testes unitários e de integração    |
| **Docker / Docker Compose**    | ^28.x  | Containerização do projeto          |

---

## Começando

### 1. Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- **Java JDK 21+**
- **Maven 21+**
- **Docker**
- **Git**

### 2. Clonar o repositório

Você pode baixar o zip deste repositório, ou então clonar via linha de comando:

```bash
git clone <https://github.com/seu-usuario/seu-projeto.git>
```

## 3. Compilar e executar a aplicação

```bash
mvn clean install 
mvn spring-boot:run
```

## 4. Configuração do Docker

### 🪟 Windows 11/10

<details> <summary>Clique aqui</summary>

## Windows 11/10

### 1. Instalação do Wsl2

> Para detalhamentos precisos, siga a [documentação oficial](https://learn.microsoft.com/pt-br/windows/wsl/install).

Para gerenciar os containers docker no windows, é necessário possuir o wsl para gerenciar os comandos. Para instalar o wsl no windows 11 primeiramente abra o powershell como administrador e rode o seguinte comando:

```powershell
wsl --install
```

Por padrão será instalado o ubuntu no seu wsl, mas você também pode instalar uma distribuição do wsl na [microsoft store](https://apps.microsoft.com/detail/9pdxgncfsczv?hl=pt-BR&gl=BR).

> Após a instalação do wsl, siga para as instruções do linux, e faça os procedimentos de acordo com a sua distribuição instalada.

## Problemas no windows 10

A configuração no windows 10 pode se tornar extensa a depender da versão da build do sistema operacional, caso os procedimentos aqui não surtam efeito, reforçamos que confira a [documentação oficial](https://learn.microsoft.com/pt-br/windows/wsl/install).

> Antes de iniciar confira se a virtualização esta ativada na sua BIOS.

```powershell
wsl --install
```

Caso tenha retornado um erro como `0x800f0805` é sinal de que os componentes importantes para a instalação do wsl estão desconfigurados, desligados ou até mesmo quebrados. Posteriormente serão dados mais informações para tratar destes casos.

</details>

### 🐧 Linux

<details> <summary>Clique aqui</summary>

## 1. Instalação

A configuração do **docker** nas distrubuições linux, é consideravelmente mais simples. Para isto, primeiro é necessário instalar o docker para a sua distribuição, aqui está o exemplo de 3 bases populares, porém você pode verificar a sua em específico na documentação oficial [link aqui](https://docs.docker.com/engine/install/).

> Recomendamos o uso do ubuntu pela documentação já existente

### Distribuições baseadas no **Debian** (Ubuntu, Linux Mint)

```bash
sudo apt install docker docker-compose
```

### Distribuições baseadas no **Fedora** (CentOs, RHEL)

```bash
sudo dnf install docker docker-compose
```

### Distribuições baseadas no **Arch** (EndeavourOs, Manjaro)

```bash
sudo pacman -S docker docker-compose
```

## 2. Configurações pós instalação

O docker vem por padrão com pré requisitos de root para seus comandos, para ambientes de desenvolvimento no entanto é preferível deixar o usuário usar o comando docker sem necessidade de usar o sudo e a senha constantemente. Para isso rode o seguinte comando:

```bash
sudo usermod -aG docker $USER
```

Com isso feito você pode optar por deixar o docker iniciar sempre que você liga o computador, para isso rode o seguinte comando no terminal:

```bash
sudo systemctl enable docker
```

Caso não queira, você pode gerenciar manualmente com:

```bash
sudo systemctl start docker # inicia o docker
sudo systemctl stop docker # para o docker
```

</details>

---

## 3. Build do Projeto via Docker Compose

### 1. Primeiramente puxe o repositório do front-end para a raiz do projeto

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

### 4.1 Comandos do Docker Cli

<details> <summary>Clique aqui</summary>

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
>
>```bash
>sudo usermod -aG docker $USER
>sudo groupadd docker # após isso reinicie a sessão ou o computador
>```

</details>

### 4.2 Comandos do Docker Via UI

<details> <summary>Clique aqui</summary>

## Vscode

No vscode existe uma extensão chamada [**container tools**](https://open-vsx.org/vscode/item?itemName=ms-azuretools.vscode-containers), que facilita bastante o manuseio dos containers dockers, e também do docker compose. Segue o passo a passo:

1. Certifique-se de que o docker esta rodando no sistema.
2. Instale a extensão container tools.
3. Abra o arquivo `docker-compose`.
4. A extensão irá exibir um botão para iniciar cada serviço, 

### [![Exemplo]](demo-vscode.mp4)

> Aviso! ao clicar em `Run All Services` o docker-compose irá iniciar o build completo, no exemplo do vídeo eu cancelei e fiz o `docker up -d` para apenas iniciar, pois o projeto já estava compilado.

## Intellij

O Intellij também existe um plugin para lidar com dockers e docker compose, porém ele automaticamente lhe surgere então fica um processo bem simples.

1. Certifique-se que o docker esteja rodando.
2. Abra o arquivo do docker-compose.
3. Clique na sugestão do intellij de buscar um plugin do docker.
4. Instale o plugin.
5. Clique para rodar o docker compose.

### [![Exemplo]](demo-intellij.mp4)

> Semelhante ao vscode, no vídeo o projeto já estava compilado, na primeira vez vai levar mais tempo por conta de precisar instalar as dependências

</details>

## 5.Padrões de Código e Diretrizes

Utilize Lombok para reduzir boilerplate (`@Getter`, `@Setter`, `@Builder`, etc.)

Todas as classes devem seguir Clean Code e arquitetura em camadas:

controller: recebe as requisições HTTP

service: contém a lógica de negócio

config: contém as configurações do Spring

dto / model: classes de dados

## 6.Convenções de Nomenclatura

| Tipo           | Convenção                     | Exemplo                              |
| -------------- | ----------------------------- | ------------------------------------ |
| **Pacotes**    | minúsculo, separado por ponto | `com.seuusuario.rabbitmqtemplate`    |
| **Classes**    | PascalCase                    | `MessageProducer`, `MessageConsumer` |
| **Métodos**    | camelCase                     | `sendMessage()`, `consumeMessage()`  |
| **Constantes** | MAIÚSCULO_COM_UNDERSCORE      | `QUEUE_NAME`                         |
| **Variáveis**  | camelCase                     | `messageContent`                     |
