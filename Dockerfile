FROM eclipse-temurin:21-jre
EXPOSE 8080
ADD target/milezero-0.0.1-SNAPSHOT.jar milezero-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "milezero-0.0.1-SNAPSHOT.jar"]