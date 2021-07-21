insert into address(content,distinction) values ('서울 중랑구',1);
insert into address(content,distinction) values ('경기도 수원시',1);
insert into address(content,distinction) values ('경기도 고양시',2);
insert into address(content,distinction) values ('경기도 동두천시',2);

insert into user(id,password,nickname,birthday,phone_number,email,role,oauth_id,grade,registered_at,address_seq)
values('test1','test1234','테스트','2001-02-23','010-4231-0000','sdgew@naver.com',1,1,1,now(),1);

insert into user(id,password,nickname,birthday,phone_number,email,role,oauth_id,grade,registered_at,address_seq)
values('test2','test2134','테스트','2000-02-23','010-1234-0000','wegwh23@naver.com',2,1,1,now(),2);

insert into category(name) values ('상의');
insert into category(name) values ('아우터');
insert into category(name) values ('바지');
insert into category(name) values ('가방');
insert into category(name) values ('신발');

insert into item(name,category_seq) values('반팔 티셔츠',1); --1
insert into item(name,category_seq) values('긴팔 티셔츠',1); --2
insert into item(name,category_seq) values('맨투맨',1); --3
insert into item(name,category_seq) values('니트',1); --4
insert into item(name,category_seq) values('코트',2); --5
insert into item(name,category_seq) values('패딩',2); --6
insert into item(name,category_seq) values('팬츠',3); --7
insert into item(name,category_seq) values('트레이닝',3); --8
insert into item(name,category_seq) values('백팩',4); --9
insert into item(name,category_seq) values('크로스백',4); --10
insert into item(name,category_seq) values('운동화',5); --11
insert into item(name,category_seq) values('샌들',5); --12

insert into partner(name,phone_number,grade,registered_at,address_seq) values('나이키','010-0000-1111',1,now(),3);
insert into partner(name,phone_number,grade,registered_at,address_seq) values('아디다스','010-0000-2222',1,now(),4);

insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('반팔티',10000,2000,10,'평범한 반팔티',1,now(),1,1);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('긴팔티',10000,2000,12,'평범한 긴팔티',2,now(),2,2);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('맨투맨',20000,2000,7,'빨간 맨투맨 티',1,now(),3,2);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('니트',20000,2000,10,'파란 니트',1,now(),4,2);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('코트',170000,2000,10,'검정 코트',2,now(),5,1);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('패딩',300000,0,10,'빨간 패딩',1,now(),6,1);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('팬츠',15000,2000,10,'파랑 팬츠',2,now(),7,1);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('트레이닝',15000,2000,10,'주황 트레이닝',1,now(),8,1);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('백팩',50000,0,10,'검정 백팩',1,now(),9,2);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('크로스백',30000,0,10,'검정 크로스백',2,now(),10,1);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('운동화',100000,2000,10,'초록 운동화',1,now(),11,2);
insert into product(name,price,delivery,quantity,contents,restock,registered_at,item_seq,partner_seq) values('샌들',30000,2000,10,'하얀 샌들',1,now(),12,1);

insert into review(contents,score,registered_at,user_seq,product_seq) values('좋아요',3,now(),1,3);
insert into review(contents,score,registered_at,user_seq,product_seq) values('좋아요',3,now(),2,5);
insert into review(contents,score,registered_at,user_seq,product_seq) values('좋아요',3,now(),1,7);

insert into shopping_basket(quantity,price,product_seq,user_seq,registered_at) values(3,62000,4,1,now());
insert into shopping_basket(quantity,price,product_seq,user_seq,registered_at) values(1,32000,12,2,now());

insert into purchase(price,quantity,registered_at,product_seq,user_seq) values(12000,1,now(),1,1);