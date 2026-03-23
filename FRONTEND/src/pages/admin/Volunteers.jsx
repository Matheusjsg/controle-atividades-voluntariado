import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { fetchVolunteers, updateVolunteerUserType, deleteVolunteer } from '../../service/volunteerApi';
import { formatUserType } from '../../utils/formatters';
import { toast } from 'react-toastify';
import { Users, Shield, Trash2, Search } from 'lucide-react';
import Sidebar from '../../components/common/Sidebar';
import Footer from '../../components/common/Footer';
import './Volunteers.css';

const Volunteers = () => {
  const { token } = useAuth();
  const [volunteers, setVolunteers] = useState([]);
  const [filteredVolunteers, setFilteredVolunteers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadVolunteers();
  }, []);

  useEffect(() => {
    filterVolunteers();
  }, [searchTerm, volunteers]);

  const loadVolunteers = async () => {
    try {
      setLoading(true);
      const data = await fetchVolunteers(token);
      setVolunteers(data);
      setFilteredVolunteers(data);
    } catch (error) {
      toast.error('Erro ao carregar voluntários');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const filterVolunteers = () => {
    if (!searchTerm) {
      setFilteredVolunteers(volunteers);
      return;
    }

    const filtered = volunteers.filter(v =>
      v.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      v.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
      v.departmentName?.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredVolunteers(filtered);
  };

  const handleToggleUserType = async (volunteerId, currentType) => {
    const newType = currentType === 'ADMIN' ? 'VOLUNTEER' : 'ADMIN';
    const action = newType === 'ADMIN' ? 'promover a Admin' : 'remover privilégios de Admin';

    if (!window.confirm(`Deseja realmente ${action} este usuário?`)) {
      return;
    }

    try {
      await updateVolunteerUserType(volunteerId, newType, token);
      toast.success(`Usuário ${newType === 'ADMIN' ? 'promovido' : 'rebaixado'} com sucesso!`);
      loadVolunteers();
    } catch (error) {
      toast.error('Erro ao atualizar tipo de usuário');
      console.error(error);
    }
  };

  const handleDelete = async (volunteerId, volunteerName) => {
    if (!window.confirm(`Deseja realmente excluir ${volunteerName}? Esta ação não pode ser desfeita.`)) {
      return;
    }

    try {
      await deleteVolunteer(volunteerId, token);
      toast.success('Voluntário excluído com sucesso!');
      loadVolunteers();
    } catch (error) {
      toast.error('Erro ao excluir voluntário');
      console.error(error);
    }
  };

  return (
    <div className="container">
      <Sidebar />
      <div className="content">
        <div className="volunteers-page">
          <div className="page-header">
            <Users size={32} />
            <div>
              <h1>Voluntários</h1>
              <p className="subtitle">Gerencie os voluntários cadastrados</p>
            </div>
          </div>

          <div className="volunteers-card">
            <div className="search-bar">
              <Search size={20} />
              <input
                type="text"
                placeholder="Buscar por nome, email ou setor..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            {loading ? (
              <div className="loading-state">
                <p>Carregando voluntários...</p>
              </div>
            ) : filteredVolunteers.length === 0 ? (
              <div className="empty-state">
                <Users size={48} />
                <p>{searchTerm ? 'Nenhum voluntário encontrado' : 'Nenhum voluntário cadastrado'}</p>
              </div>
            ) : (
              <div className="volunteers-table">
                <table>
                  <thead>
                    <tr>
                      <th>Nome</th>
                      <th>Email</th>
                      <th>Setor</th>
                      <th>Tipo</th>
                      <th>Ações</th>
                    </tr>
                  </thead>
                  <tbody>
                    {filteredVolunteers.map((volunteer) => (
                      <tr key={volunteer.id}>
                        <td>
                          <strong>{volunteer.name}</strong>
                        </td>
                        <td>{volunteer.email}</td>
                        <td>{volunteer.departmentName || '-'}</td>
                        <td>
                          <span className={`badge ${volunteer.userType === 'ADMIN' ? 'badge-admin' : 'badge-volunteer'}`}>
                            {formatUserType(volunteer.userType)}
                          </span>
                        </td>
                        <td>
                          <div className="action-buttons">
                            <button
                              onClick={() => handleToggleUserType(volunteer.id, volunteer.userType)}
                              className="btn-icon btn-toggle"
                              title={volunteer.userType === 'ADMIN' ? 'Remover Admin' : 'Promover a Admin'}
                            >
                              <Shield size={18} />
                            </button>
                            <button
                              onClick={() => handleDelete(volunteer.id, volunteer.name)}
                              className="btn-icon btn-delete"
                              title="Excluir"
                            >
                              <Trash2 size={18} />
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}

            {!loading && filteredVolunteers.length > 0 && (
              <div className="table-footer">
                <p>{filteredVolunteers.length} voluntário(s) encontrado(s)</p>
              </div>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Volunteers;
