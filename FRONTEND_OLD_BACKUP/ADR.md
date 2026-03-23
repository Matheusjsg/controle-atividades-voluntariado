# 🏛️ DECISÕES DE ARQUITETURA (ADR)

**Architecture Decision Records**  
**Projeto:** Sistema ABCAA - Registro de Atividades de Voluntariado

---

## ADR-001: Separação de Rotas por Role

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos definir como organizar as rotas do sistema considerando que temos 2 tipos de usuários (VOLUNTEER e ADMIN) com permissões diferentes.

### Decisão
Separar rotas em `/volunteer/*` e `/admin/*`, com componentes específicos para cada role.

### Alternativas Consideradas

#### Opção 1: Rotas Únicas com Renderização Condicional
```javascript
/dashboard → Renderiza Dashboard diferente baseado no role
/activities → Mostra "minhas" ou "todas" baseado no role
```
**Prós:**
- Menos rotas
- URLs mais simples

**Contras:**
- Componentes complexos com muita lógica condicional
- Difícil manutenção
- Confusão sobre qual versão está sendo exibida

#### Opção 2: Rotas Separadas (ESCOLHIDA)
```javascript
/volunteer/dashboard → Dashboard do voluntário
/admin/dashboard → Dashboard do admin
/volunteer/activities → Minhas atividades
/admin/activities → Todas as atividades
```
**Prós:**
- Separação clara de responsabilidades
- Componentes mais simples
- Fácil manutenção
- URLs descritivas
- Escalável

**Contras:**
- Mais rotas
- Possível duplicação de código (mitigado com componentes compartilhados)

### Consequências

**Positivas:**
- Código mais limpo e organizado
- Fácil adicionar novas funcionalidades
- Melhor para SEO (URLs descritivas)
- Facilita testes

**Negativas:**
- Mais arquivos para gerenciar
- Necessidade de componentes compartilhados

**Mitigação:**
- Criar pasta `components/common/` para componentes compartilhados
- Documentar claramente a estrutura

---

## ADR-002: Context API vs Redux

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos gerenciar estado global (autenticação, usuário, permissões).

### Decisão
Usar Context API do React.

### Alternativas Consideradas

#### Opção 1: Redux
**Prós:**
- Padrão da indústria
- DevTools poderosas
- Middleware (thunk, saga)

**Contras:**
- Boilerplate excessivo
- Curva de aprendizado
- Overkill para o escopo do projeto

#### Opção 2: Zustand
**Prós:**
- Simples e leve
- Menos boilerplate que Redux
- Boa performance

**Contras:**
- Menos conhecido
- Menos recursos da comunidade

#### Opção 3: Context API (ESCOLHIDA)
**Prós:**
- Nativo do React
- Sem dependências extras
- Suficiente para o escopo
- Fácil de entender

**Contras:**
- Pode ter problemas de performance em apps grandes
- Menos ferramentas de debug

### Consequências

**Positivas:**
- Menos dependências
- Código mais simples
- Fácil manutenção

**Negativas:**
- Se o app crescer muito, pode precisar migrar

**Mitigação:**
- Estruturar Context de forma que seja fácil migrar no futuro
- Usar múltiplos Contexts se necessário (AuthContext, ThemeContext, etc)

---

## ADR-003: Validação de Formulários

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos validar formulários no frontend.

### Decisão
Usar validações manuais com funções helper, migrar para Yup/Zod na Fase 2 se necessário.

### Alternativas Consideradas

#### Opção 1: Yup
**Prós:**
- Schema-based
- Validações complexas
- Integração com Formik

**Contras:**
- Dependência extra
- Curva de aprendizado

#### Opção 2: Zod
**Prós:**
- TypeScript-first
- Type inference
- Validações complexas

**Contras:**
- Dependência extra
- Melhor com TypeScript

#### Opção 3: Validações Manuais (ESCOLHIDA)
**Prós:**
- Sem dependências
- Controle total
- Simples para o escopo atual

**Contras:**
- Mais código manual
- Pode ficar repetitivo

### Consequências

**Positivas:**
- Início rápido
- Sem curva de aprendizado
- Flexibilidade total

**Negativas:**
- Pode precisar refatorar depois

**Mitigação:**
- Criar funções helper reutilizáveis
- Documentar padrões de validação
- Migrar para Yup/Zod se ficar muito complexo

---

## ADR-004: Sistema de Notificações

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos substituir `alert()` por um sistema de notificações melhor.

### Decisão
Usar react-toastify.

### Alternativas Consideradas

#### Opção 1: Componente Próprio
**Prós:**
- Controle total
- Sem dependências
- Customização ilimitada

**Contras:**
- Tempo de desenvolvimento
- Bugs potenciais
- Manutenção

#### Opção 2: react-toastify (ESCOLHIDA)
**Prós:**
- Pronto para usar
- Bem mantido
- Customizável
- Leve (12KB)

**Contras:**
- Dependência extra

#### Opção 3: react-hot-toast
**Prós:**
- Mais leve (3KB)
- API simples

**Contras:**
- Menos features
- Menos customizável

### Consequências

