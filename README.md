# Backend Technical Test 

The goal of this Backend Technical Test, is to build a REST API RESTful with Spring Boot which can manage several items described blow.

# API validation

The API must be able to create, read and modify resources (deletion is optional).
The deliverable must contains scenario documentation, giving the different API requests with their parameters. For each step of the scenario, there will be one API call (it can have two calls for step 3).

## Scenario validation

- 1- Create a message from “JérémieDurand” with the following content: “Hello, I have an issue with my new phone”.
- 2- Create a client case, with the client name “JérémieDurand”, and having the previously created message in its messages list.
- 3- Create a message from “Sonia Valentin”, with the following content: “I am Sonia, and I will do my best to help you. What is your phone brand and model?”. This message will be linked to the previously created client case.
- 4- Modification of the client case adding the client reference “KA-18B6”. This will validate the client case modification feature.
- 5- Fetching of all client cases. The result will only contains one client case, the one we created before.
It is not mandatory to use a database to persist data after creation/modification, but it must be persisted somewhere (it can be in memory).

# Expected deliverable format

Two possible options :
- A .zip file containing all elements needed to generate a .jar package with the documentation needed to generate this package. The zip file must weigh less than 100 KB. Be careful: the mail servers may reject emails containing .jar files, thus they must be removed from the deliverable.
- A link to a Gitrepository: Githubor Gitlab, with possibility to be cloned.
The application should be run with the command line “java –jar <package name >.jar” (like a Spring Boot web application).

