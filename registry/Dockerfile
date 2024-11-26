FROM openjdk:21-jdk-slim
EXPOSE 8761
COPY build/libs/registry-0.0.1-SNAPSHOT.jar .
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "config-service:8001", "--timeout=70", "--", "java", "-jar", "registry-0.0.1-SNAPSHOT.jar"]