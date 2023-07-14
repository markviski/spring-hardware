CREATE DATABASE IF NOT EXISTS IddeHardware;

USE IddeHardware;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'rootsql';
flush privileges;

create table if not exists Hardware  (
    id bigint primary key auto_increment,
    price float,
    brand varchar(40),
    model varchar(50),
    year integer,
    mem integer
);

create table if not exists SellerInfo (
    id bigint primary key auto_increment,
    hardwareid bigint,
    seller varchar(30),
    quantity integer,
    phonenumber varchar(15),
    shippingfee float,
    foreign key (hardwareid) references Hardware(id) on delete cascade
);

insert into Hardware(price, brand, model, year, mem) values (52.99, "Crucial", "DRAM", 2021, 4);
insert into Hardware(price, brand, model, year, mem) values (140, "Kingston", "ValueRam", 2022, 16);
insert into Hardware(price, brand, model, year, mem) values (85.55, "Corsair", "Valueselect", 2019, 8);
insert into SellerInfo(hardwareid, seller, quantity, phonenumber, shippingfee) values (1, "eMag", 25, "0751230642", 0);
insert into SellerInfo(hardwareid, seller, quantity, phonenumber, shippingfee) values (2, "Altex", 14, "0773264155", 4.99);


-- jpa
SELECT * from jpa_hardware;
insert into jpa_hardware(price, brand, model, year, memory) values (140, "Kingston", "ValueRam", 2022, 16);
insert into jpa_hardware(price, brand, model, year, memory) values (85.55, "Corsair", "Valueselect", 2019, 8);
select * from jpa_sellerinfohardwareid