# ER- Diagram
![image](https://github.com/abderrahim-fouili/customer-experience-services/assets/117549346/49e0072a-789a-4958-8eee-9caea9e5ecc0)

# Project Structure:

### Main folders

The project follows a standard spring project structure, is decomposed into multiple folders:
- **api**: contains different server Rest Controller, defining the various endpoints.
- **config**: contains project configuration like: openApi, security and web config.
- **domain**: contains the app's business objects.
- **dto**: contains objects representing requests and responses.
- **exception**: contains objects used in exceptions management.
- **mapper**: contains the app's mapper classes.
- **repository**: contains the database communication layer.
- **service**: contains the service layer and implementation.
- **util**: contains utility classes.
- **util/constants**: contains constants classes.

### Test folders

All Test classes are under `/src/main/test`:

- **api**: contains unit testing for API layer.
- **repository**: contains Unit testing for repository layer.
- **service**: contains Unit testing for service layer.

# Deliverable

Two possible options :

- th jar is availible from this link ==> https://drive.google.com/file/d/1QkmmTlPT7FJ-lxQIcU4I5UQec3u7C72p/view?usp=drive_link
- clone the project from develop branch  ==> git clone https://github.com/abderrahim-fouili/customer-experience-services.git
     
# Build & Run application

## Requirements
* [Open JDK-17+]
* [Maven 3]
* [Spring Tool Suite IDE]
       - Mandatory Plugins: Lombok
> Make sure you'r using OpenJDK-11, check your java version with : "java -version".

- To build the project locally, run the following command: ==> mvn clean install
- To run the application locally, run the following command: ==> java –jar customer-experience-services-0.0.1-SNAPSHOT.jar

# API Root Endpoint with Swagger
http://localhost:8080/swagger-ui/index.html

# Endpoints validation

- 1- Create a message from “JérémieDurand” with the following content: “Hello, I have an issue with my new phone”.

```
> POST : /v1/customer-experience/services/messages/add-message

- Request body
{
  "sender": "Jérémie Durand",
  "content": "Hello, I have an issue with my new phone",
  "flag": "Client"
}

- Response body
{
  "messageId": 1,
  "clientCase": null,
  "sender": "Jérémie Durand",
  "content": "Hello, I have an issue with my new phone",
  "flag": "Client",
  "createdAt": "2024-02-26T11:21:02.566+00:00"
}
```

- 2- Create a client case, with the client name “JérémieDurand”, and having the previously created message in its messages list.

```
> POST /v1/customer-experience/services/client-cases/add-client-case

- Request body
{
  "name": "Jérémie Durand"
}

- Response body
{
"clientCaseId": 1,
"name": "Jérémie Durand",
"messages": [
{
"messageId": 1,
"sender": "Jérémie Durand",
"content": "Hello, I have an issue with my new phone",
"flag": "Client",
"createdAt": "2024-02-26T11:21:02.566+00:00"
}
],
"clientReference": null
}

```

- 3- Create a message from “Sonia Valentin”, with the following content: “I am Sonia, and I will do my best to help you. What is your phone brand and model?”. This message will be linked to the previously created client case.

```
> POST /v1/customer-experience/services/messages/add-message

- Request body
{
  "sender": "Sonia Valentin",
  "content": "I am Sonia, and I will do my best to help you. What is your phone brand and model?",
  "flag": "Assistant"
}

- Response body
[
{
"messageId": 1,
"clientCase": {
"clientCaseId": 1,
"name": "Jérémie Durand",
"clientReference": null
},
"sender": "Jérémie Durand",
"content": "Hello, I have an issue with my new phone",
"flag": "Client",
"createdAt": "2024-02-26T11:21:02.566+00:00"
},
{
"messageId": 2,
"clientCase": null,
"sender": "Sonia Valentin",
"content": "I am Sonia, and I will do my best to help you. What is your phone brand and model?",
"flag": "Assistant",
"createdAt": "2024-02-26T11:21:02.566+00:00"
}
]

> PATCH /v1/customer-experience/services/client-cases/{clientCaseId}/add-message/{messageId}

- Parameters =>  clientCaseId : 1 & messageId : 2

- Response body
[
{
"clientCaseId": 1,
"name": "Jérémie Durand",
"messages": [
{
"messageId": 1,
"sender": "Jérémie Durand",
"content": "Hello, I have an issue with my new phone",
"flag": "Client",
"createdAt": "2024-02-26T11:21:02.566+00:00"
},
{
"messageId": 2,
"sender": "Sonia Valentin",
"content": "I am Sonia, and I will do my best to help you. What is your phone brand and model?",
"flag": "Assistant",
"createdAt": "2024-02-26T11:21:02.566+00:00"
}
],
"clientReference": ""
}
]
```

- 4- Modification of the client case adding the client reference “KA-18B6”. This will validate the client case modification feature.

```
> PATCH /v1/customer-experience/services/client-cases/update-client-case/{clientCaseId}

- Parameters =>  clientCaseId : 1

- Request body
{
  "clientReference": "KA-18B6"
}

- Response body
[
{
"clientCaseId": 1,
"name": "Jérémie Durand",
"messages": [
{
"messageId": 1,
"sender": "Jérémie Durand",
"content": "Hello, I have an issue with my new phone",
"flag": "Client",
"createdAt": "2024-02-26T11:21:02.566+00:00"
},
{
"messageId": 2,
"sender": "Sonia Valentin",
"content": "I am Sonia, and I will do my best to help you. What is your phone brand and model?",
"flag": "Assistant",
"createdAt": "2024-02-26T11:21:02.566+00:00"
}
],
"clientReference": "KA-18B6"
}
]
```

- 5- Fetching of all client cases. The result will only contains one client case, the one we created before.

```
> GET /v1/customer-experience/services/client-cases

- Response body
[
{
"clientCaseId": 1,
"name": "Jérémie Durand",
"messages": [
{
"messageId": 1,
"sender": "Jérémie Durand",
"content": "Hello, I have an issue with my new phone",
"flag": "Client",
"createdAt": "2024-02-26T11:21:02.566+00:00"
},
{
"messageId": 2,
"sender": "Sonia Valentin",
"content": "I am Sonia, and I will do my best to help you. What is your phone brand and model?",
"flag": "Assistant",
"createdAt": "2024-02-26T11:21:02.566+00:00"
}
],
"clientReference": "KA-18B6"
}
]
```

