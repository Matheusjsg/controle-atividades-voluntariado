# 📐 CONVENÇÕES DE CÓDIGO - PROJETO ABCAA

**Regras obrigatórias para manter consistência no código**

---

## 🌍 IDIOMAS

### ✅ REGRA PRINCIPAL

```
✅ Variáveis, funções, classes, arquivos → INGLÊS
✅ Comentários, documentação → PORTUGUÊS
```

### Exemplos Corretos

```javascript
// ✅ BOM: Variáveis em inglês, comentários em português

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

// Função para aprovar uma atividade
const handleApproveActivity = async (activityId) => {
  // Confirma a ação com o usuário
  if (!window.confirm('Deseja aprovar esta atividade?')) {
    return;
  }
  
  try {
    // Atualiza o status da atividade
    await updateActivityStatus(activityId, 'APPROVED');
    
    // Mostra mensagem de sucesso
    toast.success('Atividade aprovada com sucesso!');
    
    // Recarrega a lista
    loadActivities();
  } catch (error) {
    // Mostra mensagem de erro
    toast.error('Erro ao aprovar atividade');
  }
};
```

### Exemplos Incorretos

```javascript
// ❌ RUIM: Variáveis em português

// Busca todas as atividades do voluntário
const buscarAtividadesVoluntario = async (idVoluntario) => {
  try {
    const resposta = await fetch(`/activity/volunteer/${idVoluntario}`);
    
    if (!resposta.ok) {
      throw new Error('Erro ao buscar atividades');
    }
    
    return await resposta.json();
  } catch (erro) {
    console.error('Erro na requisição:', erro);
    throw erro;
  }
};

const [carregando, setCarregando] = useState(false);
const [atividadesAprovadas, setAtividadesAprovadas] = useState([]);


// ❌ RUIM: Comentários em inglês

// Fetch all volunteer activities
const fetchVolunteerActivities = async (volunteerId) => {
  try {
    // Make API request
    const response = await fetch(`/activity/volunteer/${volunteerId}`);
    
    // Check if response is successful
    if (!response.ok) {
      throw new Error('Error fetching activities');
    }
    
    return await response.json();
  } catch (error) {
    // Log error for debugging
    console.error('Request error:', error);
    throw error;
  }
};
```

---

## 📝 NOMENCLATURA DETALHADA

### Variáveis

```javascript
// ✅ BOM: camelCase em inglês

// Dados do usuário
const userData = { name: 'João', email: 'joao@email.com' };

// Lista de voluntários
const volunteerList = [];

// Status da atividade
const activityStatus = 'PENDING';

// Identificador do departamento
const departmentId = 1;

// Flag de carregamento
const isLoading = false;

// Flag de autenticação
const isAuthenticated = true;

// Contador de atividades
const activityCount = 0;

// Total de horas
const totalHours = 24;


// ❌ RUIM: Português ou snake_case

const dadosUsuario = {};
const lista_voluntarios = [];
const statusAtividade = 'PENDING';
const id_departamento = 1;
```

### Funções

```javascript
// ✅ BOM: camelCase, verbos em inglês

// Carrega as atividades do servidor
const loadActivities = async () => {
  // implementação
};

// Manipula o envio do formulário
const handleSubmit = (event) => {
  // implementação
};

// Valida o email do usuário
const validateEmail = (email) => {
  // implementação
};

// Formata a data para exibição
const formatDate = (date) => {
  // implementação
};

// Verifica se o usuário pode editar
const canEdit = (activity) => {
  // implementação
};

// Calcula o total de horas
const calculateTotalHours = (activities) => {
  // implementação
};


// ❌ RUIM: Português ou nomes não descritivos

const carregarAtividades = async () => {};
const manipularEnvio = (event) => {};
const validar = (email) => {};
const formatar = (date) => {};
const podeEditar = (activity) => {};
const calcular = (activities) => {};
```

### Componentes React

