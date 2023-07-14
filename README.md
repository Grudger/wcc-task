# wcc-task

## Setup Instructions
1. Install MongoDB or use <code>docker pull mongo</code>
2. Run the container exposing the port <code>docker run --name wcc-assessment -p 27017:27017 -d mongo</code>
3. Download the postcodes from <url>https://data.freemaptools.com/download/full-uk-postcodes/ukpostcodes.zip
4. Unzip the file and use command <code>mongoimport -d wcc -c postcodes --type csv --file ukpostcodes.csv --headerline</code> to load all the data into mongodb
5. Use <code>mongosh</code> then <code>use wcc</code> and add this document : ```db.users.insertOne({"username": "wccUser","password":"$2a$10$z8Qf.L62C5PxmRkEuXFozuDsuTR1pBb2VG14Y5jSJOYFpuukAiXQe","roles": ["ADMIN"]})```
6. Now that setup is done, make sure that you have Java 17 and latest version of maven installed
7. Browse a terminal to project directory and use ```mvn package``` to generate a jar file
8. Use ```java - jar assessment-0.0.1-SNAPSHOT.jar``` to run the application.
9. After the jar runs successfully, browse to ```http://localhost:8080/swagger-ui/index.html``` to view all the API calls

## Bonus Features
- Unit Tests
- Auditing logging for Update and distance calculation APIs
- Update Postcodes
- Spring Security / Authentication
- Postcode validations using regex, only valid UK postal codes will work 

### Troubleshooting
Use ```mvn clean install``` in case of any missing libraries