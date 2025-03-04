CREATE TABLE rentals
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT REFERENCES users (id),
    vehicles_id    BIGINT REFERENCES vehicles (id),
    start_point_id BIGINT REFERENCES rental_points (id),
    end_point_id   BIGINT REFERENCES rental_points (id),
    created_at     TIMESTAMP NOT NULL
);