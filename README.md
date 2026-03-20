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

* ✅ Autenticação JWT
* ✅ Cadastro de setores e voluntários
* ✅ Registro de atividades com validações
* ✅ Sistema de aprovação (Pendente, Aprovada, Rejeitada)
* ✅ Relatórios de horas com filtro por período
* ✅ Geração de certificados em PDF (mínimo 20h)
* ✅ Documentação Swagger

---

## 🚀 Como executar o projeto

### Pré-requisitos

* Java 21 ou superior
* PostgreSQL 15+
* Maven 3.6+

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/sistema-voluntariado-abcaa.git
cd sistema-voluntariado-abcaa
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
DB_PASSWORD=sua_senha_postgres
DB_URL=jdbc:postgresql://localhost:5432/ong-abcaa
DB_USER=postgres
JWT_SECRET=seu-secret-local  # Gerar com: openssl rand -base64 64
ADMIN_EMAIL=admin@localhost.dev
ADMIN_PASSWORD=admin123
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

**Autenticação:**
* `POST /auth/login` - Login
* `POST /auth/register` - Registrar voluntário

**Atividades:**
* `POST /activity/create` - Registrar atividade
* `GET /activity/listAll` - Listar todas
* `PATCH /activity/{id}/status` - Aprovar/Rejeitar (Admin)

**Relatórios:**
* `GET /activity/report/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31`

**Certificados:**
* `GET /certificate/generate/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31`

---

## 🗄️ Migrations

O Flyway gerencia automaticamente o banco de dados:

* V1 - Tabela de departamentos
* V2 - Tabela de voluntários
* V3 - Tabela de atividades
* V4 - Tabela de perfis de voluntários
* V5 - Dados iniciais (departamentos)

**⚠️ NUNCA edite migrations já executadas!** Crie novas (V6, V7, etc.)

---

## 🔒 Segurança

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
export JWT_SECRET="secret_gerado_256_bits"
export ADMIN_EMAIL="admin@abcaa.org"
export ADMIN_PASSWORD="senha_forte_admin"
# ... demais variáveis
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
src/main/java/com/abcaa/sistema_atividades/
├── business/
│   ├── dto/              # Data Transfer Objects
│   ├── entities/         # Entidades JPA
│   ├── enums/            # Enumerações
│   ├── mapper/           # Conversores DTO <-> Entity
│   ├── repositories/     # Repositórios JPA
│   ├── service/          # Lógica de negócio
│   └── validation/       # Validações customizadas
├── controller/           # Controllers REST
└── infrastructure/
    ├── config/           # Configurações
    ├── docs/             # Swagger
    ├── exception/        # Tratamento de erros
    └── security/         # JWT e Security
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
