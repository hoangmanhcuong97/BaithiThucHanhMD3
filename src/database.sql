create database ManagerProduct;
use ManagerProduct;
create table product(
    id_product int auto_increment not null primary key ,
    name_product nvarchar(50),
    price int,
    quantity int,
    color nvarchar(50),
    description nvarchar(100),
    category nvarchar(100)
);