```javascript
// ✅ BOM: PascalCase em inglês

// Formulário de atividade
const ActivityForm = () => {
  return <form>...</form>;
};

// Lista de voluntários
const VolunteerList = ({ volunteers }) => {
  return <ul>...</ul>;
};

// Painel de aprovação
const ApprovalPanel = () => {
  return <div>...</div>;
};

// Card de atividade
const ActivityCard = ({ activity }) => {
  return <div>...</div>;
};

// Barra lateral
const Sidebar = () => {
  return <aside>...</aside>;
};


// ❌ RUIM: Português ou camelCase

const FormularioAtividade = () => {};
const ListaVoluntarios = () => {};
const painelAprovacao = () => {};
const atividadeCard = () => {};
```

### Arquivos

```javascript
// ✅ BOM: PascalCase para componentes, camelCase para outros

// Componentes
ActivityForm.jsx
VolunteerList.jsx
ApprovalPanel.jsx
Sidebar.jsx
Footer.jsx

// Hooks
useAuth.js
usePermissions.js
useVolunteer.js

// Services
authApi.js
activityApi.js
volunteerApi.js

// Utils
formatDate.js
validateCPF.js
permissions.js

// Styles
activityForm.css
volunteerList.css
sidebar.css


// ❌ RUIM: Português ou snake_case

FormularioAtividade.jsx
lista_voluntarios.jsx
painel-aprovacao.jsx
auth_api.js
formatar_data.js
```

### Constantes

```javascript
// ✅ BOM: UPPER_SNAKE_CASE em inglês

// Tipos de usuário
const USER_TYPES = {
  VOLUNTEER: 'VOLUNTEER',
  ADMIN: 'ADMIN'
};

// Status de atividade
const ACTIVITY_STATUS = {
  PENDING: 'PENDING',
  APPROVED: 'APPROVED',
  REJECTED: 'REJECTED'
};

// Duração máxima em minutos
const MAX_DURATION_MINUTES = 720;

// Duração mínima em minutos
const MIN_DURATION_MINUTES = 15;

// Horas mínimas para certificado
const MIN_HOURS_FOR_CERTIFICATE = 20;

// URL base da API
const API_BASE_URL = 'http://localhost:8080';


// ❌ RUIM: Português ou camelCase

const TIPOS_USUARIO = {};
const STATUS_ATIVIDADE = {};
const duracaoMaxima = 720;
const horasMinimas = 20;
```

### Classes CSS

```javascript
// ✅ BOM: kebab-case em inglês

.activity-form { }
.activity-form__title { }
.activity-form__input { }
.activity-form--loading { }

.volunteer-list { }
.volunteer-list__item { }
.volunteer-list__item--active { }

.approval-panel { }
.approval-panel__card { }
.approval-panel__button { }

.btn-primary { }
.btn-secondary { }
.btn-danger { }

.badge-admin { }
.badge-volunteer { }
.badge-pending { }


// ❌ RUIM: Português ou camelCase

.formulario-atividade { }
.listaVoluntarios { }
.painel_aprovacao { }
.botaoPrimario { }
```

---

## 💬 COMENTÁRIOS

### Comentários de Linha

```javascript
// ✅ BOM: Português, explicativo

// Busca os dados do voluntário logado
const currentVolunteer = await fetchVolunteer(userId);

// Filtra apenas as atividades aprovadas
const approved = activities.filter(a => a.status === 'APPROVED');

// Calcula o total de horas trabalhadas
const totalHours = approved.reduce((sum, a) => sum + a.duration, 0);

// Verifica se atingiu o mínimo para certificado
if (totalHours >= MIN_HOURS_FOR_CERTIFICATE) {
  // Habilita o botão de gerar certificado
  setCanGenerateCertificate(true);
}


// ❌ RUIM: Inglês ou óbvio

// Fetch current volunteer
const currentVolunteer = await fetchVolunteer(userId);

// Filter approved
const approved = activities.filter(a => a.status === 'APPROVED');

// Soma as horas
const totalHours = approved.reduce((sum, a) => sum + a.duration, 0);
```

