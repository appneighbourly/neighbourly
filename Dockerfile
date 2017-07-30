FROM cantara/alpine-openjdk-jdk8

WORKDIR /app

ADD environments/ /app/environments

ADD build/libs/neighbourly-0.0.1-all.jar /app/neighbourly.jar

CMD java -jar /app/neighbourly.jar server /app/environments/neighbourly-dev.yml

EXPOSE 8080