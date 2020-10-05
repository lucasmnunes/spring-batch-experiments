# spring-batch-experiments
A Spring Batch POC to figure out how to configure and handle its features. 

### To Execute

1. Clone this repository;
2. In the root project directory, run ```mvn spring-boot:run``` to start the application;

### To Use

After doing the above steps:

1. Send a HTTP request (GET method) to: ```http://localhost:8080/batch/api/v1/player-batches```;

### To Check

1. The project uses H2 Database. After doing the request above, access: ```http://localhost:8080/batch/h2```;
2. Execute: ```SELECT * FROM PLAYER;```.

