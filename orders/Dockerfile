FROM maven:3-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:8-jdk
COPY --from=builder /app/target/orders-0.0.1-SNAPSHOT.jar ./ordermanager.jar
CMD java -jar ordermanager.jar

# to build/rebuild it:
# docker build --no-cache -t unict/ordermanager:0.0.1 -f Dockerfile .
# ALWAYS tag a build with a version!

# to run it:
# docker run -d --rm --name ordermanager -p 9080:9080 unict/ordermanager:0.0.1
# and ALWAYS include the desired tag!
