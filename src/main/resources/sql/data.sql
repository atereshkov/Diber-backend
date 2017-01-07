INSERT INTO `users` (`id`, `username`, `email`, `password`, `enabled`)
VALUES (
  1,
  'John',
  'user@handioq.ru',
  '$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG',
  1
);

INSERT INTO `users` (`id`, `username`, `email`, `password`, `enabled`)
VALUES (
  2,
  'Andrew',
  'admin@handioq.ru',
  '$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG',
  1
);

INSERT INTO `roles` (`id`, `name`) VALUES (1, 'ROLE_USER');
INSERT INTO `roles` (`id`, `name`) VALUES (2, 'ROLE_ADMIN');

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES (2, 2);


INSERT INTO `shops` (`id`, `address`, `name`)
VALUES  (1, '20 Suvorova str.', 'Shop name 1');

INSERT INTO `shops` (`id`, `address`, `name`)
VALUES  (2, '10 Suvorova str.', 'Shop name 2');


INSERT INTO `orders` (`id`, `description`, `shop_id`, `date`, `delivery_price`, `status`)
VALUES (1, 'Description about order with id 1', 2, '2017-01-20 12:32:02', '20.0', 'New');

INSERT INTO `orders` (`id`, `description`, `shop_id`, `date`, `delivery_price`, `status`)
VALUES (2, 'Description about order with id 2', 2, '2017-02-12 02:52:05', '100.0', 'New');


INSERT INTO `reviews` (`id`, `review`, `rating`, `user_id`)
VALUES (1, 'Review 111', 5, 1);

INSERT INTO `reviews` (`id`, `review`, `rating`, `user_id`)
VALUES (2, 'Review 22222', 3, 2);