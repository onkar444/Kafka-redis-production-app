# 🚀 Kafka–Redis Production Application

Enterprise-grade Spring Boot microservice demonstrating event-driven architecture using Apache Kafka and Redis, fully containerized with Docker Compose.

This project simulates a real-world production flow where:

- REST API receives client requests
- Messages are published to Kafka topics
- Consumers process events asynchronously
- Redis is used for high-performance caching / storage
- Infrastructure is containerized for local & production-like environments

---

## 📌 Architecture Overview
Client -> Spring Boot REST API -> Kafka Producer -> Kafka Broker -> Kafka Consumer -> Redis Cache / Storage

---

## 🏗 System Components

### 🟢 Application Layer
- Spring Boot REST API  
- Kafka Producer  
- Kafka Consumer  
- Redis Integration  

### 🟡 Messaging Layer
- Apache Kafka 7.5.0  
- Apache ZooKeeper 7.5.0  

### 🔴 Caching Layer
- Redis 7.2  

### 🐳 Containerization
- Docker  
- Docker Compose  

---

## 🧰 Technology Stack

| Layer | Technology |
|--------|------------|
| Backend | Java 17 |
| Framework | Spring Boot |
| Messaging | Apache Kafka |
| Coordination | Apache ZooKeeper |
| Caching | Redis |
| Build Tool | Maven |
| Containerization | Docker |

---

## 🚀 Getting Started

### 1️⃣ Clone Repository

```bash
git clone https://github.com/onkar444/Kafka-redis-production-app.git
cd Kafka-redis-production-app
```

2️⃣ Start Infrastructure (Kafka + Redis)
```
Navigate to the directory containing docker-compose.yml:

docker compose up -d

Verify containers:

docker ps

Expected services:

zookeeper (Port 2181)

kafka (Port 9092)

redis (Port 6379)
```

3️⃣ Run Spring Boot Application
Linux / Mac
```
./mvnw spring-boot:run
Windows
mvnw.cmd spring-boot:run
```
