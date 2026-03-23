# 🔐 Sistema de Autenticação JWT - Implementado

## ✅ O que foi implementado:

### 1. **Context API - AuthContext**
- Gerenciamento global de autenticação
- Armazenamento de usuário e token no localStorage
- Funções: `login()`, `logout()`, `isAdmin()`, `isAuthenticated()`
- Localização: `src/context/AuthContext.jsx`

### 2. **API Service com JWT**
- Novo arquivo: `src/service/authApi.js`
- Endpoints de autenticação:
  - `POST /auth/login` - Login
  - `POST /auth/register` - Registro
- Todas as requisições agora aceitam token JWT
- Funções para relatórios e certificados adicionadas

### 3. **Páginas de Autenticação**

#### Login (`src/pages/Login.jsx`)
- Formulário de login (email + senha)
- Validação de credenciais
- Redirecionamento para dashboard após login
- Link para página de registro

#### Register (`src/pages/Register.jsx`)
- Formulário de registro completo
- Campos: nome, email, senha, setor, tipo de usuário
- Validação de senha (mínimo 6 caracteres)
- Redirecionamento automático após registro

#### Dashboard (`src/pages/Dashboard.jsx`)
- Página inicial após login
- Exibe nome e tipo do usuário
- Cards de acesso rápido para:
  - Atividades
  - Voluntários
  - Setores
  - Aprovações (apenas ADMIN)

### 4. **Proteção de Rotas**
- Componente `PrivateRoute` (`src/components/PrivateRoute.jsx`)
- Redireciona para `/login` se não autenticado
- Suporta rotas exclusivas para ADMIN (`adminOnly={true}`)
- Loading state durante verificação

### 5. **Sidebar Atualizada**
- Botão de Logout
- Menu "Aprovações" (apenas para ADMIN)
- Link para Dashboard
- Integração com AuthContext

### 6. **Estilos**
- `src/styles/auth.css` - Páginas de login/registro
- `src/styles/dashboard.css` - Dashboard
- Sidebar atualizada com botão de logout

### 7. **Rotas Configuradas**
```javascript
/login          - Página de login (pública)
/register       - Página de registro (pública)
/               - Dashboard (protegida)
/dashboard      - Dashboard (protegida)
/atividades     - Atividades (protegida)
/voluntarios    - Voluntários (protegida)
/setores        - Setores (protegida)
/aprovacoes     - Aprovações (protegida, ADMIN only)
```

---

## 🚀 Como usar:

### 1. **Primeiro acesso**
```
1. Acesse http://localhost:5173/register
2. Preencha o formulário de registro
3. Será redirecionado para o dashboard
```

### 2. **Login**
```
1. Acesse http://localhost:5173/login
2. Digite email e senha
3. Clique em "Entrar"
```

### 3. **Logout**
```
1. Clique no botão "Sair" na sidebar
2. Será redirecionado para /login
```

---

## 🔧 Integração com Backend:

### Endpoints esperados:

#### POST /auth/login
```json
Request:
{
  "email": "usuario@email.com",
  "password": "senha123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "Nome do Usuário",
    "email": "usuario@email.com",
    "userType": "VOLUNTEER" | "ADMIN",
    "departmentId": 1,
    "departmentName": "Nome do Setor"
  }
}
```

#### POST /auth/register
```json
Request:
{
  "name": "Nome Completo",
  "email": "novo@email.com",
  "password": "senha123",
  "departmentId": 1,
  "userType": "VOLUNTEER"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 2,
    "name": "Nome Completo",
    "email": "novo@email.com",
    "userType": "VOLUNTEER",
    "departmentId": 1,
    "departmentName": "Nome do Setor"
  }
}
```

### Headers nas requisições protegidas:
```
Authorization: Bearer {token}
```

---

## 📝 Próximos passos:

### Fase 2 - Funcionalidades Core:
1. ✅ **Sistema de Aprovação de Atividades** (ADMIN)
   - Página `/aprovacoes`
   - Botões Aprovar/Rejeitar
   - Filtro de pendentes

2. 📊 **Relatórios de Horas**
   - Página `/relatorios`
   - Filtro por período
   - Total de horas por voluntário

3. 📜 **Certificados PDF**
   - Botão "Gerar Certificado"
   - Download automático
   - Validação de 20h mínimas

---

## 🐛 Troubleshooting:

### Erro: "useAuth must be used within AuthProvider"
- Certifique-se que o componente está dentro do `<AuthProvider>`

### Token não está sendo enviado
- Verifique se o token está no localStorage: `localStorage.getItem('token')`
- Verifique se a função está recebendo o token como parâmetro

### Redirecionamento não funciona
- Verifique se o `<RouterProvider>` está dentro do `<AuthProvider>`

---

## 🎉 Status: COMPLETO ✅

O sistema de autenticação JWT está totalmente funcional e integrado com o backend!
