# 🏗️ ARQUITETURA DE PERMISSÕES - SISTEMA ABCAA

**Objetivo:** Estruturar o sistema com base em permissões (VOLUNTEER vs ADMIN)  
**Padrão:** Role-Based Access Control (RBAC)

---

## 🎭 DEFINIÇÃO DE ROLES

### 👤 VOLUNTEER (Voluntário)
**Pode:**
- ✅ Ver e editar **seu próprio perfil**
- ✅ Criar **suas próprias atividades**
- ✅ Ver **suas próprias atividades**
- ✅ Editar **suas próprias atividades** (apenas se status = PENDING)
- ✅ Deletar **suas próprias atividades** (apenas se status = PENDING)
- ✅ Ver **seu próprio relatório**
- ✅ Gerar **seu próprio certificado**

**NÃO pode:**
- ❌ Ver perfis de outros voluntários
- ❌ Ver atividades de outros voluntários
- ❌ Aprovar/Rejeitar atividades
- ❌ Criar/Editar/Deletar setores
- ❌ Criar/Editar/Deletar outros voluntários
- ❌ Alterar tipo de usuário

### 👨‍💼 ADMIN (Administrador)
**Pode:**
- ✅ **TUDO que o VOLUNTEER pode** (para si mesmo)
- ✅ Ver **todos os perfis** de voluntários
- ✅ Ver **todas as atividades** de todos os voluntários
- ✅ **Aprovar/Rejeitar** atividades
- ✅ Criar/Editar/Deletar **setores**
- ✅ Criar/Editar/Deletar **voluntários**
- ✅ Alterar **tipo de usuário** (VOLUNTEER ↔ ADMIN)
- ✅ Ver **relatórios de qualquer voluntário**
- ✅ Gerar **certificados de qualquer voluntário**

---

## 📁 ESTRUTURA DE PASTAS RECOMENDADA

```
src/
├── components/
│   ├── common/              # Componentes compartilhados
│   │   ├── Sidebar.jsx
│   │   ├── Footer.jsx
│   │   ├── Loading.jsx
│   │   └── PrivateRoute.jsx
│   │
│   ├── volunteer/           # Componentes específicos do VOLUNTEER
│   │   ├── MyProfile.jsx
│   │   ├── MyActivities.jsx
│   │   ├── ActivityForm.jsx
│   │   └── MyReports.jsx
│   │
│   └── admin/               # Componentes específicos do ADMIN
│       ├── AllVolunteers.jsx
│       ├── AllActivities.jsx
│       ├── ApprovalPanel.jsx
│       ├── DepartmentManager.jsx
│       └── ReportsManager.jsx
│
├── pages/
│   ├── auth/
│   │   ├── Login.jsx
│   │   └── Register.jsx
│   │
│   ├── volunteer/           # Páginas do VOLUNTEER
│   │   ├── Dashboard.jsx
│   │   ├── Profile.jsx
│   │   ├── Activities.jsx
│   │   └── Reports.jsx
│   │
│   └── admin/               # Páginas do ADMIN
│       ├── Dashboard.jsx
│       ├── Volunteers.jsx
│       ├── Approvals.jsx
│       ├── Departments.jsx
│       └── AllReports.jsx
│
├── context/
│   └── AuthContext.jsx      # Gerencia autenticação e permissões
│
├── hooks/
│   ├── useAuth.js           # Hook de autenticação
│   ├── usePermissions.js    # Hook de permissões
│   └── useVolunteer.js      # Hook para dados do voluntário
│
├── service/
│   ├── authApi.js           # Autenticação
│   ├── volunteerApi.js      # Operações de voluntário
│   ├── activityApi.js       # Operações de atividade
│   ├── departmentApi.js     # Operações de setor
│   └── reportApi.js         # Relatórios e certificados
│
└── utils/
    ├── permissions.js       # Funções de verificação de permissão
    └── constants.js         # Constantes (ROLES, STATUS, etc)
```

---

## 🗺️ MAPA DE ROTAS

### Rotas Públicas
```javascript
/login              → Login.jsx
/register           → Register.jsx
```

