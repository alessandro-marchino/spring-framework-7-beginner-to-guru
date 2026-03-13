FROM eclipse-temurin:25-jre-alpine

ENV JAVA_OPTS=" -Xms512m -Xmx512m -Djava.security.egd=/dev/./urandom"
WORKDIR /application

COPY target/kberestbrewery-*.jar /application/kberestbrewery.jar

ENTRYPOINT [ "java", "-jar", "kberestbrewery.jar" ]
