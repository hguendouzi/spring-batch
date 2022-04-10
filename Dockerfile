FROM maven:3.8.5-jdk-11 as build
LABEL maintainer ="Hicham"
WORKDIR /app
ADD ./ /app/
RUN mvn -version  \
&& mvn package

FROM tomcat
LABEL maintainer =" hicham"
COPY --from=build /app/target/spring-batch-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]