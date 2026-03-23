# 📖 GUIA DE BOAS PRÁTICAS - DESENVOLVIMENTO

**Projeto:** Sistema ABCAA  
**Objetivo:** Manter código limpo, consistente e manutenível

---

## 🎯 PRINCÍPIOS FUNDAMENTAIS

### 1. KISS (Keep It Simple, Stupid)
```javascript
// ❌ RUIM: Complexo demais
const calculateTotal = (items) => {
  return items.reduce((acc, item) => {
    return acc + (item.price * item.quantity * (1 - item.discount / 100));
  }, 0);
};

// ✅ BOM: Simples e claro
const calculateTotal = (items) => {
  return items.reduce((total, item) => {
    const itemTotal = item.price * item.quantity;
    const discount = itemTotal * (item.discount / 100);
    return total + (itemTotal - discount);
  }, 0);
};
```

### 2. DRY (Don't Repeat Yourself)
```javascript
// ❌ RUIM: Código duplicado
const fetchVolunteers = async () => {
  const response = await fetch('/volunteer/list');
  if (!response.ok) throw new Error('Erro');
  return await response.json();
};

const fetchActivities = async () => {
  const response = await fetch('/activity/listAll');
  if (!response.ok) throw new Error('Erro');
  return await response.json();
};

// ✅ BOM: Função reutilizável
const fetchAPI = async (endpoint) => {
  const response = await fetch(endpoint);
  if (!response.ok) throw new Error(`Erro ao buscar ${endpoint}`);
  return await response.json();
};

const fetchVolunteers = () => fetchAPI('/volunteer/list');
const fetchActivities = () => fetchAPI('/activity/listAll');
```

### 3. YAGNI (You Aren't Gonna Need It)
```javascript
// ❌ RUIM: Código para funcionalidade futura
const ActivityForm = () => {
  const [activity, setActivity] = useState({});
  const [tags, setTags] = useState([]); // Não usado ainda
  const [attachments, setAttachments] = useState([]); // Não usado ainda
  const [comments, setComments] = useState([]); // Não usado ainda
  // ...
};

// ✅ BOM: Apenas o necessário agora
const ActivityForm = () => {
  const [activity, setActivity] = useState({});
  // Adicionar tags, attachments, comments quando necessário
};
```

---

## 📝 NOMENCLATURA

### Componentes
```javascript
// ✅ BOM: PascalCase, descritivo
ActivityForm.jsx
VolunteerDashboard.jsx
ApprovalPanel.jsx

// ❌ RUIM
activityform.jsx
volunteer_dashboard.jsx
panel.jsx
```

### Funções e Variáveis
```javascript
// ✅ BOM: camelCase, verbos para funções
const handleSubmit = () => {};
const fetchActivities = async () => {};
const isAuthenticated = () => {};
const canEditActivity = () => {};

// ❌ RUIM
const submit = () => {}; // Não é claro
const activities = async () => {}; // Parece variável
const authenticated = () => {}; // Não é claro
```

### Constantes
```javascript
// ✅ BOM: UPPER_SNAKE_CASE
const MAX_DURATION_MINUTES = 720;
const MIN_DURATION_MINUTES = 15;
const API_BASE_URL = 'http://localhost:8080';

// ❌ RUIM
const maxDuration = 720;
const minDuration = 15;
```

### Booleanos
```javascript
// ✅ BOM: Prefixos is, has, can, should
const isLoading = true;
const hasPermission = false;
const canEdit = true;
const shouldShowModal = false;

// ❌ RUIM
const loading = true;
const permission = false;
const edit = true;
```

---

## 🏗️ ESTRUTURA DE COMPONENTES

### Ordem de Elementos
```javascript
// ✅ BOM: Ordem consistente
import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { fetchData } from '../service/api';
import './styles.css';

const MyComponent = () => {
  // 1. Hooks
  const { user } = useAuth();
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);

  // 2. useEffect
  useEffect(() => {
    loadData();
  }, []);

  // 3. Funções
  const loadData = async () => {
    setLoading(true);
    try {
      const result = await fetchData();
      setData(result);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleClick = () => {
    // ...
  };

  // 4. Renderização condicional
  if (loading) return <div>Carregando...</div>;
  if (!data.length) return <div>Sem dados</div>;

  // 5. Render principal
  return (
    <div>
      {/* JSX */}
    </div>
  );
};

export default MyComponent;
```

