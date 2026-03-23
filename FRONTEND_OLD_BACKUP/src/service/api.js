const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

// ================================ ATIVIDADES ================================

const atividadeAPI = `${API_BASE_URL}/activity`;

export const fetchAtividades = async () => {
  const response = await fetch(`${atividadeAPI}/listAll`);
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchAtividadeById = async (id) => {
  const response = await fetch(`${atividadeAPI}/list/${id}`);
  return await response.json();
};

export const fetchAtividadesByVoluntario = async (volunteerId) => {
  const response = await fetch(`${atividadeAPI}/volunteer/${volunteerId}`);
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchAtividadesByStatus = async (status) => {
  const response = await fetch(`${atividadeAPI}/status/${status}`);
  if (response.status === 204) return [];
  return await response.json();
};

export const registrarAtividade = async (atividade) => {
  const response = await fetch(`${atividadeAPI}/create`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(atividade),
  });
  return await response.json();
};

export const atualizarAtividade = async (id, atividade) => {
  const response = await fetch(`${atividadeAPI}/update/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(atividade),
  });
  return await response.json();
};

export const deletarAtividade = async (id) => {
  await fetch(`${atividadeAPI}/delete/${id}`, { method: "DELETE" });
};

// ================================ SETORES ================================

const setoresAPI = `${API_BASE_URL}/departments`;

export const fetchSetores = async () => {
  const response = await fetch(`${setoresAPI}/list`);
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchSetorById = async (id) => {
  const response = await fetch(`${setoresAPI}/${id}`);
  return await response.json();
};

export const criarSetor = async (setor) => {
  const response = await fetch(`${setoresAPI}/create`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(setor),
  });
  return await response.json();
};

export const atualizarSetor = async (id, setor) => {
  const response = await fetch(`${setoresAPI}/update/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(setor),
  });
  return await response.json();
};

export const deletarSetor = async (id) => {
  await fetch(`${setoresAPI}/delete/${id}`, { method: "DELETE" });
};

// ================================ VOLUNTÁRIOS ================================

const voluntarioAPI = `${API_BASE_URL}/volunteer`;

export const fetchVoluntarios = async () => {
  const response = await fetch(`${voluntarioAPI}/list`);
  if (response.status === 204) return [];
  return await response.json();
};

export const fetchVoluntarioById = async (id) => {
  const response = await fetch(`${voluntarioAPI}/${id}`);
  return await response.json();
};

export const criarVoluntario = async (voluntario) => {
  const response = await fetch(`${voluntarioAPI}/create`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(voluntario),
  });
  return await response.json();
};

export const atualizarVoluntario = async (id, voluntario) => {
  const response = await fetch(`${voluntarioAPI}/update/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(voluntario),
  });
  return await response.json();
};

export const deletarVoluntario = async (id) => {
  await fetch(`${voluntarioAPI}/delete/${id}`, { method: "DELETE" });
};


