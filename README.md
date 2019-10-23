SONM Online-Bank

Simple online banking app

Required Technology:
    
    MySQL (version 5+)
    Java Virtual Machine 8
    Maven 3.+

Technology used:

    Java 8, SpringBoot, MySQL, H2, Lombok, Junit

Installing:

Set up your database properties as below
    
    datasource.url = localhost:3306/sonm
    datasource.username = root
    datasource.password = 12345
    
- Secondly, create database 'sonm' and run next commands in it:


    CREATE TABLE IF NOT EXISTS accounts(
        id             BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
        balance        INT NOT NULL,
        account_status VARCHAR (10) NOT NULL,
        user_id        BIGINT REFERENCES users(id)
    );
    
    CREATE TABLE IF NOT EXISTS users(
        id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
        first_name          VARCHAR (100) NOT NULL,
        encrypted_password  VARCHAR (255) NOT NULL,
        user_status         VARCHAR (10) NOT NULL
    );
    
    CREATE TABLE IF NOT EXISTS roles(
        id        BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
        role_name VARCHAR (20) NOT NULL
    );
    
    CREATE TABLE IF NOT EXISTS FK_USER_ROLE (
        id              BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
        user_id         BIGINT NOT NULL,
        role_id         BIGINT NOT NULL,
        CONSTRAINT      FK_USER_ID_USERS FOREIGN KEY (user_id)
          REFERENCES    users(id),
        CONSTRAINT      FK_ROLE_ID_ROLES FOREIGN KEY (role_id)
          REFERENCES    roles(id)
    );
    
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


- Thirdly, run your project from console;

    
    1) Run console
    2) Go to the folder where the project lies
    3) Write: mvn clean
    4) Write: mvn package
    5) Write: spring-boot:run
    
Open your browser and use next URI:

    api/v1/accounts - shows all accounts 
    api/v1/accounts/{id} - show single account, where {id} is number.
    
Authors:
    
    Egor Vakulenko