CREATE TABLE tb_voluntario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    setor_id BIGINT NOT NULL,
    tipo_usuario VARCHAR(20),

    CONSTRAINT fk_setor
        FOREIGN KEY (setor_id)
        REFERENCES tb_setor(id)
);