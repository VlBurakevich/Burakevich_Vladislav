CREATE TABLE tarifs
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50)    NOT NULL,
    type       tarif_type     NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL,
    unit_time  INT            NOT NULL
);