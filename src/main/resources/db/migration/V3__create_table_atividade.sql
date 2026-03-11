CREATE TABLE tb_atividade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    descricao TEXT,
    tempo_minutos INTEGER,
    voluntario_id BIGINT,
    status VARCHAR(20),

    CONSTRAINT fk_voluntario
        FOREIGN KEY (voluntario_id)
        REFERENCES tb_voluntario(id)
);