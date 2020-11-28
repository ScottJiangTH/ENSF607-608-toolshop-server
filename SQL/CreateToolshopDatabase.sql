DROP DATABASE IF EXISTS toolshop;
CREATE DATABASE toolshop; 
USE toolshop;

DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier (
  id			char(9) not null,
  stype			varchar(15) not null,
  company_name	varchar(50) not null, 
  address		varchar(100),
  sales_contact	varchar(50),
  primary key (id)
);

DROP TABLE IF EXISTS international_supplier;
CREATE TABLE international_supplier (
  sid			char(9) not null,
  import_tax	float,
  primary key (sid),
  foreign key (sid) references supplier(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

DROP TABLE IF EXISTS item;
CREATE TABLE item (
  id			char(9) not null, 
  itype	    	varchar(15) not null,
  iname    		varchar(30),
  idescription	varchar(30),
  price			float not null,
  quantity		integer not null,
  sid			char(9) not null,
  primary key (id),
  foreign key (sid) references supplier(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

DROP TABLE IF EXISTS electrical_item;
CREATE TABLE electrical_item (
  iid			char(9) not null,
  power_type	varchar(15),
  primary key (iid),
  foreign key (iid) references item(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

DROP TABLE IF EXISTS daily_order;
CREATE TABLE daily_order (
  id			char(9) not null,
  odate			date not null,
  primary key (id)
);


DROP TABLE IF EXISTS order_line;
CREATE TABLE order_line (
  oid			char(9) not null,
  iid			char(9) not null,
  quantity		integer not null,
  sid   		char(9) not null,
  primary key (oid, iid),
  foreign key (oid) references daily_order(id), 
  foreign key (iid) references item(id), 
  foreign key (sid) references supplier(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);


DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
  id			char(9) not null, 
  fname	    	varchar(15) not null,
  lname    		varchar(15) not null,
  address		varchar(100),
  postal		char(7),
  phone			char(12),
  ctype			char(1) not null,
  primary key (id)
);


DROP TABLE IF EXISTS purchase_history;
CREATE TABLE purchase_history (
  cid   		char(9) not null,
  iid    		char(9) not null,
  quantity  	integer not null,
  primary key (cid, iid),
  foreign key (cid) references customer(id),
  foreign key (iid) references item(id)
);

