# 👥 COMPARAÇÃO VISUAL - VOLUNTEER vs ADMIN

---

## 🎭 VISÃO GERAL

```
┌─────────────────────────────────────────────────────────────┐
│                    SISTEMA ABCAA                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  👤 VOLUNTEER              👨💼 ADMIN                        │
│  ├─ Meu Perfil             ├─ Meu Perfil                   │
│  ├─ Minhas Atividades      ├─ Minhas Atividades            │
│  ├─ Meus Relatórios        ├─ Meus Relatórios              │
│  └─ Meu Certificado        ├─ Meu Certificado              │
│                            ├─ ─────────────────            │
│                            ├─ Todos os Voluntários         │
│                            ├─ Todas as Atividades          │
│                            ├─ Aprovar/Rejeitar             │
│                            ├─ Gerenciar Setores            │
│                            └─ Todos os Relatórios          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 📱 INTERFACE DO VOLUNTEER

### Dashboard
```
┌────────────────────────────────────────────────┐
│  Bem-vindo, João Silva! 👋                     │
│  [Badge: Voluntário]                           │
├────────────────────────────────────────────────┤
│                                                │
│  ┌──────────────┐  ┌──────────────┐          │
│  │ 📝 Minhas    │  │ 👤 Meu       │          │
│  │ Atividades   │  │ Perfil       │          │
│  │              │  │              │          │
│  │ 12 registros │  │ Editar dados │          │
│  └──────────────┘  └──────────────┘          │
│                                                │
│  ┌──────────────┐  ┌──────────────┐          │
│  │ 📊 Meus      │  │ 📜 Meu       │          │
│  │ Relatórios   │  │ Certificado  │          │
│  │              │  │              │          │
│  │ Ver horas    │  │ Gerar PDF    │          │
│  └──────────────┘  └──────────────┘          │
│                                                │
└────────────────────────────────────────────────┘
```

### Minhas Atividades
```
┌────────────────────────────────────────────────┐
│  Minhas Atividades                             │
├────────────────────────────────────────────────┤
│                                                │
│  [+ Registrar Nova Atividade]                  │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 📅 15/01/2025 | ⏱️ 2h | ⏳ PENDENTE      │ │
│  │ Aula de reforço escolar                  │ │
│  │ [Editar] [Deletar]                       │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 📅 10/01/2025 | ⏱️ 3h | ✅ APROVADA      │ │
│  │ Organização de doações                   │ │
│  │ [Visualizar]                             │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 📅 05/01/2025 | ⏱️ 1h30 | ❌ REJEITADA   │ │
│  │ Atendimento ao público                   │ │
│  │ [Visualizar]                             │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode EDITAR/DELETAR apenas atividades PENDENTES
✅ Pode VISUALIZAR todas as suas atividades
❌ NÃO pode ver atividades de outros voluntários
❌ NÃO pode aprovar/rejeitar
```

### Meu Perfil
```
┌────────────────────────────────────────────────┐
│  Meu Perfil                                    │
├────────────────────────────────────────────────┤
│                                                │
│  Nome: João Silva                              │
│  Email: joao@email.com                         │
│  Setor: Educação                               │
│  Tipo: Voluntário                              │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ Dados Pessoais                           │ │
│  │                                          │ │
│  │ Telefone: [_______________]              │ │
│  │ CPF: [_______________]                   │ │
│  │ Endereço: [_______________]              │ │
│  │ Cidade: [_______________]                │ │
│  │ Estado: [_______________]                │ │
│  │ CEP: [_______________]                   │ │
│  │ Data Nasc: [_______________]             │ │
│  │                                          │ │
│  │ [Salvar Alterações]                      │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode EDITAR apenas seu próprio perfil
❌ NÃO pode ver perfis de outros voluntários
❌ NÃO pode alterar seu tipo de usuário
```

### Meus Relatórios
```
┌────────────────────────────────────────────────┐
│  Meus Relatórios                               │
├────────────────────────────────────────────────┤
│                                                │
│  Período: [01/01/2025] até [31/12/2025]        │
│  [🔍 Buscar]                                   │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ Total de Horas: 24h                      │ │
│  │ Atividades: 12                           │ │
│  │ Aprovadas: 10                            │ │
│  │ Pendentes: 2                             │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ✅ Você atingiu 24h de trabalho voluntário!   │
│  [📄 Gerar Certificado PDF]                    │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode ver APENAS seu próprio relatório
✅ Pode gerar APENAS seu próprio certificado
❌ NÃO pode ver relatórios de outros voluntários
```

---

## 🔧 INTERFACE DO ADMIN

### Dashboard
```
┌────────────────────────────────────────────────┐
│  Bem-vindo, Maria Admin! 👋                    │
│  [Badge: Administrador]                        │
├────────────────────────────────────────────────┤
│                                                │
│  ┌──────────────┐  ┌──────────────┐          │
│  │ 📝 Minhas    │  │ 👤 Meu       │          │
│  │ Atividades   │  │ Perfil       │          │
│  │              │  │              │          │
│  │ 8 registros  │  │ Editar dados │          │
│  └──────────────┘  └──────────────┘          │
│                                                │
│  ┌──────────────┐  ┌──────────────┐          │
│  │ 📊 Meus      │  │ ✅ Aprovações│          │
│  │ Relatórios   │  │              │          │
│  │              │  │ 5 pendentes  │          │
│  │ Ver horas    │  │ [ADMIN]      │          │
│  └──────────────┘  └──────────────┘          │
│                                                │
│  ┌──────────────┐  ┌──────────────┐          │
│  │ 👥 Todos os  │  │ 🏢 Setores   │          │
│  │ Voluntários  │  │              │          │
│  │              │  │ Gerenciar    │          │
│  │ 25 ativos    │  │ [ADMIN]      │          │
│  └──────────────┘  └──────────────┘          │
│                                                │
│  ┌──────────────┐                             │
│  │ 📈 Todos os  │                             │
│  │ Relatórios   │                             │
│  │              │                             │
│  │ Ver todos    │                             │
│  └──────────────┘                             │
│                                                │
└────────────────────────────────────────────────┘
```

### Aprovações (EXCLUSIVO ADMIN)
```
┌────────────────────────────────────────────────┐
│  Aprovação de Atividades                       │
│  5 atividades pendentes                        │
├────────────────────────────────────────────────┤
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 👤 João Silva                            │ │
│  │ 📅 15/01/2025 | ⏱️ 2h                    │ │
│  │ Aula de reforço escolar                  │ │
│  │                                          │ │
│  │ [✅ Aprovar] [❌ Rejeitar]               │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 👤 Maria Santos                          │ │
│  │ 📅 14/01/2025 | ⏱️ 3h                    │ │
│  │ Organização de evento                    │ │
│  │                                          │ │
│  │ [✅ Aprovar] [❌ Rejeitar]               │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode ver TODAS as atividades pendentes
✅ Pode APROVAR qualquer atividade
✅ Pode REJEITAR qualquer atividade
✅ Vê nome do voluntário em cada atividade
```

### Todos os Voluntários (EXCLUSIVO ADMIN)
```
┌────────────────────────────────────────────────┐
│  Gerenciar Voluntários                         │
│  [+ Cadastrar Novo Voluntário]                 │
├────────────────────────────────────────────────┤
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 👤 João Silva                            │ │
│  │ 📧 joao@email.com                        │ │
│  │ 🏢 Educação | [Badge: Voluntário]        │ │
│  │ 📊 12 atividades | 24h trabalhadas       │ │
│  │                                          │ │
│  │ [Ver Perfil] [Ver Atividades]            │ │
│  │ [Alterar Tipo] [Deletar]                 │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 👤 Maria Santos                          │ │
│  │ 📧 maria@email.com                       │ │
│  │ 🏢 Saúde | [Badge: Voluntário]           │ │
│  │ 📊 8 atividades | 16h trabalhadas        │ │
│  │                                          │ │
│  │ [Ver Perfil] [Ver Atividades]            │ │
│  │ [Alterar Tipo] [Deletar]                 │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode ver TODOS os voluntários
✅ Pode CRIAR novos voluntários
✅ Pode EDITAR qualquer voluntário
✅ Pode DELETAR voluntários (se sem atividades)
✅ Pode ALTERAR tipo (VOLUNTEER ↔ ADMIN)
✅ Pode ver perfil e atividades de qualquer um
```

### Todos os Relatórios (EXCLUSIVO ADMIN)
```
┌────────────────────────────────────────────────┐
│  Relatórios de Todos os Voluntários            │
├────────────────────────────────────────────────┤
│                                                │
│  Voluntário: [Selecione ▼]                     │
│  Período: [01/01/2025] até [31/12/2025]        │
│  [🔍 Buscar]                                   │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 👤 João Silva                            │ │
│  │ Total de Horas: 24h                      │ │
│  │ Atividades: 12 | Aprovadas: 10           │ │
│  │                                          │ │
│  │ [📄 Gerar Certificado]                   │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ Lista de Atividades                      │ │
│  │                                          │ │
│  │ 📅 15/01 | 2h | ✅ APROVADA              │ │
│  │ 📅 10/01 | 3h | ✅ APROVADA              │ │
│  │ 📅 05/01 | 1h30 | ❌ REJEITADA           │ │
│  │ ...                                      │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode ver relatório de QUALQUER voluntário
✅ Pode gerar certificado de QUALQUER voluntário
✅ Pode filtrar por período
✅ Vê estatísticas completas
```

### Gerenciar Setores (EXCLUSIVO ADMIN)
```
┌────────────────────────────────────────────────┐
│  Gerenciar Setores                             │
│  [+ Cadastrar Novo Setor]                      │
├────────────────────────────────────────────────┤
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 🏢 Educação                              │ │
│  │ 👥 8 voluntários                         │ │
│  │ [Editar] [Deletar]                       │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 🏢 Saúde                                 │ │
│  │ 👥 5 voluntários                         │ │
│  │ [Editar] [Deletar]                       │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │ 🏢 Administrativo                        │ │
│  │ 👥 3 voluntários                         │ │
│  │ [Editar] [Deletar]                       │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘

