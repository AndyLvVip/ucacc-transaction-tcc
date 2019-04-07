create database tcc_product;
use tcc_product;
drop table if exists product;

create table product (
  id char(36) not null,
  storage int not null,
  status varchar(10) not null,
  primary key (id)
) engine = Innodb, charset = utf8;