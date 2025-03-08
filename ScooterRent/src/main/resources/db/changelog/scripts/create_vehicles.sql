CREATE TABLE vehicles
(
    id              BIGSERIAL PRIMARY KEY,
    model_id        BIGINT               NOT NULL,
    serial_number   VARCHAR(25)          NOT NULL,
    battery_level   INT CHECK (battery_level BETWEEN 0 AND 100),
    status          vehicles_status_enum NOT NULL,
    rental_point_id BIGINT REFERENCES rental_points (id),
    kilometrage     INT                  NOT NULL
);