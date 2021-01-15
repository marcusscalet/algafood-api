create table `order_` (
id bigint not null auto_increment,
subtotal decimal(10,2) not null,
shipping_fee decimal(10,2) not null,
total_cost decimal(10,2) not null,

creation_date timestamp not null,
cancellation_date timestamp null,
delivered_date timestamp null,
confirmation_date timestamp null,
status varchar(10) not null,
 
payment_method_id bigint not null,
restaurant_id bigint not null,
user_client_id bigint not null,
    
address_city_id bigint,
address_neighborhood varchar(60),
address_number varchar(20),
address_street varchar(100),
address_zip_code varchar(9),

constraint `fk_order__address_city` foreign key (address_city_id) references city (id),
constraint `fk_order__restaurant` foreign key (restaurant_id) references restaurant (id),
constraint `fk_order__user_client` foreign key (user_client_id) references `user_` (id),
constraint `fk_order__payment_method` foreign key (payment_method_id) references payment_method(id),
    
 primary key (id)
 )engine=InnoDB default charset=utf8mb4;
 
 
create table `order_item` (
id bigint not null auto_increment,
amount smallint(6) not null,
unit_cost decimal(10,2) not null,
total_cost decimal(10,2) not null,
note varchar(255) null,
`order_id` bigint not null,
product_id bigint not null,
    
primary key (id),
unique key `uk_order_item_product` (`order_id`, product_id),

constraint `fk_order_item_order` foreign key (`order_id`) references `order_` (id),
constraint `fk_order_item_product` foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8mb4;