### Rotas do VOLUNTEER
```javascript
/volunteer/dashboard    → volunteer/Dashboard.jsx
/volunteer/profile      → volunteer/Profile.jsx (MEU perfil)
/volunteer/activities   → volunteer/Activities.jsx (MINHAS atividades)
/volunteer/reports      → volunteer/Reports.jsx (MEU relatório)
```

### Rotas do ADMIN
```javascript
/admin/dashboard        → admin/Dashboard.jsx
/admin/profile          → volunteer/Profile.jsx (MEU perfil - reutiliza)
/admin/volunteers       → admin/Volunteers.jsx (TODOS os voluntários)
/admin/activities       → admin/AllActivities.jsx (TODAS as atividades)
/admin/approvals        → admin/Approvals.jsx (Aprovar/Rejeitar)
/admin/departments      → admin/Departments.jsx (Gerenciar setores)
/admin/reports          → admin/AllReports.jsx (Relatórios de TODOS)
```

---

## 🔐 IMPLEMENTAÇÃO DE PERMISSÕES

### 1. Constantes de Permissões

```javascript
// src/utils/constants.js

export const USER_ROLES = {
  VOLUNTEER: 'VOLUNTEER',
  ADMIN: 'ADMIN'
};

export const ACTIVITY_STATUS = {
  PENDING: 'PENDING',
  APPROVED: 'APPROVED',
  REJECTED: 'REJECTED'
};

export const PERMISSIONS = {
  // Perfil
  VIEW_OWN_PROFILE: ['VOLUNTEER', 'ADMIN'],
  EDIT_OWN_PROFILE: ['VOLUNTEER', 'ADMIN'],
  VIEW_ALL_PROFILES: ['ADMIN'],
  
  // Atividades
  CREATE_OWN_ACTIVITY: ['VOLUNTEER', 'ADMIN'],
  VIEW_OWN_ACTIVITIES: ['VOLUNTEER', 'ADMIN'],
  EDIT_OWN_ACTIVITY: ['VOLUNTEER', 'ADMIN'],
  DELETE_OWN_ACTIVITY: ['VOLUNTEER', 'ADMIN'],
  VIEW_ALL_ACTIVITIES: ['ADMIN'],
  APPROVE_ACTIVITY: ['ADMIN'],
  REJECT_ACTIVITY: ['ADMIN'],
  
  // Relatórios
  VIEW_OWN_REPORT: ['VOLUNTEER', 'ADMIN'],
  GENERATE_OWN_CERTIFICATE: ['VOLUNTEER', 'ADMIN'],
  VIEW_ALL_REPORTS: ['ADMIN'],
  GENERATE_ANY_CERTIFICATE: ['ADMIN'],
  
  // Setores
  VIEW_DEPARTMENTS: ['VOLUNTEER', 'ADMIN'],
  MANAGE_DEPARTMENTS: ['ADMIN'],
  
  // Voluntários
  MANAGE_VOLUNTEERS: ['ADMIN'],
  CHANGE_USER_TYPE: ['ADMIN']
};
```

### 2. Hook de Permissões

