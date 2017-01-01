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

INSERT INTO `products` (`id`, `description`, `image`, `name`, `price`)
VALUES (1, 'Description about product with id 1',
  'https://pp.vk.me/c604623/v604623974/bc73/y7RxNusXW8s.jpg', 'Product 1 name', '20.0');

INSERT INTO `products` (`id`, `description`, `image`, `name`, `price`)
VALUES (2, 'Description about product with id 2',
  'https://pp.vk.me/c836736/v836736416/3fbb/Df6ex4KID8k.jpg', 'Product 2 name', '15.0');

INSERT INTO `images` (`id`, `image_url`, `product_id`)
VALUES('1', 'https://images-eu.ssl-images-amazon.com/images/G/31/img15/Shoes/CatNav/p._V293117552_.jpg', '1');

INSERT INTO `images` (`id`, `image_url`, `product_id`)
VALUES('2', 'https://images-eu.ssl-images-amazon.com/images/G/31/img15/Shoes/CatNav/k._V293117556_.jpg', '1');