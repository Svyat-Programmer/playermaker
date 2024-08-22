# Playermaker

## Description

**Playermaker** is a Spring Boot application that provides a REST API for calculating the top players who have participated in the most games. The API takes an array of games with player names and returns the top N players who participated in the most games. It is Home Assignment by Playermaker

**Technologies**
    Java 17
    
    Spring Boot
    
    Lombok
    
    JUnit 5
    
    Maven
    
    Docker
    
**How to Run the Project**
1. git clone https://github.com/Svyat-Programmer/playermaker.git
2. Navigate to the project directory: cd playermaker
3. Build the Docker image: docker build -t playermaker .
4. Run the Docker container docker run -p 8080:8080 playermaker
 Now the application is accessible at http://localhost:8080.

**API Endpoints**
Get Top Players
URL: /api/players/top
Method: POST
Request Body: (example)
```
{
  "requiredTopPlayers": 2,
  "participatedPlayers": [
    ["Sharon", "Shalom", "Ronaldo"],
    ["Shalom", "Myke", "Ronaldo"],
    ["Yechiel", "Sivan", "Messi", "Ronaldo"],
    ["Yechiel", "Assaf", "Shalom", "Ronaldo"]
  ]
}
```

Response:
```
{
  "mostParticipatedPlayers": ["Ronaldo", "Shalom"]
}
```
**Author**
Created by Svyat Ayrishin

