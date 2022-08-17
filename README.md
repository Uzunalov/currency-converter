# Currency Converter Application

This application is used for currency exchanges. 
You can exchange between "Manat" and any currency with this application.

## Technologies:
* Java 17
* Gradle 7.5
* PostgreSQL
* Docker

## How To Run?

* The first step is creating DB with Docker Containers:
```sh
$ cd .\docker\
$ docker-compose up
```

* The second step is building the application:
```sh
$ gradle bootRun
```

## How To Use?
* Now you can go to [Swagger Url](http://localhost:8080/swagger-ui/index.html#/).

![Product Name Screen Shot](./src/main/resources/images/swagger-screen.png)

* Use "Authorize" button for authentication of requests. If you want to use POST or DELETE requests, you must authorize as admin:
```sh
accessToken: K3XAZ71p4wln7dak224G5OaM
```

* If you want to use GET request, you must authorize as user (Basic Authentication):
```sh
username: jHEerd753n2v
password: ulLwH6pma14l
```