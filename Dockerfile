FROM openjdk:11-jdk-slim-buster as builder

WORKDIR /build

RUN apt update && \
    apt install maven -y

COPY . .
RUN mvn clean package

FROM alpine:3.15.3

RUN apk update && \
    apk add openjdk11 && \
    apk add maven

WORKDIR /spring-boot
COPY --from=builder /build/target ./
ENTRYPOINT ["java", "-jar", "BookingHotel-1.0.jar"]