```javascript
// src/hooks/usePermissions.js

import { useAuth } from '../context/AuthContext';
import { PERMISSIONS } from '../utils/constants';

export const usePermissions = () => {
  const { user } = useAuth();

  const hasPermission = (permission) => {
    if (!user || !user.userType) return false;
    return PERMISSIONS[permission]?.includes(user.userType) || false;
  };

  const canViewOwnProfile = () => hasPermission('VIEW_OWN_PROFILE');
  const canEditOwnProfile = () => hasPermission('EDIT_OWN_PROFILE');
  const canViewAllProfiles = () => hasPermission('VIEW_ALL_PROFILES');
  
  const canCreateActivity = () => hasPermission('CREATE_OWN_ACTIVITY');
  const canViewOwnActivities = () => hasPermission('VIEW_OWN_ACTIVITIES');
  const canEditOwnActivity = (activity) => {
    return hasPermission('EDIT_OWN_ACTIVITY') && 
           activity.activityStatus === 'PENDING' &&
           activity.volunteerId === user.volunteerId;
  };
  const canDeleteOwnActivity = (activity) => {
    return hasPermission('DELETE_OWN_ACTIVITY') && 
           activity.activityStatus === 'PENDING' &&
           activity.volunteerId === user.volunteerId;
  };
  const canViewAllActivities = () => hasPermission('VIEW_ALL_ACTIVITIES');
  const canApproveActivity = () => hasPermission('APPROVE_ACTIVITY');
  const canRejectActivity = () => hasPermission('REJECT_ACTIVITY');
  
  const canViewOwnReport = () => hasPermission('VIEW_OWN_REPORT');
  const canGenerateOwnCertificate = () => hasPermission('GENERATE_OWN_CERTIFICATE');
  const canViewAllReports = () => hasPermission('VIEW_ALL_REPORTS');
  const canGenerateAnyCertificate = () => hasPermission('GENERATE_ANY_CERTIFICATE');
  
  const canManageDepartments = () => hasPermission('MANAGE_DEPARTMENTS');
  const canManageVolunteers = () => hasPermission('MANAGE_VOLUNTEERS');
  const canChangeUserType = () => hasPermission('CHANGE_USER_TYPE');

  return {
    hasPermission,
    canViewOwnProfile,
    canEditOwnProfile,
    canViewAllProfiles,
    canCreateActivity,
    canViewOwnActivities,
    canEditOwnActivity,
    canDeleteOwnActivity,
    canViewAllActivities,
    canApproveActivity,
    canRejectActivity,
    canViewOwnReport,
    canGenerateOwnCertificate,
    canViewAllReports,
    canGenerateAnyCertificate,
    canManageDepartments,
    canManageVolunteers,
    canChangeUserType
  };
};
```

### 3. Componente de Rota Protegida Melhorado

```javascript
// src/components/common/PrivateRoute.jsx

import { Navigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { usePermissions } from '../../hooks/usePermissions';

const PrivateRoute = ({ children, requiredPermission, adminOnly = false }) => {
  const { isAuthenticated, user, loading } = useAuth();
  const { hasPermission } = usePermissions();

  if (loading) {
    return <div className="loading">Carregando...</div>;
  }

  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }

  // Verificação por role (legado, manter compatibilidade)
  if (adminOnly && user?.userType !== 'ADMIN') {
    return <Navigate to="/volunteer/dashboard" replace />;
  }

  // Verificação por permissão específica (novo)
  if (requiredPermission && !hasPermission(requiredPermission)) {
    return <Navigate to="/volunteer/dashboard" replace />;
  }

  return children;
};

export default PrivateRoute;
```

### 4. Configuração de Rotas

