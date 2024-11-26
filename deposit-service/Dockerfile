FROM openjdk:21-jdk-slim
EXPOSE 9999
COPY build/libs/deposit-service-0.0.1-SNAPSHOT.jar .
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "bill-service:8082", "--timeout=110", "--", "java", "-jar", "deposit-service-0.0.1-SNAPSHOT.jar"]