# Frontend - Sistema de Registro de Atividades de Voluntariado

## 🚀 Tecnologias

- React 19
- Vite
- React Router DOM
- Lucide React (ícones)

## 📁 Estrutura do Projeto

```
src/
├── assets/          # Imagens e recursos estáticos
├── components/      # Componentes reutilizáveis
│   ├── AtividadeForm.jsx
│   ├── AtividadeList.jsx
│   ├── SetorForm.jsx
│   ├── SetorList.jsx
│   ├── VoluntarioForm.jsx
│   ├── VoluntarioList.jsx
│   ├── Sidebar.jsx
│   └── Footer.jsx
├── pages/           # Páginas da aplicação
│   ├── Dashboard.jsx
│   ├── CadastroAtividade.jsx
│   ├── Setores.jsx
│   └── Voluntarios.jsx
├── service/         # Serviços de API
│   └── api.js
├── styles/          # Arquivos CSS
│   ├── form.css
│   ├── list.css
│   ├── sidebar.css
│   └── footer.css
├── App.jsx
└── main.jsx
```

## ⚙️ Configuração

### 1. Instalar dependências

```bash
npm install
```

### 2. Configurar variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
VITE_API_URL=http://localhost:8080
```

Para produção (Render):
```env
VITE_API_URL=https://atividades-voluntariado.onrender.com
```

### 3. Executar em desenvolvimento

```bash
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`

### 4. Build para produção

```bash
npm run build
```

## 🔌 Integração com a API

O frontend está integrado com os seguintes endpoints da API:

### Atividades
- `POST /activity/create` - Criar atividade
- `GET /activity/listAll` - Listar todas
- `GET /activity/list/{id}` - Buscar por ID
- `GET /activity/volunteer/{volunteerId}` - Listar por voluntário
- `GET /activity/status/{status}` - Filtrar por status
- `PUT /activity/update/{id}` - Atualizar
- `DELETE /activity/delete/{id}` - Excluir

### Setores
- `POST /departments/create` - Criar setor
- `GET /departments/list` - Listar todos
- `GET /departments/{id}` - Buscar por ID
- `PUT /departments/update/{id}` - Atualizar
- `DELETE /departments/delete/{id}` - Excluir

### Voluntários
- `POST /volunteer/create` - Criar voluntário
- `GET /volunteer/list` - Listar todos
- `GET /volunteer/{id}` - Buscar por ID
- `PUT /volunteer/update/{id}` - Atualizar
- `DELETE /volunteer/delete/{id}` - Excluir

## 📋 Funcionalidades

### ✅ Implementadas

- Cadastro de setores com listagem
- Cadastro de voluntários com listagem
- Registro de atividades com listagem
- Filtro de atividades por status (Pendente, Aprovada, Rejeitada)
- Exclusão de registros
- Formatação de tempo (minutos para horas)
- Validação de formulários
- Navegação entre páginas

### 🔲 Próximas funcionalidades

- Edição de registros (modal)
- Busca e filtros avançados
- Relatórios de horas por voluntário
- Dashboard com estatísticas
- Autenticação de usuários
- Aprovação/rejeição de atividades (admin)
- Exportação de dados (PDF/Excel)

## 🎨 Componentes Principais

### Formulários
- **AtividadeForm**: Registro de atividades
- **SetorForm**: Cadastro de setores
- **VoluntarioForm**: Cadastro de voluntários

### Listagens
- **AtividadeList**: Tabela de atividades com filtro por status
- **SetorList**: Tabela de setores
- **VoluntarioList**: Tabela de voluntários

### Layout
- **Sidebar**: Menu de navegação
- **Footer**: Rodapé da aplicação

## 🔧 Desenvolvimento

### Adicionar nova página

1. Criar componente em `src/pages/`
2. Adicionar rota no `App.jsx` ou `main.jsx`
3. Adicionar link na `Sidebar.jsx`

### Adicionar novo endpoint

1. Adicionar função em `src/service/api.js`
2. Usar a função no componente necessário

## 📝 Notas

- O projeto usa Vite para build rápido
- Variáveis de ambiente devem começar com `VITE_`
- O backend deve estar rodando para o frontend funcionar corretamente
- Configure CORS no backend para permitir requisições do frontend
