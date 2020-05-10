FROM adoptopenjdk:14-jre-hotspot
RUN mkdir /opt/app
ADD target/locations-app-client.jar /opt/app/locations-app-client.jar
CMD ["java", "-jar", "/opt/app/locations-app-client.jar"]
