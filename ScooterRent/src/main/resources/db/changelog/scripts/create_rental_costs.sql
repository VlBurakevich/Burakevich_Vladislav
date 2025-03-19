CREATE TABLE rental_costs
(
    id          BIGSERIAL PRIMARY KEY,
    start_time  TIMESTAMP      NOT NULL,
    end_time    TIMESTAMP      NOT NULL,
    tarif_id    BIGINT REFERENCES tarifs (id),
    discount_id BIGINT REFERENCES discounts (id),
    total_cost  DECIMAL(10, 2) NOT NULL
);