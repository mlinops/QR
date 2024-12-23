FROM openjdk:17-alpine

WORKDIR /app

COPY target /app/target

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/qr-vcard-app-1.0.0-RELEASE.jar"]