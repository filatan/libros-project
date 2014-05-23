drop database if exists librosdb;
create database librosdb;
 
use librosdb;
 

create table users (
username	varchar(20) not null primary key,
pass varchar (20) not null, 
name	varchar(70) not null,
email	varchar(255) not null
);

create table user_roles (
	username	varchar(20) not null,
	rolename 	varchar(20) not null,
	foreign key (username) references users(username) on delete cascade,
	primary key (username, rolename)
);

create table libros (
libroid int not null auto_increment primary key,
titulo varchar(200) not null,
lengua varchar(200) not null,
autor	  varchar(100) not null,
edicion	varchar(500) not null,
fechaed	date,
fechaimp date
);

create table reviews (
reviewid int not null auto_increment unique,
libroid int not null,
username varchar (200) not null, 
review varchar(500),
fecha timestamp not null, 
foreign key(username) references users(username) on delete cascade,
foreign key(libroid) references libros(libroid) on delete cascade,
primary key (username, libroid)
);