### Comentários de Bloco

```javascript
// ✅ BOM: Português, documentação clara

/**
 * Calcula o total de horas aprovadas de um voluntário em um período.
 * 
 * @param {number} volunteerId - ID do voluntário
 * @param {Date} startDate - Data inicial do período
 * @param {Date} endDate - Data final do período
 * @returns {Promise<number>} Total de horas aprovadas
 * 
 * @example
 * const hours = await calculateApprovedHours(1, '2025-01-01', '2025-12-31');
 * console.log(hours); // 24
 */
const calculateApprovedHours = async (volunteerId, startDate, endDate) => {
  // Busca as atividades do período
  const activities = await fetchActivitiesByPeriod(volunteerId, startDate, endDate);
  
  // Filtra apenas as aprovadas
  const approved = activities.filter(a => a.status === 'APPROVED');
  
  // Converte minutos para horas e soma
  return approved.reduce((sum, a) => sum + (a.durationMinutes / 60), 0);
};


// ❌ RUIM: Inglês ou sem documentação

/**
 * Calculate approved hours
 */
const calculateApprovedHours = async (volunteerId, startDate, endDate) => {
  const activities = await fetchActivitiesByPeriod(volunteerId, startDate, endDate);
  const approved = activities.filter(a => a.status === 'APPROVED');
  return approved.reduce((sum, a) => sum + (a.durationMinutes / 60), 0);
};
```

### Comentários TODO

```javascript
// ✅ BOM: Português, descritivo

// TODO: Adicionar validação de CPF
// TODO: Implementar paginação na lista de atividades
// TODO: Adicionar loading state durante a requisição
// FIXME: Corrigir bug ao deletar atividade aprovada
// HACK: Solução temporária até o backend implementar o endpoint correto
// NOTE: Este componente será refatorado na próxima sprint


// ❌ RUIM: Inglês ou vago

// TODO: Add validation
// TODO: Fix this
// FIXME: Bug here
```

---

## 📋 EXEMPLOS COMPLETOS

### Componente React