### Componentes Pequenos
```javascript
// ✅ BOM: Componente focado
const ActivityCard = ({ activity, onDelete }) => {
  return (
    <div className="activity-card">
      <h3>{activity.description}</h3>
      <p>{activity.date}</p>
      <button onClick={() => onDelete(activity.id)}>Deletar</button>
    </div>
  );
};

// ❌ RUIM: Componente fazendo muita coisa
const ActivityCard = ({ activity }) => {
  const [editing, setEditing] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [loading, setLoading] = useState(false);
  
  const handleEdit = async () => { /* ... */ };
  const handleDelete = async () => { /* ... */ };
  const handleApprove = async () => { /* ... */ };
  const handleReject = async () => { /* ... */ };
  
  // Muita lógica...
  
  return (
    <div>
      {/* JSX complexo */}
    </div>
  );
};
```

---

## 🎨 JSX E RENDERIZAÇÃO

### Renderização Condicional
```javascript
// ✅ BOM: Claro e legível
{isLoading && <Loading />}
{error && <ErrorMessage message={error} />}
{data.length === 0 && <EmptyState />}
{data.length > 0 && <DataList items={data} />}

// ❌ RUIM: Ternário aninhado
{isLoading ? (
  <Loading />
) : error ? (
  <ErrorMessage />
) : data.length === 0 ? (
  <EmptyState />
) : (
  <DataList />
)}
```

### Props
```javascript
// ✅ BOM: Desestruturação
const ActivityCard = ({ activity, onEdit, onDelete }) => {
  return <div>{activity.description}</div>;
};

// ❌ RUIM: Usar props diretamente
const ActivityCard = (props) => {
  return <div>{props.activity.description}</div>;
};
```

### Listas
```javascript
// ✅ BOM: Key única e estável
{activities.map(activity => (
  <ActivityCard 
    key={activity.id} 
    activity={activity} 
  />
))}

// ❌ RUIM: Index como key
{activities.map((activity, index) => (
  <ActivityCard 
    key={index} 
    activity={activity} 
  />
))}
```

---

## 🔧 HOOKS

### useState
```javascript
// ✅ BOM: Estado inicial apropriado
const [user, setUser] = useState(null);
const [activities, setActivities] = useState([]);
const [loading, setLoading] = useState(false);

// ❌ RUIM: Estado inicial errado
const [user, setUser] = useState({});
const [activities, setActivities] = useState(null);
const [loading, setLoading] = useState(true);
```

### useEffect
```javascript
// ✅ BOM: Dependências corretas
useEffect(() => {
  fetchActivities(volunteerId);
}, [volunteerId]);

// ❌ RUIM: Dependências faltando
useEffect(() => {
  fetchActivities(volunteerId);
}, []); // volunteerId deveria estar aqui

// ✅ BOM: Cleanup
useEffect(() => {
  const timer = setTimeout(() => {
    // ...
  }, 1000);
  
  return () => clearTimeout(timer);
}, []);
```

### Custom Hooks
```javascript
// ✅ BOM: Hook reutilizável
const useActivities = (volunteerId) => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadActivities = async () => {
      setLoading(true);
      try {
        const data = await fetchActivities(volunteerId);
        setActivities(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    
    loadActivities();
  }, [volunteerId]);

  return { activities, loading, error };
};

// Uso
const MyComponent = () => {
  const { activities, loading, error } = useActivities(volunteerId);
  // ...
};
```

---

## 🔐 SEGURANÇA

### Sanitização de Inputs
```javascript
// ✅ BOM: Sanitizar antes de usar
const sanitizeInput = (input) => {
  return input
    .trim()
    .replace(/[<>]/g, '') // Remove < e >
    .substring(0, 500); // Limita tamanho
};

const handleSubmit = (e) => {
  e.preventDefault();
  const sanitizedDescription = sanitizeInput(description);
  // Usar sanitizedDescription
};

// ❌ RUIM: Usar input direto
const handleSubmit = (e) => {
  e.preventDefault();
  // Usar description direto (risco de XSS)
};
```

### Validação
```javascript
// ✅ BOM: Validar no frontend E backend
const validateEmail = (email) => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
};

const handleSubmit = async (e) => {
  e.preventDefault();
  
  if (!validateEmail(email)) {
    toast.error('Email inválido');
    return;
  }
  
  // Enviar para backend (que também valida)
  await submitForm({ email });
};
```

---

## 🎯 TRATAMENTO DE ERROS

### Try-Catch
```javascript
// ✅ BOM: Tratamento específico
const loadData = async () => {
  setLoading(true);
  try {
    const data = await fetchData();
    setData(data);
  } catch (error) {
    console.error('Erro ao carregar dados:', error);
    toast.error('Erro ao carregar dados. Tente novamente.');
  } finally {
    setLoading(false);
  }
};

// ❌ RUIM: Engolir erro
const loadData = async () => {
  try {
    const data = await fetchData();
    setData(data);
  } catch (error) {
    // Nada
  }
};
```

