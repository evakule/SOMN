INSERT INTO roles VALUES(1, 'ADMIN');
INSERT INTO roles VALUES(2, 'ACCOUNTANT');
INSERT INTO roles VALUES(3, 'CUSTOMER');

INSERT INTO users VALUES(1, 'Egor', 'Vakulenko', 'active');
INSERT INTO users VALUES(2, 'Andrew', 'Kos', 'active');
INSERT INTO users VALUES(3, 'Vasya', 'Antonov', 'active');

INSERT INTO FK_USER_ROLE VALUES(1, 1, 1);
INSERT INTO FK_USER_ROLE VALUES(2, 2, 3);
INSERT INTO FK_USER_ROLE VALUES(3, 3, 3);

INSERT INTO accounts VALUES(1, '50050', 'active', 2);
INSERT INTO accounts VALUES(2, '50050', 'active', 3);
INSERT INTO accounts VALUES(3, '123456789', 'active', 3);