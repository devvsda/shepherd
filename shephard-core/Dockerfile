FROM java:8

RUN ["uname", "-a"]

RUN apt-get update

RUN apt-get install -y maven

WORKDIR /code

COPY . /code

RUN ls /code/scripts

RUN ["mvn", "clean", "package"]

EXPOSE 8080

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-cp", "target/shephard-core-1.0-SNAPSHOT.jar", "com.devsda.platform.shephardcore.application.ShephardApplication", "server", "scripts/dev-shephard-configuration.yaml"]
