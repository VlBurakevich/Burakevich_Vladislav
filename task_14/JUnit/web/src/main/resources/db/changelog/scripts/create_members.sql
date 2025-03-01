CREATE TABLE members
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(30) NOT NULL,
    last_name   VARCHAR(30) NOT NULL,
    nationality VARCHAR(30),
    type        member_type NOT NULL,
    gender      gender_type
);