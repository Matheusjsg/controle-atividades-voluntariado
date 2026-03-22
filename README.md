# Sistema de Registro de Atividades de Voluntariado

## 📌 Sobre o projeto

Sistema para registro e acompanhamento de atividades realizadas por voluntários da **Associação Beneficiente e Cultural Amor em Ação**.

Permite registro de horas, aprovação de atividades e geração de certificados de voluntariado.

---

## ⚙️ Tecnologias utilizadas

* Java 21
* Spring Boot 3.5.7
* Spring Data JPA
* Spring Security + JWT
* Flyway (versionamento de banco)
* PostgreSQL
* Maven
* iText7 (geração de PDF)
* Swagger/OpenAPI (documentação da API)

---

## 📊 Funcionalidades

* ✅ Autenticação JWT (login e registro)
* ✅ Cadastro de setores e voluntários
* ✅ Perfil de voluntário com dados pessoais
* ✅ Registro de atividades com validações
* ✅ Sistema de aprovação (Pendente, Aprovada, Rejeitada)
* ✅ Relatórios de horas com filtro por período
* ✅ Geração de certificados em PDF (mínimo 20h)
* ✅ Controle de permissões (ADMIN e VOLUNTEER)
* ✅ Documentação Swagger

---

## 🚀 Como executar o projeto

### Pré-requisitos

* Java 21 ou superior
* PostgreSQL 15+
* Maven 3.6+

### 1. Clonar o repositório

```bash
git clone https://github.com/abcaa-ong/volunteer-hours-log.git
cd volunteer-hours-log/Backend
```

### 2. Configurar Banco de Dados

```sql
CREATE DATABASE "ong-abcaa";
```

### 3. Configurar variáveis de ambiente

**Copie o arquivo de exemplo:**
```bash
cp .env.example .env
```

**Edite o arquivo `.env` com suas configurações locais:**
```bash
nano .env
```

**Variáveis obrigatórias:**
```env
# Database
DB_PASSWORD=sua_senha_postgres
DB_URL=jdbc:postgresql://localhost:5432/ong-abcaa
DB_USER=postgres

# JWT
JWT_SECRET=seu-secret-local  # Gerar com: openssl rand -base64 64

# Admin Inicial
ADMIN_NAME=Administrador Local
ADMIN_EMAIL=admin@localhost.dev
ADMIN_PASSWORD=admin123

# Certificado
CERTIFICATE_ORG_NAME=Associação Beneficiente e Cultural Amor em Ação
CERTIFICATE_ORG_CNPJ=54.794.100/0001-66
CERTIFICATE_ORG_CITY=Quitandinha
CERTIFICATE_ORG_STATE=PR
```

### 4. Executar a aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 🔐 Primeiro Acesso

Ao iniciar, o sistema cria automaticamente um usuário admin com as credenciais do `.env`:

```
Email: admin@localhost.dev (ou o configurado em ADMIN_EMAIL)
Senha: admin123 (ou a configurada em ADMIN_PASSWORD)
```

**⚠️ IMPORTANTE:** Altere a senha após o primeiro login!

---

## 📡 Documentação da API

**Swagger UI:** `http://localhost:8080/swagger-ui.html`

### Principais Endpoints:

#### 🔑 Autenticação (`/auth`)
* `POST /auth/login` - Login (retorna token JWT)
* `POST /auth/register` - Registrar novo voluntário

#### 📋 Atividades (`/activity`)
* `POST /activity/create` - Registrar atividade (status PENDING automático)
* `GET /activity/listAll` - Listar todas as atividades
* `GET /activity/list/{id}` - Buscar atividade por ID
* `GET /activity/volunteer/{volunteerId}` - Listar atividades de um voluntário
* `GET /activity/status/{status}` - Listar por status (PENDING, APPROVED, REJECTED)
* `PUT /activity/update/{id}` - Atualizar atividade
* `PATCH /activity/{id}/status` - Aprovar/Rejeitar atividade (Admin)
* `DELETE /activity/delete/{id}` - Excluir atividade

#### 📊 Relatórios (`/activity`)
* `GET /activity/report/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31` - Relatório de horas

#### 📜 Certificados (`/certificate`)
* `GET /certificate/generate/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31` - Gerar certificado PDF

#### 🏢 Setores (`/departments`)
* `POST /departments/create` - Criar setor
* `GET /departments/list` - Listar todos os setores
* `GET /departments/{id}` - Buscar setor por ID
* `PUT /departments/update/{id}` - Atualizar setor
* `DELETE /departments/delete/{id}` - Excluir setor

