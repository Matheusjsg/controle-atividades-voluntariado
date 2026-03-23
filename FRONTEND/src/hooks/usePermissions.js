import { useAuth } from '../context/AuthContext';
import { PERMISSIONS } from '../utils/constants';

/**
 * Hook para verificar permissões do usuário.
 * Retorna funções que verificam se o usuário tem permissão para realizar ações específicas.
 * 
 * @returns {Object} Objeto com funções de verificação de permissão
 * 
 * @example
 * const { canApproveActivity, canEditOwnActivity } = usePermissions();
 * 
 * if (canApproveActivity()) {
 *   // Mostra botão de aprovar
 * }
 */
export const usePermissions = () => {
  // Obtém dados do usuário autenticado
  const { user } = useAuth();

  /**
   * Verifica se o usuário tem uma permissão específica.
   * 
   * @param {string} permission - Nome da permissão a verificar
   * @returns {boolean} true se tem permissão, false caso contrário
   */
  const hasPermission = (permission) => {
    if (!user || !user.userType) {
      return false;
    }
    
    return PERMISSIONS[permission]?.includes(user.userType) || false;
  };

  // Permissões de Perfil
  const canViewOwnProfile = () => hasPermission('VIEW_OWN_PROFILE');
  const canEditOwnProfile = () => hasPermission('EDIT_OWN_PROFILE');
  const canViewAllProfiles = () => hasPermission('VIEW_ALL_PROFILES');

  // Permissões de Atividades
  const canCreateActivity = () => hasPermission('CREATE_OWN_ACTIVITY');
  const canViewOwnActivities = () => hasPermission('VIEW_OWN_ACTIVITIES');
  
  /**
   * Verifica se o usuário pode editar uma atividade específica.
   * Regras:
   * - Deve ser o dono da atividade
   * - Status deve ser PENDING
   * - Deve ter permissão EDIT_OWN_ACTIVITY
   * 
   * @param {Object} activity - Objeto da atividade
   * @returns {boolean} true se pode editar, false caso contrário
   */
  const canEditOwnActivity = (activity) => {
    return (
      hasPermission('EDIT_OWN_ACTIVITY') && 
      activity.activityStatus === 'PENDING' &&
      activity.volunteerId === user.volunteerId
    );
  };

  /**
   * Verifica se o usuário pode deletar uma atividade específica.
   * Regras:
   * - Deve ser o dono da atividade
   * - Status deve ser PENDING
   * - Deve ter permissão DELETE_OWN_ACTIVITY
   * 
   * @param {Object} activity - Objeto da atividade
   * @returns {boolean} true se pode deletar, false caso contrário
   */
  const canDeleteOwnActivity = (activity) => {
    return (
      hasPermission('DELETE_OWN_ACTIVITY') && 
      activity.activityStatus === 'PENDING' &&
      activity.volunteerId === user.volunteerId
    );
  };

  const canViewAllActivities = () => hasPermission('VIEW_ALL_ACTIVITIES');
  const canApproveActivity = () => hasPermission('APPROVE_ACTIVITY');
  const canRejectActivity = () => hasPermission('REJECT_ACTIVITY');

  // Permissões de Relatórios
  const canViewOwnReport = () => hasPermission('VIEW_OWN_REPORT');
  const canGenerateOwnCertificate = () => hasPermission('GENERATE_OWN_CERTIFICATE');
  const canViewAllReports = () => hasPermission('VIEW_ALL_REPORTS');
  const canGenerateAnyCertificate = () => hasPermission('GENERATE_ANY_CERTIFICATE');

  // Permissões de Setores
  const canViewDepartments = () => hasPermission('VIEW_DEPARTMENTS');
  const canManageDepartments = () => hasPermission('MANAGE_DEPARTMENTS');

  // Permissões de Voluntários
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
    canViewDepartments,
    canManageDepartments,
    canManageVolunteers,
    canChangeUserType
  };
};
