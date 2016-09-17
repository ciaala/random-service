FROM gcr.io/google_appengine/openjdk8
VOLUME /tmp

COPY random-service-0.1-jar-with-dependencies.jar app.jar
CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]