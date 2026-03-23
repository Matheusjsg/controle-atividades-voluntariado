# 📋 PLANEJAMENTO COMPLETO - ANTES DE COMEÇAR

**Objetivo:** Planejar TODOS os aspectos do projeto antes da implementação  
**Status:** 🔵 FASE DE PLANEJAMENTO

---

## 🎯 1. DEFINIÇÃO DE ESCOPO

### O que SERÁ implementado (MVP)
- ✅ Sistema de autenticação JWT
- ✅ Perfis de usuário (VOLUNTEER e ADMIN)
- ✅ CRUD de atividades com aprovação
- ✅ CRUD de setores
- ✅ CRUD de voluntários (apenas ADMIN)
- ✅ Relatórios de horas
- ✅ Geração de certificados PDF
- ✅ Dashboard com estatísticas básicas
- ✅ Sistema de notificações (toasts)
- ✅ Validações de formulário

### O que NÃO será implementado (Fase 2)
- ❌ Notificações em tempo real (WebSocket)
- ❌ Chat entre voluntários
- ❌ Sistema de pontos/gamificação
- ❌ Integração com redes sociais
- ❌ App mobile nativo
- ❌ Exportação para Excel
- ❌ Gráficos avançados
- ❌ Sistema de comentários em atividades
- ❌ Upload de fotos/documentos
- ❌ Histórico de alterações (audit log)

---

## 🗂️ 2. ESTRUTURA DE DADOS

### Entidades Principais

#### Volunteer (Voluntário)
```javascript
{
  id: number,
  name: string,
  email: string,
  password: string (hash),
  departmentId: number,
  userType: 'VOLUNTEER' | 'ADMIN',
  createdAt: date,
  updatedAt: date
}
```

#### VolunteerProfile (Perfil do Voluntário)
```javascript
{
  id: number,
  volunteerId: number,
  phone: string,
  address: string,
  city: string,
  state: string,
  zipCode: string,
  birthDate: date,
  cpf: string
}
```

#### Activity (Atividade)
```javascript
{
  id: number,
  date: date,
  description: string,
  durationMinutes: number,
  volunteerId: number,
  volunteerName: string (read-only),
  activityStatus: 'PENDING' | 'APPROVED' | 'REJECTED',
  createdAt: date,
  updatedAt: date
}
```

#### Department (Setor)
```javascript
{
  id: number,
  name: string,
  createdAt: date,
  updatedAt: date
}
```

### Relacionamentos
```
Department (1) ──→ (N) Volunteer
Volunteer (1) ──→ (1) VolunteerProfile
Volunteer (1) ──→ (N) Activity
```

---

## 🎨 3. DESIGN SYSTEM

### Paleta de Cores

```css
/* Cores Principais */
--primary: #3498db;      /* Azul principal */
--secondary: #2ecc71;    /* Verde sucesso */
--danger: #e74c3c;       /* Vermelho erro */
--warning: #f39c12;      /* Amarelo aviso */
--info: #9b59b6;         /* Roxo informação */

/* Cores de Status */
--pending: #f39c12;      /* Amarelo - Pendente */
--approved: #2ecc71;     /* Verde - Aprovado */
--rejected: #e74c3c;     /* Vermelho - Rejeitado */

/* Cores de Fundo */
--bg-primary: #ffffff;   /* Branco */
--bg-secondary: #f8f9fa; /* Cinza claro */
--bg-dark: #2c3e50;      /* Azul escuro */

/* Cores de Texto */
--text-primary: #2c3e50; /* Texto principal */
--text-secondary: #7f8c8d; /* Texto secundário */
--text-light: #ffffff;   /* Texto claro */

/* Gradientes */
--gradient-primary: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
--gradient-success: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
--gradient-danger: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
```

### Tipografia

```css
/* Fontes */
--font-primary: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
--font-mono: 'Fira Code', 'Courier New', monospace;

/* Tamanhos */
--text-xs: 0.75rem;   /* 12px */
--text-sm: 0.875rem;  /* 14px */
--text-base: 1rem;    /* 16px */
--text-lg: 1.125rem;  /* 18px */
--text-xl: 1.25rem;   /* 20px */
--text-2xl: 1.5rem;   /* 24px */
--text-3xl: 1.875rem; /* 30px */
--text-4xl: 2.25rem;  /* 36px */

/* Pesos */
--font-normal: 400;
--font-medium: 500;
--font-semibold: 600;
--font-bold: 700;
```

