# Cycloid_EPG_Solution
In this exercise RESTful API in Java/Springboot to provide EPG information will be developed.


### Notes:

To manually test the API please import the file in the _postman_collections_ to Postman and use those endpoints.

### Setup:

- Clone project to a folder
- Run the application with:
  - _mvn clean install_
  - _mvn spring-boot:run_
- Test the application with:
  - _mvn test_ -> run all tests
  - _mvn -Dtest=TestClass test_ -> run a single test class
  - _mvn -Dtest=TestClass1,TestClass2 test_ -> run multiple test classes
- Package the application with _mvn package_

## Endpoints:

Below are documented the endpoints of the API of this project. There are also some examples of possible outcomes that might happen when using the API.

* Channel:

   **Create a channel** - **POST** epg/api/channels/create
   
   URL: 
   
      http://localhost:8080/epg/api/channels/create
      
   Response Status:
   
      201 CREATED
      
   Body:
   
      {
        "name": "Channel 1",
        "position": 1,
        "category": "Sports"
      }
      
   Return:
      
      {
        "id": "fa3e06c9-91c2-4e46-8ab7-79bf3b42991b",
        "name": "1",
        "position": 1,
        "category": "Sports"
      }
       
    **Get all channels** - **GET** epg/api/channels
    
    URL: 
   
      http://localhost:8080/epg/api/channels
      
    Response Status:
   
      200 OK
      
    Body:
    
      empty
      
    Return:
    
      [
        {
          "id": "fa3e06c9-91c2-4e46-8ab7-79bf3b42991b",
          "name": "1",
          "position": 1,
          "category": "Sports"
        }
      ]
