FROM openjdk:8 AS build

WORKDIR /usr/src/

RUN apt-get install -y git

RUN curl -s -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

RUN java -jar BuildTools.jar --rev latest

FROM openjdk:8

WORKDIR /usr/server/

COPY --from=build /usr/src/spigot*.jar ./spigot.jar

COPY ./* ./

EXPOSE 25565

CMD ["./run.sh"]