# Imagen base de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Crear directorio de la aplicación
WORKDIR /app

# Copiar el pom y el código fuente
COPY . .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construir el proyecto con Maven (sin ejecutar tests)
RUN ./mvnw clean package -DskipTests

# Usar el JAR generado en target/
CMD ["java", "-jar", "target/gestion-proyectos-0.0.1-SNAPSHOT.jar"]


