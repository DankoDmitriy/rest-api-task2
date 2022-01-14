delete from certificate_tag;
delete from tags;
delete from order_has_certificate;
delete from gift_certificates;
delete from orders;
delete from users;

ALTER TABLE tags AUTO_INCREMENT = 10;
ALTER TABLE certificate_tag AUTO_INCREMENT = 10;
ALTER TABLE gift_certificates AUTO_INCREMENT = 10;
ALTER TABLE users AUTO_INCREMENT = 10;

insert into tags (id, name)
values (1, 'tag1'),
       (2, 'tag2'),
       (3, 'tag3'),
       (4, 'tag4');

insert into gift_certificates (id, name, description, price, duration, create_date, last_update_date)
values (1, 'Gif 1', 'description 1', '9.99', 5, '2021-09-15 18:45:30', '2021-09-15 18:45:30'),
       (2, 'Gif 2', 'second description', '12.00', 10, '2021-09-15 18:45:30', '2021-09-15 18:45:30');

insert into certificate_tag (gift_certificate_id, tag_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2);

insert into users (id, name)
value   (1, 'User1'),
        (2, 'User2'),
        (3, 'User3');

insert into `orders` (id,cost, purchase_date, user_id)
value   (1,'9.99','2021-09-15 18:45:30',1);

insert into order_has_certificate(order_id, gift_certificate_id)
value   (1,1);