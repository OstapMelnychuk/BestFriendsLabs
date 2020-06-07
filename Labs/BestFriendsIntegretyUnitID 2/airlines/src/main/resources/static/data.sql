INSERT INTO roles(name)
VALUES ('ADMINISTRATOR'),
       ('DISPATCHER'),
       ('EMPLOYEE'),
       ('OWNER');

INSERT INTO positions(name)
VALUES ('PILOT'),
       ('NAVIGATOR'),
       ('OPERATOR'),
       ('STEWARDESS');

INSERT INTO airlines(name, country, city, address, longitude, attitude, web_site)
VALUES ('flyuia', 'Ukraine', 'Lviv', '168, Ljubinska Street, International Airport', null, null, 'https://www.flyuia.com/ua/en/home');

INSERT INTO airports(id, name, country, city, address, longitude, attitude)
VALUES (1, 'Boryspil International Airport', 'Ukraine', 'Kyiv', 'Boryspil, Kyiv Oblast, 08307', 30.893753, 50.337959),
       (2, 'Lviv Danylo Halytskyi International Airport', 'Ukraine', 'Lviv', 'Zaliznychnyi district, Lviv, Lviv Oblast, 79000', 23.959904, 49.813950),
       (3, 'Chernivtsi International Airport', 'Ukraine', 'Chernivtsi', 'Chkalova St, 30, Chernivtsi, Chernivtsi Oblast, 58000', 25.967127, 48.267083),
       (4, 'Dnipropetrovsk International Airport', 'Ukraine', 'Dnipro', 'Aeroport St, Dnipro, Dnipropetrovsk Oblast, 49000', 35.093017, 48.367939),
       (5, 'Ivano-Frankivsk International Airport', 'Ukraine', 'Ivano-Frankivsk', 'Vulytsya Yevhena Konovalʹtsya, 264А, Ivano-Frankivsk, Ivano-Frankivsk Oblast, 76000', 48.887624, 24.707292),
       (6, 'Kharkiv International Airport', 'Ukraine', 'Kharkiv', 'Romashkina St, 1, Kharkiv, Kharkiv Oblast, 61000', 36.280055, 49.922100),
       (7, 'Rivne International Airport', 'Ukraine', 'Rivne', 'Vulytsya Aviatoriv, 5 А, Velyka Omelyana, Rivne Oblast, 35360', 26.155670, 50.595844),
       (8, 'Uzhhorod International Airport','Ukraine', 'Uzhhorod', 'Sobranets''ka St, 145, Uzhhorod, Zakarpattia Oblast, 88000', 22.272304, 48.637334),
       (9, 'Sumy Airport', 'Ukraine', 'Sumy', 'Herasima Kondratieva St, 168, Sumy, Sumy Oblast, 40000', 34.757516, 50.861272),
       (10, 'Kherson International Airport','Ukraine', 'Kherson', 'Kherson, Kherson Oblast, 73000', 32.508432, 46.672061);


INSERT INTO offices(id, address, city, attitude, longitude, country, airline_name)
VALUES(1, '168, Ljubinska Street, International Airport', 'Lviv', null, null, 'Ukraine', 'flyuia'),
      (2, '201-203, Kharkivske Road. Nearest tube: Boryspilska.', 'Kyiv', null, null, 'Ukraine', 'flyuia');

INSERT INTO trips(id, departure_airport_id, arrival_airport_id, both_sides)
VALUES (1, 1, 2, true),
       (2, 1, 5, true),
       (3, 1, 8, true),
       (4, 2, 9, true),
       (5, 2, 6, true),
       (6, 2, 3, true),
       (7, 3, 7, true),
       (8, 3, 4, true),
       (9, 4, 10, true),
       (10, 5, 9, true),
       (11, 5, 2, true),
       (12, 6, 10, true),
       (13, 6, 3, true),
       (14, 7, 1, true),
       (15, 7, 5, true),
       (16, 8, 3, true),
       (17, 8, 5, true),
       (18, 9, 4, true),
       (19, 10, 1, true);

