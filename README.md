# vertx-realworld-example-app
Sample aplication to demo a vertx-realworld-example-app for [gothinkster](https://github.com/gothinkster/realworld/issues/228). Built with vertx web to showcase the usage of [vertx-boot](https://github.com/greyseal/vertx-boot) library. 

## Getting Started

Git clone the project on your local machine and import it to your favorite ide.

### Prerequisites

For runnning this, you will need
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.

## Brief
This sample application make use of [Vertx-Boot](https://github.com/greyseal/vertx-boot) library to expose sample rest APIs for [gothinkster] (https://github.com/gothinkster/realworld/issues/228)
- **HttpServerVerticle**       -> Default verticle from the vertx-boot library.
- **DatabaseVerticle**         -> Database verticle to store the data.
- **PingHandler**              -> Default handler from the vertx-boot library to send a "OK" Json response.

## Running the app

For running the app, (IDE used here is IntelliJ)
- Open **appConfig.json** file and set the "http_server_port" as per your choice. Also, set "**mongo_config**".
- Once, changes are done in **appConfig.json**, add/edit Run/Debug Configurations for the project("vertx-realworld-example-app") and set:
  * **Main class**: com.greyseal.vertx.boot.AppLauncher
  * **VM options**: -Dlogback.configurationFile=file:../vertx-realworld-example-app/src/main/resources/logback.xml
  * **Program arguments**: run com.greyseal.vertx.boot.verticle.MainVerticle -conf ../vertx-realworld-example-app/src/main/resources/appConfig.json 
  * **Environment variables**: ENV=dev. Make sure to set this variable.
 <br /><br /> 

After setting the variables, Run/Debug the project. If app starts successfully, then try <br /><br /> 
**Type:** *GET http://localhost:8080/runner/ping* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479fadddda* <br />

Response<br />
```
{
    "status": "OK"
}
```
That's it.

## vertx-realworld-example-app usage
* Create user without authentication or signup <br /><br />
**Type:** *POST http://localhost:8080/runner/users* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
{
  "user": {
    "email": "s@i.e",
    "password": "p",
    "userName": "johnjacob",
    "bio":"S/w",
    "image":"/image"
  }
}
 ```
 **Response:**
 ```
 {
    "id": "5b8cd8a7ad813c83f5cfcba5"
}
 ```
<br /> <br />

* To perform login or authentication <br /><br />
**Type:** *POST http://localhost:8080/runner/users* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
{
  "user": {
    "email": "s@i.e",
    "password": "p"
  }
}
 ```
 **Response:**
 ```
{
    "email": "s@i.e",
    "userName": "johnjacobs"
}
 ```
 **Response Headers**
 ```
 Authorization → BEARER 8a2d6bdfd56b4a2a87009c1bbdca6c14
 ```
<br /> <br />

* To perform update to user model. This needs authentication token from the above API response <br /><br />
**Type:** *PUT http://localhost:8080/runner/users* <br />
**Headers:** *AUTHORIZATION: BEARER 69991c4e2ffd44968e017d1d83e1a9ec* *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
{
  "user": {
    "email": "s@i.e",
    "password": "pa",
    "userName": "johnjacobsd",
    "bio":"acc",
    "image":"/image/ima"
  }
}
 ```
 **Response:**
 ```
{
    "bio": "acc",
    "image": "/image/ima",
    "userName": "johnjacobsd",
    "updatedBy": {
        "$oid": "5b891df4ad813c331cd8a7ab"
    }
}
 ```
 **Response Headers**
 ```
 Authorization → BEARER 8a2d6bdfd56b4a2a87009c1bbdca6c14
 ```
<br /> <br />

## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