### Espaçamentos

```css
--spacing-xs: 0.25rem;  /* 4px */
--spacing-sm: 0.5rem;   /* 8px */
--spacing-md: 1rem;     /* 16px */
--spacing-lg: 1.5rem;   /* 24px */
--spacing-xl: 2rem;     /* 32px */
--spacing-2xl: 3rem;    /* 48px */
--spacing-3xl: 4rem;    /* 64px */
```

### Componentes Base

```css
/* Botões */
.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.btn-primary { background: var(--primary); color: white; }
.btn-success { background: var(--secondary); color: white; }
.btn-danger { background: var(--danger); color: white; }

/* Cards */
.card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

/* Badges */
.badge {
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.875rem;
  font-weight: 600;
}

.badge-admin { background: #3498db; color: white; }
.badge-volunteer { background: #95a5a6; color: white; }
.badge-pending { background: #f39c12; color: white; }
.badge-approved { background: #2ecc71; color: white; }
.badge-rejected { background: #e74c3c; color: white; }
```

---

## 📱 4. WIREFRAMES E FLUXOS

### Fluxo de Autenticação

```
┌─────────────┐
│   Landing   │
│    Page     │
└──────┬──────┘
       │
       ├──→ [Login] ──→ Autenticação ──→ Dashboard
       │                    ↓
       │                  Falha
       │                    ↓
       │              Mensagem Erro
       │
       └──→ [Registro] ──→ Criar Conta ──→ Dashboard
                              ↓
                            Falha
                              ↓
                        Mensagem Erro
```

### Fluxo do VOLUNTEER

```
Login
  ↓
Dashboard
  ├──→ Meu Perfil
  │      ├──→ Visualizar
  │      └──→ Editar
  │
  ├──→ Minhas Atividades
  │      ├──→ Listar (filtrar por status)
  │      ├──→ Criar Nova
  │      ├──→ Editar (se PENDING)
  │      └──→ Deletar (se PENDING)
  │
  └──→ Meus Relatórios
         ├──→ Selecionar Período
         ├──→ Ver Estatísticas
         └──→ Gerar Certificado (se >= 20h)
```

### Fluxo do ADMIN

```
Login
  ↓
Dashboard
  ├──→ Meu Perfil (igual VOLUNTEER)
  │
  ├──→ Minhas Atividades (igual VOLUNTEER)
  │
  ├──→ Meus Relatórios (igual VOLUNTEER)
  │
  ├──→ Todos os Voluntários
  │      ├──→ Listar Todos
  │      ├──→ Criar Novo
  │      ├──→ Ver Perfil de Qualquer Um
  │      ├──→ Editar Qualquer Um
  │      ├──→ Alterar Tipo (VOLUNTEER ↔ ADMIN)
  │      └──→ Deletar (se sem atividades)
  │
  ├──→ Aprovações
  │      ├──→ Listar Pendentes
  │      ├──→ Aprovar
  │      └──→ Rejeitar
  │
  ├──→ Setores
  │      ├──→ Listar Todos
  │      ├──→ Criar Novo
  │      ├──→ Editar
  │      └──→ Deletar (se sem voluntários)
  │
  └──→ Todos os Relatórios
         ├──→ Selecionar Voluntário
         ├──→ Selecionar Período
         ├──→ Ver Estatísticas
         └──→ Gerar Certificado de Qualquer Um
```

---

## 🔐 5. SEGURANÇA

### Camadas de Segurança

#### Frontend
1. **Validação de Inputs**
   - Sanitização de dados
   - Validação de formato (email, CPF, etc)
   - Validação de tamanho (min/max)
   - Prevenção de XSS

2. **Proteção de Rotas**
   - PrivateRoute para rotas autenticadas
   - Verificação de permissões por role
   - Redirect automático se não autorizado

3. **Armazenamento Seguro**
   - Token JWT no localStorage
   - Limpeza ao fazer logout
   - Expiração automática

#### Backend (já implementado)
1. **Autenticação JWT**
2. **Criptografia de senhas (BCrypt)**
3. **Validação de DTOs**
4. **CORS configurado**
5. **@PreAuthorize em endpoints sensíveis**

### Validações de Formulário