```javascript
// src/components/volunteer/ActivityForm.jsx

import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { fetchDepartmentById } from '../../service/departmentApi';
import { toast } from 'react-toastify';
import './activityForm.css';

/**
 * Formulário para registro de atividades de voluntariado.
 * Permite que o voluntário registre suas atividades com data, duração e descrição.
 */
const ActivityForm = ({ onSubmit }) => {
  // Obtém dados do usuário autenticado
  const { token, user } = useAuth();

  // Estado do formulário
  const [formData, setFormData] = useState({
    date: '',
    durationMinutes: '',
    description: '',
    volunteerId: user?.volunteerId || ''
  });

  // Nome do setor do voluntário
  const [departmentName, setDepartmentName] = useState('');

  // Carrega o nome do setor ao montar o componente
  useEffect(() => {
    const loadDepartment = async () => {
      if (user?.departmentId) {
        try {
          const department = await fetchDepartmentById(user.departmentId, token);
          setDepartmentName(department?.name || String(user.departmentId));
        } catch (error) {
          console.error('Erro ao carregar setor:', error);
        }
      }
    };
    
    loadDepartment();
  }, [token, user]);

  // Atualiza o estado quando um campo muda
  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // Valida os campos do formulário
  const validateForm = () => {
    if (!formData.date) {
      toast.error('Por favor, selecione uma data');
      return false;
    }

    if (!formData.durationMinutes) {
      toast.error('Por favor, selecione a duração');
      return false;
    }

    if (!formData.description || formData.description.trim().length < 10) {
      toast.error('Descrição deve ter no mínimo 10 caracteres');
      return false;
    }

    return true;
  };

  // Manipula o envio do formulário
  const handleSubmit = async (event) => {
    event.preventDefault();

    // Valida antes de enviar
    if (!validateForm()) {
      return;
    }

    // Prepara os dados para envio
    const payload = {
      date: formData.date,
      description: formData.description,
      durationMinutes: parseInt(formData.durationMinutes),
      volunteerId: parseInt(formData.volunteerId)
    };

    try {
      // Envia para o componente pai
      await onSubmit(payload);
      
      // Mostra mensagem de sucesso
      toast.success('Atividade registrada com sucesso! 🎉');

      // Limpa o formulário
      setFormData({
        date: '',
        durationMinutes: '',
        description: '',
        volunteerId: user?.volunteerId || ''
      });
    } catch (error) {
      // Mostra mensagem de erro
      toast.error('Erro ao registrar atividade');
      console.error('Erro:', error);
    }
  };

  return (
    <div className="activity-form">
      <h2>Registrar Atividade</h2>

      <form onSubmit={handleSubmit}>
        {/* Campo de voluntário (somente leitura) */}
        <div className="form-group">
          <label>Voluntário</label>
          <input 
            type="text" 
            value={user?.name || ''} 
            disabled 
            className="input-disabled" 
          />
        </div>

        {/* Campo de setor (somente leitura) */}
        <div className="form-group">
          <label>Setor</label>
          <input 
            type="text" 
            value={departmentName} 
            disabled 
            className="input-disabled" 
          />
        </div>

        {/* Campo de data */}
        <div className="form-group">
          <label>Data</label>
          <input 
            type="date" 
            name="date" 
            value={formData.date} 
            onChange={handleChange}
            max={new Date().toISOString().split('T')[0]}
            required
          />
        </div>

        {/* Campo de duração */}
        <div className="form-group">
          <label>Duração</label>
          <select 
            name="durationMinutes" 
            value={formData.durationMinutes} 
            onChange={handleChange}
            required
          >
            <option value="">Selecione</option>
            <option value="30">30min</option>
            <option value="60">1h</option>
            <option value="90">1h30</option>
            <option value="120">2h</option>
            <option value="150">2h30</option>
            <option value="180">3h</option>
            <option value="210">3h30</option>
            <option value="240">4h</option>
            <option value="300">5h</option>
          </select>
        </div>

        {/* Campo de descrição */}
        <div className="form-group">
          <label>Descrição</label>
          <textarea 
            name="description" 
            value={formData.description} 
            onChange={handleChange}
            rows="3"
            placeholder="Descreva a atividade realizada..."
            required
          />
        </div>

        {/* Botão de envio */}
        <button type="submit" className="btn-primary">
          Registrar
        </button>
      </form>
    </div>
  );
};

export default ActivityForm;
```

### Hook Customizado

