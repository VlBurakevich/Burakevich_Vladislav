CREATE TABLE discounts
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(50)        NOT NULL,
    type  discount_type_enum NOT NULL,
    value DECIMAL(10, 2)     NOT NULL
);