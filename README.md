#REST API URL Shortener

##Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Installation Guide](#installation-guide)
5. [Configuration](#configuration)
6. [API Documentation](#api-documentation)
	- [Authentication](#authentication)
	- [URL Operations](#url-operations)
	- [URL Statistics](#url-statistics)
7. [Security](#security)

- - -

###Introduction
The URL Shortener API is a RESTful web service designed to convert original URLs into shorter, unique ones and provide usage statistics. Users can generate short URLs, retrieve the original URLs, and view various statistics about their usage.

- - -

###Features

- User authentication and authorization using JWT.
- URL shortening and retrieval.
- URL statistics, including visit counts and active URLs.
- Role-based access control.

- - -

###Technologies Used

- **Java**
- **Spring Boot**
- **JWT** for authentication
- **OpenAPI 3.0** for API documentation
- **Maven**

- - -

###Installation Guide

Clone the repository:

```
git clone <repository-url>
cd <repository-folder>
```

Set up the database and configure credentials in application.properties.

Build and run the application:

```
mvn clean install
mvn spring-boot:run
```

Access the API version 1 at ``http://localhost:8080/api/v1`` and for version 2 ``http://localhost:8080/api/v2``.


In order to create and run an application together with a database in docker containers:

1. Select the prod profile in the Maven menu.

2. Clean the project via `mvn clean package`.

3. Execute in the terminal with one command:

```
	$env:DB_USERNAME="GoITgroup3User"
	$env:DB_PASSWORD="GoITgroup3Password"
	$env:JWT_SECRET_KEY="GoITgroup3JWTsecretKey"
	docker-compose up --build 
```

- - -

###Configuration
##### Global variables


- **DATASOURCE_URL** - *jdbc:postgresql://db:5432/shortener_db*
- **DB_USERNAME** - *GoITgroup3User*
- **DB_PASSWORD** - *GoITgroup3Password*
- **JWT_SECRET_KEY** - *GoITgroup3JWTsecretKey*

**Rest API is availible by following ports:**
default: `8080`
production: `9999`

- - -

###API Documentation

Open API documentation is availible by path `http://localhost:8080/swagger-ui/index.html` for the development server and by path `http://localhost:9999/swagger-ui/index.html` for the production server.

There is a switcher between version of API in documentation.

You may get an API documentation in *json* format by **GET** request to path `api/v1/docs` for the version 1 and to path `api/v2/docs` for the version 2.


***The API provides the following endpoints***

For more detailed information and the ability to test the API, you can go to the interactive documentation by paths stated above.
####Authentication

#####Register User

- **POST** `/auth/register`

- **Request Body:**

```
	{
  		"username": "string (5-50 characters)",
  		"password": "string (at least 8 characters, must include digits, uppercase, lowercase)"
	}
```

- **Response:**

```
	{
  		"message": "User successfully registered.",
  		"userId": 1,
  		"username": "exampleUser"
	}
```

- **Possible Errors:**

  - **400: Bad Request.**

  - **409: Conflict.**

##### Login User

- **POST** `/auth/login`

- **Request Body:**

```
	{
  		"username": "string",
  		"password": "string"
	}
```

- **Response:**

```
	{
  		"message": "User successfully authenticated.",
  		"token": "JWT token"
	}
```

- **Possible Errors:**

   - **401: Unauthorized.**

####URL Operations

#####Shorten URL

- **POST** `/url/shortFromLong`

- **Request Body:**

```
	{
  		"url": "original URL",
  		"expiresAt": "expiration date in ISO format"
	}
```

- **Response:**

```
	{
  		"shortUrl": "shortened URL",
  		"longUrl": "original URL",
  		"createdAt": "creation date",
  		"author": "username",
  		"message": "URL has been created successfully."
	}
```

- **Possible Errors:**

	- **400: Invalid URL or expiration date.**

	- **401: Unauthorized access.**

#####Retrieve Original URL

- **GET** `/url/longFromShort`

- **Request Body:**

```
	{
  		"url": "shortened URL"
	}
```

- **Response:**

```
	{
 		"longUrl": "original URL",
  		"shortUrl": "shortened URL",
  		"visits": 10,
  		"createdAt": "creation date",
  		"expiresAt": "expiration date"
}
```

- **Possible Errors:**

	- **400: Empty or null URL.**
	
	- **401: Unauthorized access.**

	- **404: URL not found.**

	- **410: URL expired.**

##### Update Shortened URL

- **PATCH** `/url/update`

- **Request Body:**

```
	{
  		"url": "shortened URL",
  		"expiresAt": "new expiration date"
	}
```

- **Response:**

```
	{
  		"message": "URL has been updated successfully."
	}
```

- **Possible Errors:**

	- **401: Unauthorized access.**

	- **404: URL not found.**

##### Delete URL

- **DELETE** `/url/delete`

- **Request Body:**

```
	{
  		"url": "shortened URL"
	}
```

- **Response:**

```
	{
  		"message": "URL has been deleted successfully."
	}
```

- **Possible Errors:**

	- **401: Unauthorized access.**

	- **404: URL not found.**

#### URL Statistics

##### Get All URLs for User

- **GET** `/url/stats/all`

- **Response:**

```
  	{
    	"shortUrl": "shortened URL",
    	"longUrl": "original URL",
    	"visits": 10,
    	"createdAt": "creation date",
    	"active": true
  	}
```

- **Possible Errors:**

	- **401: Unauthorized access.**

	- **404: No data found.**

##### Get Active URLs

- **GET** `/url/stats/active`

- **Response:**

```
  	{
    	"shortUrl": "shortened URL",
    	"longUrl": "original URL",
    	"visits": 5,
    	"createdAt": "creation date",
    	"active": true
  	}
```

- **Possible Errors:**

	- **401: Unauthorized access.**

	- **404: No data found.**

##### Get Visit Statistics

- **GET** `/url/stats/visits`

- **Request Body:**

```
	{
  		"url": "shortened URL"
	}
```

- **Response:**

```
	{
  		"visits": 10
	}
```

- **Possible Errors:**

	- **401: Unauthorized access.**

	- **404: URL not found.**

- - -

###Security

The API uses JWT tokens for authentication. Include the token in the Authorization header for secured endpoints:

```
Authorization: Bearer <token>
```

* * *


