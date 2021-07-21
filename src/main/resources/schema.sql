DROP TABLE IF EXISTS Products,address,User,category,item,partner,review,shopping_basket,purchase;

create table address(
    seq bigint(255) not null auto_increment primary key,
    content varchar(255) not null,
    distinction int(255) not null
);

CREATE TABLE User
(
    seq bigint(255) not null auto_increment primary key,
    id varchar(255) not null,
    password varchar(255) not null,
    nickname varchar(100) not null,
    birthday date,
    phone_number varchar(13) not null,
    email varchar(100) not null,
    role int(10) not null,
    oauth_id int(10) not null,
    grade int(10) not null,
    registered_at datetime,
    unregistered_at int(10),
    updated_at datetime,
    updated_by text,
    address_seq bigint(255) not null,
    foreign key (address_seq) references address (seq)
);

create table category(
    seq bigint(255) not null auto_increment primary key,
    name varchar(255) not null
);

create table item(
    seq bigint(255) not null auto_increment primary key,
    name varchar(255) not null,
    category_seq bigint(255) not null
);

create table partner(
    seq bigint(255) not null auto_increment primary key,
    name varchar(255) not null,
    phone_number varchar(13) not null,
    grade int(3) not null,
    registered_at datetime,
    unregistered_at int(10),
    updated_at datetime,
    updated_by text,
    address_seq bigint(255)
);

create table product(
    seq bigint(255) not null auto_increment primary key,
    name varchar(255) not null,
    price int(20) not null,
    delivery int(10) not null,
    quantity int(10) not null,
    contents text not null,
    restock int(3),
    img varchar(255),
    registered_at datetime,
    unregistered_at int(10),
    updated_at datetime,
    updated_by text,
    item_seq bigint(255) not null,
    partner_seq bigint(255) not null,
    foreign key (item_seq) references item(seq),
    foreign key (partner_seq) references  partner(seq)
);

create table review(
    seq bigint(255) not null auto_increment primary key,
    contents text not null,
    score int not null,
    registered_at datetime,
    unregistered_at int(10),
    updated_at datetime,
    updated_by text,
    user_seq bigint(255) not null,
    product_seq bigint(255) not null,
    foreign key (user_seq) references User(seq),
    foreign key (product_seq) references product(seq)
);

create table shopping_basket(
    seq bigint(255) not null auto_increment primary key,
    quantity int(10) not null,
    price int(20) not null,
    registered_at datetime,
    unregistered_at int(10),
    updated_at datetime,
    updated_by text,
    product_seq bigint(255) not null,
    user_seq bigint(255) not null,
    foreign key (product_seq) references product(seq),
    foreign key (user_seq) references user (seq)
);

create table purchase(
    seq bigint(255) not null auto_increment primary key,
    price int(20) not null,
    quantity int(10) not null,
    registered_at datetime,
    unregistered_at int(10),
    updated_at datetime,
    updated_by text,
    product_seq bigint(255) not null,
    user_seq bigint(255) not null,
    foreign key (product_seq) references product(seq),
    foreign key (user_seq) references user (seq)
);