```javascript
// src/main.jsx

import { createBrowserRouter } from 'react-router-dom';
import PrivateRoute from './components/common/PrivateRoute';

// Auth
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';

// Volunteer
import VolunteerDashboard from './pages/volunteer/Dashboard';
import VolunteerProfile from './pages/volunteer/Profile';
import VolunteerActivities from './pages/volunteer/Activities';
import VolunteerReports from './pages/volunteer/Reports';

// Admin
import AdminDashboard from './pages/admin/Dashboard';
import AdminVolunteers from './pages/admin/Volunteers';
import AdminApprovals from './pages/admin/Approvals';
import AdminDepartments from './pages/admin/Departments';
import AdminReports from './pages/admin/AllReports';

const router = createBrowserRouter([
  // Rotas Públicas
  { path: "/login", element: <Login /> },
  { path: "/register", element: <Register /> },
  
  // Redirect raiz baseado no role
  { 
    path: "/", 
    element: <PrivateRoute><RoleBasedRedirect /></PrivateRoute>
  },
  
  // Rotas do VOLUNTEER
  { 
    path: "/volunteer/dashboard", 
    element: <PrivateRoute><VolunteerDashboard /></PrivateRoute>
  },
  { 
    path: "/volunteer/profile", 
    element: <PrivateRoute requiredPermission="VIEW_OWN_PROFILE">
      <VolunteerProfile />
    </PrivateRoute>
  },
  { 
    path: "/volunteer/activities", 
    element: <PrivateRoute requiredPermission="VIEW_OWN_ACTIVITIES">
      <VolunteerActivities />
    </PrivateRoute>
  },
  { 
    path: "/volunteer/reports", 
    element: <PrivateRoute requiredPermission="VIEW_OWN_REPORT">
      <VolunteerReports />
    </PrivateRoute>
  },
  
  // Rotas do ADMIN
  { 
    path: "/admin/dashboard", 
    element: <PrivateRoute adminOnly={true}><AdminDashboard /></PrivateRoute>
  },
  { 
    path: "/admin/profile", 
    element: <PrivateRoute adminOnly={true}><VolunteerProfile /></PrivateRoute>
  },
  { 
    path: "/admin/volunteers", 
    element: <PrivateRoute requiredPermission="MANAGE_VOLUNTEERS">
      <AdminVolunteers />
    </PrivateRoute>
  },
  { 
    path: "/admin/approvals", 
    element: <PrivateRoute requiredPermission="APPROVE_ACTIVITY">
      <AdminApprovals />
    </PrivateRoute>
  },
  { 
    path: "/admin/departments", 
    element: <PrivateRoute requiredPermission="MANAGE_DEPARTMENTS">
      <AdminDepartments />
    </PrivateRoute>
  },
  { 
    path: "/admin/reports", 
    element: <PrivateRoute requiredPermission="VIEW_ALL_REPORTS">
      <AdminReports />
    </PrivateRoute>
  },
]);

// Componente auxiliar para redirecionar baseado no role
const RoleBasedRedirect = () => {
  const { user } = useAuth();
  
  if (user?.userType === 'ADMIN') {
    return <Navigate to="/admin/dashboard" replace />;
  }
  
  return <Navigate to="/volunteer/dashboard" replace />;
};
```

---

## 🎨 SIDEBAR DINÂMICA

```javascript
// src/components/common/Sidebar.jsx

import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { usePermissions } from '../../hooks/usePermissions';
import { 
  User, Activity, FileText, Award, 
  Users, CheckSquare, Briefcase, BarChart 
} from 'lucide-react';

const Sidebar = () => {
  const { user, logout } = useAuth();
  const {
    canViewOwnProfile,
    canViewOwnActivities,
    canViewOwnReport,
    canViewAllProfiles,
    canViewAllActivities,
    canApproveActivity,
    canManageDepartments,
    canViewAllReports
  } = usePermissions();

  const isAdmin = user?.userType === 'ADMIN';
  const baseRoute = isAdmin ? '/admin' : '/volunteer';

  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <h2>{isAdmin ? 'Admin' : 'Voluntário'}</h2>
        <p>{user?.name}</p>
      </div>

      <nav className="sidebar-nav">
        {/* Dashboard - Todos */}
        <Link to={`${baseRoute}/dashboard`} className="nav-item">
          <BarChart size={20} />
          <span>Dashboard</span>
        </Link>

        {/* Meu Perfil - Todos */}
        {canViewOwnProfile() && (
          <Link to={`${baseRoute}/profile`} className="nav-item">
            <User size={20} />
            <span>Meu Perfil</span>
          </Link>
        )}

        {/* Minhas Atividades - Todos */}
        {canViewOwnActivities() && (
          <Link to={`${baseRoute}/activities`} className="nav-item">
            <Activity size={20} />
            <span>Minhas Atividades</span>
          </Link>
        )}

        {/* Meus Relatórios - Todos */}
        {canViewOwnReport() && (
          <Link to={`${baseRoute}/reports`} className="nav-item">
            <FileText size={20} />
            <span>Meus Relatórios</span>
          </Link>
        )}

        {/* Separador - Apenas Admin */}
        {isAdmin && <div className="nav-separator">Administração</div>}

        {/* Voluntários - Apenas Admin */}
        {canViewAllProfiles() && (
          <Link to="/admin/volunteers" className="nav-item">
            <Users size={20} />
            <span>Voluntários</span>
          </Link>
        )}

        {/* Aprovações - Apenas Admin */}
        {canApproveActivity() && (
          <Link to="/admin/approvals" className="nav-item">
            <CheckSquare size={20} />
            <span>Aprovações</span>
          </Link>
        )}

        {/* Setores - Apenas Admin */}
        {canManageDepartments() && (
          <Link to="/admin/departments" className="nav-item">
            <Briefcase size={20} />
            <span>Setores</span>
          </Link>
        )}

        {/* Todos os Relatórios - Apenas Admin */}
        {canViewAllReports() && (
          <Link to="/admin/reports" className="nav-item">
            <Award size={20} />
            <span>Todos os Relatórios</span>
          </Link>
        )}
      </nav>

      <button onClick={logout} className="logout-btn">
        Sair
      </button>
    </aside>
  );
};

export default Sidebar;
```

