# Primeira etapa: construir o projeto
FROM openjdk:17-jdk AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo build.gradle e settings.gradle para permitir o cache das dependências
COPY build.gradle.kts .
COPY build .
# COPY settings.gradle .

# Copia todos os outros arquivos do projeto
COPY . .

# Constrói o projeto usando o wrapper do Gradle
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# Segunda etapa: executar o aplicativo
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR construído na etapa anterior
COPY --from=build /app/build/libs/adopet-api-0.0.1-SNAPSHOT.jar .

# Expõe a porta 8080
EXPOSE 8080

# Define o comando de entrada para iniciar o aplicativo
CMD ["java", "-jar", "jornada_milhas-0.0.1-SNAPSHOT.jar"]
