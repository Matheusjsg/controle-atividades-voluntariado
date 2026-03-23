import { API_BASE_URL } from '../utils/constants';

const authAPI = `${API_BASE_URL}/auth`;

/**
 * Faz login do usuário
 * @param {Object} credentials - Credenciais de login
 * @param {string} credentials.email - Email do usuário
 * @param {string} credentials.password - Senha do usuário
 * @returns {Promise<Object>} Dados do usuário e token
 */
export const login = async (credentials) => {
  try {
    const response = await fetch(`${authAPI}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(credentials)
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || 'Credenciais inválidas');
    }

    return await response.json();
  } catch (error) {
    console.error('Erro no login:', error);
    throw error;
  }
};

/**
 * Registra um novo usuário
 * @param {Object} userData - Dados do novo usuário
 * @param {string} userData.name - Nome do usuário
 * @param {string} userData.email - Email do usuário
 * @param {string} userData.password - Senha do usuário
 * @param {number} userData.departmentId - ID do departamento
 * @returns {Promise<Object>} Dados do usuário criado e token
 */
export const register = async (userData) => {
  try {
    const response = await fetch(`${authAPI}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userData)
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || 'Erro ao registrar usuário');
    }

    return await response.json();
  } catch (error) {
    console.error('Erro no registro:', error);
    throw error;
  }
};
