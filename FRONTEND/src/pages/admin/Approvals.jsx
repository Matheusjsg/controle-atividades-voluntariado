import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { fetchActivitiesByStatus, updateActivityStatus } from '../../service/activityApi';
import { formatDate, formatDuration, formatActivityStatus } from '../../utils/formatters';
import { toast } from 'react-toastify';
import { CheckSquare, Check, X, Clock } from 'lucide-react';
import Sidebar from '../../components/common/Sidebar';
import Footer from '../../components/common/Footer';
import './Approvals.css';

const Approvals = () => {
  const { token } = useAuth();
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('PENDING');

  useEffect(() => {
    loadActivities();
  }, [filter]);

  const loadActivities = async () => {
    try {
      setLoading(true);
      const data = await fetchActivitiesByStatus(filter, token);
      setActivities(data);
    } catch (error) {
      toast.error('Erro ao carregar atividades');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (activityId, newStatus) => {
    const statusText = newStatus === 'APPROVED' ? 'aprovar' : 'rejeitar';
    
    if (!window.confirm(`Deseja realmente ${statusText} esta atividade?`)) {
      return;
    }

    try {
      await updateActivityStatus(activityId, newStatus, token);
      toast.success(`Atividade ${newStatus === 'APPROVED' ? 'aprovada' : 'rejeitada'} com sucesso!`);
      loadActivities();
    } catch (error) {
      toast.error(`Erro ao ${statusText} atividade`);
      console.error(error);
    }
  };

  const getPendingCount = () => {
    return activities.filter(a => a.activityStatus === 'PENDING').length;
  };

  return (
    <div className="container">
      <Sidebar />
      <div className="content">
        <div className="approvals-page">
          <div className="page-header">
            <CheckSquare size={32} />
            <div>
              <h1>Aprovações</h1>
              <p className="subtitle">Gerencie as atividades dos voluntários</p>
            </div>
          </div>

          <div className="filter-tabs">
            <button
              className={`tab ${filter === 'PENDING' ? 'active' : ''}`}
              onClick={() => setFilter('PENDING')}
            >
              <Clock size={18} />
              Pendentes
              {filter === 'PENDING' && activities.length > 0 && (
                <span className="badge-count">{activities.length}</span>
              )}
            </button>
            <button
              className={`tab ${filter === 'APPROVED' ? 'active' : ''}`}
              onClick={() => setFilter('APPROVED')}
            >
              <Check size={18} />
              Aprovadas
            </button>
            <button
              className={`tab ${filter === 'REJECTED' ? 'active' : ''}`}
              onClick={() => setFilter('REJECTED')}
            >
              <X size={18} />
              Rejeitadas
            </button>
          </div>

          <div className="approvals-card">
            {loading ? (
              <div className="loading-state">
                <p>Carregando atividades...</p>
              </div>
            ) : activities.length === 0 ? (
              <div className="empty-state">
                <CheckSquare size={48} />
                <p>Nenhuma atividade {formatActivityStatus(filter).toLowerCase()}</p>
              </div>
            ) : (
              <div className="activities-table">
                <table>
                  <thead>
                    <tr>
                      <th>Voluntário</th>
                      <th>Data</th>
                      <th>Descrição</th>
                      <th>Duração</th>
                      <th>Status</th>
                      {filter === 'PENDING' && <th>Ações</th>}
                    </tr>
                  </thead>
                  <tbody>
                    {activities.map((activity) => (
                      <tr key={activity.id}>
                        <td>
                          <div className="volunteer-info">
                            <strong>{activity.volunteerName}</strong>
                            <small>{activity.departmentName}</small>
                          </div>
                        </td>
                        <td>{formatDate(activity.date)}</td>
                        <td>
                          <div className="description-cell">
                            {activity.description}
                          </div>
                        </td>
                        <td>{formatDuration(activity.durationMinutes)}</td>
                        <td>
                          <span className={`badge badge-${activity.activityStatus.toLowerCase()}`}>
                            {formatActivityStatus(activity.activityStatus)}
                          </span>
                        </td>
                        {filter === 'PENDING' && (
                          <td>
                            <div className="action-buttons">
                              <button
                                onClick={() => handleStatusChange(activity.id, 'APPROVED')}
                                className="btn-icon btn-approve"
                                title="Aprovar"
                              >
                                <Check size={18} />
                              </button>
                              <button
                                onClick={() => handleStatusChange(activity.id, 'REJECTED')}
                                className="btn-icon btn-reject"
                                title="Rejeitar"
                              >
                                <X size={18} />
                              </button>
                            </div>
                          </td>
                        )}
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Approvals;
