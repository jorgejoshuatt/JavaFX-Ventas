SET NAMES 'utf8';
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE IF NOT EXISTS ventas DEFAULT CHARACTER SET utf8;
USE ventas;
CREATE TABLE clientes(
id_clientes					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_clientes				VARCHAR(25) NOT NULL, 
apellido_clientes			VARCHAR(25) NOT NULL
)DEFAULT CHARACTER SET utf8;

INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente1','Apellido1');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente2','Apellido2');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente3','Apellido3');

CREATE TABLE facturas(
id_facturas					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
id_clientes					INTEGER NOT NULL,
referencia_facturas		    VARCHAR(40) NOT NULL,
fecha_facturas				DATE NOT NULL, 
FOREIGN KEY(id_clientes) REFERENCES clientes(id_clientes)
)DEFAULT CHARACTER SET utf8;

INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(1,'FAC1231',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(2,'FAC1131',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(3,'FAC1331',NOW());

CREATE TABLE productos(
id_productos					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_productos				VARCHAR(80) NOT NULL, 
precio_productos				DOUBLE NOT NULL
)DEFAULT CHARACTER SET utf8;

INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto1',10.23);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto2',1.12);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto3',23.30);

CREATE TABLE facturas_productos(
id_facturas					INTEGER NOT NULL,
id_productos					INTEGER NOT NULL,
cantidad_facturas_productos	DOUBLE NOT NULL,
PRIMARY KEY(id_facturas,id_productos),
FOREIGN KEY(id_facturas) REFERENCES facturas(id_facturas),
FOREIGN KEY(id_productos) REFERENCES productos(id_productos)
)DEFAULT CHARACTER SET utf8;

INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,1,120);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,2,20);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,2,10);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,1,70);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,3,7);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(3,1,17);

create view todosclientes as select * from clientes;
create view todosFacturas as select a.id_facturas, a.referencia_facturas, a.fecha_facturas, b.id_clientes from facturas a, clientes b where a.id_clientes = b.id_clientes;
create view todosProductos as select * from productos;
create view todosFacturasProductos as select id_facturas, id_productos, cantidad_facturas_productos  from facturas_productos;

DELIMITER $$
create trigger clientes_mayus before insert on clientes for each row
begin
  set new.nombre_clientes=upper(new.nombre_clientes);
  set new.apellido_clientes=upper(new.apellido_clientes);
end $$
DELIMITER ;

DELIMITER $$
create trigger productos_mayus before insert on productos for each row
begin
  set new.nombre_productos=upper(new.nombre_productos);  
end $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todosClientes()
BEGIN
	select * from todosClientes;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarClientes(in nombre varchar(25),in apellido varchar(25))
BEGIN
	insert into clientes(nombre_clientes,apellido_clientes) values(nombre,apellido);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarClientes(in id int)
BEGIN
	delete from clientes where id_clientes=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarClientes(in id int,in nombre varchar(25),in apellido varchar(25))
BEGIN
	update clientes set nombre_clientes=nombre,apellido_clientes=apellido where id_clientes=id;
END $$

DELIMITER $$
CREATE  PROCEDURE buscarClientes(in id int)
BEGIN
	select * from clientes where id_clientes like concat('%',id,'%');
END $$

DELIMITER $$
CREATE  PROCEDURE todosFacturas()
BEGIN
	select * from todosFacturas;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarFacturas(in id int, in referencia varchar(25), in fecha date)
BEGIN
	insert into facturas(id_clientes,referencia_facturas,fecha_facturas) values(id,referencia,fecha);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarFacturas(in id int)
BEGIN
	delete from facturas where id_facturas=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarFacturas(in id int,in referencia varchar(25),in fecha date)
BEGIN
	update facturas set referencia_facturas=referencia,fecha_facturas=fecha where id_facturas=id;
END $$


DELIMITER $$
CREATE  PROCEDURE buscarFacturas(in id int)
BEGIN
	select * from facturas where id_facturas like concat('%',id,'%');
END $$


DELIMITER $$
CREATE  PROCEDURE todosProductos()
BEGIN
	select * from todosProductos;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarProductos(in nombre varchar(25),in precio double(9,2))
BEGIN
	insert into productos(nombre_productos,precio_productos) values(nombre,precio);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarProductos(in id int)
BEGIN
	delete from productos where id_productos=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarProductos(in id int,in nombre varchar(25),in precio double(9,2))
BEGIN
	update productos set nombre_productos=nombre,precio_productos=precio where id_productos=id;
END $$

DELIMITER $$
CREATE  PROCEDURE buscarProductos(in id int)
BEGIN
	select * from productos where id_productos like concat('%',id,'%');
END $$

DELIMITER $$
CREATE  PROCEDURE todosFacturasProductos()
BEGIN
	select * from todosFacturasProductos;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarFacturasProductos(in id int, in id2 int, in referencia int)
BEGIN
	insert into facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(id,id,referencia);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarFacturasProductos(in id int)
BEGIN
	delete from facturas_productos where id_facturas=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarFacturasProductos(in id int,in id2 int,in referencia double)
BEGIN
	update facturas_productos set cantidad_facturas_productos=referencia where id_facturas=id and id_productos=id2;
    
END $$
use ventas;