#### 👥 Voluntários (`/volunteer`)
* `POST /volunteer/create` - Criar voluntário
* `GET /volunteer/list` - Listar todos os voluntários
* `GET /volunteer/{id}` - Buscar voluntário por ID
* `PUT /volunteer/update/{id}` - Atualizar voluntário
* `PATCH /volunteer/{id}/usertype` - Alterar tipo de usuário (Admin)
* `DELETE /volunteer/delete/{id}` - Excluir voluntário

#### 👤 Perfil do Voluntário (`/volunteer/profile`)
* `PUT /volunteer/profile` - Salvar/atualizar próprio perfil
* `GET /volunteer/profile` - Ver próprio perfil
* `GET /volunteer/profile/{volunteerId}` - Ver perfil de outro voluntário (Admin)

---

## 📝 Exemplos de Requisições

### Autenticação

**Registrar:**
```json
POST /auth/register
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123",
  "departmentId": 1
}
```

**Login:**
```json
POST /auth/login
{
  "email": "joao@email.com",
  "password": "senha123"
}

Resposta:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer"
}
```

### Atividade

**Criar (status PENDING automático):**
```json
POST /activity/create
Headers: Authorization: Bearer {token}
{
  "date": "2025-01-15",
  "description": "Aula de reforço escolar",
  "durationMinutes": 120,
  "volunteerId": 1
}
```

**⚠️ Importante:** O campo `activityStatus` NÃO deve ser enviado. O sistema define automaticamente como `PENDING`.

**Aprovar atividade (Admin):**
```http
PATCH /activity/1/status?status=APPROVED
Headers: Authorization: Bearer {token_admin}
```

### Perfil do Voluntário

**Salvar perfil:**
```json
PUT /volunteer/profile
Headers: Authorization: Bearer {token}
{
  "phone": "(41) 99999-9999",
  "address": "Rua Exemplo, 123",
  "city": "Quitandinha",
  "state": "PR",
  "zipCode": "83840-000",
  "birthDate": "1990-05-15",
  "cpf": "123.456.789-00"
}
```

---

## 🗄️ Migrations

O Flyway gerencia automaticamente o banco de dados:

* **V1** - Tabela de departamentos
* **V2** - Tabela de voluntários (com autenticação)
* **V3** - Tabela de atividades (com status de aprovação)
* **V4** - Tabela de perfis de voluntários (dados pessoais)
* **V5** - Dados iniciais (departamentos padrão)

**⚠️ NUNCA edite migrations já executadas!** Crie novas (V6, V7, etc.)

---

## 🔒 Segurança

### Autenticação JWT

Todas as rotas (exceto `/auth/login` e `/auth/register`) exigem autenticação via token JWT.

**Header obrigatório:**
```
Authorization: Bearer {seu_token_jwt}
```

### Permissões

* **VOLUNTEER** - Pode criar e visualizar suas próprias atividades
* **ADMIN** - Pode aprovar/rejeitar atividades e gerenciar todos os recursos

### Arquivos que NÃO devem ser commitados:

* `.env` - Contém credenciais locais
* `.env.production` - Contém credenciais de produção
* `application-local.properties`

### Gerar secrets fortes:

```bash
# JWT Secret (256 bits)
openssl rand -base64 64

# Admin Password
openssl rand -base64 32
```

---

## 🚀 Deploy em Produção

### 1. Configurar variáveis de ambiente no servidor:

```bash
export DB_PASSWORD="senha_forte_producao"
export DB_URL="jdbc:postgresql://host:5432/ong-abcaa"
export DB_USER="postgres"
export JWT_SECRET="secret_gerado_256_bits"
export ADMIN_NAME="Administrador"
export ADMIN_EMAIL="admin@abcaa.org"
export ADMIN_PASSWORD="senha_forte_admin"
export CERTIFICATE_ORG_NAME="Associação Beneficiente e Cultural Amor em Ação"
export CERTIFICATE_ORG_CNPJ="54.794.100/0001-66"
export CERTIFICATE_ORG_CITY="Quitandinha"
export CERTIFICATE_ORG_STATE="PR"
```

### 2. Build e execução:

