FROM gradle:7.3.0-jdk8 as build

WORKDIR /home/gradle/project
ENV GRADLE_OPTS="-Dorg.gradle.vfs.watch=false -Dorg.gradle.daemon=false"

# Resolve dependencies
COPY *.gradle.kts ./
RUN gradle dependencies

# Build the app (includes check and test)
COPY . .
RUN gradle build

FROM openjdk:8-jre

WORKDIR /app

# Copy the DataDog agent and app jar into place
RUN wget -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'
COPY --from=build /home/gradle/project/build/libs/slack-service-*SNAPSHOT.jar slack-service.jar

# Allow CI/builder to pass in app version for env
ARG APP_VERSION
ENV APP_VERSION=$APP_VERSION

ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-jar", "/app/slack-service.jar"]