---

## 📄 EXEMPLO: Página de Atividades do VOLUNTEER

```javascript
// src/pages/volunteer/Activities.jsx

import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { usePermissions } from '../../hooks/usePermissions';
import { 
  fetchAtividadesByVoluntario, 
  registrarAtividade,
  deletarAtividade 
} from '../../service/activityApi';
import Sidebar from '../../components/common/Sidebar';
import Footer from '../../components/common/Footer';
import ActivityForm from '../../components/volunteer/ActivityForm';
import { toast } from 'react-toastify';

const VolunteerActivities = () => {
  const { user, token } = useAuth();
  const { canCreateActivity, canDeleteOwnActivity } = usePermissions();
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadActivities();
  }, []);

  const loadActivities = async () => {
    try {
      setLoading(true);
      // Busca APENAS as atividades do voluntário logado
      const data = await fetchAtividadesByVoluntario(user.volunteerId, token);
      setActivities(data);
    } catch (error) {
      toast.error('Erro ao carregar atividades');
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (activityData) => {
    try {
      await registrarAtividade(activityData, token);
      toast.success('Atividade registrada com sucesso!');
      loadActivities();
    } catch (error) {
      toast.error('Erro ao registrar atividade');
    }
  };

  const handleDelete = async (id, activity) => {
    if (!canDeleteOwnActivity(activity)) {
      toast.error('Você só pode deletar atividades pendentes');
      return;
    }

    if (!window.confirm('Deseja deletar esta atividade?')) return;

    try {
      await deletarAtividade(id, token);
      toast.success('Atividade deletada!');
      loadActivities();
    } catch (error) {
      toast.error('Erro ao deletar atividade');
    }
  };

  return (
    <div className="container">
      <div className="content">
        <h1>Minhas Atividades</h1>

        {canCreateActivity() && (
          <ActivityForm onSubmit={handleCreate} />
        )}

        {loading ? (
          <div className="loading">Carregando...</div>
        ) : (
          <div className="activities-list">
            {activities.length === 0 ? (
              <p>Você ainda não tem atividades registradas.</p>
            ) : (
              activities.map(activity => (
                <div key={activity.id} className="activity-card">
                  <h3>{activity.description}</h3>
                  <p>Data: {new Date(activity.date).toLocaleDateString()}</p>
                  <p>Duração: {activity.durationMinutes} minutos</p>
                  <p>Status: {activity.activityStatus}</p>
                  
                  {canDeleteOwnActivity(activity) && (
                    <button onClick={() => handleDelete(activity.id, activity)}>
                      Deletar
                    </button>
                  )}
                </div>
              ))
            )}
          </div>
        )}
      </div>

      <Sidebar />
      <Footer />
    </div>
  );
};

export default VolunteerActivities;
```

---

## 📄 EXEMPLO: Página de Aprovações do ADMIN

