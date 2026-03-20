CREATE TABLE tb_volunteer (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    department_id BIGINT NOT NULL,
    user_type VARCHAR(20) NOT NULL DEFAULT 'VOLUNTEER',

    CONSTRAINT fk_volunteer_department
        FOREIGN KEY (department_id)
        REFERENCES tb_department(id)
);

CREATE INDEX idx_volunteer_department ON tb_volunteer(department_id);
CREATE INDEX idx_volunteer_email ON tb_volunteer(email);
