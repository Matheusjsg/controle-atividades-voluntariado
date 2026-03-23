// Tipos de usuário
export const USER_TYPES = {
  VOLUNTEER: 'VOLUNTEER',
  ADMIN: 'ADMIN'
};

// Status de atividade
export const ACTIVITY_STATUS = {
  PENDING: 'PENDING',
  APPROVED: 'APPROVED',
  REJECTED: 'REJECTED'
};

// Duração de atividades (em minutos)
export const DURATION = {
  MIN_MINUTES: 15,
  MAX_MINUTES: 720 // 12 horas
};

// Certificado
export const CERTIFICATE = {
  MIN_HOURS: 20
};

// URL da API
export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

// Permissões por role
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