INSERT INTO airlines_trips(airlines_name, trips_id)
VALUES ('flyuia', 1),
       ('flyuia', 2),
       ('flyuia', 3),
       ('flyuia', 4),
       ('flyuia', 5),
       ('flyuia', 6),
       ('flyuia', 7),
       ('flyuia', 8),
       ('flyuia', 9),
       ('flyuia', 10),
       ('flyuia', 11),
       ('flyuia', 12),
       ('flyuia', 13),
       ('flyuia', 14),
       ('flyuia', 15),
       ('flyuia', 16),
       ('flyuia', 17),
       ('flyuia', 18),
       ('flyuia', 19);

INSERT INTO planes(id, capacity, attitude, longitude, model, status)
VALUES (1, 64, null, null, 'British Aerospace ATP', 'IN_HANGAR'),
       (2, 64, null, null, 'British Aerospace ATP', 'IN_HANGAR'),
       (3, 90, null, null, 'Bombardier DHC-8', 'IN_HANGAR'),
       (4, 90, null, null, 'Bombardier DHC-8', 'IN_FLIGHT');

INSERT INTO planes_seats_count(planes_id, seats_count, seats_count_key)
VALUES (1, 30, 'ECONOMY'),
       (1, 10, 'PREMIUM'),
       (1, 15, 'BUSINESS'),
       (1, 9, 'FIRST'),
       (2, 25, 'ECONOMY'),
       (2, 15, 'PREMIUM'),
       (2, 15, 'BUSINESS'),
       (2, 9, 'FIRST'),
       (3, 30, 'ECONOMY'),
       (3, 30, 'PREMIUM'),
       (3, 20, 'BUSINESS'),
       (3, 10, 'FIRST'),
       (4, 30, 'ECONOMY'),
       (4, 30, 'PREMIUM'),
       (4, 20, 'FIRST'),
       (4, 10, 'BUSINESS');

