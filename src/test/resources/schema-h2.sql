-- Таблиця для користувачів
CREATE TABLE IF NOT EXISTS users
(
    id
    VARCHAR
(
    36
) PRIMARY KEY,
    username VARCHAR
(
    255
) NOT NULL,
    password VARCHAR
(
    255
),
    phone_number VARCHAR
(
    255
),
    balance DOUBLE,
    account_status BOOLEAN
    );

-- Таблиця для ролей користувачів (join table для ElementCollection)
CREATE TABLE IF NOT EXISTS user_roles
(
    user_id
    VARCHAR
(
    36
) NOT NULL,
    roles VARCHAR
(
    255
) NOT NULL,
    PRIMARY KEY
(
    user_id,
    roles
),
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
)
    );

-- Таблиця для ваучерів
CREATE TABLE IF NOT EXISTS voucher
(
    id
    VARCHAR
(
    36
) PRIMARY KEY,
    title VARCHAR
(
    255
),
    description VARCHAR
(
    1024
),
    price DOUBLE,
    tour_type VARCHAR
(
    255
),
    transfer_type VARCHAR
(
    255
),
    hotel_type VARCHAR
(
    255
),
    status VARCHAR
(
    255
),
    arrival_date DATE,
    eviction_date DATE,
    users VARCHAR
(
    36
), -- відповідно до @JoinColumn(name = "user")
    is_hot BOOLEAN,
    FOREIGN KEY
(
    users
) REFERENCES users
(
    id
)
    );

