CREATE TABLE call_history (
  genre VARCHAR(255) DEFAULT NULL,
  method_used VARCHAR(255) DEFAULT NULL,
  time_of_call TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  temperature FLOAT DEFAULT NULL,
  location VARCHAR(30) DEFAULT NULL,
  id INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

CREATE PROCEDURE insert_service_call(
    IN genre VARCHAR(30),
    IN method_used VARCHAR(30),
    IN time_of_call TIMESTAMP,
    IN temperature FLOAT,
    IN location VARCHAR(30)
)
BEGIN
    INSERT INTO call_history (genre, method_used, time_of_call, temperature, location)
    VALUES (genre, method_used, time_of_call, temperature, location);
END;