INSERT INTO employees(id, hire_date, email, first_name, second_name, home_address, phone_number, position_name, airline_name, office_id, password, refresh_token)
    VALUES (1, NOW(), 'lexi.buckrid@yahoo.com', 'Linda', 'Ramos', 'Santa Barbara, 4233 Par Drive', '770-207-7319', NULL, 'flyuia', 1, '$2a$10$BVl19.4dLt70oScnNBon4u7nr7Jhf5qem0UCB2SrV7HjDfQe8FCku', null), -- password: lexibuckrid
       (2, NOW(), 'BettyVBlack@jourrapide.com', 'Betty', 'Black', 'Orlando, FL 32809', '321-299-9214', NULL, 'flyuia', 1, '$2a$10$.85jrTxA6ntFOyPZlaPSoujbkpBDQV43vBBFOIkbPUTvNxiFfdGQ2', null), -- password: BettyVBlack
       (3, NOW(), 'NormanJAnderson@jourrapide.com', 'Norman ', 'Anderson', '2161 Thompson Street', '562-531-9225', NULL, 'flyuia', 1, '$2a$10$.hL8nLfpbWe9fT95AWo4yuhj54bnqgUPsCC2S13NGkBL1RpBbruMu', null), -- password: NormanJAnderson
       (4, NOW(), 'SueVRoseman@armyspy.com', 'Sue ', 'Roseman', 'Southfield, MI 48075', '810-729-7030', 'STEWARDESS', 'flyuia', 2, '$2a$10$Ppc4u6KTsawqaxn1ht/SuuIgaJcAtpvtAr1s0LtmXPHUeC9R8wMTO', null), -- password: SueVRoseman
       (5, NOW(), 'MaribethCCrawford@armyspy.com', 'Maribeth ', 'Crawford', '4538 Bagwell Avenue', '352-564-8575', 'STEWARDESS', 'flyuia', 2, '$2a$10$/2kXJnmMbL4YmdfKykJxVeO9gorfdYW5BmhL.SC7yHW0qP9WCymEm', null), -- password: MaribethCCrawford
       (6, NOW(), 'CarlosMRosenbaum@dayrep.com', 'Carlos ', 'Rosenbaum', '1165 Burnside Avenue', '435-691-1377', 'PILOT', 'flyuia', 2, '$2a$10$18tcSLyyoHKrKutTkw3.ReAoOflxgXX9lCi9CK/NzljYsTTzwxlV2', null ), -- password: CarlosMRosenbaum
       (7, NOW(), 'JefferyMJackson@rhyta.com', 'Jeffery ', 'Jackson', 'Santa Barbara, 4233 Par Drive', '817-301-0451', 'PILOT', 'flyuia', 2, '$2a$10$XHIRQJOSiEmGMfjUjBGeCObeDenFfCmJrg1QoBkpP9GdjIrHvZvyq', null ), -- password: JefferyMJackson
       (8, NOW(), 'RobinDScruggs@jourrapide.com', 'Robin ', 'Scruggs', '358 Reynolds Alley', '562-283-6876', 'NAVIGATOR', 'flyuia', 2, '$2a$10$pnmDAAp./MTLsCzAJBLLheT80aYMOW8eRKBhQl1/JgA4wq8ttUFfS', null), -- password: RobinDScruggs
       (9, NOW(), 'HelenBShelton@teleworm.us', 'Helen ', 'Shelton', '302 Lady Bug Drive', '719-206-6164', 'NAVIGATOR', 'flyuia', 2, '$2a$10$O2Na8lpmKFc3AcTFGaL43u8ISmBg8WPm6EoHBUwQ0gquC6EinXMHm', null), -- password: HelenBShelton
       (10, NOW(), 'BrandonHWatson@dayrep.com', 'Brandon ', 'Watson', '4010 Carter Street', '618-696-6752', 'OPERATOR', 'flyuia', 1, '$2a$10$SosNgMNyuWBlHf3iIok47eRRLqMJuXBMyClJ100EQJBzp9F2ANJEG', null), -- password: BrandonHWatson
       (11, NOW(), 'DarleneRRice@jourrapide.com', 'Darlene ', 'Rice', '2375 Echo Lane', '269-736-7719', 'OPERATOR', 'flyuia', 2, '$2a$10$.bU0InqW5RcoardhwREJqudqCE.RzEcaLmfqQGHdviJPTQZrj3l4m', null); -- password: DarleneRRice

INSERT INTO employees_roles(employees_id, roles_name)
VALUES (1, 'ADMINISTRATOR'),
       (2, 'DISPATCHER'),
       (3, 'OWNER'),
       (4, 'EMPLOYEE'),
       (5, 'EMPLOYEE'),
       (6, 'EMPLOYEE'),
       (7, 'EMPLOYEE'),
       (8, 'EMPLOYEE'),
       (9, 'EMPLOYEE'),
       (10, 'EMPLOYEE'),
       (11, 'EMPLOYEE');

INSERT INTO flights(id, actual_departure_time, actual_arrival_time, gate, planned_departure_time, planned_arrival_time, plane_id, airline_name, trip_id, flight_status)
VALUES (1, '2020-01-01 06:02', '2020-01-01 10:15', '08', '2020-01-01 06:00', '2020-01-01 10:00', 1, 'flyuia', 1, 'COMPLETED'),
       (3, '2020-02-01 12:34', '2020-01-01 19:22', '05', '2020-02-01 12:30', '2020-01-01 19:00', 2, 'flyuia', 2, 'COMPLETED'),
       (4, '2020-02-01 13:03', '2020-01-01 17:54', '05', '2020-02-01 13:00', '2020-01-01 17:50', 3, 'flyuia', 3, 'COMPLETED'),
       (5, null, null, '05', '2020-04-01 11:00', '2020-04-01 22:30', 4, 'flyuia', 4, 'PLANNED');
