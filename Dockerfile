FROM openjdk:latest
LABEL authors="Maxim Kabanov"
COPY build/libs/kafka_message_reciever-1.0-SNAPSHOT.jar /
RUN chmod +x kafka_message_reciever-1.0-SNAPSHOT.jar
CMD ["java","-jar","kafka_message_reciever-1.0-SNAPSHOT.jar"]