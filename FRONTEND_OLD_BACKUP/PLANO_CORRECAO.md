# 🛠️ PLANO DE CORREÇÃO - FRONTEND ABCAA

**Objetivo:** Corrigir incompatibilidades entre Frontend e Backend  
**Tempo Estimado:** 4-6 horas  
**Prioridade:** 🔴 CRÍTICA

---

## 📋 FASE 1: CORREÇÕES CRÍTICAS (1-2 horas)

### ✅ Tarefa 1.1: Corrigir AtividadeForm.jsx
**Problema:** Campo `departmentId` sendo enviado incorretamente  
**Arquivo:** `src/components/AtividadeForm.jsx`  
**Tempo:** 15 minutos

**Mudanças necessárias:**

1. Remover `departmentId` do estado inicial
2. Remover `departmentId` do payload de envio
3. Manter apenas para exibição visual

**Código a modificar:**
```javascript
// LINHA ~10 - Estado inicial
const [atividade, setAtividade] = useState({
  date: "",
  durationMinutes: "",
  description: "",
  volunteerId: user?.volunteerId || "",
  // departmentId: user?.departmentId || "" // ❌ REMOVER ESTA LINHA
})

// LINHA ~40 - Payload
const payload = {
  date: atividade.date,
  description: atividade.description,
  durationMinutes: parseInt(atividade.durationMinutes),
  volunteerId: parseInt(atividade.volunteerId),
  // departmentId: parseInt(atividade.departmentId) // ❌ REMOVER ESTA LINHA
}

// LINHA ~55 - Reset do formulário
setAtividade({
  date: "",
  durationMinutes: "",
  description: "",
  volunteerId: user?.volunteerId || "",
  // departmentId: user?.departmentId || "" // ❌ REMOVER ESTA LINHA
})
```

---

### ✅ Tarefa 1.2: Deletar api.js duplicado
**Problema:** Arquivo legado causando confusão  
**Arquivo:** `src/service/api.js`  
**Tempo:** 5 minutos

**Ação:**
```bash
rm src/service/api.js
```

**Verificar:** Nenhum componente deve importar de `api.js`, apenas de `authApi.js`

---

### ✅ Tarefa 1.3: Adicionar validação HTTP em authApi.js
**Problema:** Falta de verificação `response.ok`  
**Arquivo:** `src/service/authApi.js`  
**Tempo:** 45 minutos

**Funções a corrigir:**
1. `fetchAtividades`
2. `fetchAtividadeById`
3. `fetchAtividadesByVoluntario`
4. `fetchAtividadesByStatus`
5. `atualizarAtividade`
6. `fetchSetores`
7. `fetchSetorById`
8. `criarSetor`
9. `atualizarSetor`
10. `fetchVoluntarios`
11. `fetchVoluntarioById`
12. `criarVoluntario`
13. `atualizarVoluntario`
14. `alterarTipoVoluntario`
15. `fetchRelatorio`

**Padrão de correção:**
```javascript
// ANTES
export const fetchAtividades = async (token) => {
  const response = await fetch(`${atividadeAPI}/listAll`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  return await response.json();
};

// DEPOIS
export const fetchAtividades = async (token) => {
  const response = await fetch(`${atividadeAPI}/listAll`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Erro ${response.status}: ${errorText}`);
  }
  return await response.json();
};
```

---

## 📋 FASE 2: MELHORIAS DE UX (1-2 horas)

### ✅ Tarefa 2.1: Substituir alert() por sistema de notificações
**Problema:** Alerts nativos são ruins para UX  
**Tempo:** 1 hora

**Instalar biblioteca:**
```bash
npm install react-toastify
```

**Configurar:**
```javascript
// src/main.jsx
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

// Adicionar no render
<ToastContainer position="top-right" autoClose={3000} />
```

**Substituir em todos os componentes:**
```javascript
// ANTES
alert("Atividade registrada com sucesso! 🎉")

// DEPOIS
import { toast } from 'react-toastify';
toast.success("Atividade registrada com sucesso! 🎉")
```

**Arquivos a modificar:**
- `src/components/AtividadeForm.jsx`
- `src/components/SetorForm.jsx`
- `src/components/VoluntarioForm.jsx`
- `src/components/AtividadeList.jsx`
- `src/components/SetorList.jsx`
- `src/components/VoluntarioList.jsx`
- `src/pages/Aprovacoes.jsx`
- `src/pages/Relatorios.jsx`
- `src/pages/Login.jsx`
- `src/pages/Register.jsx`

---

### ✅ Tarefa 2.2: Adicionar loading states globais
**Problema:** Usuário não sabe quando requisição está em andamento  
**Tempo:** 30 minutos

**Criar componente de Loading:**
```javascript
// src/components/Loading.jsx
const Loading = ({ message = "Carregando..." }) => (
  <div className="loading-overlay">
    <div className="spinner"></div>
    <p>{message}</p>
  </div>
);
```

**Adicionar CSS:**
```css
/* src/styles/loading.css */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
```

---

### ✅ Tarefa 2.3: Melhorar validações de formulário
**Problema:** Validações básicas, mensagens genéricas  
**Tempo:** 30 minutos

**Exemplo para AtividadeForm:**
```javascript
const validarFormulario = () => {
  if (!atividade.date) {
    toast.error("Por favor, selecione uma data");
    return false;
  }
  
  if (!atividade.durationMinutes) {
    toast.error("Por favor, selecione a duração");
    return false;
  }
  
  if (parseInt(atividade.durationMinutes) < 15) {
    toast.error("Duração mínima: 15 minutos");
    return false;
  }
  
  if (!atividade.description || atividade.description.trim().length < 10) {
    toast.error("Descrição deve ter no mínimo 10 caracteres");
    return false;
  }
  
  return true;
};

