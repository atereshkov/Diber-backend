INSERT INTO  users  (id, username, email, password, enabled)
VALUES (
  1,
  'John',
  'user@handioq.ru',
  '$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG',
  TRUE
);

INSERT INTO  users  (id, username, email, password, enabled)
VALUES (
  2,
  'Andrew',
  'admin@handioq.ru',
  '$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG',
  TRUE
);

INSERT INTO roles (id, name) VALUES (1, 'ROLE_CUSTOMER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_COURIER');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 3);


INSERT INTO shops (id, address, name)
VALUES  (1, '20 Suvorova str.', 'Shop name 1');

INSERT INTO shops (id, address, name)
VALUES  (2, '10 Suvorova str.', 'Shop name 2');


INSERT INTO orders (id, description, shop_id, date, customer_id, courier_id, delivery_price, status, address_from, address_to)
VALUES (1, 'Description about order with id 1', 2,'2017-01-20 12:32:02', 1, 2, '20.0', 'New', 'Addr from 1', 'Addr to 2');

INSERT INTO orders (id, description, shop_id, date, customer_id, courier_id, delivery_price, status, address_from, address_to)
VALUES (2, 'Description about order with id 2', 2,'2017-02-12 02:52:05', 1, 2, '100.0', 'New', 'Addr from 21', 'Addr to 123');


INSERT INTO reviews (id, review, rating, user_id)
VALUES (1, 'Review 111', 5, 1);

INSERT INTO reviews (id, review, rating, user_id)
VALUES (2, 'Review 22222', 3, 2);


INSERT INTO requests (id, courier_id, order_id)
VALUES (1, 1, 1);

INSERT INTO requests (id, courier_id, order_id)
VALUES (2, 2, 2);


INSERT INTO addresses (id, address, city, country, phone, postal_code, region, user_id, name)
VALUES (1, '5 Green Avenue, Apt. 3', 'London', 'Великобритания', '+324861461545', '123456', 'WRS', 1, 'Работа');

INSERT INTO addresses (id, address, city, country, phone, postal_code, region, user_id, name)
VALUES (2, '20 Lombard Str.', 'Minsk', 'Belarus', '+375292223312', '123456', 'Minsk', 2, 'Home');

INSERT INTO addresses (id, address, city, country, phone, postal_code, region, user_id, name)
VALUES (3, '32 Lombard Str.', 'Grodno', 'Belarus', '+375291121312', '654321', 'Grodno', 1, 'Home');