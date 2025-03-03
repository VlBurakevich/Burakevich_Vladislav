CREATE TYPE point_type AS ENUM ('main', 'secondary');
CREATE TYPE scooter_status AS ENUM ('available', 'rented', 'charging');
CREATE TYPE tarif_type AS ENUM ('hourly', 'subscription');
CREATE TYPE discount_type AS ENUM ('percentage', 'fixed');