```markdown
# MusiCelsius
A music app that helps you go through your day like a champ. ;)

## Technologies Used

This project makes use of the following technologies:

- **JUnit, Mockito** - Testing
- **MySQL** - Database Persistence for Statistical Purposes
- **CircuitBreakers** - Resilience
- **Spring Boot Framework**
- **Google GSON** - Java Serialization / Deserialization
- **OkHttp3** - HTTP Requests Management
- **Log4j** - Logging

## Requirements

In order for the app to run, a local MySQL database named "musicelsius" needs to be running with the following configuration found in the `application.properties` file:

spring.application.name=musicelsius
spring.datasource.url=jdbc:mysql://localhost:3306/musicelsius
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### This database must contain the following items:

#### Table `call_history`

The following table is used to store the service call history:

```sql
CREATE TABLE `call_history` (
  `genre` varchar(255) DEFAULT NULL,
  `method_used` varchar(255) DEFAULT NULL,
  `time_of_call` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `temperature` decimal(10,2) DEFAULT NULL,
  `location` varchar(30) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
);
```

#### Procedure `insert_service_call`

```sql
DELIMITER //

CREATE PROCEDURE insert_service_call(
    IN genre VARCHAR(30),
    IN method_used VARCHAR(30),
    IN time_of_call TIMESTAMP,
    IN temperature DECIMAL(10, 2),
    IN location VARCHAR(30)
)
BEGIN
    INSERT INTO call_history (genre, method_used, time_of_call, temperature, location, id)
    VALUES (genre, method_used, time_of_call, temperature, location, DEFAULT);
END //

DELIMITER ;
```

## MusiCelsius Endpoints

MusiCelsius has 4 available endpoints, listed below:

- `http://localhost:8080/musicelsius/playlist/bycity/{city_name_here}`  
  Returns a list of songs from a playlist according to the temperature fetched from the specified city and creates a record for the service call in the `call_history` table.  
  **Example:** `http://localhost:8080/musicelsius/playlist/bycity/chicago`

- `http://localhost:8080/musicelsius/playlist/bycoordinates/`  
  Receives a `Coordinates` object in its JSON form in the body. It returns a list of songs from a playlist according to the temperature fetched from the specified coordinates and creates a record for the service call in the `call_history` table.  
  **Example body (Postman):**
  ```json
  {
      "latitude": 15,
      "longitude": 23.5
  }
  ```

- `http://localhost:8080/musicelsius/playlist/topgenre/`  
  Returns the most popular genre according to the data saved in the `call_history` table.

- `http://localhost:8080/musicelsius/playlist/toplocation/`  
  Returns the most popular location according to the data saved in the `call_history` table.

## Spotify Requirements

Any user using their Client ID and Secret when calling the Spotify API needs to have playlists with the following names:
- Rock
- Pop
- Classic
- Party

If you face any issues trying to get this app running, feel free to call me or send me a WhatsApp message, since you already have my number.

## Architecture

This Project follows an architecture containing the following:

- ResponseController, which takes care of the calls that our App will receive.
- SpotifyAPIInvoker, which will make the calls to the Spotify API Service.
- WeatherAPIInvoker, which will make the calls to the Weather API Service.
- WeatherAPIInvokerFactory, which will give us a WeatherAPIInvoker we can use for calls and can be easily mocked for testing.
- ServiceCallRepository, which will take care of the insertions and queries to the database.

**P.S.** - I had a blast working on this little project! Thank you so much for the experience and your time! I always wanted to play around with the Spotify API, and this was just the perfect opportunity. It was crazy fun. Cheers!!

Raúl (:

