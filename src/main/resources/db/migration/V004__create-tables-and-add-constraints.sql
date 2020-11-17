create table payment_method (
id bigint not null auto_increment,
 description varchar(60) not null,
 
 primary key (id)
 ) engine=InnoDB default charset=UTF8MB4;
 
create table ggroup (
id bigint not null auto_increment,
name varchar(60) not null,

 primary key (id)
 ) engine=InnoDB default charset=UTF8MB4;

create table ggroup_permission (
ggroup_id bigint not null,
 permission_id bigint not null,
 
primary key (ggroup_id, permission_id)
) engine=InnoDB default charset=UTF8MB4;

create table permission (
id bigint not null auto_increment,
 description varchar(255) not null,
 name varchar(255) not null,
 
 primary key (id)
) engine=InnoDB default charset=UTF8MB4;

create table product (
id bigint not null auto_increment,
 name varchar(80) not null,
 active tinyint(1) not null,
 description text not null,
 price decimal(10,2) not null,
 restaurant_id bigint not null,
 
 primary key (id)
) engine=InnoDB default charset=UTF8MB4;
 
create table restaurant (
id bigint not null auto_increment,
 cuisine_id bigint not null,
 name varchar(80) not null,
 freight_rate decimal(10,2) not null,
 registration_date datetime not null,
 update_date datetime not null,
 
 address_city_id bigint,
 address_neighborhood varchar(60),
 address_number varchar(20),
 address_street varchar(100),
 address_zip_code varchar(9),
 
 primary key (id)
 ) engine=InnoDB default charset=UTF8MB4;
 
create table restaurant_payment_method (
restaurant_id bigint not null,
 payment_method_id bigint not null,
 
 primary key (restaurant_id, payment_method_id)
 ) engine=InnoDB default charset=UTF8MB4;
 
 create table uuser (
 id bigint not null auto_increment,
 email varchar(80), 
 name varchar(80),
 password varchar(255),
 registration_date datetime not null,
 
 primary key (id)
 ) engine=InnoDB default charset=UTF8MB4;

create table uuser_ggroup (
uuser_id bigint not null,
ggroup_id bigint not null,

primary key (uuser_id, ggroup_id)
) engine=InnoDB default charset=UTF8MB4;

alter table ggroup_permission add constraint fk_group_permission_permission
 foreign key (permission_id) references permission (id);
 
alter table ggroup_permission add constraint fk_ggroup_permission_ggroup
 foreign key (ggroup_id) references ggroup (id);
 
alter table product add constraint fk_product_restaurant
 foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_city
 foreign key (address_city_id) references city (id);
 
alter table restaurant add constraint fk_restaurant_cuisine
 foreign key (cuisine_id) references cuisine (id);
 
alter table restaurant_payment_method add constraint fk_restaurant_payment_method_payment_method
 foreign key (payment_method_id) references payment_method (id);
 
alter table restaurant_payment_method add constraint fk_restaurant_payment_method_restaurant
 foreign key (restaurant_id) references restaurant (id);
 
alter table uuser_ggroup add constraint fk_uusuar_ggrupo_ggrupo
 foreign key (ggroup_id) references permission (id);
 
alter table uuser_ggroup add constraint fk_uuser_ggroup_uuser
 foreign key (uuser_id) references uuser (id);