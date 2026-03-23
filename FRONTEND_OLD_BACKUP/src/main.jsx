import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext.jsx'
import PrivateRoute from './components/PrivateRoute.jsx'
import Login from './pages/Login.jsx'
import Register from './pages/Register.jsx'
import Dashboard from './pages/Dashboard.jsx'
import CadastroAtividade from './pages/CadastroAtividade.jsx'
import Voluntarios from './pages/Voluntarios.jsx'
import Setores from './pages/Setores.jsx'
import Aprovacoes from './pages/Aprovacoes.jsx'
import Relatorios from './pages/Relatorios.jsx'
import './index.css'

const router = createBrowserRouter([
  { path: "/login", element: <Login /> },
  { path: "/register", element: <Register /> },
  { 
    path: "/", 
    element: <PrivateRoute><Dashboard /></PrivateRoute>
  },
  { 
    path: "/dashboard", 
    element: <PrivateRoute><Dashboard /></PrivateRoute>
  },
  { 
    path: "/atividades", 
    element: <PrivateRoute><CadastroAtividade /></PrivateRoute>
  },
  { 
    path: "/voluntarios", 
    element: <PrivateRoute><Voluntarios /></PrivateRoute>
  },
  { 
    path: "/setores", 
    element: <PrivateRoute><Setores /></PrivateRoute>
  },
  { 
    path: "/aprovacoes", 
    element: <PrivateRoute adminOnly={true}><Aprovacoes /></PrivateRoute>
  },
  { 
    path: "/relatorios", 
    element: <PrivateRoute><Relatorios /></PrivateRoute>
  },
])

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <RouterProvider router={router}/>
    </AuthProvider>
  </StrictMode>,
)