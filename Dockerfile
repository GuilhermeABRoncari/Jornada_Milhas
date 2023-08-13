FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

# Constrói o projeto usando o wrapper do Gradle
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# Segunda etapa: executar o aplicativo
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR construído na etapa anterior
# RUN find / -name jornada_milhas-0.0.1-SNAPSHOT.jar | xargs -I {} mv {} /app

COPY --from=build ../build/libs/jornada_milhas-0.0.1-SNAPSHOT.jar .

# Expõe a porta 8080
EXPOSE 8080

# Define o comando de entrada para iniciar o aplicativo
CMD ["java", "-jar", "jornada_milhas-0.0.1-SNAPSHOT.jar"]
