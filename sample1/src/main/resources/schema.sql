CREATE TABLE users
(
    id         UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    first_name varchar(100),
    last_name  varchar(100)
);
