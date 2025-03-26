CREATE TABLE discounts
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(50)        NOT NULL,
    type  discount_type_enum NOT NULL,
    value DECIMAL            NOT NULL
);