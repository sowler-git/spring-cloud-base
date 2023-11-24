FROM java:1.8
VOLUME /tmp
ADD order-module-1.0-SNAPSHOT.jar order-module.jar
ENTRYPOINT ["java","-Xms256m","-Xmx256m","-jar","/order-module.jar"]
