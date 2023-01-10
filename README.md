This project has been developed to add, update, remove and get stocks.

### Requirements To Run Application Without Docker

* JDK 17
* H2 Database
* Maven
* You can see APIs: [Swagger](http://localhost:9091/swagger-ui/index.html#)
* H2-Console link: [H2-Console](http://localhost:9091/h2-console/login.jsp)
* 
* Spring Boot application can be run simply by invoking StockserviceApplication class.
* As the application follows security for the resources, first thing is to get the access token.
* Once the application is up, access token can be obtained by invoking the security API : [api/v1/stock/signup]
* Use the same token to execute the application requests.
##


**Note: Once you have successfully logged in and obtained the token
### Dependencies And Tools Used To Build Application

* Git
* JDK 17
* Spring Boot 3.0.0
* RestFul Webservices
* Spring Security
* data-jpa
* starter-web
* Maven
* Lombok
* Swagger
* Junit
*

**For detailed information refer to pom.xml**

### How to dockerized SpringBoot App & PostgresSQL

* There is a **Dockerfile** in the root directory `Dockerfile and docker-compose.yml`, this file is used to dockerized
  the SpringBoot App.

* The last and most important file is **docker-compose.yml**, which is available in the `root` directory, this file
  contains the configuration which will start the **SpringBoot App**

### Start the Application with the help of Docker

Go to the `root` directory and execute the following commands in the terminal

    1- mvn clean package  2- docker-compose -f docker-compose.yml up --build

And we are done, the **SpringBoot App** will be starting on port **9091**

Now you can open the swagger to access the APIs:
[Swagger](http://localhost:9091/swagger-ui.html#)


<p align="center">
  <b>Thank You </b>
</p>
