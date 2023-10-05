# Spring Batch 실습 프로젝트

# TECH-STACK
- Java 11
- Spring Boot 2.7.16
- Spring Batch 2.7.16
- Spring Data JPA 2.7.16
- Spring Data JDBC 2.7.16
- MySQL
- H2 DB
- Gradle 8.2.1
- Lombok 1.18.30
- JUnit5
- IntelliJ IDEA 2022.1.4 (Ultimate Edition)

## BUILD & RUN
- 특정 Job 실행 시 Program arguments 에 Job 이름 추가
  - `--spring.bathc.job.names=${Job 이름}`
- Window/Mac OS 에서 Build 한 파일이 있는 곳까지 명령프롬프트/터미널을 통해 접근해서 실행
  - `.gradlew bootJar`
  - `java -jar build/libs/${빌드한 파일}.jar --spring.batch.job.names=${Job 이름}`

## INFRASTRUCTURE
### Docker
- docker-compose.yml
- `docker-compse up -d`

<details>
<summary>docker-compose.yml 예시</summary>

```yml
version: '3'

services:
  mysql:
    container_name: mysql_house
    image: mysql/mysql-server:5.7
    environment:
      MYSQL_ROOT_HOST: '%'
      MYSQL_USER: "house"
      MYSQL_PASSWORD: "house"
      MYSQL_DATABASE: "house"
    ports:
      - "3305:3306"
    command:
      - "mysqld"
      - "--character-set-server=utf8mb4"
      - "--collation-server=utf8mb4_unicode_ci"
```
</details>