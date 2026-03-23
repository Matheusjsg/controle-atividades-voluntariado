import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { 
  LayoutDashboard, 
  User, 
  Activity, 
  FileText, 
  Users, 
  CheckSquare, 
  Briefcase, 
  LogOut 
} from 'lucide-react';
import logo from '../../assets/logo.png';
import './Sidebar.css';

const Sidebar = () => {
  const { user, logout, isAdmin } = useAuth();
  const location = useLocation();

  // Determina a rota base baseado no tipo de usuário
  const baseRoute = isAdmin() ? '/admin' : '/volunteer';

  // Verifica se a rota está ativa
  const isActive = (path) => {
    return location.pathname === path;
  };

  // Manipula o logout
  const handleLogout = () => {
    if (window.confirm('Deseja realmente sair?')) {
      logout();
    }
  };

  return (
    <aside className="sidebar">
      {/* Header da sidebar */}
      <div className="sidebar-header">
        <div className="logo-container">
          <img src={logo} alt="ABCAA Logo" className="sidebar-logo" />
        </div>
        <h2 className="org-name">ABCAA</h2>
        <p className="org-subtitle">Amor em Ação</p>
        <div className="user-info">
          <p className="user-name">{user?.name}</p>
          <span className={`badge ${isAdmin() ? 'badge-admin' : 'badge-volunteer'}`}>
            {isAdmin() ? 'Admin' : 'Voluntário'}
          </span>
        </div>
      </div>

      {/* Navegação */}
      <nav className="sidebar-nav">
        {/* Dashboard */}
        <Link 
          to={`${baseRoute}/dashboard`} 
          className={`nav-item ${isActive(`${baseRoute}/dashboard`) ? 'active' : ''}`}
        >
          <LayoutDashboard size={20} />
          <span>Dashboard</span>
        </Link>

        {/* Meu Perfil */}
        <Link 
          to={`${baseRoute}/profile`} 
          className={`nav-item ${isActive(`${baseRoute}/profile`) ? 'active' : ''}`}
        >
          <User size={20} />
          <span>Meu Perfil</span>
        </Link>

        {/* Minhas Atividades */}
        <Link 
          to={`${baseRoute}/activities`} 
          className={`nav-item ${isActive(`${baseRoute}/activities`) ? 'active' : ''}`}
        >
          <Activity size={20} />
          <span>Minhas Atividades</span>
        </Link>

        {/* Meus Relatórios */}
        <Link 
          to={`${baseRoute}/reports`} 
          className={`nav-item ${isActive(`${baseRoute}/reports`) ? 'active' : ''}`}
        >
          <FileText size={20} />
          <span>Meus Relatórios</span>
        </Link>

        {/* Separador - Apenas para Admin */}
        {isAdmin() && <div className="nav-separator">Administração</div>}

        {/* Voluntários - Apenas Admin */}
        {isAdmin() && (
          <Link 
            to="/admin/volunteers" 
            className={`nav-item ${isActive('/admin/volunteers') ? 'active' : ''}`}
          >
            <Users size={20} />
            <span>Voluntários</span>
          </Link>
        )}

        {/* Aprovações - Apenas Admin */}
        {isAdmin() && (
          <Link 
            to="/admin/approvals" 
            className={`nav-item ${isActive('/admin/approvals') ? 'active' : ''}`}
          >
            <CheckSquare size={20} />
            <span>Aprovações</span>
          </Link>
        )}

        {/* Setores - Apenas Admin */}
        {isAdmin() && (
          <Link 
            to="/admin/departments" 
            className={`nav-item ${isActive('/admin/departments') ? 'active' : ''}`}
          >
            <Briefcase size={20} />
            <span>Setores</span>
          </Link>
        )}
      </nav>

      {/* Botão de logout */}
      <button onClick={handleLogout} className="logout-btn">
        <LogOut size={20} />
        <span>Sair</span>
      </button>
    </aside>
  );
};

export default Sidebar;
