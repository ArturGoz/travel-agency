-- Вставка тестового користувача
INSERT INTO users (id, username, password, phone_number, balance, account_status)
VALUES ('11111111-1111-1111-1111-111111111111', 'testuser', 'password123', '1234567890', 100.0, TRUE);

-- Призначення ролі користувачу
INSERT INTO user_roles (user_id, roles)
VALUES ('11111111-1111-1111-1111-111111111111', 'USER');

-- Вставка ваучера, який належить користувачу
INSERT INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
           '22222222-2222-2222-2222-222222222222',
           'Amazing Tour',
           'Description of amazing tour',
           299.99,
           'ADVENTURE',    -- значення enum TourType
           'BUS',        -- значення enum TransferType
           'FOUR_STARS',  -- значення enum HotelType
           'REGISTERED',  -- значення enum VoucherStatus
           '2025-04-01',
           '2025-04-10',
           '11111111-1111-1111-1111-111111111111',
           TRUE
       );

