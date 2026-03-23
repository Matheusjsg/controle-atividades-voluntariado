import { useState, useEffect } from "react"
import { fetchSetorById } from '../service/authApi'
import { useAuth } from '../context/AuthContext'
import "../styles/form.css"

const AtividadeForm = ({ onSubmit }) => {

  const { token, user } = useAuth()

  const [atividade, setAtividade] = useState({
    date: "",
    durationMinutes: "",
    description: "",
    volunteerId: user?.volunteerId || "",
    departmentId: user?.departmentId || ""
  })
  const [nomeSetor, setNomeSetor] = useState('')

  useEffect(() => {
    const carregar = async () => {
      if (user?.departmentId) {
        const setor = await fetchSetorById(user.departmentId, token)
        setNomeSetor(setor?.name || String(user.departmentId))
      }
    }
    carregar()
  }, [token, user])

  const handleChange = (e) => {
    setAtividade({ ...atividade, [e.target.name]: e.target.value })
  }

  const handleSubmit = (e) => {
    e.preventDefault()

    if (!atividade.date || !atividade.durationMinutes || !atividade.description || !atividade.volunteerId) {
      alert("Preencha todos os campos obrigatórios")
      return
    }

    const payload = {
      date: atividade.date,
      description: atividade.description,
      durationMinutes: parseInt(atividade.durationMinutes),
      volunteerId: parseInt(atividade.volunteerId),
      departmentId: parseInt(atividade.departmentId)
    }

    onSubmit(payload)
    alert("Atividade registrada com sucesso! 🎉")

    setAtividade({
      date: "",
      durationMinutes: "",
      description: "",
      volunteerId: user?.volunteerId || "",
      departmentId: user?.departmentId || ""
    })
  }

  return (
    <div className="card">
      <h2>Registrar Atividade</h2>

      <form onSubmit={handleSubmit}>

        <div className="form-group">
          <label>Voluntário</label>
          <input type="text" value={user?.name || ''} disabled className="input-disabled" />
        </div>

        <div className="form-group">
          <label>Setor</label>
          <input type="text" value={nomeSetor} disabled className="input-disabled" />
        </div>

        <div className="form-group">
          <label>Data</label>
          <input type="date" name="date" value={atividade.date || ""} onChange={handleChange} max={new Date().toISOString().split("T")[0]} />
        </div>

        <div className="form-group">
          <label>Tempo</label>
          <select name="durationMinutes" value={atividade.durationMinutes || ""} onChange={handleChange}>
            <option value="">Selecione</option>
            <option value="30">30min</option>
            <option value="60">1h</option>
            <option value="90">1h30</option>
            <option value="120">2h</option>
            <option value="150">2h30</option>
            <option value="180">3h</option>
            <option value="210">3h30</option>
            <option value="240">4h</option>
            <option value="300">5h</option>
          </select>
        </div>

        <div className="form-group">
          <label>Descrição</label>
          <textarea rows="3" name="description" value={atividade.description} onChange={handleChange} />
        </div>

        <button type="submit">registrar</button>

      </form>
    </div>
  )
}

export default AtividadeForm
