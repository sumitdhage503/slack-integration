plugins {
    id("org.springframework.boot") version "2.6.1"
    java
}

group = "com.ioffice"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

// Fix Log4j vulnerability https://spring.io/blog/2021/12/10/log4j2-vulnerability-and-spring-boot
extra["log4j2version"] = "2.15.0"

dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.1.6.RELEASE")
    implementation("com.slack.api:slack-api-client:1.12.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.70")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.1.6.RELEASE")
    implementation("com.mindscapehq:core:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.1.8.RELEASE")
}