```javascript
// Exemplo de validações
const validations = {
  email: {
    required: true,
    pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
    message: 'Email inválido'
  },
  password: {
    required: true,
    minLength: 6,
    message: 'Senha deve ter no mínimo 6 caracteres'
  },
  cpf: {
    required: false,
    pattern: /^\d{3}\.\d{3}\.\d{3}-\d{2}$/,
    message: 'CPF inválido (formato: 000.000.000-00)'
  },
  phone: {
    required: false,
    pattern: /^\(\d{2}\) \d{4,5}-\d{4}$/,
    message: 'Telefone inválido (formato: (00) 00000-0000)'
  },
  zipCode: {
    required: false,
    pattern: /^\d{5}-\d{3}$/,
    message: 'CEP inválido (formato: 00000-000)'
  },
  durationMinutes: {
    required: true,
    min: 15,
    max: 720,
    message: 'Duração deve estar entre 15 minutos e 12 horas'
  },
  description: {
    required: true,
    minLength: 10,
    maxLength: 500,
    message: 'Descrição deve ter entre 10 e 500 caracteres'
  }
};
```

---

## 📊 6. MÉTRICAS E ANALYTICS

### Métricas a Acompanhar

#### Dashboard do VOLUNTEER
```javascript
{
  totalActivities: number,        // Total de atividades
  pendingActivities: number,      // Atividades pendentes
  approvedActivities: number,     // Atividades aprovadas
  rejectedActivities: number,     // Atividades rejeitadas
  totalHours: number,             // Total de horas (aprovadas)
  currentMonthHours: number,      // Horas do mês atual
  averageHoursPerActivity: number // Média de horas por atividade
}
```

#### Dashboard do ADMIN
```javascript
{
  totalVolunteers: number,        // Total de voluntários
  activeVolunteers: number,       // Voluntários com atividades
  totalActivities: number,        // Total de atividades
  pendingActivities: number,      // Atividades pendentes
  approvedActivities: number,     // Atividades aprovadas
  rejectedActivities: number,     // Atividades rejeitadas
  totalHours: number,             // Total de horas (todas)
  totalDepartments: number,       // Total de setores
  topVolunteers: Array<{          // Top 5 voluntários
    name: string,
    hours: number
  }>
}
```

---

## 🧪 7. ESTRATÉGIA DE TESTES

### Testes Manuais (Fase 1)

#### Checklist de Testes - VOLUNTEER
```
Autenticação:
□ Registrar novo usuário
□ Login com credenciais válidas
□ Login com credenciais inválidas
□ Logout
□ Tentar acessar rota protegida sem login

Perfil:
□ Visualizar meu perfil
□ Editar meu perfil
□ Salvar alterações
□ Validar campos obrigatórios
□ Validar formato de CPF, telefone, CEP

Atividades:
□ Criar nova atividade
□ Listar minhas atividades
□ Filtrar por status (PENDING, APPROVED, REJECTED)
□ Editar atividade PENDING
□ Tentar editar atividade APPROVED (deve falhar)
□ Deletar atividade PENDING
□ Tentar deletar atividade APPROVED (deve falhar)

Relatórios:
□ Selecionar período
□ Gerar relatório
□ Verificar estatísticas
□ Tentar gerar certificado com < 20h (deve falhar)
□ Gerar certificado com >= 20h
□ Download do PDF
```

#### Checklist de Testes - ADMIN
```
Todas as funcionalidades do VOLUNTEER +

Voluntários:
□ Listar todos os voluntários
□ Criar novo voluntário
□ Editar voluntário
□ Alterar tipo de usuário (VOLUNTEER → ADMIN)
□ Alterar tipo de usuário (ADMIN → VOLUNTEER)
□ Deletar voluntário sem atividades
□ Tentar deletar voluntário com atividades (deve falhar)

Aprovações:
□ Listar atividades pendentes
□ Aprovar atividade
□ Rejeitar atividade
□ Verificar atualização da lista

Setores:
□ Listar todos os setores
□ Criar novo setor
□ Editar setor
□ Deletar setor sem voluntários
□ Tentar deletar setor com voluntários (deve falhar)

Relatórios:
□ Selecionar qualquer voluntário
□ Gerar relatório de qualquer voluntário
□ Gerar certificado de qualquer voluntário
```

