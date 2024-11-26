FROM openjdk:21-jdk-slim
EXPOSE 8081
COPY build/libs/account-service-0.0.1-SNAPSHOT.jar .
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "gateway:8989", "--timeout=70", "--", "java", "-jar", "account-service-0.0.1-SNAPSHOT.jar"]