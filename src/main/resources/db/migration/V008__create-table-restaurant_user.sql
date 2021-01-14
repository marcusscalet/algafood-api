create table restaurant_accountable_user(
restaurant_id bigint not null,
`user_id` bigint not null,

primary key(restaurant_id, `user_id`)
)engine=InnoDB default charset=UTF8MB4;

alter table restaurant_accountable_user add constraint fk_accountable_user_restaurant
foreign key (restaurant_id) references restaurant(id);

alter table restaurant_accountable_user add constraint fk_restaurant_accountable_user
foreign key (`user_id`) references `user_`(id);