# Guia de Contribuição

## 🚀 Configuração Inicial

### 1. Clone o repositório
```bash
git clone https://github.com/abcaa-ong/volunteer-hours-log.git
cd volunteer-hours-log/Backend
```

### 2. Configure suas variáveis locais
```bash
cp .env.example .env
nano .env  # Edite com suas configurações
```

### 3. Configure o banco de dados
```sql
CREATE DATABASE "ong-abcaa";
```

### 4. Execute a aplicação
```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔒 Segurança

### ⚠️ NUNCA commite:
- ❌ Arquivo `.env`
- ❌ Senhas ou tokens
- ❌ Credenciais de banco
- ❌ JWT secrets

### ✅ SEMPRE:
- Use `.env.example` como template
- Gere seus próprios secrets locais
- Mantenha credenciais fora do código

---

## 📝 Padrões de Código

### Commits
```
feat: adiciona nova funcionalidade
fix: corrige bug na validação
docs: atualiza README
refactor: refatora serviço de atividades
```

### Branches
```
feature/nome-da-funcionalidade
bugfix/descricao-do-bug
hotfix/correcao-urgente
```

---

## 🗄️ Migrations

### ⚠️ REGRAS:
1. **NUNCA** edite migrations já executadas
2. **SEMPRE** crie novas migrations (V6, V7, etc.)
3. Teste localmente antes de commitar

### Criar nova migration:
```sql
-- V6__descricao_da_mudanca.sql
ALTER TABLE tb_volunteer ADD COLUMN telefone VARCHAR(20);
```

---

## 🧪 Testes

Antes de abrir PR, execute:
```bash
mvn clean test
mvn clean compile
```

---

## 🤝 Pull Requests

1. Crie uma branch a partir de `main`
2. Faça suas alterações
3. Teste localmente
4. Commit com mensagem descritiva
5. Push e abra PR
6. Aguarde code review

---

## 📞 Dúvidas?

Entre em contato com a equipe de tecnologia.
