CREATE TABLE models
(
    id                BIGSERIAL PRIMARY KEY,
    model_name        VARCHAR(50) NOT NULL,
    max_speed         INT         NOT NULL,
    transport_type_id BIGINT REFERENCES transport_types (id)
);