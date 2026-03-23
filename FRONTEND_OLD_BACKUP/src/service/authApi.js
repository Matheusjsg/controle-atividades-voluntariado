const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

// ================================ AUTENTICAÇÃO ================================

const authAPI = `${API_BASE_URL}/auth`;

export const login = async (credentials) => {
  const response = await fetch(`${authAPI}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(credentials),
  });
  if (!response.ok) {
    throw new Error('Credenciais inválidas');
  }
  return await response.json();
};

export const register = async (userData) => {
  const response = await fetch(`${authAPI}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!response.ok) {
    throw new Error('Erro ao registrar usuário');
  }
  return await response.json();
};

// ================================ ATIVIDADES ================================

const atividadeAPI = `${API_BASE_URL}/activity`;

export const fetchAtividades = async (token) => {
  const response = await fetch(`${atividadeAPI}/listAll`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchAtividadeById = async (id, token) => {
  const response = await fetch(`${atividadeAPI}/list/${id}`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  return await response.json();
};

export const fetchAtividadesByVoluntario = async (volunteerId, token) => {
  const response = await fetch(`${atividadeAPI}/volunteer/${volunteerId}`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchAtividadesByStatus = async (status, token) => {
  const response = await fetch(`${atividadeAPI}/status/${status}`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  return await response.json();
};

export const registrarAtividade = async (atividade, token) => {
  try {
    console.log('URL:', `${atividadeAPI}/create`)
    console.log('Token:', token)
    console.log('Body:', JSON.stringify(atividade))
    
    const response = await fetch(`${atividadeAPI}/create`, {
      method: "POST",
      headers: { 
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(atividade),
    });
    
    console.log('Status:', response.status)
    
    if (!response.ok) {
      const errorText = await response.text()
      console.error('Erro da API:', errorText)
      throw new Error(`Erro ${response.status}: ${errorText}`)
    }
    
    return await response.json();
  } catch (error) {
    console.error('Erro na requisição:', error)
    throw error
  }
};

export const atualizarAtividade = async (id, atividade, token) => {
  const response = await fetch(`${atividadeAPI}/update/${id}`, {
    method: "PUT",
    headers: { 
      "Content-Type": "application/json",
      ...(token ? { "Authorization": `Bearer ${token}` } : {})
    },
    body: JSON.stringify(atividade),
  });
  return await response.json();
};

export const atualizarStatusAtividade = async (id, status, token) => {
  const response = await fetch(`${atividadeAPI}/${id}/status?status=${status}`, {
    method: "PATCH",
    headers: { 
      "Authorization": `Bearer ${token}`
    },
  });
  return await response.json();
};

export const deletarAtividade = async (id, token) => {
  await fetch(`${atividadeAPI}/delete/${id}`, { 
    method: "DELETE",
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
};

// ================================ SETORES ================================

const setoresAPI = `${API_BASE_URL}/departments`;

export const fetchSetores = async (token) => {
  const response = await fetch(`${setoresAPI}/list`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchSetorById = async (id, token) => {
  const response = await fetch(`${setoresAPI}/${id}`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  return await response.json();
};

export const criarSetor = async (setor, token) => {
  const response = await fetch(`${setoresAPI}/create`, {
    method: "POST",
    headers: { 
      "Content-Type": "application/json",
      ...(token ? { "Authorization": `Bearer ${token}` } : {})
    },
    body: JSON.stringify(setor),
  });
  return await response.json();
};

export const atualizarSetor = async (id, setor, token) => {
  const response = await fetch(`${setoresAPI}/update/${id}`, {
    method: "PUT",
    headers: { 
      "Content-Type": "application/json",
      ...(token ? { "Authorization": `Bearer ${token}` } : {})
    },
    body: JSON.stringify(setor),
  });
  return await response.json();
};

export const deletarSetor = async (id, token) => {
  await fetch(`${setoresAPI}/delete/${id}`, { 
    method: "DELETE",
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
};

// ================================ VOLUNTÁRIOS ================================

const voluntarioAPI = `${API_BASE_URL}/volunteer`;

export const fetchVoluntarios = async (token) => {
  const response = await fetch(`${voluntarioAPI}/list`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchVoluntarioById = async (id, token) => {
  const response = await fetch(`${voluntarioAPI}/${id}`, {
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
  return await response.json();
};

export const criarVoluntario = async (voluntario, token) => {
  const response = await fetch(`${voluntarioAPI}/create`, {
    method: "POST",
    headers: { 
      "Content-Type": "application/json",
      ...(token ? { "Authorization": `Bearer ${token}` } : {})
    },
    body: JSON.stringify(voluntario),
  });
  return await response.json();
};

export const atualizarVoluntario = async (id, voluntario, token) => {
  const response = await fetch(`${voluntarioAPI}/update/${id}`, {
    method: "PUT",
    headers: { 
      "Content-Type": "application/json",
      ...(token ? { "Authorization": `Bearer ${token}` } : {})
    },
    body: JSON.stringify(voluntario),
  });
  return await response.json();
};

export const alterarTipoVoluntario = async (id, userType, token) => {
  const response = await fetch(`${voluntarioAPI}/${id}/usertype?userType=${userType}`, {
    method: "PATCH",
    headers: { 
      "Authorization": `Bearer ${token}`
    },
  });
  return await response.json();
};

export const deletarVoluntario = async (id, token) => {
  await fetch(`${voluntarioAPI}/delete/${id}`, { 
    method: "DELETE",
    headers: token ? { "Authorization": `Bearer ${token}` } : {}
  });
};

// ================================ RELATÓRIOS ================================

export const fetchRelatorio = async (volunteerId, startDate, endDate, token) => {
  const response = await fetch(
    `${atividadeAPI}/report/${volunteerId}?startDate=${startDate}&endDate=${endDate}`,
    {
      headers: { "Authorization": `Bearer ${token}` }
    }
  );
  return await response.json();
};

// ================================ CERTIFICADOS ================================

export const gerarCertificado = async (volunteerId, startDate, endDate, token) => {
  const response = await fetch(
    `${API_BASE_URL}/certificate/generate/${volunteerId}?startDate=${startDate}&endDate=${endDate}`,
    {
      headers: { "Authorization": `Bearer ${token}` }
    }
  );
  
  if (!response.ok) {
    throw new Error('Erro ao gerar certificado');
  }
  
  const blob = await response.blob();
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = `certificado_${volunteerId}.pdf`;
  document.body.appendChild(a);
  a.click();
  window.URL.revokeObjectURL(url);
  document.body.removeChild(a);
};