const handleSubmit = (e) => {
  e.preventDefault();
  
  if (!validarFormulario()) return;
  
  // ... resto do código
};
```

---

## 📋 FASE 3: TESTES E VALIDAÇÃO (1-2 horas)

### ✅ Tarefa 3.1: Testar fluxo completo de autenticação
**Tempo:** 20 minutos

**Checklist:**
- [ ] Registrar novo usuário
- [ ] Fazer login
- [ ] Verificar token no localStorage
- [ ] Verificar redirecionamento para dashboard
- [ ] Fazer logout
- [ ] Verificar limpeza do localStorage
- [ ] Tentar acessar rota protegida sem login

---

### ✅ Tarefa 3.2: Testar CRUD de Atividades
**Tempo:** 30 minutos

**Checklist:**
- [ ] Criar atividade (verificar payload sem departmentId)
- [ ] Listar todas as atividades
- [ ] Filtrar por status (PENDING, APPROVED, REJECTED)
- [ ] Atualizar atividade
- [ ] Deletar atividade
- [ ] Verificar mensagens de sucesso/erro

---

### ✅ Tarefa 3.3: Testar CRUD de Voluntários
**Tempo:** 20 minutos

**Checklist:**
- [ ] Criar voluntário
- [ ] Listar voluntários
- [ ] Verificar badge de tipo (ADMIN/VOLUNTEER)
- [ ] Deletar voluntário
- [ ] Tentar deletar voluntário com atividades (deve dar erro)

---

### ✅ Tarefa 3.4: Testar CRUD de Setores
**Tempo:** 15 minutos

**Checklist:**
- [ ] Criar setor
- [ ] Listar setores
- [ ] Deletar setor
- [ ] Tentar deletar setor com voluntários (deve dar erro)

---

### ✅ Tarefa 3.5: Testar Sistema de Aprovação (ADMIN)
**Tempo:** 20 minutos

**Checklist:**
- [ ] Fazer login como ADMIN
- [ ] Acessar página de aprovações
- [ ] Ver atividades pendentes
- [ ] Aprovar atividade
- [ ] Rejeitar atividade
- [ ] Verificar atualização da lista

---

### ✅ Tarefa 3.6: Testar Relatórios e Certificados
**Tempo:** 25 minutos

**Checklist:**
- [ ] Selecionar voluntário
- [ ] Escolher período
- [ ] Gerar relatório
- [ ] Verificar estatísticas
- [ ] Verificar lista de atividades
- [ ] Tentar gerar certificado com < 20h (deve dar erro)
- [ ] Gerar certificado com >= 20h
- [ ] Verificar download do PDF

---

## 📋 FASE 4: DOCUMENTAÇÃO E DEPLOY (30 minutos)

### ✅ Tarefa 4.1: Atualizar documentação
**Tempo:** 15 minutos

**Arquivos a atualizar:**
- [ ] `README.md` - Adicionar seção de problemas conhecidos resolvidos
- [ ] `CHANGELOG.md` - Documentar todas as correções
- [ ] `CHECKLIST.md` - Marcar itens corrigidos

---

### ✅ Tarefa 4.2: Preparar para deploy
**Tempo:** 15 minutos

**Checklist:**
- [ ] Atualizar `.env` para produção
- [ ] Fazer build: `npm run build`
- [ ] Testar build localmente: `npm run preview`
- [ ] Verificar tamanho do bundle
- [ ] Commit das alterações
- [ ] Push para repositório

---

## 📊 RESUMO DO PLANO

| Fase | Tarefas | Tempo Estimado | Prioridade |
|------|---------|----------------|------------|
| Fase 1 | Correções Críticas | 1-2 horas | 🔴 CRÍTICA |
| Fase 2 | Melhorias de UX | 1-2 horas | 🟡 ALTA |
| Fase 3 | Testes e Validação | 1-2 horas | 🟡 ALTA |
| Fase 4 | Documentação | 30 minutos | 🟢 MÉDIA |

**Total:** 4-6 horas

---

## 🎯 ORDEM DE EXECUÇÃO RECOMENDADA

1. ✅ **Tarefa 1.1** - Corrigir AtividadeForm.jsx (CRÍTICO)
2. ✅ **Tarefa 1.2** - Deletar api.js (CRÍTICO)
3. ✅ **Tarefa 1.3** - Validação HTTP em authApi.js (CRÍTICO)
4. ✅ **Tarefa 3.2** - Testar CRUD de Atividades (VALIDAR CORREÇÃO)
5. ✅ **Tarefa 2.1** - Sistema de notificações (MELHORIA)
6. ✅ **Tarefa 2.2** - Loading states (MELHORIA)
7. ✅ **Tarefa 2.3** - Validações de formulário (MELHORIA)
8. ✅ **Tarefa 3.1-3.6** - Testes completos (VALIDAÇÃO)
9. ✅ **Tarefa 4.1-4.2** - Documentação e deploy (FINALIZAÇÃO)

---

## 🚀 COMEÇAR AGORA

**Próximo comando:**
```bash
# Abrir AtividadeForm.jsx e fazer as correções
code src/components/AtividadeForm.jsx
```

**Ou solicitar:**
"Quero começar pelas correções críticas. Corrija o AtividadeForm.jsx primeiro."

---

## 📝 NOTAS IMPORTANTES

1. **Backup:** Fazer commit antes de começar as correções
2. **Testes:** Testar cada correção antes de passar para a próxima
3. **Documentação:** Documentar cada mudança no CHANGELOG.md
4. **Comunicação:** Informar equipe sobre mudanças críticas

---

**Status:** 📋 PLANO PRONTO PARA EXECUÇÃO  
**Aguardando:** Confirmação para iniciar correções
