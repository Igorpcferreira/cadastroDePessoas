# Guia de Deploy para Projeto Angular e Spring Boot

Este guia descreve os passos necessários para realizar o deploy de um projeto que utiliza Angular no front-end e Spring
Boot com Java 17 no back-end.

## Pré-requisitos

Antes de começar, certifique-se de ter o seguinte instalado em sua máquina de desenvolvimento:

- Node.js (https://nodejs.org/)
- Angular CLI (https://angular.io/cli)
- Java 17 JDK (https://adoptopenjdk.net/)
- Apache Maven (https://maven.apache.org/)
- PostgreSQL 15 ou superior

## Configuração do Front-end (Angular)

1. **Instalar as dependências do projeto Angular:**
   ```bash
   cd ./aplicacao/frontend/app
   npm install
   ```

2. **Compilar o projeto Angular:**
   ```bash
   cd ./aplicacao/frontend/app/src
   ng build --prod
   ```
   Isso irá compilar o projeto Angular e gerar os arquivos estáticos dentro do diretório `aplicacao/frontend/app/dist/`.

## Configuração do Back-end (Spring Boot)

1. **Compilar e empacotar o projeto Spring Boot:**
   ```bash
   cd ./aplicacao/aplicacao
   mvn clean package
   ```
   Este comando irá compilar o código Java, executar os testes e empacotar o aplicativo em um arquivo JAR executável. O
   arquivo JAR será gerado no diretório `aplicacao/aplicacao/target/`.

## Configuração do Banco de Dados

1. **Banco de Dados PostgreSQL:**
   Certifique-se de ter o PostgreSQL 15 ou superior instalado. Crie um banco de dados chamado `cadastro` com o usuário e
   senha `postgres`.

   ```sql
   CREATE DATABASE cadastro;
   CREATE USER postgres WITH ENCRYPTED PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE cadastro TO postgres;
   ```

## Configuração do Servidor de Aplicação

Para realizar o deploy, você precisará de um servidor onde possa executar o back-end Spring Boot e servir os arquivos
estáticos do front-end Angular.

1. **Configuração do banco de dados:**
   Certifique-se de configurar corretamente o banco de dados conforme as especificações do projeto. Isso pode incluir a
   configuração de um banco de dados local ou a conexão com um banco de dados remoto.

2. **Configuração do ambiente de produção:**
   Configure as variáveis de ambiente necessárias para a execução do aplicativo. Isso pode incluir URLs de banco de
   dados, chaves de API ou qualquer outra configuração específica do ambiente de produção.

3. **Executando o aplicativo:**
   Para iniciar o aplicativo Spring Boot, você pode usar o seguinte comando:
   ```bash
   cd ./aplicacao/aplicacao/target
   java -jar aplicacao-0.0.1-SNAPSHOT.jar
   ```

4. **Configuração do servidor web para Angular:**
   Você pode configurar um servidor web (como Nginx ou Apache HTTP Server) para servir os arquivos estáticos gerados
   pelo Angular a partir do diretório `aplicacao/frontend/app/dist/`.

## Acessando o Aplicativo

Após seguir os passos acima, seu aplicativo estará disponível na URL configurada para o servidor de aplicação. Acesse
essa URL em um navegador para verificar se o deploy foi realizado com sucesso.

---

Este README fornece uma visão geral dos passos necessários para realizar o deploy de um projeto Angular e Spring Boot.
Certifique-se de ajustar os detalhes específicos do seu projeto, como configurações de banco de dados, variáveis de
ambiente e servidores de aplicação, de acordo com suas necessidades.
