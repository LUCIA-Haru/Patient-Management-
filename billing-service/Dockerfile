FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Install protoc and verify installation
RUN apt-get update && apt-get install -y protobuf-compiler && \
    protoc --version

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src


# First compile proto files using installed protoc, then build
RUN mvn protobuf:compile -DprotocExecutable=/usr/bin/protoc && \
    mvn clean package -Dprotobuf.skip=true

RUN mvn clean package

FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java","-jar","app.jar"]