```javascript
// src/pages/admin/Approvals.jsx

import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { usePermissions } from '../../hooks/usePermissions';
import { 
  fetchAtividadesByStatus, 
  atualizarStatusAtividade 
} from '../../service/activityApi';
import Sidebar from '../../components/common/Sidebar';
import Footer from '../../components/common/Footer';
import { toast } from 'react-toastify';

const AdminApprovals = () => {
  const { token } = useAuth();
  const { canApproveActivity, canRejectActivity } = usePermissions();
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadPendingActivities();
  }, []);

  const loadPendingActivities = async () => {
    try {
      setLoading(true);
      // Busca TODAS as atividades pendentes
      const data = await fetchAtividadesByStatus('PENDING', token);
      setActivities(data);
    } catch (error) {
      toast.error('Erro ao carregar atividades');
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async (id) => {
    if (!canApproveActivity()) {
      toast.error('Você não tem permissão para aprovar');
      return;
    }

    try {
      await atualizarStatusAtividade(id, 'APPROVED', token);
      toast.success('Atividade aprovada!');
      loadPendingActivities();
    } catch (error) {
      toast.error('Erro ao aprovar atividade');
    }
  };

  const handleReject = async (id) => {
    if (!canRejectActivity()) {
      toast.error('Você não tem permissão para rejeitar');
      return;
    }

    try {
      await atualizarStatusAtividade(id, 'REJECTED', token);
      toast.success('Atividade rejeitada!');
      loadPendingActivities();
    } catch (error) {
      toast.error('Erro ao rejeitar atividade');
    }
  };

  return (
    <div className="container">
      <div className="content">
        <h1>Aprovação de Atividades</h1>
        <p>{activities.length} atividades pendentes</p>

        {loading ? (
          <div className="loading">Carregando...</div>
        ) : (
          <div className="approvals-grid">
            {activities.length === 0 ? (
              <p>Nenhuma atividade pendente!</p>
            ) : (
              activities.map(activity => (
                <div key={activity.id} className="approval-card">
                  <h3>{activity.volunteerName}</h3>
                  <p>{activity.description}</p>
                  <p>Data: {new Date(activity.date).toLocaleDateString()}</p>
                  <p>Duração: {activity.durationMinutes} minutos</p>
                  
                  <div className="actions">
                    <button 
                      onClick={() => handleApprove(activity.id)}
                      className="btn-approve"
                    >
                      ✅ Aprovar
                    </button>
                    <button 
                      onClick={() => handleReject(activity.id)}
                      className="btn-reject"
                    >
                      ❌ Rejeitar
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        )}
      </div>

      <Sidebar />
      <Footer />
    </div>
  );
};

export default AdminApprovals;
```

---

## 🎯 RESUMO DA ARQUITETURA

### ✅ Vantagens desta Estrutura

1. **Separação Clara** - Volunteer e Admin têm suas próprias pastas
2. **Reutilização** - Componentes comuns compartilhados
3. **Segurança** - Permissões verificadas em múltiplas camadas
4. **Escalabilidade** - Fácil adicionar novas permissões
5. **Manutenibilidade** - Código organizado e previsível
6. **UX Consistente** - Cada role vê apenas o que pode fazer

### 📊 Fluxo de Dados

```
Login → AuthContext → Token + User Data
                    ↓
              usePermissions Hook
                    ↓
        Verifica permissões específicas
                    ↓
          Renderiza UI apropriada
                    ↓
        API calls com token JWT
                    ↓
          Backend valida permissões
```

### 🔒 Camadas de Segurança

1. **Frontend - Rotas** - PrivateRoute bloqueia acesso
2. **Frontend - UI** - Componentes verificam permissões
3. **Frontend - API** - Token JWT em todas as requisições
4. **Backend - Controller** - @PreAuthorize valida role
5. **Backend - Service** - Lógica de negócio valida ownership

---

## 🚀 PRÓXIMOS PASSOS

1. **Implementar estrutura de pastas** sugerida
2. **Criar hooks de permissões**
3. **Refatorar rotas** para separar volunteer/admin
4. **Atualizar Sidebar** para ser dinâmica
5. **Criar páginas específicas** para cada role
6. **Testar fluxos completos**

---

**Recomendação:** Esta arquitetura segue as melhores práticas de RBAC e é escalável para futuras necessidades.
