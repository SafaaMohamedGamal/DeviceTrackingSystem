# DeviceTrackingSystem
## Prerequisites
The following items should be installed in your system
  - Java 11
  - Maven 3.8.6
  - Mysql (to work with test environment)
  - postman
  
## How to run the project
  ######  I used profiling to distinguish between two running environments dev(h2 database) and test(Mysql database)
  ### 1.Development Environment
  It's the default environment
  
  Here you don't have to run any sql file to initiate some data, spring boot will run all the scripts every time it starts up(only in dev mode).
  
  Note that H2 database is local database, and all data will be cleared when project stops.
  
  use the following command in command prompt:
  
    mvn clean install ------> to clean and build the project
    
    then
    
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    or
    mvn spring-boot:run
  
  ### 2.Test Environment
  It works with mysql database, so you should run "scripts.sql" by yourself
  
  Then use the following command in command prompt to run the project:
  
    mvn spring-boot:run -Dspring-boot.run.profiles=test
  
  ## Documentation
  In this project I used Swagger UI to create documentation for the API
  
  You can access this API through the following link (while the project is running):
    http://localhost:8080/api/swagger-ui/index.html 
    
  Tests could be done through swagger instead of postman
