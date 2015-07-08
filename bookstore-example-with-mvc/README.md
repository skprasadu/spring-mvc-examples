Spring MVC
==========

This code base supports latest Spring 4.1.7.RELEASE version.

In this sample, we demonstrated a simple Bookstoree application using Spring MVC, [Kickstrap](http://ajkochanowicz.github.com/Kickstrap/). It demonstrate how to build a Spring MVC application using a TDD approach.

* To run the DAO Layer testcase run

```
    mvn clean test -Dtest=com.example.bookstore.repository.JpaBookRepositoryTest
```

* To run the Service Layer testcase run
```
    mvn clean test -Dtest=com.example.bookstore.service.AccountServiceTest
```
* To run the Controller Layer testcase run
```
    mvn clean test -Dtest=com.example.bookstore.web.controller.LoginControllerTest
```
* To run the Selenium test for front-end run
```
    mvn -DskipTests clean package tomcat7:run
    
    In another window run

    mvn test -Dtest=com.example.bookstore.web.frontend.SeleniumLoginFrontendTest
```


