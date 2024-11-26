FROM openjdk:21-jdk-slim
EXPOSE 8082
COPY build/libs/bill-service-0.0.1-SNAPSHOT.jar .
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "account-service:8081", "--timeout=90", "--", "java", "-jar", "bill-service-0.0.1-SNAPSHOT.jar"]