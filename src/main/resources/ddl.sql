create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table cuisine (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id)) engine=InnoDB
create table ggroup (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ggroup_permission (ggroup_id bigint not null, permission_id bigint not null) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), freight_rate decimal(19,2) not null, name varchar(255) not null, registration_date datetime not null, update_date datetime not null, address_city_id bigint, cuisine_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table uuser (id bigint not null auto_increment, email varchar(255), name varchar(255), password varchar(255), registration_date datetime not null, primary key (id)) engine=InnoDB
create table uuser_ggroup (uuser_id bigint not null, ggroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table ggroup_permission add constraint FKqhqgepk4yuq5vifbhrr6194hd foreign key (permission_id) references permission (id)
alter table ggroup_permission add constraint FK6ae2p7tohxvw3mhmjblinaiab foreign key (ggroup_id) references ggroup (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKa1mopooywwfnvq23on35n6xdm foreign key (cuisine_id) references cuisine (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table uuser_ggroup add constraint FK9ju53bjt203dxuhbodwdlwf0 foreign key (ggroup_id) references permission (id)
alter table uuser_ggroup add constraint FKyxs3s5y9umb7wrw90xd3bktm foreign key (uuser_id) references uuser (id)
create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table cuisine (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id)) engine=InnoDB
create table ggroup (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ggroup_permission (ggroup_id bigint not null, permission_id bigint not null) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), freight_rate decimal(19,2) not null, name varchar(255) not null, registration_date datetime not null, update_date datetime not null, address_city_id bigint, cuisine_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table uuser (id bigint not null auto_increment, email varchar(255), name varchar(255), password varchar(255), registration_date datetime not null, primary key (id)) engine=InnoDB
create table uuser_ggroup (uuser_id bigint not null, ggroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table ggroup_permission add constraint FKqhqgepk4yuq5vifbhrr6194hd foreign key (permission_id) references permission (id)
alter table ggroup_permission add constraint FK6ae2p7tohxvw3mhmjblinaiab foreign key (ggroup_id) references ggroup (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKa1mopooywwfnvq23on35n6xdm foreign key (cuisine_id) references cuisine (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table uuser_ggroup add constraint FK9ju53bjt203dxuhbodwdlwf0 foreign key (ggroup_id) references permission (id)
alter table uuser_ggroup add constraint FKyxs3s5y9umb7wrw90xd3bktm foreign key (uuser_id) references uuser (id)
create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table cuisine (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id)) engine=InnoDB
create table ggroup (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ggroup_permission (ggroup_id bigint not null, permission_id bigint not null) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), freight_rate decimal(19,2) not null, name varchar(255) not null, registration_date datetime not null, update_date datetime not null, address_city_id bigint, cuisine_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table uuser (id bigint not null auto_increment, email varchar(255), name varchar(255), password varchar(255), registration_date datetime not null, primary key (id)) engine=InnoDB
create table uuser_ggroup (uuser_id bigint not null, ggroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table ggroup_permission add constraint FKqhqgepk4yuq5vifbhrr6194hd foreign key (permission_id) references permission (id)
alter table ggroup_permission add constraint FK6ae2p7tohxvw3mhmjblinaiab foreign key (ggroup_id) references ggroup (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKa1mopooywwfnvq23on35n6xdm foreign key (cuisine_id) references cuisine (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table uuser_ggroup add constraint FK9ju53bjt203dxuhbodwdlwf0 foreign key (ggroup_id) references permission (id)
alter table uuser_ggroup add constraint FKyxs3s5y9umb7wrw90xd3bktm foreign key (uuser_id) references uuser (id)
create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table cuisine (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id)) engine=InnoDB
create table ggroup (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ggroup_permission (ggroup_id bigint not null, permission_id bigint not null) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), freight_rate decimal(19,2) not null, name varchar(255) not null, registration_date datetime not null, update_date datetime not null, address_city_id bigint, cuisine_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table uuser (id bigint not null auto_increment, email varchar(255), name varchar(255), password varchar(255), registration_date datetime not null, primary key (id)) engine=InnoDB
create table uuser_ggroup (uuser_id bigint not null, ggroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table ggroup_permission add constraint FKqhqgepk4yuq5vifbhrr6194hd foreign key (permission_id) references permission (id)
alter table ggroup_permission add constraint FK6ae2p7tohxvw3mhmjblinaiab foreign key (ggroup_id) references ggroup (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKa1mopooywwfnvq23on35n6xdm foreign key (cuisine_id) references cuisine (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table uuser_ggroup add constraint FK9ju53bjt203dxuhbodwdlwf0 foreign key (ggroup_id) references permission (id)
alter table uuser_ggroup add constraint FKyxs3s5y9umb7wrw90xd3bktm foreign key (uuser_id) references uuser (id)
create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table cuisine (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id)) engine=InnoDB
create table ggroup (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ggroup_permission (ggroup_id bigint not null, permission_id bigint not null) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), freight_rate decimal(19,2) not null, name varchar(255) not null, registration_date datetime not null, update_date datetime not null, address_city_id bigint, cuisine_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table uuser (id bigint not null auto_increment, email varchar(255), name varchar(255), password varchar(255), registration_date datetime not null, primary key (id)) engine=InnoDB
create table uuser_ggroup (uuser_id bigint not null, ggroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table ggroup_permission add constraint FKqhqgepk4yuq5vifbhrr6194hd foreign key (permission_id) references permission (id)
alter table ggroup_permission add constraint FK6ae2p7tohxvw3mhmjblinaiab foreign key (ggroup_id) references ggroup (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKa1mopooywwfnvq23on35n6xdm foreign key (cuisine_id) references cuisine (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table uuser_ggroup add constraint FK9ju53bjt203dxuhbodwdlwf0 foreign key (ggroup_id) references permission (id)
alter table uuser_ggroup add constraint FKyxs3s5y9umb7wrw90xd3bktm foreign key (uuser_id) references uuser (id)
create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table cuisine (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id)) engine=InnoDB
create table ggroup (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ggroup_permission (ggroup_id bigint not null, permission_id bigint not null) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), freight_rate decimal(19,2) not null, name varchar(255) not null, registration_date datetime not null, update_date datetime not null, address_city_id bigint, cuisine_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table uuser (id bigint not null auto_increment, email varchar(255), name varchar(255), password varchar(255), registration_date datetime not null, primary key (id)) engine=InnoDB
create table uuser_ggroup (uuser_id bigint not null, ggroup_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table ggroup_permission add constraint FKqhqgepk4yuq5vifbhrr6194hd foreign key (permission_id) references permission (id)
alter table ggroup_permission add constraint FK6ae2p7tohxvw3mhmjblinaiab foreign key (ggroup_id) references ggroup (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKa1mopooywwfnvq23on35n6xdm foreign key (cuisine_id) references cuisine (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table uuser_ggroup add constraint FK9ju53bjt203dxuhbodwdlwf0 foreign key (ggroup_id) references permission (id)
alter table uuser_ggroup add constraint FKyxs3s5y9umb7wrw90xd3bktm foreign key (uuser_id) references uuser (id)