REGRAS:
✅ Pode CRIAR novos setores
✅ Pode EDITAR setores existentes
✅ Pode DELETAR setores (se sem voluntários)
❌ Voluntários NÃO têm acesso a esta página
```

---

## 🔄 FLUXOS COMPARADOS

### Fluxo: Registrar Atividade

**VOLUNTEER:**
```
1. Login → Dashboard
2. Clica em "Minhas Atividades"
3. Preenche formulário (data, tempo, descrição)
4. Clica em "Registrar"
5. Atividade criada com status PENDING
6. Aguarda aprovação do admin
```

**ADMIN:**
```
1. Login → Dashboard
2. Clica em "Minhas Atividades"
3. Preenche formulário (data, tempo, descrição)
4. Clica em "Registrar"
5. Atividade criada com status PENDING
6. Pode ir em "Aprovações" e aprovar sua própria atividade
   (ou outro admin aprova)
```

### Fluxo: Aprovar Atividade

**VOLUNTEER:**
```
❌ NÃO TEM ACESSO
- Não vê menu "Aprovações"
- Não pode aprovar/rejeitar
- Apenas aguarda decisão do admin
```

**ADMIN:**
```
1. Login → Dashboard
2. Clica em "Aprovações"
3. Vê lista de atividades pendentes (de TODOS)
4. Clica em "✅ Aprovar" ou "❌ Rejeitar"
5. Atividade muda de status
6. Voluntário é notificado (futuro)
```

### Fluxo: Ver Relatório

**VOLUNTEER:**
```
1. Login → Dashboard
2. Clica em "Meus Relatórios"
3. Seleciona período
4. Clica em "Buscar"
5. Vê APENAS suas próprias estatísticas
6. Se >= 20h, pode gerar certificado
```

**ADMIN:**
```
1. Login → Dashboard
2. Clica em "Todos os Relatórios"
3. Seleciona QUALQUER voluntário
4. Seleciona período
5. Clica em "Buscar"
6. Vê estatísticas do voluntário selecionado
7. Pode gerar certificado para qualquer um
```

---

## 📊 TABELA DE PERMISSÕES

| Funcionalidade | VOLUNTEER | ADMIN |
|----------------|-----------|-------|
| **Perfil** |
| Ver meu perfil | ✅ | ✅ |
| Editar meu perfil | ✅ | ✅ |
| Ver perfis de outros | ❌ | ✅ |
| Editar perfis de outros | ❌ | ✅ |
| **Atividades** |
| Criar minhas atividades | ✅ | ✅ |
| Ver minhas atividades | ✅ | ✅ |
| Editar minhas atividades (PENDING) | ✅ | ✅ |
| Deletar minhas atividades (PENDING) | ✅ | ✅ |
| Ver atividades de outros | ❌ | ✅ |
| Aprovar atividades | ❌ | ✅ |
| Rejeitar atividades | ❌ | ✅ |
| **Relatórios** |
| Ver meu relatório | ✅ | ✅ |
| Gerar meu certificado | ✅ | ✅ |
| Ver relatórios de outros | ❌ | ✅ |
| Gerar certificados de outros | ❌ | ✅ |
| **Setores** |
| Ver lista de setores | ✅ | ✅ |
| Criar setores | ❌ | ✅ |
| Editar setores | ❌ | ✅ |
| Deletar setores | ❌ | ✅ |
| **Voluntários** |
| Ver lista de voluntários | ❌ | ✅ |
| Criar voluntários | ❌ | ✅ |
| Editar voluntários | ❌ | ✅ |
| Deletar voluntários | ❌ | ✅ |
| Alterar tipo de usuário | ❌ | ✅ |

---

## 🎯 RESUMO

### VOLUNTEER = Foco em SI MESMO
- Vê e gerencia apenas **suas próprias** informações
- Aguarda aprovação do admin para atividades
- Interface simples e focada

### ADMIN = Visão GLOBAL
- Vê e gerencia **tudo e todos**
- Aprova/rejeita atividades de todos
- Gerencia setores e voluntários
- Interface completa com mais opções

---

## 🚀 IMPLEMENTAÇÃO RECOMENDADA

1. **Criar pastas separadas:**
   - `pages/volunteer/` - Páginas do voluntário
   - `pages/admin/` - Páginas do admin

2. **Rotas separadas:**
   - `/volunteer/*` - Rotas do voluntário
   - `/admin/*` - Rotas do admin

3. **Componentes específicos:**
   - `components/volunteer/` - Componentes do voluntário
   - `components/admin/` - Componentes do admin

4. **Sidebar dinâmica:**
   - Mostra opções baseadas no role
   - Esconde opções sem permissão

5. **Validação em múltiplas camadas:**
   - Frontend: UI condicional
   - Frontend: Rotas protegidas
   - Backend: Validação de permissões

---

**Esta estrutura garante:**
- ✅ Segurança
- ✅ Clareza
- ✅ Manutenibilidade
- ✅ Escalabilidade
