DROP TABLE IF EXISTS address,oauth,user;

create table address(
    seq bigint(255) not null auto_increment primary key,
    content varchar(255) not null,
    distinction int(255) not null
);

create table oauth(
    seq bigint(255) not null auto_increment primary key,
    name varchar(20) not null
);

create table user(
    seq bigint(255) not null auto_increment primary key,
    id varchar(30) not null,
    password varchar(30) not null,
    name varchar(30) not null,
    nickname varchar(20) not null,
    birthday varchar(10) not null,
    phone_number varchar(13) not null,
    email varchar(80) not null,
    address varchar(255) not null,
    role int(1) not null,
    grade int(1) not null,
    registered_at date not null
);