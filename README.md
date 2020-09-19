# US-Coronavirus-Tracker-Spring-boot-Rest-Angular
US Coronavirus Tracker made with Spring boot (rest service) and Angular

To run the application:

1) Run the following command at the root directory to start the rest service:
  `mvn spring-boot:run`

  You should see the service start running on localhost port 8888
  
2) Go into the coronavirus-tracker folder:
  `cd coronavirus-tracker`
  
3) Run the Angular application by running the command:
  `npm start`
  
  This will automatically start the Angular application and create a proxy for the rest service.
  The proxy setup in proxy.config.json is used to prevent CORS (Cross-Origin-Resource-sharing) restrictions in browsers.
  
  To test the whole application, go to http://localhost:4200 in your browser.
