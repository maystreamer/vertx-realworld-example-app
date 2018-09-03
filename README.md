# messi
Sample mock server to mock APIs without writing any mock code manually. Built with vertx web to showcase the usage of [vertx-boot]
(https://github.com/greyseal/vertx-boot) library. 

## Getting Started

Git clone the project on your local machine and import it to your favorite ide.

### Prerequisites

For runnning this, you will need
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.
- [Vertx-Boot](https://github.com/greyseal/vertx-boot) library. 

## Brief
This sample application make use of [Vertx-Boot](https://github.com/greyseal/vertx-boot) library to expose a rest API **/runner/api/ping**
- **HttpServerVerticle**       -> Default verticle from the vertx-boot library. Can be extened for the functionality.
- **DatabaseVerticle**         -> Database verticle to store the mocks.
- **MessiMockHandler**         -> Sample handler to create mocks and then use to get the desired results.
- **PingHandler**              -> Default handler from the vertx-boot library to send a "OK" Json response.

## Running the app

For running the app, (IDE used here is IntelliJ)
- Open **appConfig.json** file and set the "http_server_port" as per your choice.
- Once, changes are done in **appConfig.json**, add/edit Run/Debug Configurations for the project("messi") and set:
  * **Main class**: com.greyseal.vertx.boot.AppLauncher
  * **VM options**: -Dlogback.configurationFile=file:../messi/src/main/resources/logback.xml
  * **Program arguments**: run com.greyseal.vertx.boot.verticle.MainVerticle -conf ../messi/src/main/resources/appConfig.json 
  * **Environment variables**: ENV=dev 
 <br /><br /> 

After setting the variables, Run/Debug the project. If app starts successfully, then try <br /><br /> 
**Type:** *GET http://localhost:8080/messi/ping* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479fadddda* <br />

Response<br />
```
{
    "status": "OK"
}
```
That's it.

## Messi usage
Currently added support to add a mock and then fetch it to consume. To run/debug...<br  /><br  />
* To create a Mock **MockCreate** <br /><br />
**Type:** *POST http://localhost:8080/messi/mock/create* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
{
  "response": {
    "firstName": "Not Known",
    "lastName": "Not Known"
  },
  "statusCode": 500,
  "url": "/cerberus/user/create"
}
 ```
 where, <br />
 **response** is the response of the API to mock. <br />
 **statusCode** is the statusCode for which Mock should return the above response. <br />
 **url** is the URL used to fetch the Mock API result. <br /> <br />
 **Response:**
 ```
{
    "statusCode": 500,
    "createdBy": "saurabh",
    "isActive": true,
    "response": {
        "firstName": "Not Known",
        "lastName": "Not Known"
    },
    "updatedBy": "saurabh",
    "url": "/corona/user/create",
    "_id": "5b76d2c2e9738f841076749e"
}
 ```
<br /> <br />
* To fetch the mock result for an API **FetchMock** <br /><br />
**Type:** *GET {BASE_URL}/{MOCK_API_URL}* <br />
where, <br />
  1. **BASE_URL** = http://localhost:8080/messi/mock/server This is constant.<br />
  2. **MOCK_API_URL**=corona/user/create?statusCode={status_code} This is the dynamic URL or URL for which mock result is required. This is the URL which we have passed while creating the Mock #**MockCreate**  <br />
  3. **status_code** = Http Status code for which Mock result has to be prepared. Default is 200.
  
  **Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
 **Response:**
 ```
{
    "response": {
        "firstName": "Not Known",
        "lastName": "Not Known"
    }
}
 ```
## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
