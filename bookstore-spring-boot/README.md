# Spring Boot Tutorial
In this tutorial I will demonstrate a medium complex full stack application using, Postgres, Spring JPA, Spring Data, Spring Boot, Angular. 

# Design consideration

##With respect to Spring Boot
* Takes off lot of xml headaches of Spring MVC and follows a strong convention over configuration
* Works like Ruby, with lot of starter jar dependencies like spring-boot-starter-web or like spring-boot-starter-data-jpa, which helps in quickly setup all the dependencies. There are whole bunch of them
* Encourages the concept of Microservices, where we can build low foot print self contained services, which we can deploy in PaaS like Heroku

##In addition to these goodies, this sample application also imposes few of its own,
* Minimum Boilerplate coding. Minimum amount of Handcrafted POJOs. Almost no xml configuration.
* Design is more based on Test Driven Development (TDD) including Java and Javascript side

To run the application: 

* start Postgres and create a database called pg and run the sqlscripts/schema.sql 
* run the below command

```
  mvn clean exec:java test
```

Once the test is successful, do the code walk thru the tests