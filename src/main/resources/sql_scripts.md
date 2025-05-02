-- create database if not exists postgres
-- create schema if not exists junits

-- when application starts
drop table if exists junits.users cascade;

create table junits.users (
	id bigserial not null,
	email varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	primary key (id)
);

alter table junits.users 
add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

-- POST
insert into junits.users(email, first_name, last_name) 
values (?, ?, ?);

-- GET
select
	user0_.id as id1_0_0_,
	user0_.email as email2_0_0_,
	user0_.first_name as first_na3_0_0_,
	user0_.last_name as last_nam4_0_0_
from
	junits.users user0_
where
	user0_.id =?
        
-- UPDATE
update junits.users
set email =?, first_name =?, last_name =? where id =?;
        
-- GET ALL
select
	user0_.id as id1_0_,
	user0_.email as email2_0_,
	user0_.first_name as first_na3_0_,
	user0_.last_name as last_nam4_0_
from
	junits.users user0_;
        
-- DELETE
delete from junits.users
where id =?;