### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

- I have almost 5 years of experience in Java and 4 years of Spring Boot.
- I have mostly worked on microservice architecture.
- 
### Username / Password (for testing purpose)

- SophiaB / SophiaPass123
- JamesS / JamesPass123
- IsabellaJ / IsabellaPass123
- LucasW / LucasPass123
- EmmaL / EmmaPass123

### What has been done

- Initialization DB with samples data. (data.sql in resource folder)
- Entity Validation.
- Added Logging (also logging Requests/Response of APIs).
- Added Basic Auth with Spring Security.
- Added Caching with Redis -> (This is a requirement to use the APIs now. Personally I used a docker container for Redis.)
- Added Pagination to APIs
- Tests for Controller / Repository / Service
- Comments + JavaDocs
- Containerization with Docker and docker-compose (Docker and docker-compose are requirement.)
- /shutdown API for shutdown of application

### Containerization
1) In the root folder run the command "docker-compose build java-challenge" to build the image of the application.
2) In the root folder run the command "docker-compose up" to start up the application and Redis.
3) Use CTRL+C to stop it.