```javascript
// src/hooks/usePermissions.js

import { useAuth } from '../context/AuthContext';
import { PERMISSIONS } from '../utils/constants';

/**
 * Hook para verificar permissões do usuário.
 * Retorna funções que verificam se o usuário tem permissão para realizar ações específicas.
 * 
 * @returns {Object} Objeto com funções de verificação de permissão
 * 
 * @example
 * const { canApproveActivity, canEditOwnActivity } = usePermissions();
 * 
 * if (canApproveActivity()) {
 *   // Mostra botão de aprovar
 * }
 */
export const usePermissions = () => {
  // Obtém dados do usuário autenticado
  const { user } = useAuth();

  /**
   * Verifica se o usuário tem uma permissão específica.
   * 
   * @param {string} permission - Nome da permissão a verificar
   * @returns {boolean} true se tem permissão, false caso contrário
   */
  const hasPermission = (permission) => {
    if (!user || !user.userType) {
      return false;
    }
    
    return PERMISSIONS[permission]?.includes(user.userType) || false;
  };

  /**
   * Verifica se o usuário pode visualizar seu próprio perfil.
   */
  const canViewOwnProfile = () => {
    return hasPermission('VIEW_OWN_PROFILE');
  };

  /**
   * Verifica se o usuário pode editar seu próprio perfil.
   */
  const canEditOwnProfile = () => {
    return hasPermission('EDIT_OWN_PROFILE');
  };

  /**
   * Verifica se o usuário pode visualizar todos os perfis.
   */
  const canViewAllProfiles = () => {
    return hasPermission('VIEW_ALL_PROFILES');
  };

  /**
   * Verifica se o usuário pode criar atividades.
   */
  const canCreateActivity = () => {
    return hasPermission('CREATE_OWN_ACTIVITY');
  };

  /**
   * Verifica se o usuário pode editar uma atividade específica.
   * Regras:
   * - Deve ser o dono da atividade
   * - Status deve ser PENDING
   * - Deve ter permissão EDIT_OWN_ACTIVITY
   * 
   * @param {Object} activity - Objeto da atividade
   * @returns {boolean} true se pode editar, false caso contrário
   */
  const canEditOwnActivity = (activity) => {
    return (
      hasPermission('EDIT_OWN_ACTIVITY') && 
      activity.activityStatus === 'PENDING' &&
      activity.volunteerId === user.volunteerId
    );
  };

  /**
   * Verifica se o usuário pode deletar uma atividade específica.
   * Regras:
   * - Deve ser o dono da atividade
   * - Status deve ser PENDING
   * - Deve ter permissão DELETE_OWN_ACTIVITY
   * 
   * @param {Object} activity - Objeto da atividade
   * @returns {boolean} true se pode deletar, false caso contrário
   */
  const canDeleteOwnActivity = (activity) => {
    return (
      hasPermission('DELETE_OWN_ACTIVITY') && 
      activity.activityStatus === 'PENDING' &&
      activity.volunteerId === user.volunteerId
    );
  };

  /**
   * Verifica se o usuário pode aprovar atividades.
   */
  const canApproveActivity = () => {
    return hasPermission('APPROVE_ACTIVITY');
  };

  /**
   * Verifica se o usuário pode rejeitar atividades.
   */
  const canRejectActivity = () => {
    return hasPermission('REJECT_ACTIVITY');
  };

  // Retorna todas as funções de verificação
  return {
    hasPermission,
    canViewOwnProfile,
    canEditOwnProfile,
    canViewAllProfiles,
    canCreateActivity,
    canEditOwnActivity,
    canDeleteOwnActivity,
    canApproveActivity,
    canRejectActivity
  };
};
```

---

## ✅ CHECKLIST DE REVISÃO

Antes de fazer commit, verifique:

```
□ Todas as variáveis estão em inglês
□ Todas as funções estão em inglês
□ Todos os componentes estão em inglês
□ Todos os arquivos estão em inglês
□ Todas as classes CSS estão em inglês
□ Todos os comentários estão em português
□ Comentários explicam "por quê", não "o quê"
□ Nomes são descritivos e claros
□ Sem abreviações confusas
□ Sem código comentado
```

---

## 🚫 ERROS COMUNS

### Misturar Idiomas

```javascript
// ❌ RUIM: Mistura de inglês e português
const buscarActivities = async () => {};
const fetchAtividades = async () => {};
const userDados = {};
const dadosUser = {};

// ✅ BOM: Tudo em inglês
const fetchActivities = async () => {};
const userData = {};
```

### Abreviações Confusas

```javascript
// ❌ RUIM: Abreviações não claras
const vol = {};
const act = {};
const dept = {};
const btn = document.querySelector('.btn');

// ✅ BOM: Nomes completos
const volunteer = {};
const activity = {};
const department = {};
const button = document.querySelector('.btn');
```

### Comentários Óbvios

```javascript
// ❌ RUIM: Comentário óbvio
// Incrementa o contador
counter++;

// Define loading como true
setLoading(true);

// ✅ BOM: Comentário útil
// Incrementa o contador para forçar re-renderização do componente
counter++;

// Ativa loading para prevenir múltiplos cliques durante a requisição
setLoading(true);
```

---

**Lembre-se:** Consistência é mais importante que perfeição. Siga estas convenções em TODO o código!
