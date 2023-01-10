FROM openjdk:17-jdk-slim
MAINTAINER amit chandra <amitpchandra@gmail.com>
VOLUME /tmp
ADD target/stockservice-0.0.1-SNAPSHOT.jar stockservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","stockservice-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
EXPOSE 9091