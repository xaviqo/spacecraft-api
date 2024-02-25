FROM amazoncorretto:21 as builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn/wrapper/ .mvn/wrapper/

RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /app/target/spacecraftapi.jar .

CMD ["java", "-jar", "spacecraftapi.jar"]