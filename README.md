# Diber (backend)
## Spring Boot / Spring Data Jpa / Hibernate / MySQL / OAuth2 Application

Diber is the delivery service of goods like Uber. Anyone can register as a customer or courier. 
Customers can make requests for the delivery of goods, and couriers can accept and fulfill these requests.

The system provide many features, here are the most important ones:

Customer:

* Sumbit an order (order management)
* Viewing a single order and its status
* Appointment of the courier to your order (choose from the list of suggestions)
* Look at profiles couriers (search, filter, etc.)
* Review and evaluation of the courier after the order
* Track order status and location (after the adoption of the courier)
* Chat with the courier

Courier:

* Search orders, view order list
* Submitting an application for an a specific order
* View a list of orders (all of courier orders)
* View, execution, change the status of the received order
* View your profile and reviews, rating
* Confirm profile by sending the documents (as confirmed in the VK or Twitter)
* View orders statistic
* Chat with the customer


## Frameworks

This app use following Spring projects, libraries and frameworks:

* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Security](http://projects.spring.io/spring-security/)
* [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/)
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [Hibernate](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/orm.html#orm-hibernate)

## How to build and run

Just run with maven

```
mvn clean package spring-boot:run
```

## Spring Security and OAuth2

You can find more information about Spring Security and OAuth2 integration by [following this link](https://github.com/handioq/spring-boot-security-oauth2/).

## REST API (coming soon)

<b>Auth</b>

* [About OAuth 2.0 token based requests in this project you can read there](https://github.com/handioq/spring-boot-security-oauth2#how-to-use)
* POST /api/v1/auth/register - Register user


