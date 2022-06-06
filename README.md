# precharge-service

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#features.sql.jdbc)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-kafka)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Swagger 2.0](https://swagger.io/specification/v2/)
### Guides

The following guides illustrate how to use some features concretely:

* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Swagger
```
http://localhost:8080/swagger-ui.html
```

### Build database dev
database is created using mysql,build process as fellows:
```
docker run --name rent-room -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:latest

docker exec -it rent-room bash

mysql -uroot -p

create user 'user1' identified with mysql_native_password by '123456';

grant all privileges on *.* to 'user1';

create database RENT_ROOM charset=utf8;
```
### install mvnvm
```
    brew install maven
    brew install mvnvm
```
### compile
```
   mvn compile
```
### start server
```
   mvn install
```
### run test
```
   mvn test
```

### Run the application

Run the application which will be listening on port `8080`.

### Fake Repository test

Repository test are build with H2 database

1.import H2 dependency in pom.xml
```
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
```
2.creat a sql file for initiating H2 database table at test/resources/db/migration folder;
```
    **creat_rent_user.sql**
    DROP TABLE IF EXISTS rent_user;
    CREATE TABLE rent_user (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `account` varchar(255) DEFAULT NULL,
                                 `address` varchar(255) DEFAULT NULL,
                                 `balance` decimal(19,2) DEFAULT NULL,
                                 `created` date DEFAULT NULL,
                                 `name` varchar(255) DEFAULT NULL,
                                 `phone` varchar(255) DEFAULT NULL,
                                 `status` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
    
    **insert_rent_user_data.sql**
    INSERT INTO RENT_ROOM.`rent_user`
        (id,account, address, balance, created, name, phone, status)
    VALUES
       (1,'wuhen001', '陕西省西安市', 100.00, '2022-02-12', '徐乐', '13992809276', '1');

    INSERT INTO RENT_ROOM.`rent_user`
        (id,account, address, balance, created, name, phone, status)
    VALUES
         (2,'xiaociacai', '陕西省西安市', 0.00, '2022-02-12', '高小五', '18792721645', '1');

    INSERT INTO RENT_ROOM.`rent_user`
        (id,account, address, balance, created, name, phone, status)
    VALUES
        (3,'zhangsan', '北京市海淀区', 8.00, '2022-02-12', '张三', '18999388847', '0');
```
3.creat RentUserRepositoryTest for testing RentUserRepository's getRentUserById() and save() method;

    RentUserRepositoryTest must be creat with a '@SpringbootTest' annotation
###Code struct
```
src
├── main
│ ├── java
│ │ └── com
│ │     └── tw
│ │         └── precharge
│ │             ├── PrechargeServiceApplication.java
│ │             ├── constant
│ │             │ ├── PayStatus.java
│ │             │ ├── RefundStatus.java
│ │             │ └── UserStatus.java
│ │             ├── controller
│ │             │ ├── ChargeController.java
│ │             │ ├── HelloController.java
│ │             │ ├── advice
│ │             │ │ └── ExceptionHandlerAdvice.java
│ │             │ ├── configuration
│ │             │ │ ├── FeignConfiguration.java
│ │             │ │ └── SwaggerConfig.java
│ │             │ └── dto
│ │             │     ├── ChargeDTO.java
│ │             │     ├── RefundDTO.java
│ │             │     ├── RespondEntity.java
│ │             │     ├── RespondStatus.java
│ │             │     ├── WeChatPayResDTO.java
│ │             │     └── WechatPayDTO.java
│ │             ├── domain
│ │             │ ├── rentinfo
│ │             │ │ └── RoomRentInfo.java
│ │             │ └── user
│ │             │     └── RentUser.java
│ │             ├── entity
│ │             │ ├── Chargement.java
│ │             │ └── Refundment.java
│ │             ├── infrastructure
│ │             │ ├── httpInterface
│ │             │ │ ├── WechatPayClient.java
│ │             │ │ └── WechatService.java
│ │             │ ├── mqService
│ │             │ │ └── kafka
│ │             │ │     ├── KafkaConsumer.java
│ │             │ │     ├── KafkaSender.java
│ │             │ │     └── Message.java
│ │             │ └── repository
│ │             │     ├── ChargementRepository.java
│ │             │     ├── RefundmentRepository.java
│ │             │     └── RentUserRepository.java
│ │             ├── service
│ │             │ └── ChargeService.java
│ │             └── util
│ │                 └── exception
│ │                     └── BusinessException.java
│ └── resources
│     ├── application.properties
│     └── db
│         └── migration
│             ├── V20220418230923__creat__rent__user.sql
│             └── V20220419213143__insert__rent__user.sql
└── test
    ├── java
    │ └── com
    │     └── tw
    │         └── precharge
    │             ├── PrechargeServiceApplicationTests.java
    │             ├── controllerTest
    │             │ └── ChargeControllerTest.java
    │             ├── httpInterfaceTest
    │             │ └── WechatPayClientTest.java
    │             ├── mqTest
    │             │ └── MqServiceTest.java
    │             ├── repositoryTest
    │             │ ├── ChargementRepositoryTest.java
    │             │ ├── RefundmentRepositoryTest.java
    │             │ └── RentUserRepositoryTest.java
    │             └── serviceTest
    │                 └── ChargeServiceTest.java
    └── resources
        ├── application-test.properties
        └── db
            └── migration
                ├── V20220418230923__creat__rent__user.sql
                └── V20220419213143__insert__rent__user.sql

```

### Comment

The class 'KafkaConsumer' is the 3rd system service, 
so it will not actually be implemented in this class
and don't have test for it in this project.





