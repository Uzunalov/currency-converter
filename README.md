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
* Now you can go to [Swagger Url](http://localhost:8080/swagger-ui/index.html#/)

[![Product Name Screen Shot][product-screenshot]](https://drive.google.com/file/d/191XFeaZfcjXVcw5JBF2Xbtor7w8Vabbf/view)