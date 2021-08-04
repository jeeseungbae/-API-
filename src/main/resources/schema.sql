DROP TABLE IF EXISTS product;

create table product(
    seq bigint(255) not null auto_increment primary key,
    name varchar(255) not null,
    price int(20) not null
);