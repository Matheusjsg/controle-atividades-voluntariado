CREATE TABLE tb_volunteer_profile (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    volunteer_id BIGINT UNIQUE NOT NULL,
    cpf VARCHAR(14),
    phone VARCHAR(20),
    logradouro VARCHAR(255),
    numero VARCHAR(10),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(9),
    birth_date DATE,

    CONSTRAINT fk_profile_volunteer
        FOREIGN KEY (volunteer_id)
        REFERENCES tb_volunteer(id)
);
