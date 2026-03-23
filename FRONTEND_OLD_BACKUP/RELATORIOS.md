# 📊 Sistema de Relatórios e Certificados - Implementado

## ✅ O que foi implementado:

### 1. **Página de Relatórios** (`src/pages/Relatorios.jsx`)

#### Funcionalidades:
- ✅ **Filtros de Busca:**
  - Seleção de voluntário (ADMIN vê todos, VOLUNTEER vê apenas o próprio)
  - Data de início
  - Data de fim
  - Período padrão: último ano

- ✅ **Relatório Completo:**
  - Nome do voluntário
  - Período consultado
  - Total de horas trabalhadas (destaque visual)
  - Estatísticas em cards:
    - Total de atividades
    - Atividades aprovadas
    - Total de horas
    - Média de horas por atividade

- ✅ **Lista de Atividades:**
  - Tabela com todas as atividades do período
  - Colunas: Data, Descrição, Duração, Status
  - Formatação de datas em PT-BR
  - Badges coloridos por status

- ✅ **Geração de Certificado:**
  - Botão para gerar certificado PDF
  - Validação de 20h mínimas
  - Mensagem de sucesso/erro
  - Download automático
  - Feedback visual durante geração

### 2. **Estilos** (`src/styles/relatorios.css`)
- Design moderno e profissional
- Cards com sombras e animações
- Gradientes coloridos
- Grid responsivo
- Estatísticas visuais
- Seção de certificado destacada
- Mobile-friendly

### 3. **Integração com API**

#### Endpoint de Relatório:
```
GET /activity/report/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31
Authorization: Bearer {token}
```

#### Endpoint de Certificado:
```
GET /certificate/generate/{volunteerId}?startDate=2025-01-01&endDate=2025-12-31
Authorization: Bearer {token}
Response: PDF file (binary)
```

### 4. **Navegação Atualizada**
- ✅ Rota `/relatorios` adicionada
- ✅ Link no Sidebar (ícone FileText)
- ✅ Card no Dashboard
- ✅ Acessível para todos os usuários autenticados

---

## 🚀 Como usar:

### 1. **Acessar Relatórios**
```
1. Faça login no sistema
2. Clique em "Relatórios" no menu lateral
3. Selecione o voluntário (se for ADMIN)
4. Escolha o período (data início e fim)
5. Clique em "🔍 Buscar Relatório"
```

### 2. **Visualizar Estatísticas**
```
- Total de Horas: Destaque no topo
- Cards de Estatísticas:
  - 📝 Total de atividades
  - ✅ Atividades aprovadas
  - ⏱️ Total de horas
  - 📊 Média por atividade
```

### 3. **Gerar Certificado**
```
1. Busque um relatório
2. Verifique se tem 20h ou mais
3. Clique em "📄 Gerar Certificado PDF"
4. Aguarde o download automático
```

### 4. **Validações**
```
✅ Mínimo 20h para certificado
⚠️ Mensagem se não atingir mínimo
❌ Botão desabilitado se < 20h
```

---

## 🔧 Integração com Backend:

### 1. Endpoint de Relatório

#### Request:
```http
GET /activity/report/5?startDate=2025-01-01&endDate=2025-12-31
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Response:
```json
{
  "volunteerId": 5,
  "volunteerName": "João Silva",
  "volunteerEmail": "joao@email.com",
  "startDate": "2025-01-01",
  "endDate": "2025-12-31",
  "totalActivities": 15,
  "approvedActivities": 12,
  "totalHours": 45,
  "activities": [
    {
      "id": 1,
      "date": "2025-01-15",
      "description": "Organização de evento",
      "durationMinutes": 180,
      "activityStatus": "APPROVED"
    },
    {
      "id": 2,
      "date": "2025-01-20",
      "description": "Atendimento ao público",
      "durationMinutes": 120,
      "activityStatus": "APPROVED"
    }
  ]
}
```

### 2. Endpoint de Certificado

#### Request:
```http
GET /certificate/generate/5?startDate=2025-01-01&endDate=2025-12-31
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Response:
```
Content-Type: application/pdf
Content-Disposition: attachment; filename="certificado_5.pdf"

[Binary PDF data]
```

#### Validações no Backend:
- Verificar se voluntário existe
- Verificar se tem pelo menos 20h no período
- Gerar PDF com iText7
- Incluir informações:
  - Nome do voluntário
  - Total de horas
  - Período
  - Data de emissão
  - Assinatura digital (opcional)

---

## 📊 Estrutura do Relatório:

### Header:
- Nome do voluntário
- Período consultado
- Total de horas (destaque)

### Estatísticas (4 cards):
1. **Total de Atividades** 📝
2. **Atividades Aprovadas** ✅
3. **Total de Horas** ⏱️
4. **Média por Atividade** 📊

