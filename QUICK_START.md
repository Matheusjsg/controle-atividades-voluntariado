# 🚀 Guia Rápido - Sistema de Atividades de Voluntariado

## ⚡ Início Rápido

### 1️⃣ Configurar Banco de Dados

```sql
-- Criar banco de dados
CREATE DATABASE "ong-abcaa";
```

### 2️⃣ Configurar Variáveis de Ambiente

```bash
# Copiar arquivo de exemplo
cp .env.example .env

# Editar com suas configurações
nano .env
```

**Variáveis obrigatórias no `.env`:**
```env
DB_PASSWORD=sua_senha_postgres
DB_URL=jdbc:postgresql://localhost:5432/ong-abcaa
DB_USER=postgres
JWT_SECRET=seu-secret-local  # Gerar com: openssl rand -base64 64
ADMIN_NAME=Administrador Local
ADMIN_EMAIL=admin@localhost.dev
ADMIN_PASSWORD=admin123
CERTIFICATE_ORG_NAME=Associação Beneficiente e Cultural Amor em Ação
CERTIFICATE_ORG_CNPJ=54.794.100/0001-66
CERTIFICATE_ORG_CITY=Quitandinha
CERTIFICATE_ORG_STATE=PR
```

### 3️⃣ Executar o Backend

```bash
# Instalar dependências e executar
mvn clean install
mvn spring-boot:run
```

✅ Backend rodando em: `http://localhost:8080`  
📚 Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🐳 Executar com Docker (Alternativa)

### Opção 1: Docker Compose (Recomendado)

Executa a aplicação + PostgreSQL em containers:

```bash
# Iniciar todos os serviços
docker-compose up -d

# Ver logs
docker-compose logs -f backend

# Parar serviços
docker-compose down
```

✅ Backend: `http://localhost:8080`  
✅ PostgreSQL: `localhost:5432`

### Opção 2: Apenas Docker (banco externo)

```bash
# Build da imagem
docker build -t abcaa-backend .

# Executar container
docker run -d \
  --name abcaa-backend \
  -p 8080:8080 \
  --env-file .env \
  abcaa-backend
```

**Nota:** Certifique-se de que o PostgreSQL está rodando e acessível.

---

## 🔐 Primeiro Acesso

Ao iniciar, o sistema cria automaticamente um usuário admin:

```
Email: admin@localhost.dev (configurado em ADMIN_EMAIL)
Senha: admin123 (configurado em ADMIN_PASSWORD)
```

**⚠️ IMPORTANTE:** Altere a senha após o primeiro login!

---

## 🔌 Endpoints da API

### 🔑 Autenticação (`/auth`)
- `POST /auth/register` - Cadastrar voluntário
- `POST /auth/login` - Login (retorna token JWT)

### 📋 Atividades (`/activity`)
- `POST /activity/create` - Registrar atividade (status PENDING automático)
- `GET /activity/listAll` - Listar todas
- `GET /activity/list/{id}` - Buscar por ID
- `GET /activity/volunteer/{volunteerId}` - Listar por voluntário
- `GET /activity/status/{status}` - Listar por status (PENDING, APPROVED, REJECTED)
- `PUT /activity/update/{id}` - Atualizar atividade
- `PATCH /activity/{id}/status?status=APPROVED` - Aprovar/Rejeitar (Admin)
- `DELETE /activity/delete/{id}` - Excluir
- `GET /activity/report/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31` - Relatório de horas

### 🏢 Setores (`/departments`)
- `POST /departments/create` - Criar setor
- `GET /departments/list` - Listar todos
- `GET /departments/{id}` - Buscar por ID
- `PUT /departments/update/{id}` - Atualizar
- `DELETE /departments/delete/{id}` - Excluir

### 👥 Voluntários (`/volunteer`)
- `POST /volunteer/create` - Criar voluntário
- `GET /volunteer/list` - Listar todos
- `GET /volunteer/{id}` - Buscar por ID
- `PUT /volunteer/update/{id}` - Atualizar
- `PATCH /volunteer/{id}/usertype?userType=ADMIN` - Alterar tipo (Admin only)
- `DELETE /volunteer/delete/{id}` - Excluir

