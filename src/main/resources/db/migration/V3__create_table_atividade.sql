CREATE TABLE tb_activity (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    date DATE NOT NULL,
    description TEXT,
    duration_minutes INTEGER,
    volunteer_id BIGINT NOT NULL,
    activity_status VARCHAR(20) DEFAULT 'PENDENTE',

    CONSTRAINT fk_activity_volunter
        FOREIGN KEY (volunteer_id)
        REFERENCES tb_volunteer(id)
);

CREATE INDEX idx_activity_volunteer
ON tb_activity(volunteer_id);