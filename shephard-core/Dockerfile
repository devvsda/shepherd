FROM java:8

RUN ["uname", "-a"]

WORKDIR /code

ADD target/shephard-core-1.0-SNAPSHOT.jar /code
ADD scripts /code/scripts


EXPOSE 8080

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-cp", "shephard-core-1.0-SNAPSHOT.jar", "com.devsda.platform.shephardcore.application.ShephardApplication", "server", "scripts/dev-shephard-configuration.yaml"]
