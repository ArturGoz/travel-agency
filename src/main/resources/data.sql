-- Вставка тестового користувача
INSERT IGNORE  INTO users (id, username, password, phone_number, balance, account_status)
VALUES ('1', 'testuser', '$10$Sw23wgv4jCwQkTKDY3ozXOAItL/C4d4MXwtN7D1XHDgtONIzE77u.', '1234567890', 100.0, TRUE);

-- Призначення ролі користувачу
INSERT IGNORE  INTO user_roles (user_id, roles)
VALUES ('1', 'USER');

-- Вставка ваучера, який належить користувачу
INSERT IGNORE  INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
           '2',
           'Amazing Tour',
           'Description of amazing tour',
           299.99,
           'ADVENTURE',    -- значення enum TourType
           'BUS',        -- значення enum TransferType
           'FOUR_STARS',  -- значення enum HotelType
           'REGISTERED',  -- значення enum VoucherStatus
           '2025-04-01',
           '2025-04-10',
           '1',
           TRUE
       );

-- Voucher 3: Safari Expedition
INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '3',
    'Safari Expedition',
    'Experience the wild in a luxury safari adventure.',
    499.99,
    'SAFARI',      -- TourType
    'PLANE',       -- TransferType
    'FIVE_STARS',  -- HotelType
    'UNREGISTERED',        -- VoucherStatus
    '2025-05-01',
    '2025-05-07',
    null,
    FALSE
);

-- Voucher 4: Wine Tasting Tour
INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '4',
    'Wine Tasting Tour',
    'Indulge in a scenic wine tasting journey through renowned vineyards.',
    399.99,
    'WINE',        -- TourType
    'PRIVATE_CAR', -- TransferType
    'FOUR_STARS',  -- HotelType
    'UNREGISTERED',  -- VoucherStatus
    '2025-06-15',
    '2025-06-22',
    null,
    TRUE
);

-- Voucher 5: Cultural Heritage Walk
INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '5',
    'Cultural Heritage Walk',
    'Discover historical landmarks and immerse in local traditions.',
    259.99,
    'CULTURAL',   -- TourType
    'MINIBUS',     -- TransferType
    'THREE_STARS', -- HotelType
    'UNREGISTERED',-- VoucherStatus
    '2025-07-10',
    '2025-07-15',
    null,
    FALSE
);

-- Voucher 6: Health & Wellness Retreat
INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '6',
    'Health & Wellness Retreat',
    'Rejuvenate with a full package of wellness treatments and healthy cuisine.',
    549.99,
    'HEALTH',         -- TourType
    'ELECTRICAL_CARS',-- TransferType
    'FIVE_STARS',     -- HotelType
    'UNREGISTERED',           -- VoucherStatus
    '2025-08-01',
    '2025-08-08',
    null,
    TRUE
);

-- Voucher 7: Sports Adventure
INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '7',
    'Sports Adventure',
    'Enjoy an adrenaline-packed journey with various sporting activities.',
    329.99,
    'SPORTS',    -- TourType
    'TRAIN',     -- TransferType
    'TWO_STARS', -- HotelType
    'UNREGISTERED',  -- VoucherStatus
    '2025-09-05',
    '2025-09-12',
    null,
    FALSE
);

-- Voucher 8: Leisure Getaway
INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '8',
    'Leisure Getaway',
    'Relax and unwind on a peaceful vacation in a picturesque location.',
    279.99,
    'LEISURE',   -- TourType
    'SHIP',      -- TransferType
    'FOUR_STARS',-- HotelType
    'UNREGISTERED',-- VoucherStatus
    '2025-10-20',
    '2025-10-27',
    null,
    TRUE
);


INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '9',
    'Relaxing tour',
    'Relax and unwind on a peaceful vacation in a picturesque location.',
    279.99,
    'LEISURE',   -- TourType
    'SHIP',      -- TransferType
    'FOUR_STARS',-- HotelType
    'UNREGISTERED',-- VoucherStatus
    '2025-10-20',
    '2025-10-27',
    null,
    TRUE
);


INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '10',
    'Adventure Awaits: Your Exclusive Travel Voucher',
    'Relax and unwind on a peaceful vacation in a picturesque location.',
    244.99,
    'LEISURE',   -- TourType
    'SHIP',      -- TransferType
    'FOUR_STARS',-- HotelType
    'UNREGISTERED',-- VoucherStatus
    '2025-10-20',
    '2025-10-27',
    null,
    TRUE
);

INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '11',
    'Explore More: Special Travel Pass',
    'Relax and unwind on a peaceful vacation in a picturesque location.',
    254.99,
    'LEISURE',   -- TourType
    'SHIP',      -- TransferType
    'FOUR_STARS',-- HotelType
    'UNREGISTERED',-- VoucherStatus
    '2025-10-20',
    '2025-10-27',
    null,
    TRUE
);

INSERT IGNORE INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date, eviction_date, users, is_hot)
VALUES (
    '12',
    'Wanderlust Approved: Your Ticket to New Destinations',
    'Relax and unwind on a peaceful vacation in a picturesque location.',
    264.99,
    'LEISURE',   -- TourType
    'SHIP',      -- TransferType
    'FOUR_STARS',-- HotelType
    'UNREGISTERED',-- VoucherStatus
    '2025-10-20',
    '2025-10-27',
    null,
    TRUE
);