FROM openjdk:21-jdk-slim
EXPOSE 8989
COPY build/libs/gateway-0.0.1-SNAPSHOT.jar .
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "registry:8761", "--timeout=70", "--", "java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]