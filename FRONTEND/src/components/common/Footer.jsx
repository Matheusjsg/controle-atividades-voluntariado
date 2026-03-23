import './Footer.css';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <p>
        © {currentYear} ABCAA - Associação Beneficiente e Cultural Amor em Ação
      </p>
      <p className="footer-subtitle">
        Sistema de Registro de Atividades de Voluntariado
      </p>
    </footer>
  );
};

export default Footer;
