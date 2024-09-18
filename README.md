# Autopilot API


## Steps to Setup

**1. Clone the application**

```bash
https://github.com/Yosiak-alv/AutoPilotApi
```
Copy the example env file and make the required configuration changes in the .env file
```bash
 cp .env.example .env
```
**On docker**
first you need to create the jar file
```bash
.\mvnw.cmd clean package -DskipTests
```
Then you can build and run the app using docker compose
```bash
docker compose build
```
```bash
docker compose up
```
In this you will need to execute `import.sql` on postgresql database container.

**On local**

Just run the app with `spring.jpa.hibernate.ddl-auto=create`