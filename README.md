# demo-springboot-database

[![Coverage Status](https://coveralls.io/repos/github/florentin-a-p/demo-springboot-database/badge.svg)](https://coveralls.io/github/florentin-a-p/demo-springboot-database)

A Java Springboot application that provides CRUD APIs that can read and update countries, which are stored in PostgreSQL database. The build tool is Gradle. This is the iteration of https://github.com/florentin-a-p/demo-springboot-hashmap which stores the data in HashMap. The next iteration will implement all API versioning methods and try to save data in AWS cloud database instead of local database

Differences from the previous hashmap version:
- `Country` bean needs no argument constructor (which can be achieved by creating it manually or using Lombok's @NoArgsConstructor annotation). This is because we use @Autowired notation.
- `Country` bean needs to be annotated with the PostgreSQL table and column names to map the Java objects to the PostgreSQL objects.
- `CountryRepository` is needed to access the PostgreSQL database. Since the interface is already implemented automatically by SpringBoot, there is no need for us to manually implement the methods.
- `application.properties` needs to be configured to connect to the local PostgreSQL database.
- `annotationProcessor` is added to `build.gradle` to ensure the Lombok annotations can be initialized properly during runtime.

Before running the application, we need to create the database in PostgreSQL. 
I created it using postgres v14.1. To see the configuration you can follow the `application.properties` file. 
After creating the database, use Beekeeper to access the database and create the table. <br/>
<img width="354" alt="image" src="https://user-images.githubusercontent.com/52971362/191016573-15e0e13e-252d-4120-8245-5c5ad47641ad.png">

After creating the table, insert data into the table <br/>
<img width="496" alt="image" src="https://user-images.githubusercontent.com/52971362/191016695-68ca4751-18e1-44d8-906f-98be607e6f6b.png">

This is how the table is going to look like after everything is ready <br/>
<img width="355" alt="image" src="https://user-images.githubusercontent.com/52971362/191016769-db5af45e-650c-4e8e-bbd2-03a65da3b00f.png">

After that, we can follow the same API requests below and get the following results

| **Description**     | **Request**                                                                                                                                       | **Expected Response**                                                                                                                                                     |
|---------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| get all countries   | curl http://localhost:8080/getCountries                                                                                                           | [{"id":1,"countryName":"Indonesia","capital":"Jakarta"},{"id":2,"countryName":"Malaysia","capital":"Kuala Lumpur"},{"id":3,"countryName":"Thailand","capital":"Bangkok"}] |
| get country by id   | curl http://localhost:8080/getCountries/2                                                                                                         | {"id":2,"countryName":"Malaysia","capital":"Kuala Lumpur"}                                                                                                                |
| delete country      | curl -X DELETE  http://localhost:8080/deleteCountry/1                                                                                             | {"msg":"country deleted"}                                                                                                                                                 |
| update country      | curl -X PUT -d '{"id":2,"countryName":"Japan","countryCapital":"Tokyo"}' -H "Content-Type: application/json"  http://localhost:8080/updateCountry | {"id":2,"countryName":"Japan","capital":"Tokyo"}                                                                                                                          |
| get country by name | curl http://localhost:8080/getCountries/countryName?name=India                                                                                    | {"id":0,"countryName":"NONE","capital":"NONE"}                                                                                                                            |
| add new country     | curl -X POST -d '{"countryName":"Italy","capital":"Rome"}' -H "Content-Type: application/json"  http://localhost:8080/addCountry                  | {"id":4,"countryName":"Italy","capital":"Rome"}                                                                                                                           |


