CREATE TABLE tb_password_reset_token (
    id UUID PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiration TIMESTAMP NOT NULL,
    volunteer_id BIGINT,

    CONSTRAINT fk_user
        FOREIGN KEY (volunteer_id)
        REFERENCES tb_volunteer(id)
        ON DELETE CASCADE
);