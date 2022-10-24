INSERT INTO address (street, houseNumber, zipCode, location) VALUES ("Getreidegasse", "3", 1234, "Salzburg");
INSERT INTO address (street, houseNumber, zipCode, location) VALUES ("Landstrasse", "54", 1235, "Linz");
INSERT INTO address (street, houseNumber, zipCode, location) VALUES ("Mariahilferstrasse", "42", 1223, "6. Bezirk Mariahilf");

INSERT INTO facilities (facilityName, country, district, addressId, region, activeness) VALUES ("Maria hilft!", "Wien", "6. Bezirk/Mariahilf", 3, "Donetsk", "aktiv");
INSERT INTO facilities (facilityName, country, district, addressId, region, activeness) VALUES ("Barmherzige Brüder Linz", "Oberösterreich", "Linz", 2, "Kiew", "aktiv");
INSERT INTO facilities (facilityName, country, district, addressId, region, activeness) VALUES ("Annahmestelle Salzburg", "Salzburg", "Salzburg", 1, "Donetsk", "aktiv");

INSERT INTO categories (category) VALUES ("Hygiene");
INSERT INTO categories (category) VALUES ("Kleidung");
INSERT INTO categories (category) VALUES ("Lebensmittel");
INSERT INTO categories (category) VALUES ("Elektronik");
INSERT INTO categories (category) VALUES ("Medikamente");

INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (1, "Duschgel", "Egal welcher Duft", "neu", 1, 250);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (1, "Shampoo", "kein spezielles", "neu", 1, 200);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (1, "Kaffee", "", "neu", 3, 400);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (1, "Bandagen", "", "neu", 5, 560);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (2, "T-Shirts", "einfache T-Shirts. größe egal", "gebraucht", 2, 1300);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (2, "Laptop", "egal welche Marke", "gebraucht", 4, 450);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (3, "Strampler", "Strampler für Neugeborene", "gebraucht", 2, 260);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (3, "Ratiodolor", "nur Großpackungen", "neu", 5, 5600);
INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) VALUES (3, "Ibuprofen", "", "neu", 5, 6450);

INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("Gertrude", "Werner", "", "WerniGert69", "IchLiebeMeinenEnkel");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("Manfred", "Zünsler", "", "Zuensli53", "BuchsBaumZuensler");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("Siglinde", "Mauerblümchen", "", "Blümchen13", "IchMagMauern");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("test", "user", "", "test", "test");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "example@gmail.com", "", "1234");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "example2@gmail.com", "", "test123");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "example3@gmail.com", "", "1234");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "guttuher.asdf@gmail.com", "", "test123");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "123@gmail.com", "", "1234");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "IAmDonating@gmail.com", "", "1234");
INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ("", "", "help@gmx.com", "", "test123");

SELECT * FROM users;

INSERT INTO donations (facilityId, userId, email, good, quantity, delivery) VALUES (1, 5, "example@gmail.com", "Duschgel", 10, NOW());
INSERT INTO donations (facilityId, userId, email, good, quantity, delivery) VALUES (2, 6, "example2@gmail.com", "Laptop", 3, NOW());
INSERT INTO donations (facilityId, userId, email, good, quantity, delivery) VALUES (3, 7, "example3@gmail.com", "Ratiodolor", 350, NOW());
INSERT INTO donations (facilityId, userId, email, good, quantity, delivery) VALUES (1, 8, "guttuher.asdf@gmail.com", "Kaffee", 150, NOW());
INSERT INTO donations (facilityId, userId, email, good, quantity, delivery) VALUES (2, 9, "123@gmail.com", "T-Shirts", 30, NOW());
INSERT INTO donations (facilityId, userId, email, good, quantity, delivery) VALUES (3, 10, "IAmDonating@gmail.com", "Ibuprofen", 1000, NOW());

ALTER TABLE donations
    ADD token VARCHAR(60);