# 🏨 Hotel Review App

A Spring Boot 3 application that allows users to create and review hotels. It is secured using **OAuth2 (Keycloak)** and uses **Elasticsearch** for storing and searching hotel/review data.

---

## 📦 Tech Stack

- Java 23
- Spring Boot 3.x
- Spring Security (OAuth2 Resource Server)
- Keycloak (as OAuth2 Authorization Server)
- Elasticsearch
- Maven
- Docker & Docker Compose
- MapStruct (for DTO mapping)
- Swagger (OpenAPI 3)

---

## 🚀 Features

- 🔐 User authentication with Keycloak
- 🏨 Create and fetch hotel information
- 📝 Post and view hotel reviews
- 📡 Full-text search using Elasticsearch
- 📘 API documentation with Swagger

---

## 🛠️ Prerequisites

Make sure you have the following installed:

- [Java 23](https://jdk.java.net/23/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/ajayInbox/reviewapp-elasticsearch.git
cd reviewapp-elasticsearch
```

### 2. run the docker compose

```bash
docker-compose up
```
this will start keycloak, elasticsearch and kibana servers

- ✅ **Keycloak** at [http://localhost:9090](http://localhost:9090)  
- ✅ **Elasticsearch** at [http://localhost:9200](http://localhost:9200)
- ✅ **Kibana** at [http://localhost:5601](http://localhost:5601)

### 3. run spring boot application
```bash
mvn clean install
mvn spring-boot:run
```

### Access the application on below URL
**Swagger Ui**  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html
)


## 📌 Notes

- Ensure you configure **roles and scopes** properly in **Keycloak** if needed.
- Token is needed for API call which can be generated with help of **Keycloak**.
- You can extend the app to support **hotel search** using **Elasticsearch full-text queries**.


