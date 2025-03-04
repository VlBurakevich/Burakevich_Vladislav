CREATE TABLE transport_types
(
    id         BIGSERIAL PRIMARY KEY,
    type_name  VARCHAR(50)    NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL
);