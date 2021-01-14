set foreign_key_checks = 0;
 
delete from city;
delete from cuisine;
delete from state;
delete from payment_method;
delete from `group_`;
delete from group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from `user_`;
delete from user_group;
delete from restaurant_accountable_user;
delete from `order_`;
delete from `order_item`;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table `group_` auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table `user_` auto_increment = 1;
alter table `order_` auto_increment = 1;
alter table `order_item` auto_increment = 1;

insert into cuisine (id, name) values (1, 'Tailandesa');
insert into cuisine (id, name) values (2, 'Indiana');
insert into cuisine (id, name) values (3, 'Argentina');
insert into cuisine (id, name) values (4, 'Brasileira');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, shipping_fee, cuisine_id, registration_date, update_date, active, open, address_city_id, address_zip_code, address_street, address_number, address_neighborhood) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true,1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurant (id, name, shipping_fee, cuisine_id, registration_date, update_date, active, open) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, cuisine_id, registration_date, update_date, active, open) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, cuisine_id, registration_date, update_date, active, open) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, cuisine_id, registration_date, update_date, active, open) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, cuisine_id, registration_date, update_date, active, open) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into payment_method (id, description) values (1, 'Cartão de crédito');
insert into payment_method (id, description) values (2, 'Cartão de débito');
insert into payment_method (id, description) values (3, 'Dinheiro');

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cuisines');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cuisines');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into `group_` (name) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into group_permission (`group_id`, permission_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1); 

insert into `user_` (id, name, email, password, registration_date) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp);   

insert into user_group (`user_id`, `group_id`) values (1, 1), (1, 2), (2, 1);

insert into `user_` (id, name, email, password, registration_date) values
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);

insert into restaurant_accountable_user(restaurant_id, user_id) values (1, 5), (3, 5);

insert into `order_` (id, restaurant_id, user_client_id, payment_method_id, address_city_id, address_zip_code, address_street, address_number, address_neighborhood, status, creation_date, subtotal, shipping_fee, total_cost)
values (1, 1, 1, 1, 1, '38400-000', '500', 'Rua Floriano Peixoto', 'Brasil',
'CREATED', utc_timestamp, 298.90, 10, 308.90);

insert into `order_item` (id, `order_id`, product_id, amount, unit_cost, total_cost, note)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into `order_item` (id, `order_id`, product_id, amount, unit_cost, total_cost, note)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

insert into `order_` (id, restaurant_id, user_client_id, payment_method_id, address_city_id, address_zip_code, address_street, address_number, address_neighborhood, status, creation_date, subtotal, shipping_fee, total_cost)
values (2, 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Centro',
'CREATED', utc_timestamp, 79, 0, 79);

insert into `order_item` (id, `order_id`, product_id, amount, unit_cost, total_cost, note)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');