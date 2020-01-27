#Recipe book

##Technologies
- Spring Boot Web version 2.2.4;
- Spring Boot Data MongoDB version 2.2.4;
- MongoDB plugin version 2.2.0;
- Swagger version 2.9.2;
- JUnit version 5;
- Gradle version 6.0.1

##Why?
- Spring Boot is scalable, cloud ready and easy to deploy;
- MongoDB is a NOSQL database and in the case of the recipe book, it was easier to model the data;
- Swagger is a well know API documentation tool;
- JUnit is a testing framework well know in the market;

##How to run it:
- To run the tests just type ./gradle test and hit enter
- To run the application just type ./gradle bootRun and hit enter
- For production is necessary to configure the real database connection;
- I left the MongoDB plugin enabled, so you can run the application without a real database;
- To change the profiles, you just need to set the spring.profiles.active variable to prod or dev in the application.yml
file. 

##Technical decisions:
- I using MongoDB because the ingredients would be a little hard to model, you have different types, measures and
substances. Besides, if for some reason an ingredient changes, in a relational database every single recipe would be
affected. So, instead to model joins, I decided to use a single document in MongoDB and modeled the ingredients as a list
of strings;
- I got 97% of test coverage in general I decided to leave it that way, because it's Spring Boot boilerplate code;
- In the Swagger configuration, data related to the Author and API wasn't put into constant because it is used only once;
- The rest of the code is pretty much Spring Standard;
- There still improvements to be made in the front-end, haven't work with it lately and I'm not sharp anymore.
  
