CREATE TABLE tb_atividade (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data DATE NOT NULL,
    descricao TEXT,
    tempo_minutos INTEGER,
    voluntario_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDENTE',

    CONSTRAINT fk_atividade_voluntario
        FOREIGN KEY (voluntario_id)
        REFERENCES tb_voluntario(id)
);

CREATE INDEX idx_atividade_voluntario
ON tb_atividade(voluntario_id);