```bash
mvn clean package -DskipTests
java -jar target/Backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 3. Com Docker:

```bash
docker-compose --env-file .env.production up -d
```

---

## 📝 Estrutura do Projeto

```
Backend/
├── src/main/java/com/abcaa/sistema_atividades/
│   ├── business/
│   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── ActivityDTO
│   │   │   ├── ActivityReportDTO
│   │   │   ├── DepartmentDTO
│   │   │   ├── LoginDTO
│   │   │   ├── RegisterDTO
│   │   │   ├── TokenDTO
│   │   │   ├── VolunteerDTO
│   │   │   └── VolunteerProfileDTO
│   │   ├── entities/         # Entidades JPA
│   │   │   ├── Activity
│   │   │   ├── Department
│   │   │   ├── Volunteer
│   │   │   └── VolunteerProfile
│   │   ├── enums/            # Enumerações
│   │   │   ├── ActivityStatus (PENDING, APPROVED, REJECTED)
│   │   │   └── UserType (VOLUNTEER, ADMIN)
│   │   ├── mapper/           # Conversores DTO <-> Entity
│   │   ├── repositories/     # Repositórios JPA
│   │   ├── service/          # Lógica de negócio
│   │   │   ├── ActivityService
│   │   │   ├── AuthService
│   │   │   ├── CertificateService
│   │   │   ├── DepartmentService
│   │   │   ├── VolunteerService
│   │   │   └── VolunteerProfileService
│   │   └── validation/       # Validações customizadas
│   ├── controller/           # Controllers REST
│   │   ├── ActivityController
│   │   ├── AuthController
│   │   ├── CertificateController
│   │   ├── DepartmentController
│   │   ├── VolunteerController
│   │   └── VolunteerProfileController
│   └── infrastructure/
│       ├── config/           # Configurações
│       ├── docs/             # Swagger
│       ├── exception/        # Tratamento de erros
│       └── security/         # JWT e Security
├── src/main/resources/
│   ├── db/migration/         # Migrations Flyway
│   │   ├── V1__create_table_department.sql
│   │   ├── V2__create_table_volunteer.sql
│   │   ├── V3__create_table_activity.sql
│   │   ├── V4__create_table_volunteer_profile.sql
│   │   └── V5__insert_initial_data.sql
│   └── application.properties
├── .env.example              # Template de variáveis
├── .gitignore
├── pom.xml
├── QUICK_START.md            # Guia rápido
└── README.md                 # Este arquivo
```

---

## 🧪 Testes

```bash
# Executar testes
mvn test

# Executar com cobertura
mvn clean test jacoco:report
```

---

## 🎯 Fluxo de Uso

1. **Registrar/Login** → Obter token JWT
2. **Cadastrar Setores** → Criar departamentos da organização
3. **Cadastrar Voluntários** → Vincular a um setor
4. **Completar Perfil** → Adicionar dados pessoais (CPF, telefone, etc.)
5. **Registrar Atividades** → Criar atividades (status PENDING automático)
6. **Aprovar Atividades** → Admin aprova/rejeita atividades
7. **Gerar Relatórios** → Consultar horas aprovadas por período
8. **Emitir Certificados** → Gerar PDF para voluntários com 20h+ aprovadas

---

## ⚠️ Regras de Negócio

### Atividades
* Status inicial sempre é `PENDING`
* Apenas ADMIN pode aprovar/rejeitar
* Duração mínima: 15 minutos
* Duração máxima: 12 horas (720 minutos)

### Certificados
* Requer no mínimo 20 horas aprovadas
* Pode filtrar por período (startDate/endDate)
* Gerado em PDF com dados da organização

### Permissões
* VOLUNTEER: CRUD próprias atividades, visualizar próprio perfil
* ADMIN: Todas as operações, incluindo aprovação e gerenciamento de usuários

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch: `git checkout -b feature/nova-funcionalidade`
3. Commit suas mudanças: `git commit -m 'Adiciona nova funcionalidade'`
4. Push para a branch: `git push origin feature/nova-funcionalidade`
5. Abra um Pull Request

---

## 📄 Licença

Este projeto é de uso interno da **Associação Beneficiente e Cultural Amor em Ação**.

---

## 👥 Equipe

Desenvolvido pela equipe de Tecnologia da ABCAA.

**Contato:** admin@abcaa.org

---

## 📚 Documentação Adicional

* [QUICK_START.md](QUICK_START.md) - Guia rápido de início
* [CONTRIBUTING.md](CONTRIBUTING.md) - Guia de contribuição
* Swagger UI: `http://localhost:8080/swagger-ui.html`
