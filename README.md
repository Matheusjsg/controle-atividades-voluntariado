# Sistema de Registro de Atividades de Voluntariado

## 📌 Sobre o projeto

Este projeto é um sistema simples para registro de atividades realizadas por voluntários.
O objetivo é permitir que voluntários registrem as horas trabalhadas e descrevam as atividades realizadas, possibilitando o acompanhamento das horas de voluntariado pela organização.

O sistema também permitirá a geração de relatórios para controle de horas e emissão de certificados.

---

## ⚙️ Tecnologias utilizadas

* Java
* Spring Boot
* Spring Data JPA
* Flyway (versionamento de banco)
* MySQL
* Maven

---

## 📊 Funcionalidades iniciais

* Cadastro de setores da organização
* Cadastro de voluntários
* Registro de atividades realizadas
* Controle de tempo de atividades
* Listagem de registros de atividades

---

## 🗂 Estrutura básica do sistema

Principais entidades:

**Voluntário**

* Nome
* Email
* Setor
* Tipo de usuário (Voluntário ou Administrador)

**Setor**

* Nome do setor

**Atividade**

* Data
* Tempo de atividade
* Descrição
* Voluntário responsável

---

## ⏱ Controle de horas

O tempo de atividade é registrado em intervalos padronizados:

* 30 minutos
* 1 hora
* 1h30
* 2 horas
* 2h30
* 3 horas
* 3h30
* 4 horas
* 5 horas

Esses registros permitem acompanhar ciclos de:

* **20 horas de voluntariado**
* **45 dias de participação**

---

## 🚧 Status do projeto

Projeto em desenvolvimento.
Funcionalidades como autenticação, relatórios de horas e interface para voluntários ainda serão implementadas.

---

## 👨‍💻 Autor

Desenvolvido por **Matheus Jesus**
Projeto desenvolvido como iniciativa de apoio ao registro e acompanhamento de atividades de voluntariado.
