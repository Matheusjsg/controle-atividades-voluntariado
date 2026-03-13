CREATE TABLE tb_voluntario (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    setor_id BIGINT NOT NULL,
    tipo_usuario VARCHAR(20),

    CONSTRAINT fk_voluntario_setor
        FOREIGN KEY (setor_id)
        REFERENCES tb_setor(id),

    CONSTRAINT uk_voluntario_nome UNIQUE (nome)
);

CREATE INDEX idx_voluntario_setor
ON tb_voluntario(setor_id);