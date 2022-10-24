SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

DROP DATABASE IF EXISTS PomagajuDB;

CREATE DATABASE IF NOT EXISTS PomagajuDB DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci;
USE PomagajuDB;

CREATE TABLE users (
                         id int(11) NOT NULL,
                         firstName varchar(60),
                         lastName varchar(60),
                         email varchar(60),
                         userName varchar(255),
                         password char(60) NOT NULL,

);

ALTER TABLE users
    ADD PRIMARY KEY (id);

CREATE TABLE facilities (
                            id int(11) NOT NULL,
                            facilityName varchar(80) NOT NULL,
                            country varchar(60) NOT NULL,
                            district varchar(60) NOT NULL,
                            addressId int(11) NOT NULL,
                            region varchar(80) NOT NULL,
                            activeness varchar(10) NOT NULL
);

CREATE TABLE address(
                            id int(11) NOT NULL,
                            street varchar(30) NOT NULL,
                            houseNumber int NOT NULL,
                            zipCode varchar(10) NOT NULL,
                            location varchar(30) NOT NULL
);

ALTER TABLE facilities
    ADD PRIMARY KEY (id);

ALTER TABLE facilities
    MODIFY id int(11) NOT NULL AUT0_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE address
    ADD PRIMARY KEY (id);

ALTER TABLE address
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE facilities
    ADD CONSTRAINT facilities_address_id FOREIGN KEY (addressId) REFERENCES address (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE donations(
    id int(11) NOT NULL,
    facilityId int(11) NOT NULL,
    email varchar(50) NOT NULL,
    good varchar(50) NOT NULL,
    quantity int NOT NULL,
    delivery timestamp NOT NULL
);

ALTER TABLE donations
    ADD PRIMARY KEY (id);

ALTER TABLE donations
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE donations
    ADD CONSTRAINT facilities_donations_fk FOREIGN KEY (facilityId) REFERENCES facilities (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE goods (
    id int(11) NOT NULL,
    facilityId int(11) NOT NULL,
    identifier varchar(50) NOT NULL,
    description varchar(255) NOT NULL,
    stateOfGood varchar(10) NOT NULL,
    categoryId int(11) NOT NULL,
    quantity int NOT NULL
);

CREATE TABLE categories(
    id int(11) NOT NULL,
    category varchar(30) NOT NULL
);

ALTER TABLE goods
    ADD PRIMARY KEY (id);

ALTER TABLE goods
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE categories
    ADD PRIMARY KEY (id);

ALTER TABLE categories
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE goods
    ADD CONSTRAINT facilities_goods_fk FOREIGN KEY (facilityId) REFERENCES facilities (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT categories_goods_fk FOREIGN KEY (categoryId) REFERENCES categories (id) ON DELETE CASCADE ON UPDATE CASCADE;

COMMIT;