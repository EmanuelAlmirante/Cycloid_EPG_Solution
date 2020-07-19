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
      
* Program:

    **Create a program** - **POST** epg/api/programs/create
    
    URL:
      
      http://localhost:8080/epg/api/programs/create
      
    Response Status:
   
      201 CREATED
      
    Body:
      
      {
        "channelId": "7140e93a-7241-4b93-8c4c-1b40ae8480cf",
        "imageUrl": "http://cycloid.com/channel1-image/",
        "title": "Best EPL Goals",
        "description": "Review the amazing goals scored in the last English Premier League season!",
        "startTime": "2020-07-18T11:45:47",
        "endTime": "2020-07-18T12:45:47"
      }
       
    Return:
     
      {
        "id": "36d23a70-82de-4dbb-8a18-14f7c97507fe",
        "channelId": "7140e93a-7241-4b93-8c4c-1b40ae8480cf",
        "imageUrl": "http://cycloid.com/channel1-image/",
        "title": "Best EPL Goals",
        "description": "Review the amazing goals scored in the last English Premier League season!",
        "startTime": "2020-07-18T11:45:47",
        "endTime": "2020-07-18T12:45:47"
      }
      
    **Get all programs by channel id** - **GET** epg/api/programs/channelId/{channelId}
    
    URL:
    
      http://localhost:8080/epg/api/programs/channelId/7140e93a-7241-4b93-8c4c-1b40ae8480cf
      
    Response Status:
    
      200 OK
      
    Body:
    
      Empty
      
    Return:
    
      [
        {
          "id": "36d23a70-82de-4dbb-8a18-14f7c97507fe",
          "channelId": "7140e93a-7241-4b93-8c4c-1b40ae8480cf",
          "imageUrl": "http://cycloid.com/channel1-image/",
          "title": "Best EPL Goals",
          "description": "Review the amazing goals scored in the last English Premier League season!",
          "startTime": "2020-07-18T11:45:47",
          "endTime": "2020-07-18T12:45:47"
        }
      ]
      
    **Get program by id** - **GET** epg/api/programs/programId/{id}
    
    URL:
    
      http://localhost:8080/epg/api/programs/programId/36d23a70-82de-4dbb-8a18-14f7c97507fe
      
    Response Status:
    
      200 OK
      
    Body:
    
      Empty
      
    Return:
    
      {
        "id": "36d23a70-82de-4dbb-8a18-14f7c97507fe",
        "channelId": "7140e93a-7241-4b93-8c4c-1b40ae8480cf",
        "imageUrl": "http://cycloid.com/channel1-image/",
        "title": "Best EPL Goals",
        "description": "Review the amazing goals scored in the last English Premier League season!",
        "startTime": "2020-07-18T11:45:47",
        "endTime": "2020-07-18T12:45:47"
      }
      
    **Delete program by id** - **DELETE** epg/api/programs/programId/{id}
    
    URL:
    
      http://localhost:8080/epg/api/programs/programId/36d23a70-82de-4dbb-8a18-14f7c97507fe
      
    Response Status:
    
      204 NO CONTENT
      
    Body:
    
      Empty
      
    Return:
    
      Empty
      
    **Update program by id** - **PUT** epg/api/programs/programId/{id}
    
    URL:
    
      http://localhost:8080/epg/api/programs/programId/36d23a70-82de-4dbb-8a18-14f7c97507fe
      
    Response Status:
    
      200 OK
      
    Body:
    
      {
        "imageUrl": "http://cycloid.com/channel2-image/"
      }
      
    Return:
    
      {
        "id": "36d23a70-82de-4dbb-8a18-14f7c97507fe",
        "channelId": "7140e93a-7241-4b93-8c4c-1b40ae8480cf",
        "imageUrl": "http://cycloid.com/channel2-image/",
        "title": "Best EPL Goals",
        "description": "Review the amazing goals scored in the last English Premier League season!",
        "startTime": "2020-07-18T11:45:47",
        "endTime": "2020-07-18T12:45:47"
      }
