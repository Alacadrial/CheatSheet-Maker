# cheatsheet-maker
This is a monolithic Spring Boot application that allows users to register and start creating cheatsheets.

The main purpose behind the creation of this project is to practice what I have learned regarding mainly implementation of authentication and authorization techniques using Spring Security. This is by no means a good looking full stack application, infact it misses some of the features on the front end part of the project.

As for the project:
- The project has two SecurityFilterChain configuration, one for "/api" endpoint and one for "/" web endpoint.
- Authorization of the Calls made to api endpoint are handled using jwt validation. Aside from "/api/auth" and "/api/public/**" any other api endpoint requires authentication.
- Jwt authorization handled by putting a OncePerRequest filter before securityfilter chain, meaning every request, HTTP headers are checked for the JWT and user authorization is questioned.
- Web endpoint uses SessionID formlogin authentication. When the user logs in, authentication is stored in the SecurityContext and kept until logging out.
- For the database that holds the user information and roles I have chosen MySql and using Spring Data JPA, I handled the connections.
- Database for the User and Role table schemas are the default schema that is recommended by the spring boot.
- As for holding the "CheatSheet" objects, I have chosen MongoDB as my database.
- At the frontend part I have used Thymeleaf template engine.  (Incomplete)
- Configurations such as JWT_SECRET or database connection queries and such secret informations are accessed through environmental variables and "ApplicationConfig.java" configuration file.

This sums up the project, rest can be looked through examining the source code.
All and all this project made me more intimate with Spring and made me learn how some of the internal parts of Spring actually operates behind the doors.