### Testes Automatizados (Fase 2)
```javascript
// Exemplos de testes futuros
describe('AuthContext', () => {
  test('deve fazer login com sucesso', () => {});
  test('deve fazer logout', () => {});
  test('deve verificar se está autenticado', () => {});
});

describe('usePermissions', () => {
  test('VOLUNTEER não pode aprovar atividades', () => {});
  test('ADMIN pode aprovar atividades', () => {});
  test('VOLUNTEER pode editar apenas atividades PENDING', () => {});
});

describe('ActivityForm', () => {
  test('deve validar campos obrigatórios', () => {});
  test('deve validar duração mínima', () => {});
  test('deve enviar dados corretos', () => {});
});
```

---

## 🚀 8. ESTRATÉGIA DE DEPLOY

### Ambientes

#### Desenvolvimento (Local)
```
Frontend: http://localhost:5173
Backend: http://localhost:8080
Database: PostgreSQL local
```

#### Produção
```
Frontend: Vercel / Netlify
Backend: Render / Railway
Database: PostgreSQL (Render / Supabase)
```

### Variáveis de Ambiente

#### Frontend (.env)
```bash
# Desenvolvimento
VITE_API_URL=http://localhost:8080

# Produção
VITE_API_URL=https://api-abcaa.onrender.com
```

#### Backend (application.properties)
```properties
# Desenvolvimento
spring.datasource.url=jdbc:postgresql://localhost:5432/ong-abcaa
spring.datasource.username=postgres
spring.datasource.password=sua_senha

# Produção
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

### Checklist de Deploy

```
Pré-Deploy:
□ Todos os testes manuais passando
□ Build do frontend sem erros
□ Build do backend sem erros
□ Variáveis de ambiente configuradas
□ Banco de dados criado
□ Migrations executadas

Deploy Frontend:
□ Build: npm run build
□ Upload para Vercel/Netlify
□ Configurar variável VITE_API_URL
□ Testar URL de produção

Deploy Backend:
□ Build: mvn clean package
□ Upload para Render/Railway
□ Configurar variáveis de ambiente
□ Executar migrations
□ Testar endpoints

Pós-Deploy:
□ Testar fluxo completo em produção
□ Verificar logs de erro
□ Monitorar performance
□ Criar usuário admin inicial
```

---

## 📚 9. DOCUMENTAÇÃO

### Documentos a Criar/Atualizar

```
□ README.md - Instruções de instalação e uso
□ CONTRIBUTING.md - Guia de contribuição
□ CHANGELOG.md - Histórico de alterações
□ API.md - Documentação de endpoints
□ ARCHITECTURE.md - Arquitetura do sistema
□ DEPLOYMENT.md - Guia de deploy
□ USER_GUIDE.md - Manual do usuário
□ ADMIN_GUIDE.md - Manual do administrador
```

### Comentários no Código

```javascript
// ✅ BOM: Comentário explicativo
/**
 * Verifica se o usuário pode editar a atividade.
 * Regras:
 * - Deve ser o dono da atividade
 * - Status deve ser PENDING
 * - Deve ter permissão EDIT_OWN_ACTIVITY
 */
const canEditActivity = (activity) => {
  // implementação
};

// ❌ RUIM: Comentário óbvio
// Retorna true se pode editar
const canEditActivity = (activity) => {
  return true;
};
```

---

## 🔄 10. VERSIONAMENTO

### Git Flow

```
main (produção)
  ↑
develop (desenvolvimento)
  ↑
feature/nome-da-feature (features)
  ↑
bugfix/nome-do-bug (correções)
```

### Convenção de Commits

```bash
# Features
git commit -m "feat: adiciona sistema de aprovação de atividades"

# Correções
git commit -m "fix: corrige campo departmentId em AtividadeForm"

# Documentação
git commit -m "docs: atualiza README com instruções de deploy"

# Refatoração
git commit -m "refactor: reorganiza estrutura de pastas"

# Estilo
git commit -m "style: ajusta espaçamento em Sidebar"

# Testes
git commit -m "test: adiciona testes para AuthContext"

# Performance
git commit -m "perf: otimiza carregamento de atividades"
```

---

## 📅 11. CRONOGRAMA

### Fase 1: Correções Críticas (1-2 dias)
```
Dia 1:
□ Corrigir AtividadeForm.jsx
□ Deletar api.js duplicado
□ Adicionar validação HTTP em authApi.js
□ Testar correções

