# API REST para Sistema de Apoio a Testes Exploratórios

Projeto desenvolvido para a disciplina de Desenvolvimento de Software para a Web 1 (DSW) da Universidade Federal de São Carlos (UFSCar).

---

## 📝 Descrição

Esta é uma REST API desenvolvida com Spring Boot que serve como backend para um sistema de apoio a testes exploratórios em video games. A API permite o gerenciamento de usuários, projetos, estratégias de teste e o ciclo de vida completo de uma sessão de teste, desde sua criação até o registro de bugs.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.x**
- Spring Web
- Spring Data JPA
- Spring Security
- **Maven**
- **H2 Database** (Banco de Dados em Memória)
- Lombok

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar a aplicação localmente.

### Pré-requisitos

- [JDK 21](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/gus-kim/game-testing-api](https://github.com/gus-kim/game-testing-api)
    ```

2.  **Acesse a pasta do projeto:**
    ```bash
    cd game-testing-api
    ```

3.  **Execute a aplicação com o Maven:**
    * No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    * No Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```

4.  A API estará disponível em `http://localhost:8080`.

---

## ⚙️ Configuração do Ambiente de Desenvolvimento

O projeto utiliza um banco de dados em memória (H2) por padrão, portanto **nenhuma configuração adicional é necessária**.

Para visualizar o banco de dados pelo navegador, siga os passos:

1.  Garanta que as seguintes linhas estão no seu arquivo `src/main/resources/application.properties`:
    ```properties
    # H2 Console Configuration
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    ```
2.  Após iniciar a aplicação, acesse: `http://localhost:8080/h2-console`
3.  Use as seguintes credenciais para login:
    - **JDBC URL:** `jdbc:h2:mem:testdb`
    - **User Name:** `sa`
    - **Password:** (deixe em branco)

---

## 📖 Endpoints da API

A documentação completa dos endpoints será mantida em uma Collection do Postman. O link será adicionado aqui futuramente.

---

## 👨‍💻 Equipe

- **Gustavo Kim Alcantara** - Pacote 1: Gestão de Usuários e Segurança
- **Nome Integrante B** - Pacote 2: Gestão de Estratégias
- **Nome Integrante C** - Pacote 3: Gestão de Projetos e Membros
- **Nome Integrante D** - Pacote 4: Criação e Consulta de Sessões de Teste
- **Nome Integrante E** - Pacote 5: Ciclo de Vida da Sessão e Registro de Bugs