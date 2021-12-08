delete from certificate_tag;
delete from tag;
delete from gift_certificate;

ALTER TABLE tag AUTO_INCREMENT = 10;
ALTER TABLE certificate_tag AUTO_INCREMENT = 10;
ALTER TABLE gift_certificate AUTO_INCREMENT = 10;

insert into tag (id, name)
values (1, 'tag1'),
       (2, 'tag2'),
       (3, 'tag3');

insert into gift_certificate (id, name, description, price, duration, create_date, last_update_date)
values (1, 'Gif 1', 'description 1', '9.99', 5, '2021-09-15 18:45:30', '2021-09-15 18:45:30'),
       (2, 'Gif 2', 'second description', '12.00', 10, '2021-09-15 18:45:30', '2021-09-15 18:45:30');

insert into certificate_tag (gift_certificate_id, tag_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2);