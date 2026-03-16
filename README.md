# Sistema de Registro de Atividades de Voluntariado

## 📌 Sobre o projeto

Este projeto é um sistema simples para registro de atividades realizadas por voluntários.
O objetivo é permitir que voluntários registrem as horas trabalhadas e descrevam as atividades realizadas, possibilitando o acompanhamento das horas de voluntariado pela organização.

O sistema também permitirá a geração de relatórios para controle de horas e emissão de certificados.

---

## ⚙️ Tecnologias utilizadas

* Java 21
* Spring Boot 3.5.7
* Spring Data JPA
* Spring Validation
* Flyway (versionamento de banco)
* PostgreSQL
* Maven
* Swagger/OpenAPI (documentação da API)

---

## 📊 Funcionalidades implementadas

* Cadastro de setores da organização
* Cadastro de voluntários
* Registro de atividades realizadas
* Controle de tempo de atividades
* Listagem de registros de atividades
* Sistema de aprovação de atividades (Pendente, Aprovada, Rejeitada)
* Documentação interativa da API com Swagger

---

## 🗂 Estrutura básica do sistema

Principais entidades:

**Voluntário**

* Nome
* Email
* Setor
* Tipo de usuário (Voluntário ou Administrador)

**Setor**

* Nome do setor

**Atividade**

* Data
* Tempo de atividade (em minutos)
* Descrição
* Voluntário responsável
* Status (Pendente, Aprovada, Rejeitada)

---

## ⏱ Controle de horas

O tempo de atividade é registrado em minutos, permitindo intervalos como:

* 30 minutos
* 1 hora (60 minutos)
* 1h30 (90 minutos)
* 2 horas (120 minutos)
* 2h30 (150 minutos)
* 3 horas (180 minutos)
* 3h30 (210 minutos)
* 4 horas (240 minutos)
* 5 horas (300 minutos)

Esses registros permitem acompanhar ciclos de:

* **20 horas de voluntariado**
* **45 dias de participação**

---

## 🚀 Como executar o projeto

### Pré-requisitos

* Java 21 ou superior
* PostgreSQL instalado e rodando
* Maven 3.6+

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE sistema_atividades;
```

2. Configure as credenciais no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_atividades
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

### Executando a aplicação

```bash
# Clone o repositório
git clone <https://github.com/Matheusjsg/controle-atividades-voluntariado.git>

# Entre no diretório
cd sistema-atividades

# Execute com Maven
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📡 Documentação da API

Após iniciar a aplicação, acesse a documentação interativa Swagger:

**Swagger UI:** `http://localhost:8080/swagger-ui.html`

### Endpoints disponíveis:

**Setores**
* `POST /departments/create` - Cadastrar novo setor
* `GET /departments/list` - Listar todos os setores

**Voluntários**
* `POST /volunteer/create` - Cadastrar novo voluntário
* `GET /volunteer/list` - Listar todos os voluntários

**Atividades**
* `POST /activity/create` - Registrar nova atividade
* `GET /activity/list` - Listar todas as atividades

---

## 🗄️ Banco de Dados

O projeto utiliza Flyway para versionamento do banco de dados.

**Migrations disponíveis:**
* V1 - Criação da tabela de setores
* V2 - Criação da tabela de voluntários
* V3 - Criação da tabela de atividades
* V4 - Inserção de dados iniciais de setores
* V5 - Inserção de dados iniciais de voluntários

As migrations são executadas automaticamente ao iniciar a aplicação.

---

## 🚧 Status do projeto

Projeto em desenvolvimento.

**Funcionalidades implementadas:**
* ✅ CRUD de setores
* ✅ CRUD de voluntários
* ✅ Registro de atividades
* ✅ Sistema de status de atividades
* ✅ Documentação Swagger

**Próximas funcionalidades:**
* 🔲 Autenticação e autorização
* 🔲 Relatórios de horas por voluntário
* 🔲 Geração de certificados
* 🔲 Interface web para voluntários
* 🔲 Dashboard administrativo

---

## 👨💻 Autor

Desenvolvido por **Matheus Jesus**

Projeto desenvolvido como iniciativa de apoio ao registro e acompanhamento de atividades de voluntariado.
