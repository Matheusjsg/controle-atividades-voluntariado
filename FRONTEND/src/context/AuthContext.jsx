/* eslint-disable react-refresh/only-export-components */
import { createContext, useState, useContext, useEffect } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  // Estado do usuário (carrega do localStorage se existir)
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  });

  // Token JWT (carrega do localStorage se existir)
  const [token, setToken] = useState(() => {
    return localStorage.getItem('token');
  });

  // Loading state
  const [loading, setLoading] = useState(false);

  /**
   * Faz login do usuário
   * @param {string} userType - Tipo do usuário (VOLUNTEER ou ADMIN)
   * @param {string} name - Nome do usuário
   * @param {number} volunteerId - ID do voluntário
   * @param {number} departmentId - ID do departamento
   * @param {string} authToken - Token JWT
   */
  const login = (userType, name, volunteerId, departmentId, email, authToken) => {
    const userData = { 
      userType, 
      name, 
      volunteerId, 
      departmentId,
      email
    };
    
    setUser(userData);
    setToken(authToken);
    
    // Salva no localStorage
    localStorage.setItem('token', authToken);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  /**
   * Faz logout do usuário
   */
  const logout = () => {
    setUser(null);
    setToken(null);
    
    // Remove do localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  /**
   * Verifica se o usuário é admin
   * @returns {boolean} true se for admin, false caso contrário
   */
  const isAdmin = () => {
    return user?.userType === 'ADMIN';
  };

  /**
   * Verifica se o usuário está autenticado
   * @returns {boolean} true se autenticado, false caso contrário
   */
  const isAuthenticated = () => {
    return !!token && !!user;
  };

  // Verifica se o token ainda é válido ao carregar
  useEffect(() => {
    if (token && !user) {
      // Token existe mas user não, limpa tudo
      logout();
    }
  }, [token, user]);

  const value = {
    user,
    token,
    loading,
    login,
    logout,
    isAdmin,
    isAuthenticated,
    setLoading
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

/**
 * Hook para usar o contexto de autenticação
 * @returns {Object} Contexto de autenticação
 */
export const useAuth = () => {
  const context = useContext(AuthContext);
  
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  
  return context;
};
