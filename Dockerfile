FROM eclipse-temurin:21-jdk
ADD target/digital_bank.jar digital_bank.jar
ENTRYPOINT ["java", "-jar", "/digital_bank.jar"]