**Positivas:**
- Implementação rápida
- UX profissional
- Bem documentado

**Negativas:**
- Dependência extra (mas vale a pena)

---

## ADR-005: Estrutura de Pastas

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos organizar o código de forma escalável.

### Decisão
Estrutura por feature/role:

```
src/
├── components/
│   ├── common/      # Compartilhados
│   ├── volunteer/   # Específicos do volunteer
│   └── admin/       # Específicos do admin
├── pages/
│   ├── auth/
│   ├── volunteer/
│   └── admin/
├── hooks/
├── context/
├── service/
├── utils/
└── styles/
```

### Alternativas Consideradas

#### Opção 1: Por Tipo de Arquivo
```
src/
├── components/
├── pages/
├── hooks/
├── services/
└── utils/
```
**Contras:**
- Difícil encontrar arquivos relacionados
- Não escala bem

#### Opção 2: Por Feature (ESCOLHIDA)
**Prós:**
- Fácil encontrar arquivos relacionados
- Escala bem
- Clara separação de responsabilidades

---

## ADR-006: Tratamento de Erros HTTP

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Muitas funções em `authApi.js` não tratam erros adequadamente.

### Decisão
Adicionar verificação `response.ok` em todas as funções de API e lançar erros descritivos.

### Padrão Adotado

```javascript
export const fetchData = async (token) => {
  const response = await fetch(url, {
    headers: { "Authorization": `Bearer ${token}` }
  });
  
  // Verificar status 204 (No Content)
  if (response.status === 204) return [];
  
  // Verificar se resposta é OK
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Erro ${response.status}: ${errorText}`);
  }
  
  return await response.json();
};
```

### Consequências

**Positivas:**
- Erros capturados adequadamente
- Mensagens descritivas para o usuário
- Fácil debug

**Negativas:**
- Mais código em cada função

**Mitigação:**
- Criar função wrapper para fetch
- Centralizar tratamento de erros

---

## ADR-007: Permissões no Frontend

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos controlar o que cada role pode fazer.

### Decisão
Criar hook `usePermissions` com funções específicas para cada permissão.

### Padrão Adotado

```javascript
const { 
  canEditOwnActivity,
  canApproveActivity,
  canManageVolunteers 
} = usePermissions();

// Uso em componentes
{canApproveActivity() && (
  <button onClick={handleApprove}>Aprovar</button>
)}
```

### Alternativas Consideradas

#### Opção 1: Verificação Inline
```javascript
{user?.userType === 'ADMIN' && <button>Aprovar</button>}
```
**Contras:**
- Lógica espalhada
- Difícil manutenção
- Não considera regras complexas

#### Opção 2: Hook de Permissões (ESCOLHIDA)
**Prós:**
- Lógica centralizada
- Fácil adicionar novas regras
- Reutilizável
- Testável

---

## ADR-008: Responsividade

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Sistema precisa funcionar em diferentes dispositivos.

### Decisão
Mobile-first com breakpoints padrão.

### Breakpoints Adotados

```css
/* Mobile: 0-767px (padrão) */
/* Tablet: 768px-1023px */
@media (min-width: 768px) { }

/* Desktop: 1024px+ */
@media (min-width: 1024px) { }

/* Large Desktop: 1440px+ */
@media (min-width: 1440px) { }
```

### Consequências

**Positivas:**
- Funciona em todos os dispositivos
- Performance melhor em mobile
- Acessibilidade

---

## ADR-009: Gerenciamento de Token JWT

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos armazenar e gerenciar o token JWT.

### Decisão
Armazenar no localStorage, limpar no logout, verificar expiração.

### Alternativas Consideradas

#### Opção 1: localStorage (ESCOLHIDA)
**Prós:**
- Persiste entre sessões
- Fácil implementação
- Suportado por todos os browsers

**Contras:**
- Vulnerável a XSS (mitigado com sanitização)

#### Opção 2: sessionStorage
**Prós:**
- Mais seguro (limpa ao fechar aba)

**Contras:**
- Não persiste entre sessões
- UX ruim (precisa fazer login sempre)

#### Opção 3: Cookies httpOnly
**Prós:**
- Mais seguro contra XSS

**Contras:**
- Requer mudanças no backend
- Mais complexo

### Consequências

**Positivas:**
- UX melhor (não precisa fazer login sempre)
- Implementação simples

**Negativas:**
- Precisa sanitizar inputs para prevenir XSS

**Mitigação:**
- Sanitizar todos os inputs
- Validar token no backend
- Implementar refresh token no futuro

---

## ADR-010: Nomenclatura de Componentes

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos de padrão consistente para nomear componentes.

### Decisão
PascalCase para componentes, camelCase para funções/variáveis.

### Padrão Adotado

```javascript
// Componentes
MyComponent.jsx
ActivityForm.jsx
VolunteerDashboard.jsx

// Hooks
useAuth.js
usePermissions.js
useVolunteer.js

// Utils
formatDate.js
validateCPF.js
permissions.js