### Tabela de Atividades:
| Data | Descrição | Duração | Status |
|------|-----------|---------|--------|
| 15/01/2025 | Organização de evento | 3h | Aprovada |
| 20/01/2025 | Atendimento ao público | 2h | Aprovada |

### Seção de Certificado:
- Informação sobre requisito de 20h
- Progresso atual
- Botão de geração (habilitado/desabilitado)

---

## 🎨 Design Implementado:

### Cores:
- **Primária:** Gradiente roxo (#667eea → #764ba2)
- **Certificado:** Gradiente rosa (#f093fb → #f5576c)
- **Sucesso:** Verde (#d4edda)
- **Aviso:** Amarelo (#fff3cd)
- **Erro:** Vermelho (#f8d7da)

### Componentes:
- Cards com sombra e hover
- Gradientes suaves
- Ícones emoji para estatísticas
- Badges coloridos por status
- Botões com feedback visual

### Responsividade:
- Desktop: Grid de 4 colunas (estatísticas)
- Tablet: Grid de 2 colunas
- Mobile: 1 coluna, layout vertical

---

## 🔒 Permissões:

### ADMIN:
- ✅ Pode ver relatórios de qualquer voluntário
- ✅ Pode gerar certificados para qualquer voluntário
- ✅ Dropdown com todos os voluntários

### VOLUNTEER:
- ✅ Pode ver apenas o próprio relatório
- ✅ Pode gerar apenas o próprio certificado
- ✅ Dropdown desabilitado (pré-selecionado)

---

## 📝 Validações Implementadas:

### Frontend:
- ✅ Campos obrigatórios (voluntário, datas)
- ✅ Data fim >= Data início
- ✅ Mínimo 20h para certificado
- ✅ Feedback visual de loading
- ✅ Mensagens de erro/sucesso

### Backend (esperado):
- ✅ Validar token JWT
- ✅ Verificar permissões (VOLUNTEER só vê próprio)
- ✅ Validar datas
- ✅ Verificar se voluntário existe
- ✅ Validar 20h mínimas para certificado
- ✅ Gerar PDF com iText7

---

## 🧪 Casos de Teste:

### 1. Buscar Relatório (Sucesso)
```
1. Login como ADMIN
2. Selecionar voluntário
3. Definir período
4. Clicar em "Buscar"
5. ✅ Relatório exibido com estatísticas
```

### 2. Gerar Certificado (Sucesso)
```
1. Buscar relatório com >= 20h
2. Clicar em "Gerar Certificado"
3. ✅ PDF baixado automaticamente
```

### 3. Gerar Certificado (Falha - < 20h)
```
1. Buscar relatório com < 20h
2. Botão desabilitado
3. ⚠️ Mensagem: "Faltam Xh para atingir o mínimo"
```

### 4. Voluntário vê apenas próprio relatório
```
1. Login como VOLUNTEER
2. Dropdown desabilitado
3. Próprio ID pré-selecionado
4. ✅ Vê apenas suas atividades
```

---

## 📦 Arquivos Criados/Atualizados:

### Novos:
- ✅ `src/pages/Relatorios.jsx`
- ✅ `src/styles/relatorios.css`
- ✅ `RELATORIOS.md` (esta documentação)

### Atualizados:
- ✅ `src/main.jsx` (rota /relatorios)
- ✅ `src/components/Sidebar.jsx` (link Relatórios)
- ✅ `src/pages/Dashboard.jsx` (card Relatórios)
- ✅ `src/service/authApi.js` (já tinha as funções)

---

## 🎉 Status: COMPLETO ✅

O sistema de relatórios e certificados está totalmente funcional!

**Funcionalidades:**
- ✅ Busca de relatórios por período
- ✅ Estatísticas visuais
- ✅ Lista de atividades
- ✅ Geração de certificado PDF
- ✅ Validação de 20h mínimas
- ✅ Permissões por tipo de usuário
- ✅ Design responsivo
- ✅ Integração com JWT

---

## 🚀 Próximos Passos (Opcionais):

### Melhorias Futuras:
1. 📈 **Gráficos:**
   - Chart.js ou Recharts
   - Gráfico de horas por mês
   - Gráfico de atividades por setor

2. 📤 **Exportação:**
   - Exportar relatório em Excel
   - Exportar lista de atividades em CSV

3. 🔔 **Notificações:**
   - Notificar quando atingir 20h
   - Lembrete de registrar atividades

4. 📊 **Dashboard Avançado:**
   - Ranking de voluntários
   - Metas de horas
   - Comparativo mensal

5. 🎨 **Personalização:**
   - Template customizável de certificado
   - Logo da ONG no certificado
   - Assinatura digital

---

## 📞 Suporte:

Para dúvidas sobre implementação:
- Consulte `AUTENTICACAO.md` para JWT
- Consulte `APROVACOES.md` para aprovações
- Consulte o README.md do backend para endpoints
