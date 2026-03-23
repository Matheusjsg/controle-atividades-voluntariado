import { API_BASE_URL } from '../utils/constants';

const activityAPI = `${API_BASE_URL}/activity`;
const certificateAPI = `${API_BASE_URL}/certificate`;

export const fetchActivityReport = async (volunteerId, startDate, endDate, token) => {
  const response = await fetch(
    `${activityAPI}/report/${volunteerId}?startDate=${startDate}&endDate=${endDate}`,
    {
      headers: { Authorization: `Bearer ${token}` }
    }
  );

  if (!response.ok) {
    throw new Error('Erro ao buscar relatório');
  }

  return await response.json();
};

export const generateCertificate = async (volunteerId, startDate, endDate, token) => {
  const response = await fetch(
    `${certificateAPI}/generate/${volunteerId}?startDate=${startDate}&endDate=${endDate}`,
    {
      headers: { Authorization: `Bearer ${token}` }
    }
  );

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || 'Erro ao gerar certificado');
  }

  return await response.blob();
};
