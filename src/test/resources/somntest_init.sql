DROP TABLE IF EXISTS FK_USER_ROLE;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    first_name         VARCHAR(100)                      NOT NULL,
    encrypted_password VARCHAR(255)                      NOT NULL,
    user_status        VARCHAR(10)                       NOT NULL
);

CREATE TABLE accounts
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    balance        INT                               NOT NULL,
    account_status VARCHAR(10)                       NOT NULL,
    user_id        BIGINT REFERENCES users (id)
);

CREATE TABLE roles
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    role_name VARCHAR(20)                       NOT NULL
);

CREATE TABLE FK_USER_ROLE
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id BIGINT                            NOT NULL,
    role_id BIGINT                            NOT NULL,
    CONSTRAINT FK_USER_ID_USERS FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT FK_ROLE_ID_ROLES FOREIGN KEY (role_id)
        REFERENCES roles (id)
);