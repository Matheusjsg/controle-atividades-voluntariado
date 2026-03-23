import { API_BASE_URL } from '../utils/constants';

const profileAPI = `${API_BASE_URL}/volunteer/profile`;

export const fetchVolunteerProfile = async (token) => {
  const response = await fetch(profileAPI, {
    headers: { Authorization: `Bearer ${token}` }
  });
  
  if (!response.ok) {
    throw new Error('Erro ao carregar perfil');
  }
  
  return await response.json();
};

export const saveVolunteerProfile = async (profileData, token) => {
  const response = await fetch(profileAPI, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify(profileData)
  });
  
  if (!response.ok) {
    throw new Error('Erro ao salvar perfil');
  }
  
  return await response.json();
};

export const fetchVolunteerProfileById = async (volunteerId, token) => {
  const response = await fetch(`${API_BASE_URL}/volunteer/profile/${volunteerId}`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  
  if (!response.ok) {
    throw new Error('Erro ao carregar perfil');
  }
  
  return await response.json();
};
