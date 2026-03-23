import { useAuth } from './context/AuthContext';
import { Navigate } from 'react-router-dom';

/**
 * Componente raiz que redireciona baseado no tipo de usuário
 */
const App = () => {
  const { user, isAuthenticated } = useAuth();

  // Se não estiver autenticado, redireciona para login
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }

  // Redireciona baseado no tipo de usuário
  if (user?.userType === 'ADMIN') {
    return <Navigate to="/admin/dashboard" replace />;
  }

  return <Navigate to="/volunteer/dashboard" replace />;
};

export default App;