### Mensagens de Erro
```javascript
// ✅ BOM: Mensagens claras
toast.error('Erro ao salvar atividade. Verifique os campos e tente novamente.');
toast.error('Você não tem permissão para realizar esta ação.');
toast.error('Sessão expirada. Faça login novamente.');

// ❌ RUIM: Mensagens genéricas
toast.error('Erro');
toast.error('Algo deu errado');
toast.error('Erro 500');
```

---

## 📊 PERFORMANCE

### Evitar Renderizações Desnecessárias
```javascript
// ✅ BOM: useCallback para funções
const handleClick = useCallback(() => {
  // ...
}, [dependency]);

// ✅ BOM: useMemo para cálculos pesados
const expensiveValue = useMemo(() => {
  return calculateExpensiveValue(data);
}, [data]);

// ✅ BOM: React.memo para componentes
const ActivityCard = React.memo(({ activity }) => {
  return <div>{activity.description}</div>;
});
```

### Lazy Loading
```javascript
// ✅ BOM: Carregar componentes sob demanda
const AdminDashboard = lazy(() => import('./pages/admin/Dashboard'));
const VolunteerDashboard = lazy(() => import('./pages/volunteer/Dashboard'));

// Uso
<Suspense fallback={<Loading />}>
  <AdminDashboard />
</Suspense>
```

---

## 🧪 TESTABILIDADE

### Componentes Testáveis
```javascript
// ✅ BOM: Lógica separada
const calculateTotal = (activities) => {
  return activities.reduce((sum, act) => sum + act.durationMinutes, 0);
};

const ActivitySummary = ({ activities }) => {
  const total = calculateTotal(activities);
  return <div>Total: {total} minutos</div>;
};

// Fácil testar calculateTotal isoladamente

// ❌ RUIM: Lógica no componente
const ActivitySummary = ({ activities }) => {
  const total = activities.reduce((sum, act) => sum + act.durationMinutes, 0);
  return <div>Total: {total} minutos</div>;
};
```

---

## 📝 COMENTÁRIOS

### Quando Comentar
```javascript
// ✅ BOM: Explicar "por quê", não "o quê"
// Usamos setTimeout para evitar race condition com o backend
setTimeout(() => {
  fetchData();
}, 100);

// ✅ BOM: Documentar funções complexas
/**
 * Calcula o total de horas aprovadas no período.
 * @param {Array} activities - Lista de atividades
 * @param {Date} startDate - Data inicial
 * @param {Date} endDate - Data final
 * @returns {number} Total de horas
 */
const calculateApprovedHours = (activities, startDate, endDate) => {
  // ...
};

// ❌ RUIM: Comentário óbvio
// Incrementa contador
counter++;

// ❌ RUIM: Código comentado
// const oldFunction = () => {
//   // código antigo
// };
```

---

## 🎨 CSS

### Nomenclatura BEM
```css
/* ✅ BOM: BEM (Block Element Modifier) */
.activity-card { }
.activity-card__title { }
.activity-card__description { }
.activity-card--pending { }
.activity-card--approved { }

/* ❌ RUIM: Nomes genéricos */
.card { }
.title { }
.description { }
```

### Evitar !important
```css
/* ✅ BOM: Especificidade correta */
.activity-card .btn-primary {
  background: blue;
}

/* ❌ RUIM: Usar !important */
.btn-primary {
  background: blue !important;
}
```

---

## 🔄 GIT

### Commits
```bash
# ✅ BOM: Mensagens descritivas
git commit -m "feat: adiciona sistema de aprovação de atividades"
git commit -m "fix: corrige validação de CPF no formulário"
git commit -m "refactor: reorganiza estrutura de pastas"

# ❌ RUIM: Mensagens vagas
git commit -m "update"
git commit -m "fix bug"
git commit -m "changes"
```

### Branches
```bash
# ✅ BOM: Nomes descritivos
feature/approval-system
bugfix/cpf-validation
refactor/folder-structure

# ❌ RUIM: Nomes genéricos
feature/new-feature
bugfix/fix
update
```

---

## ✅ CHECKLIST ANTES DE COMMIT

```
□ Código funciona corretamente
□ Sem console.log desnecessários
□ Sem código comentado
□ Variáveis com nomes descritivos
□ Funções pequenas e focadas
□ Tratamento de erros adequado
□ Validações implementadas
□ Sem warnings no console
□ Código formatado
□ Comentários onde necessário
□ Testes manuais realizados
```

---

## 📚 RECURSOS

### Documentação
- [React Docs](https://react.dev)
- [MDN Web Docs](https://developer.mozilla.org)
- [JavaScript.info](https://javascript.info)

### Ferramentas
- ESLint - Linting
- Prettier - Formatação
- React DevTools - Debug

---

**Lembre-se:** Código é lido muito mais vezes do que é escrito. Priorize clareza!
