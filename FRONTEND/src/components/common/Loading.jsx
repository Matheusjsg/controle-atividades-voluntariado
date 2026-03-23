import './Loading.css';

/**
 * Componente de loading
 * @param {Object} props - Props do componente
 * @param {string} props.message - Mensagem a ser exibida
 */
const Loading = ({ message = 'Carregando...' }) => {
  return (
    <div className="loading-container">
      <div className="spinner"></div>
      <p className="loading-message">{message}</p>
    </div>
  );
};

export default Loading;