### 📜 Certificados (`/certificate`)
- `GET /certificate/generate/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31` - Gerar PDF (mínimo 20h)

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
```

### Setor

```json
POST /departments/create
{
  "name": "Educação"
}
```

### Voluntário

```json
POST /volunteer/create
{
  "name": "Maria Santos",
  "email": "maria@email.com",
  "password": "senha123",
  "departmentId": 1,
  "userType": "VOLUNTEER"
}
```

### Atividade

```json
POST /activity/create
{
  "date": "2025-01-15",
  "description": "Aula de reforço escolar",
  "durationMinutes": 120,
  "volunteerId": 1
}
```

**⚠️ Nota:** O campo `activityStatus` NÃO deve ser enviado na criação. O sistema define automaticamente como `PENDING`.

---

## 🎯 Fluxo de Uso

1. **Login/Registro** → Obter token JWT
2. **Cadastrar Setores** → Criar departamentos
3. **Cadastrar Voluntários** → Vincular a um setor
4. **Registrar Atividades** → Vincular a um voluntário (status PENDING)
5. **Aprovar Atividades** → Admin altera status para APPROVED/REJECTED
6. **Gerar Relatórios** → Consultar horas aprovadas
7. **Emitir Certificados** → Gerar PDF (mínimo 20h aprovadas)

---

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security + JWT** (autenticação)
- **Spring Data JPA** (persistência)
- **PostgreSQL** (banco de dados)
- **Flyway** (migrations)
- **iText7** (geração de PDF)
- **Swagger/OpenAPI** (documentação)
- **Maven** (build)

---

## 📁 Estrutura do Projeto

```
Backend/
├── src/main/java/com/abcaa/sistema_atividades/
│   ├── controller/           # Controllers REST
│   ├── service/              # Lógica de negócio
│   ├── repository/           # Repositórios JPA
│   ├── domain/
│   │   ├── entity/           # Entidades JPA
│   │   └── enums/            # ActivityStatus, UserType
│   ├── dto/                  # Data Transfer Objects
│   ├── mapper/               # Conversores DTO <-> Entity
│   ├── validation/           # Validações customizadas
│   └── infrastructure/
│       ├── config/           # Configurações
│       ├── docs/             # Swagger
│       ├── exception/        # Tratamento de erros
│       └── security/         # JWT e Security
├── src/main/resources/
│   ├── db/migration/         # Migrations Flyway
│   └── application.properties
├── .env.example              # Template de variáveis
├── pom.xml
└── README.md
```

---

## 🗄️ Migrations Flyway

O Flyway gerencia automaticamente o banco:

- **V1** - Tabela de departamentos
- **V2** - Tabela de voluntários
- **V3** - Tabela de atividades
- **V4** - Tabela de perfis de voluntários
- **V5** - Dados iniciais (departamentos padrão)
- **V6** - Tabela de tokens para reset de senha


**⚠️ NUNCA edite migrations já executadas!** Crie novas versões (V6, V7, etc.)

---

## ⚠️ Troubleshooting

### Backend não inicia
- ✅ Verificar se PostgreSQL está rodando
- ✅ Conferir credenciais no `.env`
- ✅ Verificar se porta 8080 está livre
- ✅ Validar se o banco `ong-abcaa` foi criado

### Erro de autenticação
- ✅ Verificar se JWT_SECRET está configurado
- ✅ Confirmar que o token está sendo enviado no header: `Authorization: Bearer {token}`

### Erro ao criar atividade
- ✅ Certifique-se de que o voluntário existe
- ✅ NÃO envie o campo `activityStatus` (é automático)
- ✅ Valide o formato da data: `YYYY-MM-DD`

### Certificado não gera
- ✅ Voluntário precisa ter no mínimo 20 horas APROVADAS
- ✅ Verificar se as variáveis CERTIFICATE_* estão no `.env`

### Docker não inicia
- ✅ Verificar se Docker e Docker Compose estão instalados
- ✅ Verificar se as portas 5432 e 8080 estão livres
- ✅ Conferir se o arquivo `.env` existe e está preenchido
- ✅ Ver logs: `docker-compose logs -f`

---

## 🔒 Segurança

### Arquivos que NÃO devem ser commitados:
- `.env` - Credenciais locais
- `.env.production` - Credenciais de produção
- `application-local.properties`

### Gerar secrets fortes:
```bash
# JWT Secret (256 bits)
openssl rand -base64 64

# Admin Password
openssl rand -base64 32
```

---

## 🚀 Deploy em Produção

### 1. Configurar variáveis no servidor:
```bash
export DB_PASSWORD="senha_forte_producao"
export JWT_SECRET="secret_gerado_256_bits"
export ADMIN_EMAIL="admin@abcaa.org"
export ADMIN_PASSWORD="senha_forte_admin"
```

### 2. Build e execução:
```bash
mvn clean package -DskipTests
java -jar target/Backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 3. Com Docker:
```bash
# Desenvolvimento (usa .env)
docker-compose up -d

# Produção (usa .env.production)
docker-compose --env-file .env.production up -d

# Ver logs
docker-compose logs -f backend
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

## 📞 Suporte

Desenvolvido pela equipe de Tecnologia da **Associação Beneficiente e Cultural Amor em Ação**.

**Contato:** admin@abcaa.org

Para documentação completa, consulte o [README.md](README.md)
