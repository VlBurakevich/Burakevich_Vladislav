CREATE TABLE rentals
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT REFERENCES users (id),
    scooter_id     BIGINT REFERENCES scooters (id),
    start_point_id BIGINT REFERENCES rental_points (id),
    end_point_id   BIGINT REFERENCES rental_points (id),
    created_at     TIMESTAMP NOT NULL
);