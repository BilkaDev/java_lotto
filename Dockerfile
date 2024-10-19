FROM eclipse-temurin:17
COPY /target/lotto.jar /lotto.jar
ENTRYPOINT ["java","-jar","/lotto.jar"]