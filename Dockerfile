FROM openjdk:11-slim

VOLUME /tmp

EXPOSE 8080

ADD target/THC-1.0.0.jar .

ENTRYPOINT ["java", "-jar", "THC-1.0.0.jar"]