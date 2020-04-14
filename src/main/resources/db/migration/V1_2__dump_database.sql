INSERT INTO color (id, color_name) VALUES (1, 'white');
INSERT INTO color (id, color_name) VALUES (2, 'black');
INSERT INTO color (id, color_name) VALUES (3, 'red');
INSERT INTO blank_shirt (id, size, color_id, quantity, price) VALUES ('WS001', 'S', 1, 10, 20);
INSERT INTO blank_shirt (id, size, color_id, quantity, price) VALUES ('BM002', 'M', 2, 20, 20);
INSERT INTO blank_shirt (id, size, color_id, quantity, price) VALUES ('WM006', 'M', 1, 8, 30);
INSERT INTO blank_shirt (id, size, color_id, quantity, price) VALUES ('RXS005', 'XS', 3, 14, 25);
INSERT INTO blank_shirt (id, size, color_id, quantity, price) VALUES ('BL004', 'L', 2, 0, 25);
INSERT INTO blank_shirt (id, size, color_id, quantity, price) VALUES ('RL003', 'L', 3, 15, 30);
INSERT INTO role (id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, role_name) VALUES (2, 'ROLE_USER');
INSERT INTO m_user (id, login, password, full_name, city, street, zip, phone_number, mail, birth_date, role_id) VALUES (9, 'marta_winc', '$2a$10$UioHUIVzZjoRvlDL.vOvE.6wi0PylBAfqxPKsjpiujVPK0sWi9DaG', 'Marta Vintskevich', 'Minsk', 'Pritytskogo', 220200, '+375291335851', 'mar_win92@mail.ru', '1992-12-21', 1);

