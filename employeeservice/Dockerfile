FROM openjdk:11-jdk-slim-buster

RUN mkdir -p /home/app

EXPOSE 9000

ADD . /home/app
RUN cd /home/app &&\
 ./gradlew build -x test &&\
 mv /home/app/build/libs/*.jar /home/app/build/libs/app.jar

ENTRYPOINT sleep 30 && java -Xmx512m -Xms256m -jar /home/app/build/libs/app.jar
