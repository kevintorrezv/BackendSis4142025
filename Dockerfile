# --- Fase 1: Construcción (Build) ---
FROM gradle:8.8-jdk21-alpine AS builder

WORKDIR /app

# Copiamos todo el proyecto al contenedor
# Hacemos esto primero para poder cambiar los permisos
COPY . .

# --- ¡LA LÍNEA CLAVE DE LA SOLUCIÓN! ---
# Damos permisos de ejecución al script gradlew dentro del entorno Linux de Docker.
RUN chmod +x ./gradlew

# Ahora ejecutamos el comando de build usando el wrapper para asegurar consistencia.
RUN ./gradlew build --no-daemon --build-cache


# --- Fase 2: Ejecución (Runtime) ---
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos solo el JAR compilado desde la fase de construcción.
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]