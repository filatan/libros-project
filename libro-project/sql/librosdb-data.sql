source librosdb-schema.sql;
insert into users values('alicia', 'alicia', 'Alicia', 'alicia@acme.com');
insert into users values('blas', 'blas', 'Blas', 'blas@acme.com');
insert into users values('administrador', 'administrador', 'Admin', 'admin@acme.com');
insert into users values('test', 'test', 'Test', 'test@acme.com');

insert into user_roles values('administrador', 'administrador');
insert into user_roles values('test', 'registered');
insert into user_roles values('blas', 'registered');
insert into user_roles values('alicia', 'registered');


insert into libros (titulo, autor,lengua, edicion) values('els narradors', 'Rafik', 'catalan', 'la magrana' );
insert into libros (titulo, autor, lengua, edicion) values('titulo2', 'autor2',  'spanish', 'la magrana' );
insert into libros (titulo, autor, lengua, edicion) values('titulo3', 'autor3',  'spanish', 'la magrana' );
insert into libros (titulo, autor, lengua, edicion) values('titulo4', 'autor4',  'spanish', 'la magrana' );
insert into libros (titulo, autor, lengua, edicion) values('titulo5', 'autor5',  'spanish', 'vives' );
insert into libros (titulo, autor, lengua, edicion) values('titulo6', 'autor6',  'spanish', 'vives');
insert into libros (titulo, autor, lengua, edicion) values('titulo7', 'autor7',  'spanish', 'vives' );
insert into libros (titulo, autor, lengua, edicion) values('titulo8', 'autor8',  'spanish', 'vives' );
insert into libros (titulo, autor, lengua, edicion) values('titulo9', 'autor9',  'spanish', 'vives' );
insert into libros (titulo, autor, lengua, edicion) values('titulo10', 'autor10',  'spanish', 'vives' );

insert into reviews (libroid, username, review) values (01, 'blas', 'el libro mola');
insert into reviews (libroid, username, review) values (02, 'blas', 'el libro mola');
insert into reviews (libroid, username, review) values (03, 'blas', 'el libro mola');
insert into reviews (libroid, username, review) values (04, 'blas', 'el libro mola');
insert into reviews (libroid, username, review) values (05, 'test', 'el libro mola');
insert into reviews (libroid, username, review) values (06, 'test', 'el libro mola');
insert into reviews (libroid, username, review) values (07, 'test', 'el libro mola');
insert into reviews (libroid, username, review) values (08, 'alicia', 'el libro mola');
insert into reviews (libroid, username, review) values (09, 'alicia', 'el libro mola');
insert into reviews (libroid, username, review) values (10, 'alicia', 'el libro mola');