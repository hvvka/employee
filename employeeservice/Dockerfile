FROM openjdk:11-jdk-slim-buster

RUN mkdir -p /home/app

EXPOSE 9000

ADD . /home/app
RUN cd /home/app && ./gradlew build -x test
ENTRYPOINT ["java","-jar","/home/app/build/libs/*.jar"]
