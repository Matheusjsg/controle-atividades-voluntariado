import { Navigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import Loading from './Loading';

/**
 * Componente para proteger rotas que requerem autenticação
 * @param {Object} props - Props do componente
 * @param {React.ReactNode} props.children - Componente filho a ser renderizado
 * @param {boolean} props.adminOnly - Se true, apenas ADMIN pode acessar
 * @param {string} props.requiredPermission - Permissão específica necessária
 */
const PrivateRoute = ({ children, adminOnly = false }) => {
  const { isAuthenticated, isAdmin, loading, user } = useAuth();

  // Mostra loading enquanto verifica autenticação
  if (loading) {
    return <Loading message="Verificando autenticação..." />;
  }

  // Redireciona para login se não estiver autenticado
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }

  // Redireciona para dashboard do volunteer se tentar acessar rota de admin
  if (adminOnly && !isAdmin()) {
    return <Navigate to="/volunteer/dashboard" replace />;
  }

  return children;
};

export default PrivateRoute;
