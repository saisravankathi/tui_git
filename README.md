
# Git Branches with Commit IDs

This Springboot project lets us fetch the details of all the user owned repositories along with their branches and last commit IDs.

# Technical Specifications

* Springboot 2.6.6
* [Github API_V3](https://developer.github.com/v3)
* [Scoop](https://scoop.sh/)(A command line installer for Windows, similar to brew in Mac)

# System setup requirements

* [JDK 11](https://jdk.java.net/11/)
* Internet Connection (for resolving dependencies & accessing Github APIs)
* install maven using `scoop install maven` for Windows users and `brew install maven` for Mac users

# Setup Process

* Clone or Fork [this](https://github.com/saisravankathi/tui_git) repository.
* `cd tui_git`, from cmd or terminal.
* `mvn install`
*  The dependencies will be resolved and the springboot application starts at port 8080 as a default port.
* Server will be up and running once the dependencies are resolved and the execution of tests are successful.


# Swagger

* Springfox Swagger 2.9.2 is used with Springboot to create Rest API documentation.
* [Swagger.json](http://localhost:8080/v2/api-docs), this can be uploaded in Swagger editor [online](https://editor.swagger.io/) to generate the Swagger UI to start testing the rest APIs.
* [Swagger UI](http://localhost:8080/swagger-ui.html) is accessible from the browser, which is integrated to Springboot.


# API Reference

#### Get all branches and commit IDs in their repositories for a given user.

```http
  POST /tui/all-user-info
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | **Required**. userId |
| `accept` | `header` |  `application/json`, `application/xml` |

#### Get Author based on the userID

```http
  GET /tui/author/${userId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | **Required**. userId |
| `accept` | `header` |  `application/json`, `application/xml` |


#### Get all owned repositories by an User.

```http
  GET /tui/repositories/${userId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | **Required**. userId |
| `accept` | `header` |  `application/json`, `application/xml` |


#### Get all branches of a repository of an user.

```http
  GET /tui/branches/${userId}/${repo}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | **Required**. userId |
| `repo` | `string` | **Required**. repo |
| `accept` | `header` |  `application/json`, `application/xml` |



## Docker Images

***Docker has to be installed as a pre-requisite before proceeding with the following steps**

To create an image for this project, execute the below commands from the root directory where Dockerfile is located.

```bash
  docker build -t tui_git/spring:deploy .
  docker run -p 8090:8080 tui_git/spring:deploy
```
The tag name `tui_git/spring` could be anything of your interest.
The Spring application will run on port 8090.

## Deployment (Continuous Integration)

To build this project automatically run

```bash
  docker run -p 80:8080  --restart always -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts-jdk11
```

This will create a local running instance of Jenkins running on port 80.

[Jenkins](http://localhost:80), it can be accessed from here and basic pipeline with SCM has to be created and docker credentials are to be created in the credentials manager to push the generated Images to the Docker Hub or AWS, from where the Image can be deployed.

