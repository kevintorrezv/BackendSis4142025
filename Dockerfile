# --- Fase 1: Construcción (Build) ---
FROM gradle:8.8-jdk21-alpine AS builder

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

# --- ¡LÍNEA MODIFICADA! Añadimos '--info' para obtener un log detallado ---
RUN ./gradlew build --info --no-daemon --build-cache


# --- Fase 2: Ejecución (Runtime) ---
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]