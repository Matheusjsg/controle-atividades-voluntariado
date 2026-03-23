import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { 
  fetchActivitiesByVolunteer, 
  createActivity, 
  deleteActivity 
} from '../../service/activityApi';
import Sidebar from '../../components/common/Sidebar';
import Footer from '../../components/common/Footer';
import ActivityForm from '../../components/volunteer/ActivityForm';
import ActivityList from '../../components/volunteer/ActivityList';
import Loading from '../../components/common/Loading';
import { toast } from 'react-toastify';

const Activities = () => {
  const { user, token } = useAuth();
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);

  // Carrega as atividades ao montar o componente
  useEffect(() => {
    loadActivities();
  }, []);

  // Carrega as atividades do voluntário
  const loadActivities = async () => {
    setLoading(true);
    try {
      const data = await fetchActivitiesByVolunteer(user.volunteerId, token);
      setActivities(data);
    } catch (error) {
      console.error('Erro ao carregar atividades:', error);
      toast.error('Erro ao carregar atividades');
    } finally {
      setLoading(false);
    }
  };

  // Manipula a criação de uma nova atividade
  const handleCreate = async (activityData) => {
    try {
      await createActivity(activityData, token);
      toast.success('Atividade registrada com sucesso! \ud83c\udf89');
      loadActivities();
    } catch (error) {
      console.error('Erro ao criar atividade:', error);
      toast.error('Erro ao registrar atividade');
      throw error;
    }
  };

  // Manipula a exclusão de uma atividade
  const handleDelete = async (id) => {
    try {
      await deleteActivity(id, token);
      return true;
    } catch (error) {
      console.error('Erro ao deletar atividade:', error);
      throw error;
    }
  };

  return (
    <div className="container">
      <div className="content">
        <h1>Minhas Atividades</h1>
        <p className="subtitle">Registre e acompanhe suas atividades de voluntariado</p>

        {/* Formulário de registro */}
        <ActivityForm onSubmit={handleCreate} />

        {/* Lista de atividades */}
        {loading ? (
          <Loading message="Carregando atividades..." />
        ) : (
          <ActivityList
            activities={activities}
            onDelete={handleDelete}
            onRefresh={loadActivities}
          />
        )}
      </div>
      <Sidebar />
      <Footer />
    </div>
  );
};

export default Activities;
