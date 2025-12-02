# --- Fase 1: Construcción (Build) ---
# Usamos una imagen oficial de Gradle que ya tiene el JDK 24.
# La versión 8.8.0 es la más reciente compatible.
FROM gradle:8.8.0-jdk21 AS builder

# Establecemos el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copiamos solo los archivos de build primero para aprovechar el caché de Docker.
# Esto hace que las futuras compilaciones sean más rápidas.
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copiamos el resto del código fuente de tu aplicación.
COPY src ./src

# Ejecutamos el comando de Gradle para construir la aplicación.
# Esto compilará el código y creará el archivo JAR ejecutable.
RUN gradle build --no-daemon


# --- Fase 2: Ejecución (Runtime) ---
# Usamos una imagen oficial de Java 24 mucho más ligera.
# 'slim' significa que solo tiene lo esencial para ejecutar, no para compilar.
FROM openjdk:21-slim

# Establecemos el directorio de trabajo.
WORKDIR /app

# Copiamos el archivo JAR que se construyó en la fase anterior desde la carpeta de build.
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponemos el puerto 8080. Esto le dice a Render que tu aplicación escucha en este puerto.
EXPOSE 8080

# El comando que se ejecutará cuando el contenedor inicie.
# Esto es equivalente a 'java -jar app.jar' en tu terminal.
ENTRYPOINT ["java", "-jar", "app.jar"]