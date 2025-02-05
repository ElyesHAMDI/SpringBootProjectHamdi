# Use the official maven/gradle image to build the application
# For Maven:
FROM maven:3.8.4-openjdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# For Gradle:
# FROM gradle:6.7.0-jdk11 AS build
# COPY --chown=gradle:gradle . /home/gradle/project
# WORKDIR /home/gradle/project
# RUN gradle build --no-daemon

# Use the official openjdk image to run the application
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/your-app.jar /usr/local/lib/your-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/your-app.jar"]
