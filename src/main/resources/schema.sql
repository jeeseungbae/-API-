DROP TABLE IF EXISTS address;

create table address(
    seq bigint(255) not null auto_increment primary key,
    content varchar(255) not null,
    distinction int(255) not null
);