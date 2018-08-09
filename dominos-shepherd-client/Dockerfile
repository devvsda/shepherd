FROM java:8

RUN ["uname", "-a"]

WORKDIR /code

ADD target/dominos-shepherd-client-1.0-SNAPSHOT.jar /code

ADD configurations /code/configurations

RUN ls *

EXPOSE 8080

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-cp", "dominos-shepherd-client-1.0-SNAPSHOT.jar", "com.devsda.platform.shepherdclient.dominos.DominosApplication", "server", "configurations/dev-dominos-configuration.yaml"]
