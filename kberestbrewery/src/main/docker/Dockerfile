FROM eclipse-temurin:25-jre-alpine AS builder
WORKDIR /application
ADD maven/${project.build.finalName}.jar /application/
RUN java -Djarmode=layertools -jar /application/${project.build.finalName}.jar extract

FROM eclipse-temurin:25-jre-alpine
WORKDIR /application

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /application/dependencies/ /application/
COPY --from=builder /application/spring-boot-loader/ /application/
COPY --from=builder /application/snapshot-dependencies/ /application/
COPY --from=builder /application/application/ /application/

ENV JAVA_OPTS=" -Xms512m -Xmx512m -Djava.security.egd=/dev/./urandom"

ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