// Services
authApi.js
activityApi.js
volunteerApi.js
```

---

## ADR-011: Convenção de Idiomas

**Status:** ✅ Aceito  
**Data:** 2025-01  
**Decisores:** Equipe de Desenvolvimento

### Contexto
Precisamos definir qual idioma usar para código e documentação.

### Decisão
- **Código (variáveis, funções, classes, arquivos):** INGLÊS
- **Comentários e documentação:** PORTUGUÊS

### Justificativa

**Por que código em inglês:**
1. Padrão da indústria
2. Facilita colaboração internacional
3. Integração com bibliotecas (todas em inglês)
4. Melhor para SEO e busca de código
5. Evita problemas com acentuação

**Por que comentários em português:**
1. Equipe brasileira
2. Facilita compreensão do contexto de negócio
3. Documentação mais clara para stakeholders
4. Reduz barreira de entrada para novos desenvolvedores

### Exemplos

```javascript
// ✅ BOM: Código em inglês, comentários em português

// Busca todas as atividades do voluntário
const fetchVolunteerActivities = async (volunteerId) => {
  try {
    // Faz requisição para a API
    const response = await fetch(`/activity/volunteer/${volunteerId}`);
    
    // Verifica se a resposta foi bem-sucedida
    if (!response.ok) {
      throw new Error('Erro ao buscar atividades');
    }
    
    return await response.json();
  } catch (error) {
    // Loga o erro para debug
    console.error('Erro na requisição:', error);
    throw error;
  }
};

// Estado para controlar o carregamento
const [isLoading, setIsLoading] = useState(false);

// Lista de atividades aprovadas
const [approvedActivities, setApprovedActivities] = useState([]);


// ❌ RUIM: Código em português

const buscarAtividadesVoluntario = async (idVoluntario) => {
  const resposta = await fetch(`/activity/volunteer/${idVoluntario}`);
  return await resposta.json();
};

const [carregando, setCarregando] = useState(false);
const [atividadesAprovadas, setAtividadesAprovadas] = useState([]);


// ❌ RUIM: Comentários em inglês

// Fetch all volunteer activities
const fetchVolunteerActivities = async (volunteerId) => {
  // Make API request
  const response = await fetch(`/activity/volunteer/${volunteerId}`);
  return await response.json();
};
```

### Consequências

**Positivas:**
- Código profissional e padronizado
- Facilita manutenção futura
- Melhor integração com ferramentas
- Documentação clara em português

**Negativas:**
- Desenvolvedores precisam conhecer inglês básico
- Possível inconsistência inicial durante migração

**Mitigação:**
- Criar glossário de termos técnicos
- Revisar código em pull requests
- Usar linter para detectar português em variáveis

### Glossário Básico

| Português | Inglês |
|-----------|--------|
| Atividade | Activity |
| Voluntário | Volunteer |
| Setor | Department |
| Aprovado | Approved |
| Rejeitado | Rejected |
| Pendente | Pending |
| Relatório | Report |
| Certificado | Certificate |
| Usuário | User |
| Perfil | Profile |
| Carregando | Loading |
| Erro | Error |
| Sucesso | Success |
| Buscar | Fetch |
| Criar | Create |
| Atualizar | Update |
| Deletar | Delete |
| Listar | List |

---

## 📊 RESUMO DAS DECISÕES

| ADR | Decisão | Status | Impacto |
|-----|---------|--------|---------|
| 011 | Código em Inglês, Comentários em Português | ✅ Aceito | Alto |
| 001 | Rotas Separadas por Role | ✅ Aceito | Alto |
| 002 | Context API | ✅ Aceito | Médio |
| 003 | Validações Manuais | ✅ Aceito | Baixo |
| 004 | react-toastify | ✅ Aceito | Baixo |
| 005 | Estrutura por Feature | ✅ Aceito | Alto |
| 006 | Tratamento de Erros | ✅ Aceito | Médio |
| 007 | Hook de Permissões | ✅ Aceito | Alto |
| 008 | Mobile-First | ✅ Aceito | Médio |
| 009 | localStorage para JWT | ✅ Aceito | Médio |
| 010 | Nomenclatura PascalCase | ✅ Aceito | Baixo |
| 011 | Código em Inglês, Comentários em Português | ✅ Aceito | Alto |

---

## 🔄 REVISÃO DE DECISÕES

Estas decisões devem ser revisadas:
- **Trimestralmente** - Verificar se ainda fazem sentido
- **Quando surgir problema** - Reavaliar decisão específica
- **Antes de grandes mudanças** - Garantir alinhamento

---

## 📝 TEMPLATE PARA NOVAS ADRs

```markdown
## ADR-XXX: Título da Decisão

**Status:** 🔵 Proposto / ✅ Aceito / ❌ Rejeitado / ⚠️ Depreciado
**Data:** YYYY-MM
**Decisores:** Nome(s)

### Contexto
[Descrever o problema/situação]

### Decisão
[Descrever a decisão tomada]

### Alternativas Consideradas
[Listar outras opções e por que não foram escolhidas]

### Consequências
**Positivas:**
- [Lista de benefícios]

**Negativas:**
- [Lista de desvantagens]

**Mitigação:**
- [Como lidar com as negativas]
```

---

**Última Atualização:** 2025-01  
**Próxima Revisão:** 2025-04
