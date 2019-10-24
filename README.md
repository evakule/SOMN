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
    
- Secondly, create database 'sonm' and run commands from this files:


    somn_init.sql
    fill_in_db.sql

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