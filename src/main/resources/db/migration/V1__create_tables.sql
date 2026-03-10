CREATE TABLE tb_setor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE tb_voluntario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    setor_id BIGINT,
    tipo_usuario VARCHAR(20),

    CONSTRAINT fk_setor
        FOREIGN KEY (setor_id)
        REFERENCES setor(id)
);

CREATE TABLE tb_atividade (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    descricao TEXT,
    tempo_minutos INTEGER,
    voluntario_id BIGINT,
    status VARCHAR(20),

    CONSTRAINT fk_voluntario
        FOREIGN KEY (voluntario_id)
        REFERENCES voluntario(id)
);