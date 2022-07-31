FROM openjdk:11

COPY questionapp-0.0.1-SNAPSHOT.jar questionapp.jar

ENTRYPOINT ["java","-jar","/questionapp.jar"]