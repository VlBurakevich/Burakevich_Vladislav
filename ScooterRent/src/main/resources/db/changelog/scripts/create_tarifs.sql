CREATE TABLE tarifs
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(50)     NOT NULL,
    type              tarif_type_enum NOT NULL,
    base_price        DECIMAL(10, 2)  NOT NULL,
    unit_time         INT             NOT NULL,
    transport_type_id BIGINT REFERENCES transport_types (id)
);