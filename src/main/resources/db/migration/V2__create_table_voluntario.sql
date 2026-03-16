CREATE TABLE tb_volunteer (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    department_id BIGINT NOT NULL,
    user_type VARCHAR(20),

    CONSTRAINT fk_volunteer_department
        FOREIGN KEY (department_id)
        REFERENCES tb_department(id),

    CONSTRAINT uk_volunteer_name UNIQUE (name)
);

CREATE INDEX idx_volunteer_department
ON tb_volunteer(department_id);