
INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');


INSERT INTO transport_types (type_name, base_price)
VALUES ('Bike', 5.00);

INSERT INTO transport_types (type_name, base_price)
VALUES ('Scooter', 10.00);


INSERT INTO models (model_name, max_speed, transport_type_id)
VALUES ('CityBike 2021', 25, 1);

INSERT INTO models (model_name, max_speed, transport_type_id)
VALUES ('TurboScooter X', 35, 2);


INSERT INTO tarifs (name, type, base_price, unit_time, transport_type_id)
VALUES ('Standard Hourly', 'hourly', 1.50, 60, 1);

INSERT INTO tarifs (name, type, base_price, unit_time, transport_type_id)
VALUES ('Premium Subscription', 'subscription', 29.99, 1440, 2);


INSERT INTO discounts (name, type, value)
VALUES ('Summer Sale', 'percentage', 20.00);

INSERT INTO discounts (name, type, value)
VALUES ('New User Discount', 'fixed', 5.00);


INSERT INTO rental_points (point_name, location, capacity, point_type)
VALUES ('Central Park', 'New York', 50, 'main');

INSERT INTO rental_points (point_name, location, capacity, point_type, parent_point_id)
VALUES ('Times Square', 'New York', 30, 'secondary', 1);


INSERT INTO vehicles (model_id, serial_number, battery_level, status, rental_point_id, kilometrage)
VALUES (1, 123456, 85, 'available', 1, 120);

INSERT INTO vehicles (model_id, serial_number, battery_level, status, rental_point_id, kilometrage)
VALUES (2, 654321, 90, 'available', 2, 80);