Dia 2:
□ Implementar sistema de notificações (react-toastify)
□ Adicionar loading states
□ Melhorar validações de formulário
□ Testar melhorias
```

### Fase 2: Reestruturação (3-5 dias)
```
Dia 3:
□ Criar estrutura de pastas (volunteer/admin)
□ Criar hooks de permissões
□ Criar constantes e utils

Dia 4:
□ Refatorar rotas
□ Criar Sidebar dinâmica
□ Criar páginas do VOLUNTEER

Dia 5:
□ Criar páginas do ADMIN
□ Implementar validações de permissão
□ Testar fluxos completos
```

### Fase 3: Polimento e Deploy (2-3 dias)
```
Dia 6:
□ Ajustes de UI/UX
□ Testes manuais completos
□ Correção de bugs encontrados

Dia 7:
□ Atualizar documentação
□ Preparar para deploy
□ Deploy em produção

Dia 8:
□ Testes em produção
□ Ajustes finais
□ Treinamento de usuários
```

**Total: 8-10 dias**

---

## 🎯 12. CRITÉRIOS DE SUCESSO

### Funcionalidades
- ✅ Todos os fluxos principais funcionando
- ✅ Permissões implementadas corretamente
- ✅ Validações em todos os formulários
- ✅ Tratamento de erros adequado
- ✅ Feedback visual para todas as ações

### Performance
- ✅ Tempo de carregamento < 3 segundos
- ✅ Sem memory leaks
- ✅ Requisições otimizadas
- ✅ Bundle size < 500KB

### Segurança
- ✅ Autenticação JWT funcionando
- ✅ Rotas protegidas
- ✅ Validações no frontend e backend
- ✅ Sanitização de inputs

### UX
- ✅ Interface intuitiva
- ✅ Mensagens claras
- ✅ Loading states visíveis
- ✅ Responsivo (mobile/tablet/desktop)

### Documentação
- ✅ README completo
- ✅ Código comentado
- ✅ Guias de usuário
- ✅ Documentação de API

---

## 🤔 13. DECISÕES TÉCNICAS PENDENTES

### Perguntas a Responder

1. **Notificações:**
   - Usar react-toastify ou criar componente próprio?
   - **Recomendação:** react-toastify (mais rápido)

2. **Validações:**
   - Usar Yup/Zod ou validações manuais?
   - **Recomendação:** Validações manuais primeiro, Yup depois

3. **Estado Global:**
   - Continuar com Context API ou migrar para Redux/Zustand?
   - **Recomendação:** Context API (suficiente para o escopo)

4. **Estilização:**
   - CSS puro, CSS Modules ou Styled Components?
   - **Recomendação:** CSS puro (já está assim)

5. **Testes:**
   - Jest + RTL ou Vitest?
   - **Recomendação:** Vitest (integração com Vite)

6. **Internacionalização:**
   - Implementar i18n agora ou depois?
   - **Recomendação:** Depois (não é prioridade)

7. **Dark Mode:**
   - Implementar agora ou depois?
   - **Recomendação:** Depois (não é prioridade)

8. **PWA:**
   - Transformar em PWA agora ou depois?
   - **Recomendação:** Depois (não é prioridade)

---

## 📝 14. CHECKLIST FINAL ANTES DE COMEÇAR

### Planejamento
- ✅ Escopo definido
- ✅ Estrutura de dados definida
- ✅ Design system definido
- ✅ Fluxos mapeados
- ✅ Segurança planejada
- ✅ Testes planejados
- ✅ Deploy planejado
- ✅ Cronograma definido

### Ambiente
- □ Node.js instalado
- □ Java 21 instalado
- □ PostgreSQL instalado
- □ Git configurado
- □ IDE configurada
- □ Extensões instaladas

### Repositório
- □ Repositório criado
- □ .gitignore configurado
- □ README inicial criado
- □ Branch develop criada

### Dependências
- □ Frontend: npm install executado
- □ Backend: mvn clean install executado
- □ Banco de dados criado
- □ Migrations executadas

---

## 🚀 PRÓXIMO PASSO

Agora que temos TODO o planejamento, podemos:

1. **Revisar o planejamento** - Ajustar algo?
2. **Começar Fase 1** - Correções críticas
3. **Criar mais documentos** - Algum detalhe específico?
4. **Configurar ambiente** - Preparar tudo antes de começar

**O que você prefere fazer agora?**
