FROM openjdk:21-jdk-slim
COPY build/libs/notification-service-0.0.1-SNAPSHOT.jar .
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "bill-service:8082", "--timeout=110", "--", "java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]
