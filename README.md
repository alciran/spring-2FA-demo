# spring-2FA-demo
<p align="center">
<a><img src="https://img.shields.io/static/v1?label=SpringBoot&message=2.7.0&color=brightgreen" alt="Spring Boot 2.7.0"/></a>
<a><img src="https://img.shields.io/static/v1?label=Licence&message=MIT&color=brightgreen" alt="License MIT"></a>
</p>


Spring Boot demo application with two-factor authentication. 

## What you need to use
- Java version 11 (or newer, tested up with Java 18)
- IDE (tested with vscode)
- maven (tested up with version 3.6.0)

## How to use

Clone the repo:

```
git clone https://github.com/alciran/spring-2FA-demo.git
```
To run:

- _From the command-line:_ `mvn clean spring-boot:run`
- _From your IDE:_ run the main method in the `Spring2FADemoApplication` class.

Then open your browser at [http://localhost:8080](http://localhost:8080)

The application used the [H2 Database](https://h2database.com/html/main.html) which is an in-memory datase. The database is recreated from scratch every time you start the app. If you want to save/create data in database, you can use and see the SQL code in [this](https://github.com/alciran/spring-2FA-demo/blob/main/src/main/resources/data.sql) file.
You can inspect the database any time you need to at http://localhost:8080/h2-console. The credential os DB are [here](https://github.com/alciran/spring-2FA-demo/blob/main/src/main/resources/application.properties#L3-L4).

## How to proceed?

- Register a new user at register page: [http://localhost:8080/register](http://localhost:8080/register)
- After registered, you will be redirected to a page with the QR Code. Scan this QR Code with your 2FA authentication app.
- Then, you can access the home page through login page: [http://localhost:8080/login](http://localhost:8080/login) 

## Screenshots

You can see screenshots from this application at [screenshots page.](screenshots.md)
