# 🚀 Guia Rápido - Sistema de Atividades de Voluntariado

## ⚡ Início Rápido

### 1️⃣ Backend (Spring Boot)

```bash
# Navegar para o diretório do projeto
cd sistema-atividades

# Executar o backend
mvn spring-boot:run
```

✅ Backend rodando em: `http://localhost:8080`  
📚 Swagger UI: `http://localhost:8080/swagger-ui.html`

---

### 2️⃣ Frontend (React + Vite)

```bash
# Navegar para o diretório do frontend
cd Frontend/frontend-ABCAA

# Instalar dependências (primeira vez)
npm install

# Executar o frontend
npm run dev
```

✅ Frontend rodando em: `http://localhost:5173`

---

## 🗄️ Banco de Dados

### PostgreSQL

```sql
-- Criar banco de dados
CREATE DATABASE sistema_atividades;
```

### Configurar `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_atividades
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

---

## 📁 Estrutura do Projeto

```
sistema-atividades/
│
├── src/main/java/              # Backend (Spring Boot)
│   └── com/abcaa/sistema_atividades/
│       ├── business/
│       │   ├── dto/
│       │   ├── entities/
│       │   ├── enums/
│       │   ├── mapper/
│       │   ├── repositories/
│       │   └── service/
│       ├── controller/
│       └── infrastructure/
│
└── Frontend/frontend-ABCAA/    # Frontend (React)
    └── src/
        ├── components/         # Componentes reutilizáveis
        ├── pages/             # Páginas da aplicação
        ├── service/           # Integração com API
        └── styles/            # Arquivos CSS
```

---

## 🔌 Endpoints da API

### Atividades
- `POST /activity/create` - Criar
- `GET /activity/listAll` - Listar todas
- `DELETE /activity/delete/{id}` - Excluir

### Setores
- `POST /departments/create` - Criar
- `GET /departments/list` - Listar todos
- `DELETE /departments/delete/{id}` - Excluir

### Voluntários
- `POST /volunteer/create` - Criar
- `GET /volunteer/list` - Listar todos
- `DELETE /volunteer/delete/{id}` - Excluir

---

## 🎯 Fluxo de Uso

1. **Cadastrar Setores** → Página "Setores"
2. **Cadastrar Voluntários** → Página "Voluntários" (vincular a um setor)
3. **Registrar Atividades** → Página "Atividades" (vincular a um voluntário)

---

## 🛠️ Tecnologias

### Backend
- Java 21
- Spring Boot 3.5.7
- PostgreSQL
- Flyway
- Swagger/OpenAPI

### Frontend
- React 19
- Vite
- React Router DOM
- Lucide React

---

## 📝 Dados de Exemplo

### Setor
```json
{
  "name": "Educação"
}
```

### Voluntário
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "departmentId": 1,
  "userType": "VOLUNTEER"
}
```

### Atividade
```json
{
  "activityDate": "2024-01-15",
  "activityTime": 120,
  "description": "Aula de reforço escolar",
  "volunteerId": 1
}
```

---

## ⚠️ Troubleshooting

### Backend não inicia
- Verificar se o PostgreSQL está rodando
- Conferir credenciais no `application.properties`
- Verificar se a porta 8080 está livre

### Frontend não conecta
- Verificar se o backend está rodando
- Conferir o arquivo `.env` (deve ter `VITE_API_URL=http://localhost:8080`)
- Verificar CORS no backend

### Erro ao criar voluntário
- Certifique-se de que existe pelo menos um setor cadastrado

### Erro ao criar atividade
- Certifique-se de que existe pelo menos um voluntário cadastrado

---

## 📞 Suporte

Desenvolvido por **Matheus Jesus**

Para dúvidas ou sugestões, consulte a documentação completa no README.md
