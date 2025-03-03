CREATE TABLE rental_points
(
    id              BIGSERIAL PRIMARY KEY,
    point_name      VARCHAR(50) NOT NULL,
    location        VARCHAR(64) NOT NULL,
    capacity        INT         NOT NULL,
    point_type      point_type  NOT NULL,
    parent_point_id BIGINT REFERENCES rental_points (id)
);