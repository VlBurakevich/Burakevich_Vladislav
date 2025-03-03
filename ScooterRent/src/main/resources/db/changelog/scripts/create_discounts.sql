CREATE TABLE discounts
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(50)    NOT NULL,
    type  discount_type  NOT NULL,
    value DECIMAL(10, 2) NOT NULL
);