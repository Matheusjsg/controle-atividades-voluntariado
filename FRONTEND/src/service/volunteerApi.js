import { API_BASE_URL } from '../utils/constants';

const volunteerAPI = `${API_BASE_URL}/volunteer`;

export const fetchVolunteers = async (token) => {
  const response = await fetch(`${volunteerAPI}/list`, {
    headers: { Authorization: `Bearer ${token}` }
  });

  if (!response.ok) {
    throw new Error('Erro ao buscar voluntários');
  }

  return await response.json();
};

export const fetchVolunteerById = async (volunteerId, token) => {
  const response = await fetch(`${volunteerAPI}/${volunteerId}`, {
    headers: { Authorization: `Bearer ${token}` }
  });

  if (!response.ok) {
    throw new Error('Erro ao buscar voluntário');
  }

  return await response.json();
};

export const updateVolunteerUserType = async (volunteerId, userType, token) => {
  const response = await fetch(`${volunteerAPI}/${volunteerId}/usertype?userType=${userType}`, {
    method: 'PATCH',
    headers: { Authorization: `Bearer ${token}` }
  });

  if (!response.ok) {
    throw new Error('Erro ao atualizar tipo de usuário');
  }

  return await response.json();
};

export const deleteVolunteer = async (volunteerId, token) => {
  const response = await fetch(`${volunteerAPI}/delete/${volunteerId}`, {
    method: 'DELETE',
    headers: { Authorization: `Bearer ${token}` }
  });

  if (!response.ok) {
    throw new Error('Erro ao excluir voluntário');
  }
};
