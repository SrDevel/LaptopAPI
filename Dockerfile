# Usar una imágen basecon JDK 17 y Maven 3.9.2
FROM maven:3.9.2-amazoncorretto-17

# Crear el directorio de trabajo
WORKDIR /app

# Copiar todos los archivos del proyecto al directorio del trabajo
COPY . /app

# Ejecutar el comando Maven para ejecutar el proyecto
RUN mvn cleanpackage

# Crear una imágen con JDK 17 amazon coretto
FROM amazoncorretto:17

# Exponemos el puerto 8080
EXPOSE 8080

# Copiar el archivo jar del proyecto al directorio de trabajo
COPY --from=build /app/target/tallerspringRest-0.0.1-SNAPSHOT.jar /app/apptallerspringRest-0.0.1-SNAPSHOT.jar

# Establecemos el punto de entrada para ejecutar el proyecto
ENTRYPOINT ["java", "-jar" , "/app/apptallerspringRest-0.0.1-SNAPSHOT.jar"]


