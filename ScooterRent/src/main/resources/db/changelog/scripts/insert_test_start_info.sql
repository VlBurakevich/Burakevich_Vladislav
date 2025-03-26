INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO credentials (password, email)
VALUES ('$2a$10$3MBLKoz0sy/EegkvJspvH.w0ybnu6hYmPx.eH9ZutpDXXt3anerRi', 'admin@mail.com');

INSERT INTO users (username, balance, credential_id)
VALUES ('admin', 0.00, 1);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2);


INSERT INTO transport_types (type_name, base_price)
VALUES ('Bike', 5.00);

INSERT INTO transport_types (type_name, base_price)
VALUES ('Scooter', 10.00);


INSERT INTO models (model_name, max_speed, transport_type_id)
VALUES ('CityBike 2021', 25, 1);

INSERT INTO models (model_name, max_speed, transport_type_id)
VALUES ('TurboScooter X', 35, 2);


INSERT INTO tarifs (name, type, base_price, unit_time, transport_type_id)
VALUES ('Standard Hourly', 'HOURLY', 1.50, 60, 1);

INSERT INTO tarifs (name, type, base_price, unit_time, transport_type_id)
VALUES ('Premium Subscription', 'SUBSCRIPTION', 29.99, 1440, 2);


INSERT INTO discounts (name, type, value)
VALUES ('Summer Sale', 'PERCENTAGE', 20.00);

INSERT INTO discounts (name, type, value)
VALUES ('New User Discount', 'FIXED', 5.00);


INSERT INTO rental_points (point_name, location, capacity, point_type)
VALUES ('Central Park', '{"latitude": 40.785091, "longitude": -73.968285}'::jsonb, 50, 'MAIN');

INSERT INTO rental_points (point_name, location, capacity, point_type, parent_point_id)
VALUES ('Times Square', '{"latitude": 40.758896, "longitude": -73.985130}'::jsonb, 30, 'SECONDARY', 1);


INSERT INTO vehicles (model_id, serial_number, battery_level, status, rental_point_id, mileage)
VALUES (1, 123456, 85, 'AVAILABLE', 1, 120);

INSERT INTO vehicles (model_id, serial_number, battery_level, status, rental_point_id, mileage)
VALUES (2, 654321, 90, 'AVAILABLE', 2, 80);